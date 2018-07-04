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
                i.putExtra("number", contacts_list.get(position).getNumber());
                startActivity(i);
            }
            @Override
            public void onItemLongClick(View v, int position) {
                Intent i = new Intent(getActivity().getApplicationContext(), ContactClick.class);
                i.putExtra("name", contacts_list.get(position).getName());
                i.putExtra("number", contacts_list.get(position).getNumber());
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
        int nameidx = cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME);
        cursor.moveToFirst();
        while (cursor.moveToNext()) {

            String name = cursor.getString(nameidx);
            String id = cursor.getString(ididx);

            Cursor cursor2 = getContext().getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                    null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "=?", new String[]{id}, null);

            int typeidx = cursor2.getColumnIndex(ContactsContract.CommonDataKinds.Phone.TYPE);
            int numidx = cursor2.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
            String mobile_num = null;
            String home_num = null;
            while(cursor2.moveToNext()) {
                String num = cursor2.getString(numidx);
                switch (cursor2.getInt(typeidx)) {
                    case ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE:
                        mobile_num = "휴대폰) "+num;
                        break;
                    case ContactsContract.CommonDataKinds.Phone.TYPE_HOME:
                        mobile_num = "집) "+num;
                        break;
                }
            }
            //이메일 파트
            String strContactId = cursor.getString(0);
            Cursor cursor3 = getContext().getContentResolver().query(
                    ContactsContract.CommonDataKinds.Email.CONTENT_URI,
                    arrEmailProjection,
                    ContactsContract.CommonDataKinds.Phone.CONTACT_ID+" = "+strContactId,
                    null,null);
            String email = null;
            while(cursor3.moveToNext()){
                email = cursor3.getString(0);
            }
            cursor3.close();
            list.add(new ModelContacts(name, mobile_num, email));
        }

        return list;
    }
}
