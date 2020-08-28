package com.example.chatapplication.contact;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.chatapplication.R;
import com.example.chatapplication.entity.Contact;
import com.example.chatapplication.utils.Constants;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ContactUpdateActivity extends AppCompatActivity {

    private String userIdentifier,numberContact,firstContact,lastContact,emailContact;
    private EditText contactFirstName, contactLastName, contactTelephone, contactEmail;
    private Button btnUpdate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FirebaseApp.initializeApp(this);
        setContentView(R.layout.activity_update_contact);

        this.setTitle("Register Contact");
        userIdentifier = getIntent().getStringExtra("userIdentifier");
        numberContact = getIntent().getStringExtra("numberContact");
        firstContact = getIntent().getStringExtra("firstContact");
        lastContact = getIntent().getStringExtra("lastContact");
        emailContact = getIntent().getStringExtra("emailContact");

        contactFirstName = (EditText)  findViewById(R.id.contactFirstName);
        contactFirstName.setText(firstContact);
        contactLastName = (EditText)  findViewById(R.id.contactLastName);
        contactLastName.setText(lastContact);
        contactTelephone = (EditText)  findViewById(R.id.contactTelephone);
        contactTelephone.setText(numberContact);
        contactEmail = (EditText)  findViewById(R.id.contactEmail);
        contactEmail.setText(emailContact);
        btnUpdate = (Button) findViewById(R.id.updateContact);


        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // redirect to RegisterActivity
                // Write a message to the database
                FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
                DatabaseReference databaseReference = firebaseDatabase.getReference().child(Constants.CONTACT_TABLE);
                databaseReference.child("usr" + userIdentifier).setValue(new Contact(contactFirstName.getText().toString(),
                        contactLastName.getText().toString(),contactTelephone.getText().toString(),contactEmail.getText().toString(),"usr" + userIdentifier)).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        // Write was successful!
                        Toast.makeText(ContactUpdateActivity.this, "Contact update success", Toast.LENGTH_SHORT).show();
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
                        Toast.makeText(ContactUpdateActivity.this, "Contact update failure", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}