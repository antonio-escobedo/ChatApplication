package com.example.chatapplication.contact;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.chatapplication.R;
import com.example.chatapplication.entity.Contact;
import com.example.chatapplication.utils.Constants;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ContactRegisterActivity extends AppCompatActivity {

    private String userIdentifier;
    private EditText contactFirstName, contactLastName, contactTelephone, contactEmail;
    private Button btnRegister;
    private String numContacts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FirebaseApp.initializeApp(this);
        setContentView(R.layout.activity_register_contact);

        this.setTitle("Register Contact");
        userIdentifier = getIntent().getStringExtra("userIdentifier");
        numContacts = getIntent().getStringExtra("numContacts");
        contactFirstName = (EditText)  findViewById(R.id.contactFirstName);
        contactLastName = (EditText)  findViewById(R.id.contactLastName);
        contactTelephone = (EditText)  findViewById(R.id.contactTelephone);
        contactEmail = (EditText)  findViewById(R.id.contactEmail);
        btnRegister = (Button) findViewById(R.id.registerContact);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // redirect to RegisterActivity
                // Write a message to the database
                FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
                DatabaseReference databaseReference = firebaseDatabase.getReference().child(Constants.CONTACT_TABLE);
                //databaseReference.child("usr" + userIdentifier).setValue(new Contact(contactFirstName.getText().toString(),
                databaseReference.child("usr" + userIdentifier + numContacts).setValue(new Contact(contactFirstName.getText().toString(),
                        contactLastName.getText().toString(),contactTelephone.getText().toString(),contactEmail.getText().toString(),"usr" + userIdentifier)).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        // Write was successful!
                        Toast.makeText(ContactRegisterActivity.this, "Contact register success", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(), ContactListActivity.class);
                        intent.putExtra("userIdentifier", userIdentifier);
                        startActivity(intent);
                        finish();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Write failed
                        // ...
                        Toast.makeText(ContactRegisterActivity.this, "Contact register failure", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }


}