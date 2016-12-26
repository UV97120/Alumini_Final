    package vishal.alumini_final;

    import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

    public class Profile_settings extends AppCompatActivity {

        private final int SELECT_PHOTO = 1;
        private static int RESULT_LOAD_IMAGE = 1;
        private ImageView imageView;
        private String url = "http://www.jarvismedia.tech/final-ckp/android/profiledetails/";
        private RequestQueue queue;
        private boolean status = true;
        private CollapsingToolbarLayout collapsingToolbarLayout = null;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_profile_settings);

            imageView = (ImageView) findViewById(R.id.profile_id);

            final SharedPreferences sp = this.getSharedPreferences("user_credential", Context.MODE_PRIVATE);
            String email = sp.getString("email", "No-Email");

            Log.d("email", email);
            if(status){
                url = url+email;
                status = false;
            }
            StringRequest stringRequest = new StringRequest(Request.Method.GET,url , new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("response", response);
                Toast.makeText(getApplicationContext(), response.toString(), Toast.LENGTH_LONG).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_LONG).show();
            }

        });

        queue = Volley.newRequestQueue(getApplicationContext());
        queue.add(stringRequest);


        }

    }










