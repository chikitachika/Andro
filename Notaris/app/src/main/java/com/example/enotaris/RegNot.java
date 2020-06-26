package com.example.enotaris;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;

import org.json.JSONObject;

public class RegNot extends AppCompatActivity {

    EditText etNama, etEmail,etTelp,etSK, etPassword;;
    String nama,sk,email,telp,password;
    Button btnRegister2;
    ProgressDialog progressDialog;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regnot);


        etNama         = findViewById(R.id.et_name2);
        etSK         = findViewById(R.id.et_sk);
        etEmail            = findViewById(R.id.et_reg_email2);
        etPassword         = findViewById(R.id.et_reg_password2);
        etTelp             = findViewById(R.id.et_reg_telp2);
        btnRegister2          = findViewById(R.id.btn_register2);

        progressDialog      = new ProgressDialog(this);

        btnRegister2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog.setMessage("Menambahkan Data...");
                progressDialog.setCancelable(false);
                progressDialog.show();
                Intent i = new Intent(RegNot.this,
                        LoginActivity.class);
                startActivity(i);
                nama = etNama.getText().toString();
                email = etEmail.getText().toString();
                sk = etSK.getText().toString();
                telp = etTelp.getText().toString();
                password = etPassword.getText().toString();



                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        validasiData();
                    }
                },1000);
            }
        });

    }

    void validasiData(){
        if(nama.equals("") || email.equals("") || sk.equals("") || telp.equals("") || password.equals("")){
            progressDialog.dismiss();
            Toast.makeText(RegNot.this, "Periksa kembali data yang anda masukkan !", Toast.LENGTH_SHORT).show();
        }else {
            kirimData();
        }
    }

    void kirimData(){
        AndroidNetworking.post("http://192.168.1.7/kelompok-5/restnot/index.php/regis")
                .addBodyParameter("nama",""+nama)
                .addBodyParameter("email",""+email)
                .addBodyParameter("no_sk",""+sk)
                .addBodyParameter("telepon",""+telp)
                .addBodyParameter("password",""+password)
                .setPriority(Priority.MEDIUM)
                .setTag("Tambah Data")
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        progressDialog.dismiss();
                        Log.d("Status",""+response);
                        try {
                            Boolean status = response.getBoolean("status");
                            String pesan = response.getString("result");
                            Toast.makeText(RegNot.this, ""+pesan, Toast.LENGTH_SHORT).show();
                            Log.d("status",""+status);
                            if(status){
                                new AlertDialog.Builder(RegNot.this)

                                        .setMessage("Berhasil Menambahkan Data !")
                                        .setCancelable(false)
                                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                dialog.dismiss();
                                                etNama.getText().clear();
                                                etEmail.getText().clear();
                                                etSK.getText().clear();
                                                etTelp.getText().clear();
                                                etPassword.getText().clear();

                                            }
                                        })

                                        .show();


                            }
                            else{
                                new AlertDialog.Builder(RegNot.this)
                                        .setMessage("Gagal Menambahkan Data !")
                                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                dialog.dismiss();
                                            }
                                        })
                                        .setCancelable(false)
                                        .show();
                            }
                        }catch (Exception e){
                            e.printStackTrace();
                        }


                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.d("ErrorTambahData",""+anError.getErrorBody());
                    }
                });
    }
}
