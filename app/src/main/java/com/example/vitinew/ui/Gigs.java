package com.example.vitinew.ui;


import android.graphics.PorterDuff;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.example.vitinew.Classes.SaveSharedPreference;
import com.example.vitinew.Classes.gigsClass;
import com.example.vitinew.Connections.UserController;
import com.example.vitinew.R;
import com.example.vitinew.Util.API;
import com.example.vitinew.Webrequest.ResponseListener;
import com.example.vitinew.Adapters.gigsAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.view.View.GONE;

/**
 * A simple {@link Fragment} subclass.
 */
public class Gigs extends Fragment {

    UserController userController;
    Toolbar toolbar;

    ProgressBar progressBarRegister;
    RecyclerView gigsRecyclerview;
    Button apply;
    private List<gigsClass> mygigs = new ArrayList<>();
    public Gigs() {
        // Required empty
        // public constructor
    }


    private final ResponseListener responseListener = new ResponseListener() {

        @Override
        public void onRequestStart() {
            progressBarRegister.setVisibility(View.VISIBLE);
        }

        @Override
        public void onSuccess(String response) {
            try {
                Log.d("str",response);
                JSONObject json = new JSONObject(response);
                JSONObject jsonObject = json.getJSONObject("response");
                String code=jsonObject.getString("code");
                mygigs.clear();
                switch(code){
                    case "SUCCESS":
                        JSONArray gigsarray=new JSONArray();
                        gigsarray= jsonObject.getJSONArray("gigs");
                        for(int i=0;i<gigsarray.length();i++){
                            JSONObject gigsObject=gigsarray.getJSONObject(i);
                            int id=gigsObject.getInt("id");
                            String cats=gigsObject.getString("cats");
                            int per_cost=gigsObject.getInt("per_cost");
                            String gigs_title=gigsObject.getString("campaign_title");
                            String gigs_description=gigsObject.getString("description");
                            String user_id=gigsObject.getString("user_id");
                            String brand=gigsObject.getString("brand");
                            String logo=gigsObject.getString("logo");
                            gigsClass thisgig = new gigsClass();
                           thisgig.setBrand(brand);
                           thisgig.setId(id);
                           thisgig.setUser_id(user_id);
                           thisgig.setPer_cost(per_cost);
                           thisgig.setCampaign_title(gigs_title);
                           thisgig.setCats(cats);
                           thisgig.setDescription(gigs_description);
                           thisgig.setLogo("http://herody.in/assets/employer/profile_images/"+logo);
                           mygigs.add(thisgig);
                        }
                        gigsRecyclerview.setLayoutManager(new LinearLayoutManager(getContext()));
                        gigsAdapter adapter = new gigsAdapter(mygigs);
                        // Attach the adapter to the recyclerview to populate items
                        gigsRecyclerview.setAdapter(adapter);
                        gigsRecyclerview.setHasFixedSize(true);


                        break;
                    default:
                        Toast.makeText(getContext(), "something wrong", Toast.LENGTH_SHORT).show();
                        break;

                }

            } catch (JSONException e) {
                e.printStackTrace();
            } finally {
                progressBarRegister.setVisibility(GONE);
            }
        }

        @Override
        public void onError(VolleyError error) {
            String s = "";
            progressBarRegister.setVisibility(GONE);

        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the projectlist for this fragment
        View view=inflater.inflate(R.layout.fragment_gigs, container, false);
        setuptoolbar();
         gigsRecyclerview = view.findViewById(R.id.gigsRecyclerView);
        progressBarRegister=view.findViewById(R.id.progressBargigs);
        // Initialize contacts
        //api call
        userController = new UserController(getContext());

        Map<String, String> dataMap = new HashMap<String,String>();
        dataMap.put("uid",String.valueOf(SaveSharedPreference.getUserId(getContext())));
        userController.getRequest(dataMap, API.Gigs,responseListener);

        // Create adapter passing in the sample user data

        // Set projectlist manager to position the items

        return view;
    }

    private void setuptoolbar(){
        toolbar=getActivity().findViewById(R.id.toolbar);
        toolbar.getMenu().clear();
        toolbar.inflateMenu(R.menu.mission_menu);
        toolbar.getNavigationIcon().setColorFilter(getResources().getColor(R.color.black), PorterDuff.Mode.SRC_ATOP);
        toolbar.setBackgroundColor(getResources().getColor(R.color.white));
        toolbar.setTitleTextColor(getResources().getColor(R.color.black));
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }



}
