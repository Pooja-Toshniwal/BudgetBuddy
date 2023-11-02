package com.who.budgetbuddy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.view.View;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

import java.util.regex.Pattern;

public class registrationActivity extends AppCompatActivity {
    public class User {
        public String name;
        public String email;
        public String password;

        public User() {
            // Default constructor required for calls to DataSnapshot.getValue(User.class)
        }

        public User(String name, String email, String password) {
            this.name = name;
            this.email = email;
            this.password = password;
        }
    }

    private FirebaseAuth Auth;
    private ProgressBar bar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        Auth = FirebaseAuth.getInstance();
        bar = new ProgressBar(this);

        EditText nam_e = findViewById(R.id.name);
        EditText username = findViewById(R.id.u_name);
        EditText password = findViewById(R.id.password);

        Button signupButton = findViewById(R.id.signup_button);
        signupButton.setOnClickListener(new View.OnClickListener() {
            //            @Override
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
                String name = nam_e.getText().toString();
                if(TextUtils.isEmpty(user)){
                    username.setError("Username Required");
                    return;
                }
                if(TextUtils.isEmpty(pass)){
                    password.setError("Password Required");
                    return;
                }
                if (isValid(user)==false) {
                    Toast.makeText(registrationActivity.this, "Invalid email_id. Please enter a valid email address.", Toast.LENGTH_SHORT).show();
                    return;

                }

               else  if (pass.length() < 7) {
                    Toast.makeText(registrationActivity.this, "Invalid password. Password must be at least 7 characters long.", Toast.LENGTH_SHORT).show();
                    return;

                }
                else if(name.length()==0){
                    Toast.makeText(registrationActivity.this,"Name field is empty",Toast.LENGTH_SHORT);
                    return;

                }
                 else{
                    Toast.makeText(registrationActivity.this,"Registered succesfully",Toast.LENGTH_SHORT);
                }

                Auth.createUserWithEmailAndPassword(user,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            FirebaseUser userF = FirebaseAuth.getInstance().getCurrentUser();
                            String userId = userF.getUid();
                            FirebaseDatabase.getInstance().getReference("users").child(userId)
                                    .setValue(new User(name, user, pass))
                                    .addOnSuccessListener(aVoid -> {
                                        // Name added successfully to the database
                                    })
                                    .addOnFailureListener(e -> {
                                        // Name failed to add to the database
                                    });
//                            Toast.makeText(getApplicationContext(),"Registration Complete", Toast.LENGTH_SHORT).show();
                              startActivity(new Intent(getApplicationContext(),home.class));
                        }
                        else{
                            Toast.makeText(getApplicationContext(),"Registration Failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
            public boolean isValid(String email)
            {
                String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\."+
                        "[a-zA-Z0-9_+&*-]+)*@" +
                        "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                        "A-Z]{2,7}$";

                Pattern pat = Pattern.compile(emailRegex);
                if (email == null)
                    return false;
                return pat.matcher(email).matches();
            }
        });


    }
}