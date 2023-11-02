package com.who.budgetbuddy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.view.View;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth Auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Auth = FirebaseAuth.getInstance();

        EditText username = findViewById(R.id.u_name);
        EditText password = findViewById(R.id.password);

        Button loginButton = findViewById(R.id.login_button);
        Button signupButton = findViewById(R.id.signup_button);
        loginButton.setOnClickListener(new View.OnClickListener() {
                        @Override
            public void onClick(View v) {
                v.animate()
                        .scaleX(1.2f)
                        .scaleY(1.2f)
                        .setDuration(200)
                        .withEndAction(new Runnable() {
                            @Override
                            public void run() {
                                v.animate()
                                        .scaleX(1f)
                                        .scaleY(1f)
                                        .setDuration(200)
                                        .start();
                            }
                        })
                        .start();

                String user = username.getText().toString();
                String pass = password.getText().toString();
                if (TextUtils.isEmpty(user)){
                    username.setError("Username required.");
                    return;
                }
                if(TextUtils.isEmpty(pass)){
                    password.setError("Password required.");
                    return;
                }



                Auth.signInWithEmailAndPassword(user,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            if(Auth.getCurrentUser()!=null)
                            {
                                Toast.makeText(getApplicationContext(),"Login Successful", Toast.LENGTH_SHORT).show();

                                startActivity(new Intent(getApplicationContext(),home.class));

                            }

                        }
                        else{
                            Toast.makeText(getApplicationContext(),"Login Failed. Try Again.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });


        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.animate()
                        .scaleX(1.2f)
                        .scaleY(1.2f)
                        .setDuration(200)
                        .withEndAction(new Runnable() {
                            @Override
                            public void run() {
                                v.animate()
                                        .scaleX(1f)
                                        .scaleY(1f)
                                        .setDuration(200)
                                        .start();
                            }
                        })
                        .start();

                startActivity(new Intent(getApplicationContext(),registrationActivity.class));
            }
        });
    }
}