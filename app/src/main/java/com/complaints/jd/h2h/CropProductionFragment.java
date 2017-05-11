package com.complaints.jd.h2h;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class CropProductionFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
        private static String COMID;
    RecyclerView recyclerView;
    SwipeRefreshLayout swipeRefreshLayout;
    List<Crop> cropList=new ArrayList<>();
String company;
    public CropProductionFragment(String companyid) {
        company=companyid;
    }
    public Context mycontext;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mycontext = container.getContext();
        Toast.makeText(mycontext,company,Toast.LENGTH_SHORT).show();
        return inflater.inflate(R.layout.fragment_crop_production, container, false);
    }

    @Override
    public void onRefresh() {
        cropList.clear();
        arrayList.clear();
        arrayList1.clear();
        arrayList2.clear();
        arrayList3.clear();
        getData();
    }
    ArrayList<String> arrayList = new ArrayList<>();
    ArrayList<String> arrayList1 = new ArrayList<>();
    ArrayList<String> arrayList2 = new ArrayList<>();
    ArrayList<String> arrayList3 = new ArrayList<>();
    ArrayList<String> arrayList4 = new ArrayList<>();
    String center,quantity,croptype,msp,centerid;

    public void getData()
    {
        //loading = ProgressDialog.show(mycontext,"Please wait...","Fetching...",false,false);

        String url = "http://kmzenon.pe.hu/app/production.php";
        // Toast.makeText(mycontext,"getData",Toast.LENGTH_LONG).show();
        StringRequest stringRequest = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                // loading.dismiss();
                // Toast.makeText(getApplicationContext(),response,Toast.LENGTH_LONG).show();
                showJSON(response);
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //Toast.makeText(mycontext,error.getMessage().toString(),Toast.LENGTH_LONG).show();
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(mycontext);
        requestQueue.add(stringRequest);
    }
    private void showJSON(String response) {
        String pro = "";
        String bar = "";
        String pr = "";
        String image = "";
        String rat = "";
        String id = "";
        // Toast.makeText(mycontext,response,Toast.LENGTH_SHORT).show();
        try {
            JSONArray contacts = new JSONArray(response);
            for (int j = 0; j < contacts.length(); j++) {
                JSONObject c = contacts.getJSONObject(j);
                pro = c.getString("district");
                arrayList.add(pro);
                bar = c.getString("msp");
                arrayList1.add(bar);
                image = c.getString("crop");
                arrayList2.add(image);
                pr = c.getString("sum(crop.quantity)");
                arrayList3.add(pr);
                centerid=c.getString("cid");
                arrayList4.add(centerid);


            }
            prepare();
        } catch (JSONException e) {
            e.printStackTrace();
            }
    }
    /**
     * Initializing collapsing toolbar
     * Will show and hide the toolbar title on scroll
     */


    /**
     * Adding few albums for testing
     */
    private void prepare() {
        for (int i = 0; i < arrayList.size(); i++) {

            String product = arrayList.get(i);
            String bar = arrayList1.get(i);
            String imgsrc = arrayList2.get(i);
            String price = arrayList3.get(i);
            String id= arrayList4.get(i);
            Crop a = new Crop(product, bar,"http://kmzenon.pe.hu/app/wheat.jpg",price,id,company);
            cropList.add(a);

        }

        adapter.notifyDataSetChanged();
        swipeRefreshLayout.setRefreshing(false);
    }

    CropAdapter adapter;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        recyclerView=(RecyclerView)getActivity().findViewById(R.id.recyclerview);
        swipeRefreshLayout=(SwipeRefreshLayout)getActivity().findViewById(R.id.swipe);
        adapter = new CropAdapter(mycontext, cropList);
        swipeRefreshLayout.setOnRefreshListener(this);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(mycontext, 1);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
        onRefresh();
    }
}
