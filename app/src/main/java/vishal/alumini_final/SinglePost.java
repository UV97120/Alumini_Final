package vishal.alumini_final;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static vishal.alumini_final.R.id.post_image;

public class SinglePost extends AppCompatActivity {

    private String postid=null;
    private String url = "http://jarvismedia.tech/final-ckp/android/singlepost/";
    private String urlImage = "http://jarvismedia.tech/final-ckp/files/image/";
    private TextView title, desc, desig, ref, branch, name, time, platform;
    private ImageView postImage;
    private RequestQueue postReqQue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_post);

        postid = getIntent().getStringExtra("postid");
        Log.d("postid", postid);
        url = url+postid;

        title = (TextView)findViewById(R.id.sp_title);
        desc = (TextView)findViewById(R.id.sp_desc);
        desig = (TextView)findViewById(R.id.sp_designation);
        ref = (TextView)findViewById(R.id.sp_reference);
        branch = (TextView)findViewById(R.id.sp_branch);
        name = (TextView)findViewById(R.id.sp_user);
        time = (TextView)findViewById(R.id.sp_date);
        platform = (TextView)findViewById(R.id.sp_platform);
        postImage = (ImageView)findViewById(post_image);

        JSONObject jsonObject = new JSONObject();

        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.POST, url, jsonObject,new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("response", response.toString());
//                        Toast.makeText(getApplicationContext(), response.toString(), Toast.LENGTH_LONG).show();


                        try {
                            JSONObject resp = (JSONObject) response.get("Post");
                            String resp2 = response.getString("Name");
                            title.setText(resp.getString("title"));
                            platform.setText(resp.getString("technology"));
                            desig.setText(resp.getString("designation"));
                            desc.setText(resp.getString("description"));
                            ref.setText(resp.getString("reference"));
                            branch.setText(resp.getString("branchpost"));
                            time.setText(resp.getString("created_at"));
                            name.setText(resp2);
                            String Fname = resp.getString("filename");

                            urlImage = urlImage+Fname;

                            Log.d("pathToImg", urlImage);

                            Uri uri = Uri.parse(urlImage);
                            Picasso.with(getApplicationContext()).load(uri).into(postImage);

                        } catch (JSONException e1) {
                            e1.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_LONG).show();
                        Log.d("Error", error.toString());

                        NetworkResponse response = error.networkResponse;
                        if(response != null && response.data != null){

                            // Log.d("ERROR_MESSAGE", response.data.);
                        }
                        //Additional cases
                    }

                }){

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {

                Map<String, String> header = new HashMap<String, String>();
                header.put("Content-Type", "application/json");
//                header.put("X-CSRF-TOKEN", token);
                return header;
            }
        };



        // if (flag) {
        postReqQue = Volley.newRequestQueue(getApplicationContext());
        postReqQue.add(jsObjRequest);//year selected

    }

}
