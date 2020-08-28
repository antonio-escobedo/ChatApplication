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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import static com.example.chatapplication.utils.Commons.isBlankOrEmpty;

public class RegisterActivity extends AppCompatActivity {

    EditText userName, password;
    Button register;
    TextView login;
    boolean isNameValid, isEmailValid, isPhoneValid, isPasswordValid;
    TextInputLayout nameError, emailError, phoneError, passError;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        userName = (EditText) findViewById(R.id.userName);
        password = (EditText) findViewById(R.id.password);
        login = (TextView) findViewById(R.id.login);
        register = (Button) findViewById(R.id.register);

        emailError = (TextInputLayout) findViewById(R.id.emailError);

        passError = (TextInputLayout) findViewById(R.id.passError);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              //  SetValidation();
                
                if(isBlankOrEmpty(userName.getText().toString())){
                    Toast.makeText(RegisterActivity.this, "Please capture your username and try register on again", Toast.LENGTH_SHORT).show();
                }if (isBlankOrEmpty(password.getText().toString())){
                    Toast.makeText(RegisterActivity.this, "Please capture your password and try register on again", Toast.LENGTH_SHORT).show();
                }else{
                    // Write a message to the database
                    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
                    DatabaseReference databaseReference = firebaseDatabase.getReference(Constants.USER_TABLE);
                    databaseReference.child("usr"+ userName.getText()).setValue(new User(userName.getText().toString(), Commons.md5Hash(password.getText().toString())));

                    // redirect to ContactListActivity
                    Intent intent = new Intent(getApplicationContext(), ContactListActivity.class);
                    startActivity(intent);
                    finish();

                }
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // redirect to LoginActivity
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
            }
        });
    }
}