package com.example.schedule;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;


import com.example.schedule.mysql.Prevalent;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.regex.Pattern;

import io.paperdb.Paper;

public class LogInActivity extends AppCompatActivity implements View.OnClickListener {
    private Button login;
    private Button register;
    // @Required
    private EditText email;
    private EditText password;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private Context context;
    private CheckBox checkBox;
    private ProgressDialog progressDialog;
    private static final Pattern PASSWORD_PATTERN = Pattern.compile("^" +
            "(?=.*\\d)" +
            "(?=.*[a-z])" +
            "(?=.*[A-Z])" +
            "(?!.*\\s).*$");

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
        checkBox = (CheckBox) findViewById(R.id.checkBox);
        progressDialog = new ProgressDialog(this);
        Paper.init(this);


    }



    private boolean validatePassword() {
        boolean password1 = password.getText().toString().trim().isEmpty();
        String password2 = password.getText().toString().trim();
        if (password1) {
            password.setError("Поле не должно быть пустым");
            return false;
        } else if (!PASSWORD_PATTERN.matcher(password2).matches()) {
            password.setError("Слабый пароль. ");
            return false;
        } else {
            password.setError(null);
            return true;
        }
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

    public void logIn(final  String email, final String password) {
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
if(checkBox.isChecked()){

                if (validateEmail()) {
                    if (task.isSuccessful()) {
                        Toast.makeText(LogInActivity.this, "Successfully", Toast.LENGTH_SHORT).show();
                        startNewActivity();
                    } else Toast.makeText(LogInActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                } else if(email.isEmpty() ||password.isEmpty())
                    Toast.makeText(LogInActivity.this, "Проверьте введенные данные", Toast.LENGTH_SHORT).show();
                ;
            }}
        });
    }
    private boolean validateEmail() {
        String email1 = email.getText().toString().trim();
        if (email1.isEmpty()) {
            email.setError("Поле не должно быть пустым");
            return false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email1).matches()) {
            email.setError("Введите правильный E-mail");
            return false;
        } else {
            email.setError(null);
            return true;
        }
    }
    public void register(final String email, final String password) {


        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (validateEmail() & validatePassword()) {
                    if (task.isSuccessful()) {
                        Toast.makeText(LogInActivity.this, "Successfully", Toast.LENGTH_SHORT).show();
                    } else Toast.makeText(LogInActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                } else if (email.isEmpty() ||password.isEmpty())
                    Toast.makeText(LogInActivity.this, "Поля не должно быть пустыми", Toast.LENGTH_SHORT).show();
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