package com.example.chatapplication.user;

import android.content.Intent;
import android.graphics.Color;
import android.util.Patterns;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.chatapplication.R;
import com.example.chatapplication.contact.ContactListActivity;
import com.example.chatapplication.entity.User;
import com.example.chatapplication.utils.Commons;
import com.example.chatapplication.utils.Constants;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UpdateActivity extends AppCompatActivity {

    EditText userName, password,newPassword;
    Button update;
    TextView loginBack;
    boolean isEmailValid, isPasswordValid;
    TextInputLayout emailError, passError;
    String userIdentifier;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FirebaseApp.initializeApp(this);
        setContentView(R.layout.activity_update);

        userIdentifier = getIntent().getStringExtra("userIdentifier");
        userName = (EditText) findViewById(R.id.firstNameContact);
        password = (EditText) findViewById(R.id.password);
        newPassword= (EditText) findViewById(R.id.newPassword);
        update = (Button) findViewById(R.id.update);
        emailError = (TextInputLayout) findViewById(R.id.emailError);
        passError = (TextInputLayout) findViewById(R.id.passError);
        loginBack = (TextView) findViewById(R.id.loginBack);

        if(userIdentifier != null || userIdentifier != ""){
            loginBack.setVisibility(View.GONE);
            userName.setText(userIdentifier);
        }

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Write a message to the database
                // I continue with the development
                FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
                final DatabaseReference databaseReference = firebaseDatabase.getReference(Constants.USER_TABLE);

                databaseReference.child("usr" + userName.getText()).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        User user = dataSnapshot.getValue(User.class);

                        if (user == null) {
                            Toast.makeText(UpdateActivity.this, "Please verify your user name and old password and try update on again", Toast.LENGTH_SHORT).show();
                        } else {
                            if(user.getUserName().equals(userName.getText().toString()) && user.getPassword().equals(Commons.md5Hash(password.getText().toString()))){
                                databaseReference.child("usr" + userName.getText()).child("password").setValue(Commons.md5Hash(newPassword.getText().toString()));
                                Toast.makeText(UpdateActivity.this, "Your password is updated success", Toast.LENGTH_SHORT).show();
                                // redirect to ContactListActivity
                                Intent intent = new Intent(getApplicationContext(), ContactListActivity.class);
                                intent.putExtra("userIdentifier", user.getUserName());
                                startActivity(intent);
                                finish();
                            }else{
                                Toast.makeText(UpdateActivity.this, "Please verify your old password and try update on again", Toast.LENGTH_SHORT).show();
                            }

                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError error) {
                        // Failed to read value

                    }

                });
            }
        });

        loginBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // redirect to RegisterActivity
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
            }
        });

    }
}