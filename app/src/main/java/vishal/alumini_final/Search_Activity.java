package vishal.alumini_final;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import vishal.alumini_final.adapter.PostAdapter;
import vishal.alumini_final.model.PostInformation;

public class Search_Activity extends AppCompatActivity {
    private ArrayList<PostInformation> postResults = new ArrayList<PostInformation>();
    private ArrayList<PostInformation> filteredResults = new ArrayList<PostInformation>();
    private String imageuri= "http://jarvismedia.tech/final-ckp/files/image/";
    private RequestQueue registerQueue2;
    RecyclerView recyclerView_search;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_);
        String s= getIntent().getStringExtra("qry");
        Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG).show();
         recyclerView_search = (RecyclerView)findViewById(R.id.postRecyclerView_search);

      JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("query",s);

            Toast.makeText(getBaseContext(), s,Toast.LENGTH_SHORT).show();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, "http://jarvismedia.tech/final-ckp/android/searchpost", jsonObject, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                        Toast.makeText(Search_Activity.this, response.toString(), Toast.LENGTH_LONG).show();
                Log.d("data", response.toString());

                try {
                    JSONArray jsonArray = response.getJSONArray("Results");
                    Log.d("searh","search1");
                    for(int i=0 ;i <jsonArray.length(); i++){

                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                        PostInformation postInformation = new PostInformation();
                        postInformation.setTitle(jsonObject1.getString("title"));
                        postInformation.setTech(jsonObject1.getString("technology"));
                        postInformation.setDesignation(jsonObject1.getString("designation"));
                        postInformation.setDescription(jsonObject1.getString("description"));
                        postInformation.setReference(jsonObject1.getString("reference"));
                        postInformation.setBranch(jsonObject1.getString("branchpost"));
                        postInformation.setTimeStamp(jsonObject1.getString("created_at"));
                        //postInformation.setUserName(jsonObject1.getString("userid"));
                        postInformation.setImage(imageuri+jsonObject1.getString("filename"));

                        JSONObject jsonObject2 = new JSONObject();
                        // jsonObject2.put("userid", jsonObject1.getString("userid"));

                        String uid = jsonObject1.getString("userid");
                        String newurl = "http://jarvismedia.tech/final-ckp/android/getname";

                        postResults.add(postInformation);

                        recyclerView_search.setAdapter(new PostAdapter(getApplicationContext(), postResults));

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                Log.d("viewpost", response.toString());
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_LONG).show();
                Log.d("bhosdike_lode", error.toString());
            }
        });

        registerQueue2 = Volley.newRequestQueue(getApplicationContext());
        registerQueue2.add(jsonObjectRequest);

    }
}
