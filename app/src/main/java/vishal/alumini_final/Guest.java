package vishal.alumini_final;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import vishal.alumini_final.adapter.Card_custom_adapter;
import vishal.alumini_final.model.DataModel;

public class Guest extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    SwipeRefreshLayout swipeRefreshLayout;
    private static ArrayList<DataModel> data;
    private static ArrayList<Integer> removedItems;
    private static long back_pressed;
    public static View.OnClickListener myOnClickListener;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;
    DrawerLayout drawer;
    private RecyclerView.LayoutManager layoutManager;
    private static final String TAG = "Guest Refresh";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guest);
/*-------------------------Swipe Refresh layout----------------------------*/
        swipeRefreshLayout=(SwipeRefreshLayout)findViewById(R.id.activity_main_swipe_refresh_layout);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {

            public void onRefresh() {
                swipeRefreshLayout.setColorSchemeResources(R.color.orange,R.color.green,R.color.blue);
                Log.i(TAG,"Swipe refresh");        // This method performs the actual data-refresh operation.
                // The method calls setRefreshing(false) when it's finished.
                //    myUpdateOperation();
                swipeRefreshLayout.setRefreshing(false);
            }
        });
/*----------------Card View Content---------------------------------------------------------*/
        myOnClickListener = new MyOnClickListener(this);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        data = new ArrayList<>();
        for (int i = 0; i < MyData.nameArray.length; i++) {
            data.add(new DataModel(
                    MyData.nameArray[i],
                    MyData.versionArray[i],
                    MyData.id_[i],
                    MyData.drawableArray[i]
            ));
        }
        removedItems = new ArrayList<Integer>();

        RecyclerView.Adapter adapter = new Card_custom_adapter(data);
        recyclerView.setAdapter(adapter);
/*---------------Toolbar And Floating button-----------------------*/
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        assert fab != null;
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
/*-------------Drawer-----------------*/
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        assert drawer != null;
        drawer.setDrawerListener(toggle);
        toggle.syncState();
/*-----------right Drawer--------------*/
        NavigationView leftnavigationView = (NavigationView) findViewById(R.id.left_guest_view);
        assert leftnavigationView != null;
        leftnavigationView.setNavigationItemSelectedListener(this);
/*-----------Right drawer---------------*/
        NavigationView rightnavigationView = (NavigationView) findViewById(R.id.right_guest_view);
        assert rightnavigationView != null;
        rightnavigationView.setNavigationItemSelectedListener(this);


    }
    /*--------Back pressed----------------*/
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        assert drawer != null;
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
    /*----------option selection--------------*/
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.guest, menu);
        return true;
    }
    /*----------On option selected------------------*/
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

        return super.onOptionsItemSelected(item);
    }
    /*---------------Naviagation drawer---------------------------------*/
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.register) {
            // Handle the camera action
            Intent register = new Intent(Guest.this,RegisterActivity.class);
            startActivity(register);
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.register) {

        } else if (id == R.id.contactus) {

        }else if (id == R.id.aboutus) {

        }else if (id == R.id.nav_why_join_ckp_alumni) {

        } else if (id == R.id.nav_subscribe) {

        } else if (id == R.id.nav_upcoming_events) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    /*---------onclick on card view-------------------*/
    private static class MyOnClickListener implements View.OnClickListener {

        private final Context context;

        private MyOnClickListener(Context context) {
            this.context = context;
        }

        @Override
        public void onClick(View v) {

            Intent intent = new Intent(context, CardViewContent.class);
            context.startActivity(intent);

        }

    }

}