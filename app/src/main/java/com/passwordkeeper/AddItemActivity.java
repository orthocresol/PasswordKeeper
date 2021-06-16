package com.passwordkeeper;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class AddItemActivity extends AppCompatActivity {
    EditText et_name, et_username, et_password, et_url;
    ProgressBar progressBar;
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("item", "ioncreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);

        setTitle("Add Item");

        Objects.requireNonNull(getSupportActionBar()).setHomeAsUpIndicator(R.drawable.ic_baseline_close_24);


        db = FirebaseFirestore.getInstance();
        progressBar = findViewById(R.id.updateItemProgressBar);
        et_name = findViewById(R.id.updateItemName);
        et_username = findViewById(R.id.updateItemUsername);
        et_password = findViewById(R.id.updateItemPassword);
        et_url = findViewById(R.id.updateItemURL);
        progressBar.setVisibility(View.INVISIBLE);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_add_item, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.add_item_save_button:
                addItem();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    private void addItem() {


        String name = et_name.getText().toString().trim();
        String username = et_username.getText().toString().trim();
        String password = et_password.getText().toString().trim();
        String url = et_url.getText().toString().trim();

        progressBar.setVisibility(View.VISIBLE);

        Item item = new Item(name, username, password, url);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String userNameForCollection = user.getEmail();

        if(userNameForCollection == null){
            Toast.makeText(AddItemActivity.this, "Why email is not returning", Toast.LENGTH_SHORT).show();
            return;
        }

        db.collection(userNameForCollection).document().set(item)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(AddItemActivity.this, "Item added successfully", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(AddItemActivity.this, DashboardActivity.class);
                        progressBar.setVisibility(View.INVISIBLE);
                        startActivity(intent);
                        finish();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull @NotNull Exception e) {
                        Toast.makeText(AddItemActivity.this, "Item adding failed", Toast.LENGTH_SHORT).show();
                        progressBar.setVisibility(View.INVISIBLE);
                        Log.d("Item Add", e.toString());
                    }
                });


    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("item", "ionresume");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("item", "ionstart");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("item", "ionPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("item", "ionStop");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d("item", "irestart");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("item", "ionDestroy");
    }
}