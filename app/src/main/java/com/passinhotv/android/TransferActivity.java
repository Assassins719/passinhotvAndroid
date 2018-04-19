package com.passinhotv.android;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.zxing.Result;
import com.passinhotv.android.request.TransferTransactionRequest;
import com.passinhotv.android.util.AddressUtil;
import com.wavesplatform.wavesj.PrivateKeyAccount;
import com.wavesplatform.wavesj.Transaction;

import org.json.JSONException;
import org.json.JSONObject;

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
    String strDesc = "";
    long balance, amount;
    long customFee = (long) 2000000;

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
//        Node node = new Node();
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
                checkSend();
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

    public void checkSend(){
        strAddressTo = String.valueOf(et_address.getText());
        strDesc = String.valueOf(et_desc.getText());
        try {
            amount = (long) (Float.parseFloat(String.valueOf(et_waves.getText())) * 100000000);
        }catch (Exception e){

        }
//        customFee = 2000000;
        int res = validateTransfer();
        if (res == 0) {
            showDialog(this);
        } else {
            Toast.makeText(getApplicationContext(),res,Toast.LENGTH_SHORT).show();
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

    @SuppressLint("CheckResult")
    public void doTransfer() {
        dialog.dismiss();
        long timestamp = System.currentTimeMillis();

        TransferTransactionRequest tx = new TransferTransactionRequest(
                GlobalVar.assetID,
                GlobalVar.strPublic,
                strAddressTo, amount, timestamp, customFee, strDesc);
        PrivateKeyAccount mTemp = PrivateKeyAccount.fromPrivateKey(GlobalVar.strPrivate, 'N');
        byte[] mbyte = mTemp.getPrivateKey();
        tx.sign(mbyte);
        String strSignature = tx.getSignature();
        Log.d("Sign","Sign");

        RequestQueue requestQueue = Volley.newRequestQueue(this);
            String URL = "http://207.148.29.110:9069/assets/broadcast/transfer";
            JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("senderPublicKey", GlobalVar.strPublic);
            jsonBody.put("assetId", GlobalVar.assetID);
            jsonBody.put("recipient", strAddressTo);
            jsonBody.put("amount", amount);
            jsonBody.put("fee", customFee);
            jsonBody.put("feeAssetId", GlobalVar.assetID);
            jsonBody.put("timestamp", timestamp);
            jsonBody.put("attachment", strDesc);
            jsonBody.put("signature", strSignature);
        } catch (JSONException e) {
            e.printStackTrace();
        }
            final String requestBody = jsonBody.toString();

            StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.i("VOLLEY", response);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("VOLLEY", error.toString());
                }
            }) {
                @Override
                public String getBodyContentType() {
                    return "application/json; charset=utf-8";
                }
                @Override
                public byte[] getBody() throws AuthFailureError {
                    try {
                        return requestBody == null ? null : requestBody.getBytes("utf-8");
                    } catch (UnsupportedEncodingException uee) {
                        VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", requestBody, "utf-8");
                        return null;
                    }
                }
                @Override
                protected Response<String> parseNetworkResponse(NetworkResponse response) {
                    String responseString = "";
                    if (response != null) {
                        responseString = String.valueOf(response.statusCode);
                        // can get more details such as response.headers
                    }
                    return Response.success(responseString, HttpHeaderParser.parseCacheHeaders(response));
                }
            };
            requestQueue.add(stringRequest);

    }
    private int validateTransfer() {
        if (!AddressUtil.isValidAddress(strAddressTo)) {
            return R.string.invalid_address;
        } else if (strDesc.length() > TransferTransactionRequest.MaxAttachmentSize) {
            return R.string.attachment_too_long;
        } else if (amount <= 0) {
            return R.string.invalid_amount;
        } else if (amount > Long.MAX_VALUE - customFee) {
            return R.string.invalid_amount;
        } else if (customFee <= 0 || customFee< TransferTransactionRequest.MinFee) {
            return R.string.insufficient_fee;
        } else if (GlobalVar.strAddress.equals(strAddressTo)) {
            return R.string.send_to_same_address_warning;
        }
        return 0;
    }
    @Override
    public void handleResult(Result result) {

    }
}
