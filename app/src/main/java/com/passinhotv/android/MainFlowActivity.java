package com.passinhotv.android;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.passinhotv.android.ui.auth.LoginActivity;
import com.passinhotv.android.ui.auth.Welcome_Activity;

import static android.graphics.Color.BLACK;
import static android.graphics.Color.TRANSPARENT;
import static android.graphics.Color.WHITE;

public class MainFlowActivity extends AppCompatActivity implements View.OnClickListener {
    Button btn_qr, btn_updte;
    Bitmap bmp_qr= null;
    ProgressDialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main_flow);
        initView();
    }

    public void initView() {
        dialog = ProgressDialog.show(MainFlowActivity.this, "",
                "Please wait...", true);
        new Thread(new Runnable() {
            public void run(){
                String strAddress = GlobalVar.strAddressEncrypted;
                try {
                    bmp_qr = encodeAsBitmap(strAddress);
                    dialog.dismiss();
                } catch (WriterException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        btn_qr = findViewById(R.id.btn_qr);
        btn_updte = findViewById(R.id.btn_update);
        btn_qr.setOnClickListener(this);
        btn_updte.setOnClickListener(this);
    }
    Bitmap encodeAsBitmap(String str) throws WriterException {
        BitMatrix result;
        int WIDTH = 500;
        try {
            result = new MultiFormatWriter().encode(str,
                    BarcodeFormat.QR_CODE, WIDTH, WIDTH, null);
        } catch (IllegalArgumentException iae) {
            // Unsupported format
            return null;
        }
        int w = result.getWidth();
        int h = result.getHeight();
        int[] pixels = new int[w * h];
        for (int y = 0; y < h; y++) {
            int offset = y * w;
            for (int x = 0; x < w; x++) {
                pixels[offset + x] = result.get(x, y) ? TRANSPARENT: getResources().getColor(R.color.color_green);
            }
        }
        Bitmap bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_4444);
        bitmap.setPixels(pixels, 0, WIDTH, 0, 0, w, h);
        return bitmap;
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_qr:
                showDialog(this);
                break;
            case R.id.btn_update:
                Intent intent = new Intent(MainFlowActivity.this, BoxActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }


    public void showDialog(Activity activity) {
        final Dialog dialog = new Dialog(activity);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog_qrcode);
        LinearLayout lyt_dlg = dialog.findViewById(R.id.lyt_qr);
        ImageView img_qr = dialog.findViewById(R.id.img_qr);
        img_qr.setImageBitmap(bmp_qr);
        lyt_dlg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }
}
