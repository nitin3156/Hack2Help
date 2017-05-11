package com.complaints.jd.h2h;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {
    EditText username,password;
    Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setTitle("Login!");
        username=(EditText)findViewById(R.id.username);
        password=(EditText)findViewById(R.id.password);
        button=(Button)findViewById(R.id.login);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user=username.getText().toString();
                String pass=password.getText().toString();
                validate(user,pass);
            }
        });
    }



    public void onGuest(View view) {
        Intent intent=new Intent(LoginActivity.this,MainTabActivity.class);
        intent.putExtra("guest","guest");
        SharedPreferences sharedPreferences = getSharedPreferences("settings", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("COMID",null);
        editor.putBoolean("ISGUEST",true);
        editor.apply();
        startActivity(intent);
    }
    String mypass;String checkpass,comid;
    public void validate(String user , String pass)
    {
        mypass=pass;
        StringRequest stringRequest = new StringRequest("http://kmzenon.pe.hu/app/getUser.php?tin="+user, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray result = jsonObject.getJSONArray("result");
                    JSONObject crop_json = result.getJSONObject(0);
                    checkpass=crop_json.getString("password");
                    comid=crop_json.getString("comid");

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if(mypass.equals(checkpass)) {
                    Intent intent1 = new Intent(getApplicationContext(), MainTabActivity.class);
                    SharedPreferences sharedPreferences = getSharedPreferences("settings", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("COMID",comid);
                    editor.putBoolean("ISGUEST",false);
                    editor.apply();
//                    intent1.putExtra("comid",comid);
//                    intent1.putExtra("guest","");
                    startActivity(intent1);
                }
                else
                    Toast.makeText(getApplicationContext(),"Wrong Password",Toast.LENGTH_SHORT).show();
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Toast.makeText(mycontext,error.getMessage().toString(),Toast.LENGTH_LONG).show();
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }

    public void onRegister(View view) {
        startActivity(new Intent(LoginActivity.this,RegistrationActivity.class));
    }
}
