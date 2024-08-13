package com.example.login;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.ToggleButton;

public class MainActivity extends AppCompatActivity {
    EditText uname, pwd;
    Button loginBtn;
    SharedPreferences pref;
    Intent intent;
    private ToggleButton themeToggleButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // Load the theme before setting the content view
        SharedPreferences preferences = getSharedPreferences("themePrefs", MODE_PRIVATE);
        boolean isDarkTheme = preferences.getBoolean("isDarkTheme", false);
        if (isDarkTheme) {
            setTheme(R.style.AppTheme_Dark);
        } else {
            setTheme(R.style.AppTheme_Light);
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        for toggle button
        themeToggleButton = findViewById(R.id.themeToggleButton);
        themeToggleButton.setChecked(isDarkTheme);
        updateToggleButtonIcon(isDarkTheme);

        themeToggleButton.setOnCheckedChangeListener((buttonView, isChecked) -> {
            // Save the theme preference
            SharedPreferences.Editor editor = preferences.edit();
            editor.putBoolean("isDarkTheme", isChecked);
            editor.apply();

            // Recreate the activity to apply the new theme
            recreate();
        });

//        other login code
        uname = findViewById(R.id.txtName);
        pwd = findViewById(R.id.txtPwd);
        loginBtn = findViewById(R.id.btnLogin);
        pref = getSharedPreferences("user_details", MODE_PRIVATE);
        intent = new Intent(MainActivity.this, validateActivity.class);

        if(pref.contains("username") && pref.contains("password")){
            startActivity(intent);
        }

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = uname.getText().toString();
                String password = pwd.getText().toString();
                if(username.equals("admin") && password.equals("admin")){
                    SharedPreferences.Editor editor = pref.edit();
                    editor.putString("username", username);
                    editor.putString("password", password);
                    editor.apply();
                    Toast.makeText(getApplicationContext(), "Login Successful", Toast.LENGTH_SHORT).show();
                    startActivity(intent);
                } else {
                    Toast.makeText(getApplicationContext(), "Credentials are not valid", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void updateToggleButtonIcon(boolean isDarkTheme) {
        if (isDarkTheme) {
            themeToggleButton.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_moon_foreground, 0, 0);
        } else {
            themeToggleButton.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_sun_foreground, 0, 0);
        }
    }
}
