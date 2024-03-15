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
import com.anik.capstone.home.HomeActivity;
import com.anik.capstone.manualInput.ManualInputFragment;
import com.google.common.util.concurrent.ListenableFuture;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class AddNewBookFragment extends Fragment implements BookDataListener {
    private AddNewBookViewModel addNewBookViewModel;
    private FragmentAddNewBookBinding fragmentAddNewBookBinding;
    private PreviewView previewView;
    private ExecutorService cameraExecutor;
    private ActivityResultLauncher<String> requestPermissionLauncher =
            registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> {
                if (isGranted) {
                    startCamera();
                } else {
                    ((HomeActivity) requireActivity()).replaceFragment(ManualInputFragment.newInstance());
                }
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
        previewView = fragmentAddNewBookBinding.previewView;
        if (!addNewBookViewModel.isFirstTimeCameraPermission() && !addNewBookViewModel.hasCameraPermission()) {
            ((HomeActivity) requireActivity()).replaceFragment(ManualInputFragment.newInstance());
        } else if (!addNewBookViewModel.isFirstTimeCameraPermission() && addNewBookViewModel.hasCameraPermission()) {
            startCamera();
        } else {
            addNewBookViewModel.setFirstTimeCameraPermission(false);
            requestPermissionLauncher.launch(Manifest.permission.CAMERA);
        }
        fragmentAddNewBookBinding.manualInputButton.setOnClickListener(v -> {
            ((HomeActivity) requireActivity()).replaceFragment(ManualInputFragment.newInstance());
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        startCamera();
    }

    @Override
    public void onPause() {
        super.onPause();
        stopCamera();
    }

    private void startCamera() {
        cameraExecutor = Executors.newSingleThreadExecutor();
        ListenableFuture<ProcessCameraProvider> cameraProviderFuture = ProcessCameraProvider.getInstance(requireContext());
        cameraProviderFuture.addListener(() -> {
            try {
                ProcessCameraProvider cameraProvider = cameraProviderFuture.get();
                Preview preview = new Preview.Builder().build();
                CameraSelector cameraSelector = new CameraSelector.Builder().requireLensFacing(CameraSelector.LENS_FACING_BACK).build();
                preview.setSurfaceProvider(previewView.getSurfaceProvider());

                ImageAnalysis imageAnalysis = new ImageAnalysis.Builder().setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST).build();
                BarcodeAnalyzer barcodeAnalyzer = new BarcodeAnalyzer();
                barcodeAnalyzer.setBookDataListener(this);
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
    public void onBookDataReceived(String isbn) {
        ((HomeActivity) requireActivity()).replaceFragment(BookDetailsFragment.newInstance(isbn));

    }
}