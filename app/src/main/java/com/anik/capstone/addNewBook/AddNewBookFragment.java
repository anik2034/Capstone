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

import com.anik.capstone.bookDetails.BookDetailsFragment;
import com.anik.capstone.databinding.FragmentAddNewBookBinding;
import com.anik.capstone.home.DisplayType;
import com.anik.capstone.home.HomeActivity;
import com.anik.capstone.manualInput.ManualInputFragment;
import com.google.common.util.concurrent.ListenableFuture;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class AddNewBookFragment extends Fragment implements BarcodeAnalyzer.BarcodeDataListener {
    private AddNewBookViewModel addNewBookViewModel;
    private FragmentAddNewBookBinding fragmentAddNewBookBinding;

    private String barcodeDataReceived;
    private PreviewView previewView;

    private ExecutorService cameraExecutor;
    private ListenableFuture<ProcessCameraProvider> cameraProviderFuture;
    private CameraSelector cameraSelector;
    private Preview preview;
    private ImageAnalysis imageAnalysis;
    private BarcodeAnalyzer barcodeAnalyzer;

    private ActivityResultLauncher<String> requestPermissionLauncher =
            registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> {
                addNewBookViewModel.checkCameraPermissions();
                addNewBookViewModel.cameraStart.observe(getViewLifecycleOwner(), cameraStart -> {
                    if (cameraStart) {
                        startCamera();
                    } else {
                        addNewBookViewModel.setNextScreen(DisplayType.MANUAL_INPUT);
                    }
                });
                addNewBookViewModel.nextScreen.observe(getViewLifecycleOwner(), nextScreen -> {
                    switch (nextScreen) {
                        case MANUAL_INPUT: {
                            ((HomeActivity) requireActivity()).replaceFragment(ManualInputFragment.newInstance());
                            break;
                        }
                        case BOOK_DETAILS: {
                            ((HomeActivity) requireActivity()).replaceFragment(BookDetailsFragment.newInstance(barcodeDataReceived));
                            break;
                        }
                    }
                });
            });

    public static AddNewBookFragment newInstance() {
        return new AddNewBookFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        fragmentAddNewBookBinding = FragmentAddNewBookBinding.inflate(inflater, container, false);
        return fragmentAddNewBookBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        fragmentAddNewBookBinding.setLifecycleOwner(this);
        addNewBookViewModel = new ViewModelProvider(this).get(AddNewBookViewModel.class);
        addNewBookViewModel.init();

        previewView = fragmentAddNewBookBinding.previewView;

        cameraExecutor = Executors.newSingleThreadExecutor();
        cameraProviderFuture = ProcessCameraProvider.getInstance(requireContext());
        preview = new Preview.Builder().build();
        cameraSelector = new CameraSelector.Builder().requireLensFacing(CameraSelector.LENS_FACING_BACK).build();
        imageAnalysis = new ImageAnalysis.Builder().setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST).build();
        barcodeAnalyzer = new BarcodeAnalyzer(this);

        addNewBookViewModel.permissionRequest.observe(getViewLifecycleOwner(), permissionRequest -> {
            if (permissionRequest) {
                requestPermissionLauncher.launch(Manifest.permission.CAMERA);
            }
        });
        fragmentAddNewBookBinding.manualInputButton.setOnClickListener(v -> {
            addNewBookViewModel.setNextScreen(DisplayType.MANUAL_INPUT);
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
    public void onBarcodeDataReceived(String barcodeDataReceived) {
        this.barcodeDataReceived = barcodeDataReceived;
        addNewBookViewModel.setNextScreen(DisplayType.BOOK_DETAILS);
    }
}