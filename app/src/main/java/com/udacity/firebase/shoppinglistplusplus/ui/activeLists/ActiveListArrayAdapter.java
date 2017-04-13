package com.udacity.firebase.shoppinglistplusplus.ui.activeLists;

import android.app.Activity;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.udacity.firebase.shoppinglistplusplus.R;
import com.udacity.firebase.shoppinglistplusplus.model.ShoppingList;

import java.util.List;

/**
 * Populates the list_view_active_lists inside ShoppingListsFragment
 */
public class ActiveListArrayAdapter extends ArrayAdapter<ShoppingList> {
    public ActiveListArrayAdapter(FragmentActivity context, int resource, List<ShoppingList> objects) {
        super(context, resource, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = ((Activity) getContext()).getLayoutInflater().inflate(R.layout.single_active_list, parent, false);
        }

        /**
         * Grab the needed Textivews and strings
         */
        TextView textViewListName = (TextView) convertView.findViewById(R.id.text_view_list_name);
        TextView textViewCreatedByUser = (TextView) convertView.findViewById(R.id.text_view_created_by_user);

        ShoppingList list = getItem(position);

        textViewListName.setText(list.getListName());
        textViewCreatedByUser.setText(list.getOwner());

        return convertView;
    }
}
