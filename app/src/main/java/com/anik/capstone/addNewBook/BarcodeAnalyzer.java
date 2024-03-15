package com.anik.capstone.addNewBook;

import android.graphics.ImageFormat;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.OptIn;
import androidx.camera.core.ExperimentalGetImage;
import androidx.camera.core.ImageAnalysis;
import androidx.camera.core.ImageProxy;

import com.google.mlkit.vision.barcode.BarcodeScanner;
import com.google.mlkit.vision.barcode.BarcodeScannerOptions;
import com.google.mlkit.vision.barcode.BarcodeScanning;
import com.google.mlkit.vision.barcode.common.Barcode;
import com.google.mlkit.vision.common.InputImage;

public class BarcodeAnalyzer implements ImageAnalysis.Analyzer {
    private BookDataListener listener;
    private String ISBN;

    public void setBookDataListener(BookDataListener listener) {
        this.listener = listener;
    }

    @Override
    @OptIn(markerClass = ExperimentalGetImage.class)
    public void analyze(@NonNull ImageProxy imageProxy) {
        try {
            if (imageProxy.getImage() == null) {
                return;
            }
            if (imageProxy.getFormat() != ImageFormat.YUV_420_888) {
                Log.e("BarcodeAnalyzer", "Invalid image format: " + imageProxy.getFormat());
                return;
            }
            InputImage inputImage = InputImage.fromMediaImage(imageProxy.getImage(),
                    imageProxy.getImageInfo().getRotationDegrees());
            BarcodeScannerOptions options =
                    new BarcodeScannerOptions.Builder()
                            .setBarcodeFormats(
                                    Barcode.FORMAT_EAN_13,
                                    Barcode.FORMAT_EAN_8,
                                    Barcode.FORMAT_QR_CODE)
                            .build();
            BarcodeScanner scanner = BarcodeScanning.getClient(options);
            scanner.process(inputImage)
                    .addOnSuccessListener(barcodes -> {
                        for (Barcode barcode : barcodes) {
                            ISBN = barcode.getRawValue();
                            if (listener != null) {
                                listener.onBookDataReceived(ISBN);
                            }
                            break;
                        }
                    })
                    .addOnFailureListener(e -> Log.e("BarcodeScanner", "Barcode scanning failed: " + e.getMessage()));
        } finally {
            imageProxy.close();
        }
    }


}
