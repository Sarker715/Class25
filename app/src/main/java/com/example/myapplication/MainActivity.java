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

    private EditText etEmail;
    private EditText etPassword;

    private RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        etEmail = findViewById(R.id.et_email);
        etPassword = findViewById(R.id.et_password);

        requestQueue = Volley.newRequestQueue(this);


    }






    public void submitData(View view) {
        String url = "http://192.168.50.193/phpfiles/signup.php";

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


    }

    public void signIn(View view) {

        startActivity(new Intent(MainActivity.this, SignInActivity.class));

    }
}






