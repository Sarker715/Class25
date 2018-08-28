package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
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

public class SignInActivity extends AppCompatActivity {
    private EditText etEmail;
    private EditText etPassword;
    private RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        etEmail = findViewById(R.id.et_login_email);
        etPassword = findViewById(R.id.et_login_password);
        requestQueue = Volley.newRequestQueue(this);
    }

    public void signIn(View view) {

        String url = "http://192.168.50.193/phpfiles/signin.php";

        final String email = etEmail.getText().toString();
        final String password = etPassword.getText().toString();


        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject root = new JSONObject(response);
                    JSONArray result = root.getJSONArray("result");
                    JSONObject user = result.getJSONObject(0);

                    if (user.getString("error").equalsIgnoreCase("no")) {

                        startActivity(new Intent(SignInActivity.this, ProfileActivity.class));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(SignInActivity.this, "Error Detected", Toast.LENGTH_SHORT).show();

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
    }
}
