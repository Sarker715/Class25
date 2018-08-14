package com.example.myapplication;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
    private TextView tvResult;

    private RequestQueue requestQueue;
    private ProgressDialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvResult = findViewById(R.id.tv_result);
        requestQueue = Volley.newRequestQueue(this);

        dialog = new ProgressDialog(this);
        dialog.setTitle("Loading Data");
        dialog.setMessage("Please wait...");
        dialog.setCancelable(false);

    }

    private void showDialog(){
        if (!dialog.isShowing()){
            dialog.show();
        }
    }

    private void cancelDialog(){
        if(dialog.isShowing()){
            dialog.cancel();
        }
    }


    public void fetchData(View view) {
        String url = "http://biswajitdas.me/projects/studytools/index.php";

        final StringBuilder builder = new StringBuilder();

        showDialog();

        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray root = new JSONArray(response);
                    for(int i=0; i<root.length(); i++) {
                        JSONObject student = (JSONObject) root.get(i);
                        builder.append(student.getString("name")+"\n");
                        builder.append(student.getString("email")+"\n");
                        builder.append(student.getString("mobile")+"\n\n");
                    }

                    cancelDialog();
                    tvResult.setText(builder.toString());
                } catch (JSONException e) {
                    cancelDialog();
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                cancelDialog();
                Toast.makeText(MainActivity.this, "Failed to Fetch Data", Toast.LENGTH_SHORT).show();
            }
        });

        requestQueue.add(request);
    }
}
