package com.passwordkeeper;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.jetbrains.annotations.NotNull;

public class SearchActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        setTitle("Search");
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setSelectedItemId(R.id.btm_nav_search);

        setNavigationBar();
    }

    private void setNavigationBar() {
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull @NotNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.btm_nav_search:
                        return true;
                    case R.id.btm_nav_generator:
                        Intent intent = new Intent(SearchActivity.this, GeneratorActivity.class);
                        startActivity(intent);
                        overridePendingTransition(0, 0);
                        finish();
                        return true;
                    case R.id.btm_nav_myvault:
                        intent = new Intent(SearchActivity.this, DashboardActivity.class);
                        startActivity(intent);
                        overridePendingTransition(0, 0);
                        finish();
                        return true;
                    case R.id.btm_nav_settings:
                        intent = new Intent(SearchActivity.this, SettingsActivity.class);
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