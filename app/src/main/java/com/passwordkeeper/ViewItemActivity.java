package com.passwordkeeper;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

public class ViewItemActivity extends AppCompatActivity {
    TextView name;
    TextView username;
    TextView password;
    TextView url;
    TextView note;
    ImageView logo;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    String sName, sUsername, sPassword, sUrl, sNote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_item);

        setTitle("View Item");
        name = findViewById(R.id.viewItemName);
        username = findViewById(R.id.viewItemUsername);
        password = findViewById(R.id.viewItemPassword);
        url = findViewById(R.id.viewItemUrl);
        note = findViewById(R.id.viewItemNote);
        logo = findViewById(R.id.viewItemImageView);

        String path = getIntent().getStringExtra("path");

        db.document(path)
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if (documentSnapshot.exists()) {
                            Item item = documentSnapshot.toObject(Item.class);
                            sName = item.getName();
                            sUsername = item.getUsername();
                            sPassword = item.getPassword();
                            sUrl = item.getUrl();
                            sNote = "will be added soon";

                            setViewItems();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull @NotNull Exception e) {
                Toast.makeText(ViewItemActivity.this, "failed: " + e.toString(), Toast.LENGTH_SHORT).show();
                Log.d("TAG", "Failled");
            }
        });



    }

    private void setViewItems() {
        Picasso.get()
                .load("https://logo.clearbit.com/" + sUrl)
                .placeholder(R.drawable.web_24)
                .error(R.drawable.web_24)
                .into(logo);

        name.setText(sName);
        username.setText(sUsername);
        password.setText(sPassword);
        url.setText(sUrl);
        note.setText(sNote);
    }
}