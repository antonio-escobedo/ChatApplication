package com.example.chatapplication.user;

import android.content.Intent;
import android.util.Patterns;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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

public class LoginActivity extends AppCompatActivity {

    EditText userName, password;
    Button login;
    TextView register,updatePassword;
    boolean isEmailValid, isPasswordValid;
    TextInputLayout emailError, passError;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FirebaseApp.initializeApp(this);
        setContentView(R.layout.activity_login);

        userName = (EditText) findViewById(R.id.userName);
        password = (EditText) findViewById(R.id.password);
        login = (Button) findViewById(R.id.login);
        register = (TextView) findViewById(R.id.register);
        updatePassword = (TextView) findViewById(R.id.update);
        emailError = (TextInputLayout) findViewById(R.id.emailError);
        passError = (TextInputLayout) findViewById(R.id.passError);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Write a message to the database
                FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
                DatabaseReference databaseReference = firebaseDatabase.getReference(Constants.USER_TABLE);
               // databaseReference.child("usrAntonio").setValue(new User("Antonio", "Alan"));
                databaseReference.child("usr" + userName.getText()).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        // List<String> usersList = new ArrayList<String>();
                      /*  for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                            cities.add(postSnapshot.getValue().toString());
                        }*/

                        User user = dataSnapshot.getValue(User.class);
                        if (user == null) {
                            Toast.makeText(LoginActivity.this, "Please verify your user name and password and try logging on again", Toast.LENGTH_SHORT).show();
                        } else {
                            if(user.getPassword().equals(Commons.md5Hash(password.getText().toString()))){
                                Intent intent = new Intent(getApplicationContext(), ContactListActivity.class);
                                intent.putExtra("userIdentifier", user.getUserName());
                                startActivity(intent);
                                finish();
                            }else{
                                Toast.makeText(LoginActivity.this, "Please verify your password and try logging on again", Toast.LENGTH_SHORT).show();
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

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // redirect to RegisterActivity
                Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(intent);
                finish();
            }
        });

        updatePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // redirect to UpdateActivity
                Intent intent = new Intent(getApplicationContext(), UpdateActivity.class);
                startActivity(intent);
            }
        });
    }
}