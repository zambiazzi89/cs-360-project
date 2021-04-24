package com.zybooks.cs_330project;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;

public class RegisterActivity extends AppCompatActivity {

    TextInputEditText username, password, rePassword;
    Button signUpButton;

    InventoryDB inventoryDB;

    SessionManager sessionManager;

    private TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }
        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            if (username.getText().toString().trim().isEmpty() ||
                    password.getText().toString().trim().isEmpty() ||
                    rePassword.getText().toString().trim().isEmpty()) {
                signUpButton.setEnabled(false);
            } else {
                signUpButton.setEnabled(true);
            }
        }
        @Override
        public void afterTextChanged(Editable s) {
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        username = (TextInputEditText) findViewById(R.id.username);
        password = (TextInputEditText) findViewById(R.id.password);
        rePassword = (TextInputEditText) findViewById(R.id.rePassword);
        signUpButton = (Button) findViewById(R.id.signUpButton);

        username.addTextChangedListener(textWatcher);
        password.addTextChangedListener(textWatcher);
        rePassword.addTextChangedListener(textWatcher);

        inventoryDB = new InventoryDB(this);

        sessionManager = new SessionManager(getApplicationContext());

    };

    public void onSignUpClick(View v) {
        boolean checkUsername = inventoryDB.checkUsername(username.getText().toString());
        if (checkUsername == true) {
            Toast toast = Toast.makeText(getApplicationContext(), "Username is already registered", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        } else {
            if (password.getText().toString().equals(rePassword.getText().toString())) {
                boolean addUser = inventoryDB.addUser(username.getText().toString(), password.getText().toString());
                if (addUser) {
                    Toast toast = Toast.makeText(getApplicationContext(), "User registered successfully", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                }
                sessionManager.setLogin(true);
                sessionManager.setUsername(username.getText().toString());
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            } else {
                Toast toast = Toast.makeText(getApplicationContext(), "Passwords must match", Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
            }
        }
    };

    public void onGoToLogInClick(View v) {
        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(intent);
    };
}
