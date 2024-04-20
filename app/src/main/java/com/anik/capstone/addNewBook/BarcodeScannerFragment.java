package com.anik.capstone.addNewBook;

import android.Manifest;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.ImageAnalysis;
import androidx.camera.core.Preview;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.camera.view.PreviewView;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.anik.capstone.R;
import com.anik.capstone.bookDetails.BookDetailsFragment;
import com.anik.capstone.bookDetails.SearchType;
import com.anik.capstone.databinding.FragmentBarcodeScannerBinding;
import com.anik.capstone.home.HomeActivity;
import com.google.common.util.concurrent.ListenableFuture;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class BarcodeScannerFragment extends Fragment implements BarcodeAnalyzer.BarcodeDataListener {

    private BarcodeScannerViewModel barcodeScannerViewModel;

    private final ActivityResultLauncher<String> requestPermissionLauncher =
            registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted ->
                    barcodeScannerViewModel.onReceivedPermissionResult(isGranted));

    private FragmentBarcodeScannerBinding barcodeScannerBinding;
    private PreviewView previewView;
    private ExecutorService cameraExecutor;
    private ListenableFuture<ProcessCameraProvider> cameraProviderFuture;
    private CameraSelector cameraSelector;
    private Preview preview;
    private ImageAnalysis imageAnalysis;
    private BarcodeAnalyzer barcodeAnalyzer;

    public static BarcodeScannerFragment newInstance() {
        return new BarcodeScannerFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        barcodeScannerBinding = FragmentBarcodeScannerBinding.inflate(inflater, container, false);
        return barcodeScannerBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        barcodeScannerBinding.setLifecycleOwner(this);
        barcodeScannerViewModel = new ViewModelProvider(this).get(BarcodeScannerViewModel.class);
        barcodeScannerViewModel.init();

        previewView = barcodeScannerBinding.previewView;

        cameraExecutor = Executors.newSingleThreadExecutor();
        cameraProviderFuture = ProcessCameraProvider.getInstance(requireContext());
        preview = new Preview.Builder().build();
        cameraSelector = new CameraSelector.Builder().requireLensFacing(CameraSelector.LENS_FACING_BACK).build();
        imageAnalysis = new ImageAnalysis.Builder().setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST).build();
        barcodeAnalyzer = new BarcodeAnalyzer(this);

        barcodeScannerBinding.manualInputButton.setOnClickListener(v -> barcodeScannerViewModel.onManualInputButtonClicked());

        barcodeScannerViewModel.permissionRequest.observe(getViewLifecycleOwner(), permissionRequest -> {
            if (permissionRequest) {
                requestPermissionLauncher.launch(Manifest.permission.CAMERA);
            }
        });
        barcodeScannerViewModel.cameraStart.observe(getViewLifecycleOwner(), cameraStart -> startCamera());
        barcodeScannerViewModel.nextScreen.observe(getViewLifecycleOwner(), nextScreenData -> {
            int fragmentId = 0;
            String data = null;
            switch (nextScreenData.getNextScreen()) {
                case MANUAL_INPUT: {
                    fragmentId = R.id.manualInputFragment;
                    break;
                }
                case BOOK_DETAILS: {
                    fragmentId = R.id.bookDetailsFragment;
                    data = nextScreenData.getData();
                    break;
                }
            }
            ((HomeActivity) requireActivity()).navigateTo(fragmentId, data, true, SearchType.SEARCH_BY_ISBN);
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        startCamera();
    }

    @Override
    public void onStop() {
        super.onStop();
        stopCamera();
    }

    private void startCamera() {
        cameraProviderFuture.addListener(() -> {
            try {
                ProcessCameraProvider cameraProvider = cameraProviderFuture.get();
                preview.setSurfaceProvider(previewView.getSurfaceProvider());
                imageAnalysis.setAnalyzer(cameraExecutor, barcodeAnalyzer);
                cameraProvider.unbindAll();
                cameraProvider.bindToLifecycle(this, cameraSelector, preview, imageAnalysis);
            } catch (ExecutionException | InterruptedException e) {
                Log.e("BarcodeScanner", "Error starting camera: " + e.getMessage());
            }
        }, ContextCompat.getMainExecutor(requireContext()));
    }

    private void stopCamera() {
        if (cameraExecutor != null) {
            cameraExecutor.shutdown();
            cameraExecutor = null;
        }
    }

    @Override
    public void onBarcodeDataReceived(String barcodeData) {
        barcodeScannerViewModel.onBarcodeDataReceived(barcodeData);
    }
}