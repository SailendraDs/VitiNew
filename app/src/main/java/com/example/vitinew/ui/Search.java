package com.example.vitinew.ui;


import android.graphics.PorterDuff;
import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.vitinew.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class Search extends Fragment {


    public Search() {
        // Required empty public constructor
    }
    Toolbar toolbar;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        setuptoolbar();
        // Inflate the projectlist for this fragment
        return inflater.inflate(R.layout.fragment_search, container, false);
    }
   private void setuptoolbar(){
       toolbar=getActivity().findViewById(R.id.toolbar);
       toolbar.getMenu().clear();
       toolbar.getNavigationIcon().setColorFilter(getResources().getColor(R.color.black), PorterDuff.Mode.SRC_ATOP);
       toolbar.setBackgroundColor(getResources().getColor(R.color.white));
       toolbar.setTitleTextColor(getResources().getColor(R.color.black));
   }
}
