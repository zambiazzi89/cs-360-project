package com.zybooks.cs_330project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

public class LoginActivity extends AppCompatActivity {

    TextInputEditText username, password;
    Button logInButton, signUpButton;

    InventoryDB inventoryDB;

    SessionManager sessionManager;

    private TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }
        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            if (username.getText().toString().trim().isEmpty() || password.getText().toString().trim().isEmpty()) {
                logInButton.setEnabled(false);
            } else {
                logInButton.setEnabled(true);
            }
        }
        @Override
        public void afterTextChanged(Editable s) {
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        username = (TextInputEditText) findViewById(R.id.username);
        password = (TextInputEditText) findViewById(R.id.password);
        logInButton = (Button) findViewById(R.id.logInButton);
        signUpButton = (Button) findViewById(R.id.signUpButton);

        username.addTextChangedListener(textWatcher);
        password.addTextChangedListener(textWatcher);

        inventoryDB = new InventoryDB(this);

        sessionManager = new SessionManager(getApplicationContext());
    }

    public void onLogInClick(View v) {
        boolean checkUsername = inventoryDB.checkUsername(username.getText().toString());
        if (checkUsername == false) {
            Toast toast = Toast.makeText(getApplicationContext(), "Username is not registered", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        } else {
            boolean checkPassword = inventoryDB.checkPassword(username.getText().toString(), password.getText().toString());
            if (checkPassword == false) {
                Toast toast = Toast.makeText(getApplicationContext(), "Incorrect Username/Password", Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
            } else {
                sessionManager.setLogin(true);
                sessionManager.setUsername(username.getText().toString());
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        }
    };

    public void onGoToSignUpClick(View v) {
        Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
        startActivity(intent);
    };
}