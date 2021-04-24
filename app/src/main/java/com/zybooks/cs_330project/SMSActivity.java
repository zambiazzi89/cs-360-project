package com.zybooks.cs_330project;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.google.android.material.switchmaterial.SwitchMaterial;

public class SMSActivity extends AppCompatActivity {

    private static final int PERMISSION_REQUEST_SEND_SMS = 0;
    SessionManager sessionManager;
    TextView smsStatus;
    SwitchMaterial switchMaterial;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sms);

        sessionManager = new SessionManager(getApplicationContext());

        switchMaterial = findViewById(R.id.switch_sms);
        smsStatus = findViewById(R.id.smsStatus);

        // Listener to handle toggling the switch
        switchMaterial.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    // If switch is on, check to see if user gave permission to send SMS
                    if (hasFilePermissions()) {
                        smsStatus.setTextColor(Color.parseColor("#B97375"));
                        smsStatus.setText("Active");
                    } else {
                        smsStatus.setTextColor(Color.parseColor("#2D2D34"));
                        smsStatus.setText("Inactive");
                        switchMaterial.setChecked(false);
                    }
                } else {
                    smsStatus.setTextColor(Color.parseColor("#2D2D34"));
                    smsStatus.setText("Inactive");
                }
            }
        });
    }

    // Goes back to home screen
    public void onLogoClick(View v) {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
    }

    // Logs out and clears session manager data
    public void onLogOutClick(View v){
        sessionManager.setLogin(false);
        sessionManager.setUsername("");
        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(intent);
    }

    // Checks if user granted permission to send SMS
    private boolean hasFilePermissions() {
        String smsPermission = Manifest.permission.SEND_SMS;
        if (ContextCompat.checkSelfPermission(this, smsPermission)
                != PackageManager.PERMISSION_GRANTED) {
            // If user denied it previously, show dialog to request permission again
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, smsPermission )) {
                new AlertDialog.Builder(this)
                        .setTitle("Permission Needed")
                        .setMessage("Permission is needed to access files from your device...")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                ActivityCompat.requestPermissions(SMSActivity.this,new String[]{smsPermission},PERMISSION_REQUEST_SEND_SMS);
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        }).create().show();
            } else {
                ActivityCompat.requestPermissions(this,
                        new String[] { smsPermission }, PERMISSION_REQUEST_SEND_SMS);
            }
            return false;
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_SEND_SMS: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Permission granted!
                }
                else {
                    // Permission denied!
                }
                return;
            }
        }
    }
}