package com.example.chatapplication.contact;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import android.util.Log;
import android.view.Menu;

import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.chatapplication.R;
import com.example.chatapplication.entity.Contact;
import com.example.chatapplication.user.UpdateActivity;
import com.example.chatapplication.utils.Constants;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ContactListActivity extends AppCompatActivity {

    public ListView listView;
    private ValueEventListener mMessagesListener;
    private ChildEventListener mMessagesQueryListener;
    private List<Contact> contactList;
    private String userIdentifier,numContacts;
    private ImageView btnChat, btnEditContact, btnDeleteContact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FirebaseApp.initializeApp(this);
        setContentView(R.layout.activity_contact_list);
        contactList = new ArrayList<>();
        this.setTitle("Contact List");
        userIdentifier = getIntent().getStringExtra("userIdentifier");
        btnEditContact = (ImageView) findViewById(R.id.updateImg);
        btnChat = (ImageView) findViewById(R.id.chatImg);
        btnDeleteContact = (ImageView) findViewById(R.id.deleteImg);

        // Write a message to the database
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference().child(Constants.CONTACT_TABLE);
       // databaseReference.child("usrpedrin").setValue(new Contact("Brandon", "Ramsel","6474637367","brandon@gmail.com","usrpedrin"));
        databaseReference.orderByChild("userId").equalTo("usr" + userIdentifier).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                      for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                          Contact contact = postSnapshot.getValue(Contact.class);
                          contactList.add(contact);
                        }

                      if(contactList.size() > 0){
                          setRowList();
                      }else {
                          Toast.makeText(ContactListActivity.this, "You have no contacts, please add them", Toast.LENGTH_SHORT).show();
                          Intent intent = new Intent(getApplicationContext(), ContactRegisterActivity.class);
                          intent.putExtra("userIdentifier",userIdentifier);
                          numContacts = String.valueOf(contactList.size() + 1);
                          intent.putExtra("numContacts",numContacts);
                          finish();
                          startActivity(intent);
                      }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value

            }
        });

        //

}

    public void setRowList(){
        listView = findViewById(R.id.listViewContact);
        ContactListAdapter contactListAdapter = new ContactListAdapter(contactList, this);
        listView.setAdapter(contactListAdapter);
        listView.setClickable(true);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {

                final Contact contactLst = contactList.get(position);
                int itemSelected = 0;
                String[] singleChoiceItems = {"Start Chat","Edit Contact", "Delete Contact"};
                new AlertDialog.Builder(ContactListActivity.this)
                        .setTitle("Select your action")
                        .setSingleChoiceItems(singleChoiceItems, itemSelected, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int selectedIndex) {
                                int itemSelected = 0;
                                String[] singleChoiceItems = {"Start Chat","Edit Contact", "Delete Contact"};
                                new AlertDialog.Builder(ContactListActivity.this)
                                        .setTitle("Select your action")
                                        .setSingleChoiceItems(singleChoiceItems, itemSelected, new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int selectedIndex) {
                                                if(selectedIndex == 0){

                                                }else if(selectedIndex == 1){

                                                    Intent intentEditContact = new Intent(getApplicationContext(), ContactUpdateActivity.class);
                                                    intentEditContact.putExtra("userIdentifier",userIdentifier);
                                                    intentEditContact.putExtra("numberContact", contactLst.getTelephone());
                                                    intentEditContact.putExtra("firstContact",contactLst.getFirstName());
                                                    intentEditContact.putExtra("lastContact", contactLst.getLastName());
                                                    intentEditContact.putExtra("emailContact",contactLst.getEmail());
                                                    finish();
                                                    startActivity(intentEditContact);
                                                }else if(selectedIndex == 2){

                                                    deleteContact(contactLst.getTelephone());
                                                    finish();
                                                    startActivity(getIntent());

                                                }
                                            }
                                        })
                                        .setPositiveButton("Ok", null)
                                        .setNegativeButton("Cancel", null)
                                        .show();
                            }
                        })
                        .setPositiveButton("Ok", null)
                        .setNegativeButton("Cancel", null)
                        .show();

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch(item.getItemId()) {
            case R.id.addContact:
                Intent intent = new Intent(getApplicationContext(), ContactRegisterActivity.class);
                intent.putExtra("userIdentifier",userIdentifier);
                numContacts = String.valueOf(contactList.size() + 1);
                intent.putExtra("numContacts",numContacts);
                finish();
                startActivity(intent);
                break;
            case R.id.editProfile:
                Intent intentEdit = new Intent(getApplicationContext(), UpdateActivity.class);
                intentEdit.putExtra("userIdentifier",userIdentifier);
                finish();
                startActivity(intentEdit);
                return true;

            case R.id.closeSession:
                System.exit(0);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void deleteContact(String number){
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference().child(Constants.CONTACT_TABLE);
        // databaseReference.child("usrpedrin").setValue(new Contact("Brandon", "Ramsel","6474637367","brandon@gmail.com","usrpedrin"));
        databaseReference.orderByChild("telephone").equalTo(number).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                    postSnapshot.getRef().removeValue();
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value

            }
        });

    }

}