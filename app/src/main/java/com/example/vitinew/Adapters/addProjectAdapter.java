package com.example.vitinew.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.VolleyError;
import com.example.vitinew.Classes.Addskills;
import com.example.vitinew.Classes.ProjectDetails;
import com.example.vitinew.Connections.UserController;
import com.example.vitinew.R;
import com.example.vitinew.Util.API;
import com.example.vitinew.Webrequest.ResponseListener;
import com.example.vitinew.ui.ResumeExtra.AddProject;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class addProjectAdapter  extends RecyclerView.Adapter<addProjectAdapter.myskillholder> {
    private List< ProjectDetails> cartlist=new ArrayList<>();
   Context context;

    public addProjectAdapter(List<ProjectDetails> cartlist, Context context) {
        this.cartlist = cartlist;
        this.context = context;
    }

    public addProjectAdapter(List< ProjectDetails> cartlist) {
        this.cartlist = cartlist;
        //  this.onClick = onClick;
    }

    public interface OnItemClicked {
        void onbuttonclicked(int position);
        //   void onbidclicked(int position);
    }


    @NonNull
    @Override
    public myskillholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemview= LayoutInflater.from(parent.getContext())
                .inflate(R.layout.itemprojectresume,parent,false);
        return new addProjectAdapter.myskillholder(itemview);
    }

    @Override
    public void onBindViewHolder(@NonNull myskillholder holder, int position) {
        final ProjectDetails currentnote=cartlist.get(position);
        holder.title.setText(currentnote.getTitle());
        holder.desc.setText(currentnote.getDesc());
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id=cartlist.get(position).getId();
                UserController user=new UserController(context);
                JSONObject jsonObject=new JSONObject();
                try {
                    jsonObject.put("id",id);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                user.postWithJsonRequest(API.DELETEPROJECT,jsonObject,getallProjectsListener);
                cartlist.remove(position);
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return cartlist.size();
    }

    class myskillholder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView title,desc;
        private ImageView delete;
        public myskillholder(@NonNull View itemView) {
            super(itemView);
            delete=itemView.findViewById(R.id.deleteicon);

            title=itemView.findViewById(R.id.title);
            desc=itemView.findViewById(R.id.desc);
            //        Removecart.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

        }
    }

    private final ResponseListener getallProjectsListener = new ResponseListener() {

        @Override
        public void onRequestStart() {

        }

        @Override
        public void onSuccess(String response) {
            try {
                JSONObject json = new JSONObject(response);
                JSONObject jsonObject = json.getJSONObject("response");
                String code = jsonObject.getString("code");
                switch (code) {
                    case "SUCCESS":
                        Toast.makeText(context, "Deleted Successful", Toast.LENGTH_SHORT).show();
                        break;
                    default:
                        Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show();
                        break;

                }
            }
            catch (JSONException e) {
                e.printStackTrace();
            } finally {
            }
        }
        @Override
        public void onError(VolleyError error) {
            String s = "";

        }
    };
}
