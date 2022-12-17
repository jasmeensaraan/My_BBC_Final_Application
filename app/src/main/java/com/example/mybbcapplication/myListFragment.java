package com.example.mybbcapplication;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.ListFragment;

import com.google.android.material.snackbar.Snackbar;

public class myListFragment extends Fragment implements AdapterView.OnItemClickListener {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_desc, container,false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        String[] title = {"Twitter condemned by UN and EU over reporters’ ban"
                , "Thousands of unedited government JFK assassination files released"
                ,"A third of US executions botched in 2022 - report"
                , "Nigerian child chess prodigy granted US asylum"
                , "Jane Fonda: Hollywood star 'feels blessed' her cancer is in remission"
                , "Starbucks set for walkouts in US over unionisation"
                , "Former US President Donald Trump launches $99 NFT trading cards"
                , "Two boaters and a dog rescued after 10 days at sea"
                , "Idaho student murders: Mother Kristi Goncalves describes 'sleepless nights'"};

        ListView myList = view.findViewById(R.id.list);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, title);

        myList.setAdapter(adapter);
        myList.setOnClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (position == 0){
            Snackbar.make(view,"", Snackbar.LENGTH_LONG).show();
        }
    }
}
