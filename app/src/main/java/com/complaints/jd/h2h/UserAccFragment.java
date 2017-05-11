package com.complaints.jd.h2h;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

public class UserAccFragment extends Fragment {

String comid1;
    public UserAccFragment(String comid) {
        comid1=comid;
    }


Context mycontext1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mycontext1=container.getContext();
        return inflater.inflate(R.layout.fragment_user_acc, container, false);
    }

    // TODO: Rename method, update argument and hook method into UI event


    TextView tin,comp,loc,user;
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        tin=(TextView)getActivity().findViewById(R.id.tinNo1);
        comp=(TextView)getActivity().findViewById(R.id.companyName1);
        loc=(TextView)getActivity().findViewById(R.id.location1);
        user=(TextView)getActivity().findViewById(R.id.username);
        String url = "http://kmzenon.pe.hu/app/getacc.php?comid="+comid1;
        Toast.makeText(mycontext1,url,Toast.LENGTH_SHORT).show();
        StringRequest stringRequest = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                show(response);
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(mycontext1);
        requestQueue.add(stringRequest);
    }
    public  void show(String response)
    {
        try {
            Toast.makeText(mycontext1,response,Toast.LENGTH_SHORT).show();
            JSONObject acc = new JSONObject(response);
            JSONArray result = acc.getJSONArray("result");
            JSONObject c = result.getJSONObject(0);
            tin.setText(c.getString("tin"));
            comp.setText(c.getString("company"));
            loc.setText(c.getString("location"));
            user.setText(c.getString("owner"));
            Toast.makeText(mycontext1,c.getString("tin"),Toast.LENGTH_SHORT).show();
        }
        catch (Exception e) {
        }
    }

    }

