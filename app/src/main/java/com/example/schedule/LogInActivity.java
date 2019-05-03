package com.example.schedule;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LogInActivity extends AppCompatActivity implements View.OnClickListener {
    private Button login;
    private Button register;
    private EditText email;
    private EditText password;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FirebaseApp.initializeApp(this);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();
        this.context = getApplicationContext();

//        mAuthListener = new FirebaseAuth.AuthStateListener() {
//            @Override
//            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
//                FirebaseUser user = firebaseAuth.getCurrentUser();
//                if (user != null) {
//
//                } else {
//                    // User is signed out
//
//                }
//
//            }
//        };


        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);
        findViewById(R.id.login).setOnClickListener(this);
        findViewById(R.id.register).setOnClickListener(this);


//        FirebaseUser user = mAuth.getCurrentUser();
//        if (user!=null){
//        Intent intent = new Intent(LogInActivity.this, MainActivity.class);
//        startActivity(intent);}
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.login) {
            logIn(email.getText().toString(), password.getText().toString());
        } else if (view.getId() == R.id.register) {
            register(email.getText().toString(), password.getText().toString());
        }
        ;
    }

    public void logIn(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(LogInActivity.this, "Successfully", Toast.LENGTH_SHORT).show();
                    startNewActivity();
                } else Toast.makeText(LogInActivity.this, "Failed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void register(String email, String password) {
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(LogInActivity.this, "Successfully", Toast.LENGTH_SHORT).show();


                } else Toast.makeText(LogInActivity.this, "Failed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }


    private void startNewActivity() {
        Intent intent = new Intent(context, MainActivity.class);
        startActivity(intent);
        this.finish();
    }
}