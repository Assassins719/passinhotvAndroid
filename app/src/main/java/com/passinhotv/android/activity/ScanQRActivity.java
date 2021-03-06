package com.passinhotv.android.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;
import com.google.zxing.Result;
import com.passinhotv.android.R;

import java.io.FileNotFoundException;
import java.io.InputStream;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class ScanQRActivity extends AppCompatActivity implements ZXingScannerView.ResultHandler{
    private ZXingScannerView mScannerView;
    Button btn_flash, btn_img;
    boolean isOn = false;
    private static int RESULT_LOAD_IMAGE = 1;
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_qr);
        ViewGroup contentFrame = (ViewGroup) findViewById(R.id.content_frame);
        mScannerView = new ZXingScannerView(this);
        contentFrame.addView(mScannerView);

        btn_flash = (Button) findViewById(R.id.btn_flash);
        btn_img = (Button) findViewById(R.id.btn_file);

        btn_flash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isOn = !isOn;
                mScannerView.setFlash(isOn);
                if (isOn)
                    btn_flash.setBackgroundResource(R.drawable.on);
                else
                    btn_flash.setBackgroundResource(R.drawable.off);
            }
        });

        btn_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                photoPickerIntent.setType("image/*");
                startActivityForResult(photoPickerIntent, RESULT_LOAD_IMAGE);
            }
        });

        ActivityCompat.requestPermissions(this,
                new String[]{ android.Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE },
                1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
            try {
                final Uri imageUri = data.getData();
                final InputStream imageStream;
                imageStream = getContentResolver().openInputStream(imageUri);
                final Bitmap myBitmap = BitmapFactory.decodeStream(imageStream);
                BarcodeDetector detector = new BarcodeDetector.Builder(getApplicationContext()).setBarcodeFormats( Barcode.QR_CODE ).build();
                if(!detector.isOperational()){
                    return;
                }
                Frame frame = new Frame.Builder().setBitmap(myBitmap).build();
                SparseArray<Barcode> barcodes = detector.detect(frame);
                if(barcodes.size() > 0)
                {
                    Barcode thisCode = barcodes.valueAt(0);
                    Uri uri = Uri.parse("http://www.google.com/#q=" + thisCode.rawValue);
                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                    startActivity(intent);
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                } else {
                    Toast.makeText(ScanQRActivity.this, "Permission denied to read your External storage", Toast.LENGTH_SHORT).show();
                }
                return;
            }
        }
    }
    @Override
    public void onResume() {
        super.onResume();
        mScannerView.setResultHandler(this);
        mScannerView.startCamera();
    }

    @Override
    public void onPause() {
        super.onPause();
        mScannerView.stopCamera();
    }

    @Override
    public void handleResult(Result rawResult) {
        Intent data = new Intent();
        String strAddress = rawResult.getText();
        data.putExtra("address",strAddress);
        setResult(RESULT_OK, data);
        finish();
    }
}
