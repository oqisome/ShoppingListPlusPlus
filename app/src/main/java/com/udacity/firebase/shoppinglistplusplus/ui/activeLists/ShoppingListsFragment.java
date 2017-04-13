package com.udacity.firebase.shoppinglistplusplus.ui.activeLists;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.udacity.firebase.shoppinglistplusplus.R;
import com.udacity.firebase.shoppinglistplusplus.model.ShoppingList;
import com.udacity.firebase.shoppinglistplusplus.ui.activeListDetails.ActiveListDetailsActivity;
import com.udacity.firebase.shoppinglistplusplus.utils.Constants;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass that shows a list of all shopping lists a user can see.
 * Use the {@link ShoppingListsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ShoppingListsFragment extends Fragment {
    private ActiveListArrayAdapter mActiveListAdapter;
    private ListView mListView;
    private ValueEventListener mActiveListRefListener;
    private ShoppingList mShoppingList;
    private List<ShoppingList> shoppingLists;
    private List<String> shoppingKeyLists;
    private DatabaseReference activeListsRef;


    /**
     * Create fragment and pass bundle with data as it's arguments
     * Right now there are not arguments...but eventually there will be.
     */
    public static ShoppingListsFragment newInstance() {
        ShoppingListsFragment fragment = new ShoppingListsFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * Initialize instance variables with data from bundle
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
        shoppingLists = new ArrayList<>();
        shoppingKeyLists = new ArrayList<>();
        activeListsRef = FirebaseDatabase.getInstance().getReferenceFromUrl(Constants.FIREBASE_URL_ACTIVE_LISTS);
        mActiveListAdapter = new ActiveListArrayAdapter(getActivity(), R.layout.single_active_list, shoppingLists);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        /**
         * Initialize UI elements
         */
        View rootView = inflater.inflate(R.layout.fragment_shopping_lists, container, false);
        initializeScreen(rootView);

        mActiveListRefListener = activeListsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                shoppingLists.clear();
                shoppingKeyLists.clear();
                for (DataSnapshot childSnapshot: dataSnapshot.getChildren()) {
                    ShoppingList shoppingList = childSnapshot.getValue(ShoppingList.class);
                    String shoppingListKey = childSnapshot.getKey();
                    shoppingLists.add(shoppingList);
                    shoppingKeyLists.add(shoppingListKey);
                }
                Log.d("-----------------onDataChange", Integer.toString(shoppingLists.size()));
                mActiveListAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        });

        return rootView;
    }

    /**
     * Cleanup the adapter when activity is destroyed.
     */
    @Override
    public void onDestroy() {
        super.onDestroy();
        mActiveListAdapter.notifyDataSetChanged();
    }

    @Override
    public void onPause(){
        super.onPause();
        Log.d("-----------------onPause", Integer.toString(shoppingLists.size()));
    }

    @Override
    public void onResume(){
        super.onResume();
        Log.d("-----------------onResume", Integer.toString(shoppingLists.size()));
    }

    /**
     * Link list view from XML
     */
    private void initializeScreen(View rootView) {
        mListView = (ListView) rootView.findViewById(R.id.list_view_active_lists);
        mListView.setAdapter(mActiveListAdapter);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ShoppingList selectedList = mActiveListAdapter.getItem(position);

                if (selectedList != null) {
                    Intent intent = new Intent(getActivity(), ActiveListDetailsActivity.class);
                    String listId = shoppingKeyLists.get(position);
                    intent.putExtra(Constants.KEY_LIST_ID, listId);
                    /* Starts an active showing the details for the selected list */
                    startActivity(intent);
                     }
                    }
                    });
            }
}
