package com.passwordkeeper;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

import org.jetbrains.annotations.NotNull;

public class SettingsActivity extends AppCompatActivity {
    Button btn_logout;
    FirebaseAuth auth;
    BottomNavigationView bottomNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        setTitle("Settings");

        auth = FirebaseAuth.getInstance();
        btn_logout = findViewById(R.id.settingsLogout);
        bottomNavigationView = findViewById(R.id.bottomNavigationView);

        bottomNavigationView.setSelectedItemId(R.id.btm_nav_settings);

        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                auth.signOut();
                Intent intent = new Intent(SettingsActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
        setNavigationBar();
    }
    private void setNavigationBar() {
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull @NotNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.btm_nav_settings:
                        return true;
                    case R.id.btm_nav_generator:
                        Intent intent = new Intent(SettingsActivity.this, GeneratorActivity.class);
                        startActivity(intent);
                        overridePendingTransition(0, 0);
                        finish();
                        return true;
                    case R.id.btm_nav_search:
                        intent = new Intent(SettingsActivity.this, SearchActivity.class);
                        startActivity(intent);
                        overridePendingTransition(0, 0);
                        finish();
                        return true;
                    case R.id.btm_nav_myvault:
                        intent = new Intent(SettingsActivity.this, DashboardActivity.class);
                        startActivity(intent);
                        overridePendingTransition(0, 0);
                        finish();
                        return true;

                }
                return false;
            }
        });
    }
}