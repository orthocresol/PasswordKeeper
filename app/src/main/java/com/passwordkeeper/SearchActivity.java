package com.passwordkeeper;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.BoringLayout;
import android.view.MenuItem;
import android.widget.SearchView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    SearchView searchView;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    CollectionReference entries = db.collection(user.getEmail());
    EntryAdapterForSearch adapter;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        setTitle("Search");

        searchView = findViewById(R.id.search_searchview);
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setSelectedItemId(R.id.btm_nav_search);

        setNavigationBar();
        searchViewListener();

        recyclerView = findViewById(R.id.search_recylerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter = null;
    }

    private void searchViewListener() {
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                //setupRecylerView(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                setupRecylerView(newText);
                return false;
            }
        });
    }

    private void setupRecylerView(String newText) {
        ArrayList<Item> array = new ArrayList<>();
        entries.get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                            Item item = documentSnapshot.toObject(Item.class);
                            item.setId(documentSnapshot.getId());
                            String itemName = item.getName();
                            String itemUsername = item.getUsername();

                            Boolean bool1 = itemName.toLowerCase().contains(newText.toLowerCase());
                            Boolean bool2 = newText.toLowerCase().contains(itemName.toLowerCase());
                            Boolean bool3 = itemUsername.toLowerCase().contains(newText.toLowerCase());
                            Boolean bool4 = newText.toLowerCase().contains(itemUsername.toLowerCase());

                            if (itemUsername.trim().length() == 0) {
                                bool3 = false;
                                bool4 = false;
                            }

                            if (itemName.trim().length() == 0) {
                                bool1 = false;
                                bool2 = false;
                            }

                            if (bool1 || bool2 || bool3 || bool4) {
                                array.add(item);
                            }
                        }
                    }
                });
        adapter = new EntryAdapterForSearch(SearchActivity.this, array);

        recyclerView.setAdapter(adapter);
    }

    private void setNavigationBar() {
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull @NotNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.btm_nav_search:
                        return true;
                    case R.id.btm_nav_generator:
                        Intent intent = new Intent(SearchActivity.this, GeneratorActivity.class);
                        startActivity(intent);
                        overridePendingTransition(0, 0);
                        finish();
                        return true;
                    case R.id.btm_nav_myvault:
                        /*intent = new Intent(SearchActivity.this, DashboardActivity.class);
                        startActivity(intent);
                        overridePendingTransition(0, 0);*/
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