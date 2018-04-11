package com.passinhotv.android;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.zxing.Result;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidParameterSpecException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class TransferActivity extends AppCompatActivity implements View.OnClickListener, ZXingScannerView.ResultHandler {
    Button btn_qr, btn_continue, btn_back, btn_chat;
    EditText et_waves, et_reais, et_desc, et_address, et_fee;
    TextView tx_feavelas, tx_reais, tx_option;
    Dialog dialog;

    String strAddressTo = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_transfer);
        initView();
        ActivityCompat.requestPermissions(this,
                new String[]{android.Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE},
                1);
    }

    public void initView() {
        btn_qr = findViewById(R.id.btn_scan_qr);
        btn_continue = findViewById(R.id.btn_continue);
        btn_back = findViewById(R.id.btn_back);
        btn_chat = findViewById(R.id.btn_chat);
        btn_qr.setOnClickListener(this);
        btn_continue.setOnClickListener(this);
        btn_back.setOnClickListener(this);
        btn_chat.setOnClickListener(this);

        tx_feavelas = findViewById(R.id.tx_favelas);
        tx_reais = findViewById(R.id.tx_reais);
        tx_option = findViewById(R.id.tx_option);
        String strCrie = "<font color='white'>As Transações Enviadas <font color='red'>NÃO</font> São Reversíveis.</font>";
        tx_option.setText(Html.fromHtml(strCrie), TextView.BufferType.SPANNABLE);
        et_waves = findViewById(R.id.edt_amount);
        et_address = findViewById(R.id.edt_address);
        et_desc = findViewById(R.id.edt_desc);
        et_reais = findViewById(R.id.edt_converted);
        et_fee = findViewById(R.id.edt_tax);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_back:
                this.finish();
                break;
            case R.id.btn_continue:
                showDialog(this);
                break;
            case R.id.btn_chat:
                break;
            case R.id.btn_scan_qr:
                Intent intent = new Intent(TransferActivity.this, ScanQRActivity.class);
                startActivityForResult(intent, 100);
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (requestCode == 0) {
            if (resultCode == RESULT_OK) {
                // Handle successful scan
            } else if (resultCode == RESULT_CANCELED) {
                // Handle cancel
            }
        }
        if (requestCode == 100) {
            if (resultCode == RESULT_OK) {
                strAddressTo = intent.getStringExtra("address");
                try {
                    strAddressTo = GlobalVar.decryptMsg(strAddressTo);
                } catch (NoSuchPaddingException e) {
                    e.printStackTrace();
                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                } catch (InvalidParameterSpecException e) {
                    e.printStackTrace();
                } catch (InvalidAlgorithmParameterException e) {
                    e.printStackTrace();
                } catch (InvalidKeyException e) {
                    e.printStackTrace();
                } catch (BadPaddingException e) {
                    e.printStackTrace();
                } catch (IllegalBlockSizeException e) {
                    e.printStackTrace();
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }catch (Exception e){}
                et_address.setText(strAddressTo);
            }
        }
    }

    public void showDialog(Activity activity) {
        dialog = new Dialog(activity);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog_transfer);
        Button btn_cancel = dialog.findViewById(R.id.btn_back);
        Button btn_confirm = dialog.findViewById(R.id.btn_confirm);
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        btn_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                doTransfer();
            }
        });
        dialog.show();
    }

    public void doTransfer() {
        dialog.dismiss();
    }

    @Override
    public void handleResult(Result result) {

    }
}
