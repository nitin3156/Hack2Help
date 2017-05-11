package com.complaints.jd.h2h;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MakeBiddingActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener{

    List<CenterBiddingData> data = new ArrayList<>();
    ArrayList<String> arrayList = new ArrayList<>();
    ArrayList<String> arrayList1 = new ArrayList<>();
    CenterBiddingAdapter mAdapter;
    SwipeRefreshLayout swipeRefreshLayout;
    FloatingActionButton done;
String comid,quan1,st,cid;
    EditText price,quant;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_bidding);
        setTitle("Center Information");
        st=getIntent().getExtras().getString("msp");
         quan1=getIntent().getExtras().getString("quan");
         comid=getIntent().getExtras().getString("com");
        cid=getIntent().getExtras().getString("cid");
        TextView msp=(TextView)findViewById(R.id.msp);
        TextView quan=(TextView)findViewById(R.id.production);
        msp.setText(st);
        quan.setText(quan1);
        //Toast.makeText(getApplicationContext(),st,Toast.LENGTH_SHORT).show();
        //Initiating recycler view
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycleViewContainer);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        getData(getApplicationContext());
       swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe);
        mAdapter = new CenterBiddingAdapter(data);
       swipeRefreshLayout.setOnRefreshListener(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(mAdapter);

        //Moving to next activity on clicking of the done button
        done = (FloatingActionButton) findViewById(R.id.fab);
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                checking if the user entry is valid before moving to the next activity.
                //Alert dialog as confirmation to order placed

                LayoutInflater li = LayoutInflater.from(getApplicationContext());
                final View promptView = li.inflate(R.layout.bid_view,null);

                AlertDialog.Builder builder = new AlertDialog.Builder(MakeBiddingActivity.this);
                builder.setView(promptView)

                        .setPositiveButton("Make Bid", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                price=(EditText)promptView.findViewById(R.id.bid_price);
                                quant=(EditText)promptView.findViewById(R.id.bid_quantity);
                                Toast.makeText(getApplicationContext(),price.getText().toString(),Toast.LENGTH_SHORT).show();
                                Toast.makeText(getApplicationContext(),comid,Toast.LENGTH_SHORT).show();
                                StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://kmzenon.pe.hu/app/postbid.php", new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        Toast.makeText(getApplicationContext(),cid,Toast.LENGTH_SHORT).show();
                                    }
                                }, new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        //   Toast.makeText(mContext, error.toString(), Toast.LENGTH_SHORT).show();
                                    }
                                }) {
                                    @Override
                                    protected Map<String, String> getParams() throws AuthFailureError {
                                        Map<String, String> params = new HashMap<String, String>();
                                        params.put("comid",comid);
                                        params.put("cid",cid);
                                        params.put("crop","Wheat");
                                        params.put("price",price.getText().toString());
                                        params.put("quantity",quant.getText().toString());
                                        return params;
                                    }
                                };
                                RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                                requestQueue.add(stringRequest);
                                Toast.makeText(getApplicationContext(),"User Registered",Toast.LENGTH_SHORT).show();
                                //startActivity(new Intent(getApplicationContext(),MainTabActivity.class));


                            }
                        })
                         .setNegativeButton("Cancel",new DialogInterface.OnClickListener() {
                             @Override
                             public void onClick(DialogInterface dialog, int which) {
                                 dialog.dismiss();
                             }
                         });
                builder.show();
            }
        });

    }

    @Override
    public void onRefresh() {
      data.clear();
        getData(getApplicationContext());
        arrayList.clear();
        arrayList1.clear();
    }

    public void getData(Context context){
        String url = "http://kmzenon.pe.hu/app/centerbidding.php?cid="+"1";
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

        RequestQueue requestQueue = Volley.newRequestQueue(MakeBiddingActivity.this);
        requestQueue.add(stringRequest);
    }
    private void showJSON(String response) {
        String price;
        String quantity;
        // Toast.makeText(mycontext,response,Toast.LENGTH_SHORT).show();
        try {
            JSONArray contacts = new JSONArray(response);
            for (int j = 0; j < contacts.length(); j++) {
                JSONObject c = contacts.getJSONObject(j);
                price = c.getString("price");
                arrayList.add(price);
                quantity = c.getString("quantity");
                arrayList1.add(quantity);

            }
            prepare();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    private void prepare() {
        for (int i = 0; i < arrayList.size(); i++) {

            String price = arrayList.get(i);
            String quantity = arrayList1.get(i);
            CenterBiddingData a = new CenterBiddingData(price, quantity);
            data.add(a);

        }

        mAdapter.notifyDataSetChanged();
        swipeRefreshLayout.setRefreshing(false);
    }

}
