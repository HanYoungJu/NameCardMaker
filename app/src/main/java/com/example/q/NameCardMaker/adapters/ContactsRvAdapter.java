package com.example.q.NameCardMaker.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.q.NameCardMaker.R;
import com.example.q.NameCardMaker.fragments.FragmentContacts;
import com.example.q.NameCardMaker.models.ModelContacts;

import java.util.List;

public class ContactsRvAdapter extends RecyclerView.Adapter<ContactsRvAdapter.ViewHolder>{
    private Context mContext;
    private LayoutInflater inflater;
    private List<ModelContacts> mListContacts;

    public ContactsRvAdapter(Context context, List<ModelContacts> listContacts) {
        mContext = context;
        mListContacts = listContacts;
    }

    public List<ModelContacts> getContacts(){
        return mListContacts;
    }

    private ItemClick itemClick;

    public interface ItemClick{
        public void onClick(View view,int position);
    }


    public void setItemClick(ItemClick itemClick) {
        this.itemClick = itemClick;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        inflater = LayoutInflater.from(mContext);

        View view = inflater.inflate(R.layout.items_contacts, parent, false);

        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        TextView contact_name, contact_number, contact_email;
        contact_name = holder.contact_name;
        contact_number = holder.contact_number;
        contact_email = holder.contact_email;

        contact_name.setText(mListContacts.get(position).getName());
        contact_number.setText(mListContacts.get(position).getNumber());
        contact_email.setText(mListContacts.get(position).getEmail());
    }

    @Override
    public int getItemCount() {
        return mListContacts.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView contact_name, contact_number, contact_email;
        public ViewHolder(View itemView) {
            super(itemView);

            contact_name = itemView.findViewById(R.id.contact_name);
            contact_number = itemView.findViewById(R.id.contact_number);
            contact_email = itemView.findViewById(R.id.contact_email);
        }
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(v.getContext() , FragmentContacts.class);
            v.getContext().startActivity(intent);
        }
    }
}
