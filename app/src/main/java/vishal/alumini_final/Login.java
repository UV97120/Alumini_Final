package vishal.alumini_final;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Login extends AppCompatActivity {
    private static final String TAG = "LoginActivity";

    private EditText email;
    private EditText password;
    private JSONObject jsonObject;
    private TextView toRegister, toGuest, forgotPassword;
    private Button login;
    private String sendEmail;
    private String sendPassword;
    private String token;
    private RequestQueue queue;
    private RelativeLayout relativeLayout;

    private boolean user_login_status = false;

    String url = "http://jarvismedia.tech/final-ckp/android/login";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email = (EditText) findViewById(R.id.input_email_login);
        password = (EditText) findViewById(R.id.input_password_login);
        login = (Button) findViewById(R.id.btn_login);
        forgotPassword = (TextView)findViewById(R.id.forgotPassword);
        relativeLayout = (RelativeLayout)findViewById(R.id.scroll_view_login);

        final SharedPreferences sp = this.getSharedPreferences("user_credential", Context.MODE_PRIVATE);
        boolean user_status = sp.getBoolean("user_login_status", false);

        if(user_status){
            finish();
            startActivity(new Intent(getApplicationContext(), RegisteredHome.class));
        }

        toRegister = (TextView)findViewById(R.id.redirectRegister);
        toGuest = (TextView)findViewById(R.id.toGuest);


//        StringRequest stringRequest = new StringRequest(Request.Method.GET, "http://www.jarvismedia.tech/final-ckp/Auth/Login", new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//                fetch(response.toString());
//                Log.d("Token", token);
//
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//
//                Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_LONG).show();
//            }
//
//        });
//
//        queue = Volley.newRequestQueue(getApplicationContext());
//        queue.add(stringRequest);


//        Snackbar snackbar = Snackbar.make(relativeLayout ,token, Snackbar.LENGTH_INDEFINITE);
//        snackbar.show();
//        /*
        // * Login Loading progress Bar
        //* */
        assert login != null;
        login.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Log.d(TAG, "Login");
                login.setEnabled(false);

                final ProgressDialog progressDialog = new ProgressDialog(Login.this, R.style.AppTheme_NoActionBar_PopupOverlay);
                progressDialog.setIndeterminate(true);

                progressDialog.setMessage("Authenticating...");
                progressDialog.show();

                new android.os.Handler().postDelayed(
                        new Runnable() {
                            public void run() {
                                // On complete call either onLoginSuccess or onLoginFailed
                                //    onLoginSuccess();
                                // onLoginFailed();
                                progressDialog.dismiss();
                            }
                        }, 3000);
            }

        });
        jsonObject = new JSONObject();


        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                startActivity(new Intent(getApplicationContext(), ForgotPassword.class));
                startActivity(new Intent(getApplicationContext(), RegisteredHome.class));

            }
        });

        toRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(getApplicationContext(), RegisterActivity.class));
            }
        });

        toGuest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), Guest.class));
            }
        });




        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    jsonObject.put("email", email.getText());
                    jsonObject.put("password", password.getText());
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, jsonObject, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
//                        Toast.makeText(Login.this, response.toString(), Toast.LENGTH_LONG).show();
                        try {
                            if(response.getString("Status").equals("Success"))
                            {

                                user_login_status = true;
                                SharedPreferences.Editor editor = sp.edit();
                                editor.putString("email", email.getText().toString());
                                editor.putBoolean("user_login_status", user_login_status);
                                editor.commit();

                                Intent intent = new Intent(Login.this, RegisteredHome.class);
                                startActivity(intent);
                            }
                            else if (response.getString("Status").equals("Failure"))
                            {
                                Snackbar.make(findViewById(R.id.scroll_view_login),"Username or Password Incorrect", Snackbar.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        Log.d("bhosdike", response.toString());
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Login.this, error.toString(), Toast.LENGTH_LONG).show();
                        Log.d("bhosdike_lode", error.toString());

                    }
                });

                RequestQueue queue = Volley.newRequestQueue(Login.this);
                queue.add(jsonObjectRequest);
            }
        });
    }

    private void fetch(String response) {

        Document doc = Jsoup.parse(response);
        Elements element = doc.select("meta");
        for(Element elem:element) {

            if(elem.attr("name").equals("csrf-token") ){

                token = (elem.attr("content"));

            }

        }
    }
}