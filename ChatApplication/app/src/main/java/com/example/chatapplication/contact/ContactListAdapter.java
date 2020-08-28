package com.example.chatapplication.contact;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.chatapplication.R;
import com.example.chatapplication.entity.Contact;

import java.util.List;

public class ContactListAdapter extends BaseAdapter {
    private String[] contactsName, contactsNumber, contactsEmail;
    private LayoutInflater inflter;
    private List<Contact> contactList;
    private Context context;
    private ImageView contactImg;
    private TextView contactName, contactNumber, contactEmail;

    public ContactListAdapter(List contactList, Context context){
        this.contactList = contactList;
        this.context = context;
        inflter = (LayoutInflater.from(context));
    }
    @Override
    public int getCount() {
        return contactList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        view = inflter.inflate(R.layout.activity_contact_list_row, null);
        contactImg = view.findViewById(R.id.contactImg);
        contactName = view.findViewById(R.id.contactName);
        contactNumber = view.findViewById(R.id.contactNumber);
        contactEmail = view.findViewById(R.id.contactEmail);
        contactName.setText(contactList.get(position).getFirstName() +" "+ contactList.get(position).getLastName());
        contactNumber.setText(contactList.get(position).getTelephone());
        contactEmail.setText(contactList.get(position).getEmail());
        return view;
    }
}
