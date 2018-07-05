package com.example.q.NameCardMaker.fragments;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.q.NameCardMaker.ContactClick;
import com.example.q.NameCardMaker.R;
import com.example.q.NameCardMaker.RecyclerViewOnItemClickListener;
import com.example.q.NameCardMaker.adapters.ContactsRvAdapter;
import com.example.q.NameCardMaker.models.ModelContacts;

import java.util.ArrayList;
import java.util.List;

public class FragmentContacts extends Fragment {
    private View v;
    private RecyclerView recyclerView;

    public FragmentContacts() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.frag_contacts, container, false);
        recyclerView = v.findViewById(R.id.rv_contacts);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        RecyclerView.LayoutManager layoutManager = linearLayoutManager;
        recyclerView.setLayoutManager(layoutManager);

        ContactsRvAdapter adapter = new ContactsRvAdapter(getContext(), getContacts());

        recyclerView.setAdapter(adapter);
        final List<ModelContacts> contacts_list = adapter.getContacts();
        recyclerView.addOnItemTouchListener(new RecyclerViewOnItemClickListener(getActivity(), recyclerView,
                new RecyclerViewOnItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                Intent i = new Intent(getActivity().getApplicationContext(), ContactClick.class);
                i.putExtra("name", contacts_list.get(position).getName());
                i.putExtra("mobile_num", contacts_list.get(position).getMobile_num());
                i.putExtra("home_num", contacts_list.get(position).getHome_num());
                i.putExtra("email", contacts_list.get(position).getEmail());
                startActivity(i);
            }
            @Override
            public void onItemLongClick(View v, int position) {
                Intent i = new Intent(getActivity().getApplicationContext(), ContactClick.class);
                i.putExtra("name", contacts_list.get(position).getName());
                i.putExtra("mobie_num", contacts_list.get(position).getMobile_num());
                i.putExtra("home_num", contacts_list.get(position).getHome_num());
                i.putExtra("email", contacts_list.get(position).getEmail());
                startActivity(i);
            }
        } ));

        return v;
    }

    private List<ModelContacts> getContacts() {
        List<ModelContacts> list = new ArrayList<>();
        String[] arrEmailProjection = {
                ContactsContract.CommonDataKinds.Email.DATA
        };
        Cursor cursor = getContext().getContentResolver().query(ContactsContract.Contacts.CONTENT_URI,
                null, null, null, ContactsContract.Contacts.DISPLAY_NAME);

        int ididx = cursor.getColumnIndex(ContactsContract.Contacts._ID);
        cursor.moveToFirst();
        do {
            String id = cursor.getString(ididx);
            Cursor cursor2 = getContext().getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                    null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "=?", new String[]{id}, null);
            while(cursor2.moveToNext()) {
                String photo=null;
                String name = cursor2.getString(cursor2.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                String num = cursor2.getString(cursor2.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                String email = cursor2.getString(cursor2.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA));
                int type = cursor2.getInt(cursor2.getColumnIndex(ContactsContract.CommonDataKinds.Phone.TYPE));
                String mobile_num = null, home_num = null;
                switch (type) {
                    case ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE:
                        mobile_num = num;
                        break;
                    case ContactsContract.CommonDataKinds.Phone.TYPE_HOME:
                        home_num = num;
                        break;
                }
                list.add(new ModelContacts(photo, name, mobile_num, home_num, email));
            }
        }while (cursor.moveToNext());

        return list;
    }
}
