package vishal.alumini_final;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
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

public class ForgotPassword extends AppCompatActivity {

    private EditText email;
    private TextView submit;
    private String url = "http://jarvismedia.tech/final-ckp/android/resetpassword/";
    private RequestQueue queue;
    private boolean password_flag = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);


        email = (EditText)findViewById(R.id.registered_emailid);
        submit = (TextView)findViewById(R.id.forgot_button);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(password_flag==false){
                    url = url+email.getText().toString();
                    password_flag = true;
                }

                JSONObject jsonObject = new JSONObject();
                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, jsonObject, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
//                        Toast.makeText(Login.this, response.toString(), Toast.LENGTH_LONG).show();
                        try {
                            if(response.get("Status").equals("Success")) {
                                Toast.makeText(getApplicationContext(), "password updated", Toast.LENGTH_LONG).show();
                            }
                            else{
                                Toast.makeText(getApplicationContext(), "password updation failed", Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_LONG).show();
                        Log.d("bhosdike_lode", error.toString());

                    }
                });

                queue = Volley.newRequestQueue(getApplicationContext());
                queue.add(jsonObjectRequest);
            }
        });
    }
}
