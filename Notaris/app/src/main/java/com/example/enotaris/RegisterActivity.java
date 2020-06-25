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

public class RegisterActivity extends AppCompatActivity {

    com.rengwuxian.materialedittext.MaterialEditText etName, etEmail,etTelp, etPassword;;
    String name,email,telp,password;
    Button btnRegister;
    ProgressDialog progressDialog;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        etName         = findViewById(R.id.et_name);
        etEmail            = findViewById(R.id.et_reg_email);
        etPassword         = findViewById(R.id.et_reg_password);
        etTelp             = findViewById(R.id.et_reg_telp);
        btnRegister          = findViewById(R.id.btn_register);

        progressDialog      = new ProgressDialog(this);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog.setMessage("Menambahkan Data...");
                progressDialog.setCancelable(false);
                progressDialog.show();

                name = etName.getText().toString();
                email = etEmail.getText().toString();
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
        if(name.equals("") || email.equals("") || telp.equals("") || password.equals("")){
            progressDialog.dismiss();
            Toast.makeText(RegisterActivity.this, "Periksa kembali data yang anda masukkan !", Toast.LENGTH_SHORT).show();
        }else {
            kirimData();
        }
    }

    void kirimData(){
        AndroidNetworking.post("http://192.168.1.7/kelompok-5/restnot/index.php/regisclient")
                .addBodyParameter("nama",""+name)
                .addBodyParameter("email",""+email)
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
                            Toast.makeText(RegisterActivity.this, ""+pesan, Toast.LENGTH_SHORT).show();
                            Log.d("status",""+status);
                            if(status){
                                new AlertDialog.Builder(RegisterActivity.this)
                                        .setMessage("Berhasil Menambahkan Data !")
                                        .setCancelable(false)
                                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                dialog.dismiss();
                                                etName.getText().clear();
                                                etEmail.getText().clear();
                                                etTelp.getText().clear();
                                                etPassword.getText().clear();
                                            }
                                        })
                                        .show();
                            }
                            else{
                                new AlertDialog.Builder(RegisterActivity.this)
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