package vishal.alumini_final;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
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

public class RegisteredHome extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private RequestQueue queue;
    private RequestQueue nameQueue;
    private SearchView searchView;
    private ListView searchResults;
    private ArrayList<PostInformation> postResults = new ArrayList<PostInformation>();
    private ArrayList<PostInformation> filteredResults = new ArrayList<PostInformation>();
    private String imageuri= "http://jarvismedia.tech/final-ckp/files/image/";

    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registerd_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView leftnavigationView = (NavigationView) findViewById(R.id.nav_view);
        leftnavigationView.setNavigationItemSelectedListener(this);

        NavigationView rightnavigationView = (NavigationView) findViewById(R.id.right_guest_view);
        rightnavigationView.setNavigationItemSelectedListener(this);


        searchView = (SearchView)findViewById(R.id.action_search_reg);
        //searchResults = (ListView)findViewById(R.id.listview_search);

        //PostAdapter postAdapter = new PostAdapter();
        recyclerView = (RecyclerView)findViewById(R.id.postRecyclerView2);

        Log.d("recyclerview", recyclerView+"");

        //recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        LinearLayoutManager lm = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(lm);

//        vishalseth

        final JSONObject jsonObject = new JSONObject();
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, "http://jarvismedia.tech/final-ckp/android/viewpost", jsonObject, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
//                        Toast.makeText(Login.this, response.toString(), Toast.LENGTH_LONG).show();

                try {
                    JSONArray jsonArray = response.getJSONArray("Post");
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
                        //newurl = newurl+uid;
//                        JsonObjectRequest jsonObjectRequest1 = new JsonObjectRequest(Request.Method.GET, newurl, jsonObject2, new Response.Listener<JSONObject>() {
//                            @Override
//                            public void onResponse(JSONObject response) {
//                                Log.d("name", response.toString());
//                            }
//                        }, new Response.ErrorListener() {
//                            @Override
//                            public void onErrorResponse(VolleyError error) {
//                                Log.d("error ", error.toString());
//                            }
//                        });
//
//                        nameQueue = Volley.newRequestQueue(getApplicationContext());
//                        nameQueue.add(jsonObjectRequest1);

                        postResults.add(postInformation);

                        recyclerView.setAdapter(new PostAdapter(getApplicationContext(), postResults));

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

        queue = Volley.newRequestQueue(getApplicationContext());
        queue.add(jsonObjectRequest);


    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.registerd_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        if(id == R.id.user_logout){
            final SharedPreferences sp = this.getSharedPreferences("user_credential", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sp.edit();
            editor.putBoolean("user_login_status", false);
            editor.putString("email", null);
            editor.commit();
            finish();
            startActivity(new Intent(getApplicationContext(), Login.class));
        }

        if(id == R.id.user_profile){
            startActivity(new Intent(getApplicationContext(), Profile_settings.class));
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_add_post) {
            finish();
            startActivity(new Intent(getApplicationContext(), AddPost.class));
            // Handle the camera action
        } else if (id == R.id.nav_View_post) {
            Intent viewpost = new Intent(getApplicationContext(),Single_Post_view.class);
            startActivity(viewpost);

        } else if (id == R.id.nav_Opportunities) {

        } else if (id == R.id.nav_contacth) {

        } else if (id == R.id.nav_abouth) {
            Intent aboutus = new Intent(RegisteredHome.this,About_us.class);
            startActivity(aboutus);

        }else if (id == R.id.nav_why_join_ckp_alumni) {
            startActivity(new Intent(getApplicationContext(), WhyJoinCKP.class));
        } else if (id == R.id.nav_subscribe) {

        } else if (id == R.id.nav_upcoming_events) {

        } else if (id == R.id.nav_Opportunities) {
            Intent setting = new Intent(RegisteredHome.this,Profile_settings.class);
            startActivity(setting);
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}

