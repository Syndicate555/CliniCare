package com.example.intercrew;

/**
 * Created by Miguel Garzón on 2017-05-09.
 */

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;


public class UserList extends ArrayAdapter<Account> {
    private Activity context;
    List<Account> accounts;


    public UserList(Activity context, List<Account> accounts) {
        super(context, R.layout.layout_users_list, accounts);
        this.context = context;
        this.accounts = accounts;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.layout_users_list, null, true);

        TextView textViewName = (TextView) listViewItem.findViewById(R.id.textViewUserName);
        TextView textViewEmail= (TextView) listViewItem.findViewById(R.id.textViewUserEmail);
        TextView textViewRole= (TextView) listViewItem.findViewById(R.id.textViewUserRole);

        Account account = accounts.get(position);

        User user=account.getUser();


        if (user != null) {
            textViewEmail.setText(user.getEmail());
            textViewName.setText(user.getFirstName());

        }

        textViewRole.setText("Role: "+account.getRole());
        return listViewItem;
    }
}

