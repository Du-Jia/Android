package com.example.user.musicplayer.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.user.musicplayer.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class OnlineFragment extends Fragment {
    private ListView listView;
    private  String[] usaMusic = {"1、See you again-Wiz khalicafa","2、Take me to you heart - shaoshuai","3、Never say never -chaochen"};






    public OnlineFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_online, container, false);

        bangID(view);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1,usaMusic);

        listView.setAdapter(adapter);



        return view;
    }

    private void bangID(View view) {
        listView = view.findViewById(R.id.bfrag_list_lv4);
    }

}
