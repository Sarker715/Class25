package com.example.myapplication;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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

import java.util.HashMap;
import java.util.Map;


/*

step 1: layout design
step 2: TextView EditText initialization
step 3: RequestQueue inititalization
step 4: StringRequest initializations
step 5: add stringrequest to requestqueue
step 6: install xamp server > database create > table create
step 7: cmd > ipconfig > ip

 */

public class MainActivity extends AppCompatActivity {
    private TextView tvResult;
    private EditText etEmail;
    private EditText etPassword;

    private RequestQueue requestQueue;
    private ProgressDialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvResult = findViewById(R.id.tv_result);
        etEmail = findViewById(R.id.et_email);
        etPassword = findViewById(R.id.et_password);

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
        String url = "http://192.168.1.112/phpfiles/index.php";

        final StringBuilder builder = new StringBuilder();

        showDialog();

        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray root = new JSONArray(response);
                    for(int i = 0; i<root.length(); i++) {
                        JSONObject student = (JSONObject) root.get(i);
                        builder.append(student.getString("email")+"\n");
                        builder.append(student.getString("password") + "\n\n");
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

    public void submitData(View view) {
        String url = "http://192.168.1.112/phpfiles/signup.php";

        final String email = etEmail.getText().toString();
        final String password = etPassword.getText().toString();

        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(MainActivity.this, response, Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();

                params.put("email", email);
                params.put("password", password);

                return params;
            }
        };

        requestQueue.add(request);

        fetchData(view);
    }
}






