package com.example.enotaris;



import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;



import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    Button login, signup, signup2,login2;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // Assigning ID's to Button.

        signup = (Button) findViewById(R.id.signup);
        login = (Button) findViewById(R.id.login);
        signup2 = (Button) findViewById(R.id.signup2);
        login2 = (Button) findViewById(R.id.login2);


        //button login notaris
        login2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Redirect to Main Login activity after log out.
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);

                startActivity(intent);

            }
        });

        signup2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Redirect to Main Login activity after log out.
                Intent intent = new Intent(MainActivity.this, RegisterActivity.class);

                startActivity(intent);

            }
        });



        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Redirect to Main Login activity after log out.
                Intent intent = new Intent(MainActivity.this, RegNot.class);

                startActivity(intent);

            }
        });


    }
}
