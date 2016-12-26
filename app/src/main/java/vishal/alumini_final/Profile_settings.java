    package vishal.alumini_final;

    import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
    import android.util.Base64;
    import android.util.Log;
import android.view.View;
    import android.widget.AdapterView;
    import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.nononsenseapps.filepicker.FilePickerActivity;

import org.json.JSONException;
import org.json.JSONObject;

    import java.io.ByteArrayOutputStream;
    import java.io.FileInputStream;
    import java.io.FileNotFoundException;
    import java.io.InputStream;
    import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

    public class Profile_settings extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

        private final int SELECT_PHOTO = 1;
        private ImageView imageView;
        private String url = "http://www.jarvismedia.tech/final-ckp/android/profiledetails/";
        private String url2 = "http://www.jarvismedia.tech/final-ckp/android/updatepassword/";
        private RequestQueue queue;
        private RequestQueue queue2;
        private boolean status = true;
        private CollapsingToolbarLayout collapsingToolbarLayout = null;
        private Button updateProfile;
        private EditText updatePassword;
        private String ek;
        private boolean flag;
        private boolean token_status= false;
        private String filepath;
        private ArrayList<Integer> arrlist = new ArrayList<Integer>(80);
        private Spinner spinner;
        private Button choose;
        private TextView loginLink;
        private TextView fname;
        private TextView lname;
        private TextView email;
        private Button register;
        private String FileName;
        private String bs64;//to convert filedata to bs64
        private ImageView imgView;
        private String newPath;
        private String fileName;
        private boolean btn_status = false;

        private byte [] bytes;

        private static final int RESULT_LOAD_IMAGE = 1;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_profile_settings);

            imageView = (ImageView) findViewById(R.id.profile_id);
            updateProfile = (Button)findViewById(R.id.updateProfile);
            updatePassword = (EditText)findViewById(R.id.updatePassword);

            final SharedPreferences sp = this.getSharedPreferences("user_credential", Context.MODE_PRIVATE);
            String email = sp.getString("email", "No-Email");

            Log.d("email", email);
            if(status){
                url = url+email;
                url2 = url2+email;
                status = false;
            }
            StringRequest stringRequest = new StringRequest(Request.Method.POST,url , new Response.Listener<String>() {
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

            updateProfile.setOnClickListener(new View.OnClickListener() {


                @Override
                public void onClick(View view) {

                    String upPass = updatePassword.getText().toString().trim();

                    try {
                        InputStream inputStream = new FileInputStream(newPath);

                        byte[] buffer = new byte[8192];

                        int bytesRead;

                        ByteArrayOutputStream output = new ByteArrayOutputStream();

                        while((bytesRead = inputStream.read(buffer)) != -1) {
                            output.write(buffer, 0, bytesRead);
                        }

                        bytes = output.toByteArray();

                        bs64 = Base64.encodeToString(bytes, Base64.DEFAULT);

                    } catch (FileNotFoundException e) {
                        Log.d("bs64", e.toString());
                        //e.printStackTrace();
                    }catch (Exception e){
                        Log.d("bs64_2", e.toString());
                        //e.printStackTrace();
                    }

                    if(upPass.length()==0)
                    {
                        if(!btn_status)
                        {
                            Toast.makeText(getApplicationContext(), "Profile is up to date", Toast.LENGTH_SHORT).show();
                        }
                        else if(btn_status)
                        {
                            JSONObject jsonObjectProf = new JSONObject();
                            try {
                                jsonObjectProf.put("filename", fileName);
                                jsonObjectProf.put("data", bs64);
                                Log.d("file", fileName);
                                Log.d("btn_status", jsonObjectProf.toString());
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            JsonObjectRequest jsObjRequest = new JsonObjectRequest
                                    (Request.Method.POST, url2, jsonObjectProf, new Response.Listener<JSONObject>() {

                                        @Override
                                        public void onResponse(JSONObject response) {
                                            Log.d("response", response.toString());
                                            Toast.makeText(getApplicationContext(), response.toString(), Toast.LENGTH_LONG).show();
                                            if(response.get("Status").equals(""))

                                        }
                                    }, new Response.ErrorListener() {

                                        @Override
                                        public void onErrorResponse(VolleyError error) {
                                            Toast.makeText(getApplicationContext(), error.toString() + "Bhosdike", Toast.LENGTH_LONG).show();
                                            Log.d("Error", error.toString());

                                            NetworkResponse response = error.networkResponse;
                                            if (response != null && response.data != null) {

                                                // Log.d("ERROR_MESSAGE", response.data.);
                                            }
                                            //Additional cases
                                        }

                                    }) {

                                @Override
                                public Map<String, String> getHeaders() throws AuthFailureError {

                                    Map<String, String> header = new HashMap<String, String>();
                                    header.put("Content-Type", "application/json");
                                    //      header.put("X-CSRF-TOKEN", token);
                                    return header;
                                }
                            };

                            queue2 = Volley.newRequestQueue(getApplicationContext());
                            queue2.add(jsObjRequest);
                        }
                    }
                    if((upPass.length()!=0) && (btn_status))
                    {
                        if(upPass.length()<8)
                        {
                            Toast.makeText(getApplicationContext(), "Password must be atleast 8 characters", Toast.LENGTH_LONG).show();
                        }
                    }
                    else {
                        JSONObject jsonObject = new JSONObject();
                        try {
                            jsonObject.put("password", upPass);
                            jsonObject.put("data", bs64);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                                (Request.Method.POST, url2, jsonObject, new Response.Listener<JSONObject>() {

                                    @Override
                                    public void onResponse(JSONObject response) {
                                        Log.d("response", response.toString());
                                        Toast.makeText(getApplicationContext(), response.toString(), Toast.LENGTH_LONG).show();

                                    }
                                }, new Response.ErrorListener() {

                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        Toast.makeText(getApplicationContext(), error.toString() + "Bhosdike", Toast.LENGTH_LONG).show();
                                        Log.d("Error", error.toString());

                                        NetworkResponse response = error.networkResponse;
                                        if (response != null && response.data != null) {

                                            // Log.d("ERROR_MESSAGE", response.data.);
                                        }
                                        //Additional cases
                                    }

                                }) {

                            @Override
                            public Map<String, String> getHeaders() throws AuthFailureError {

                                Map<String, String> header = new HashMap<String, String>();
                                header.put("Content-Type", "application/json");
                                //      header.put("X-CSRF-TOKEN", token);
                                return header;
                            }
                        };

                        queue2 = Volley.newRequestQueue(getApplicationContext());
                        queue2.add(jsObjRequest);
                    }
                }
            });

            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    btn_status = true;
                    Intent i = new Intent(getApplicationContext(), FilePickerActivity.class);
                    // This works if you defined the intent filter
                    // Intent i = new Intent(Intent.ACTION_GET_CONTENT);

                    // Set these depending on your use case. These are the defaults.
                    i.putExtra(FilePickerActivity.EXTRA_ALLOW_MULTIPLE, false);
                    i.putExtra(FilePickerActivity.EXTRA_ALLOW_CREATE_DIR, false);
                    i.putExtra(FilePickerActivity.EXTRA_MODE, FilePickerActivity.MODE_FILE);

                    // Configure initial directory by specifying a String.
                    // You could specify a String like "/storage/emulated/0/", but that can
                    // dangerous. Always use Android's API calls to get paths to the SD-card or
                    // internal memoryi.putExtra(FilePickerActivity.EXTRA_START_PATH, Environment.getExternalStorageDirectory().getPath());

                    startActivityForResult(i, 1);

                }
            });
        }

        @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
        @Override
        protected void onActivityResult(int requestCode, int resultCode, Intent data) {
            Log.d("ghus benchod", "gaya lode");
            if (requestCode == 1 && resultCode == Activity.RESULT_OK) {
                Log.d("if me ghus benchod", "gaya lode");
                if (data.getBooleanExtra(FilePickerActivity.EXTRA_ALLOW_MULTIPLE, false)) {
                    // For JellyBean and above
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                        ClipData clip = data.getClipData();

                        if (clip != null) {
                            for (int i = 0; i < clip.getItemCount(); i++) {
                                Uri uri = clip.getItemAt(i).getUri();
                                // Do something with the URI

                                filepath = uri.toString();

                            }
                        }
                        // For Ice Cream Sandwich
                    } else {
                        ArrayList<String> paths = data.getStringArrayListExtra
                                (FilePickerActivity.EXTRA_PATHS);

                        if (paths != null) {
                            for (String path: paths) {
                                Uri uri = Uri.parse(path);
                                // Do something with the URI
                                filepath = uri.toString();
                            }
                        }
                    }

                } else {
                    Uri uri = data.getData();
                    // Do something with the URI

                    filepath = uri.toString();
                }
                Log.d("path dekh benchod", filepath);
            }
            else
            {
                Log.d("path dekh benchod lode", resultCode + "" + requestCode);
            }
            Toast.makeText(getApplicationContext(), filepath.toString(),Toast.LENGTH_LONG).show();

            newPath = filepath.substring(7,filepath.length());

            Log.d("LatestPath" , newPath);

            String[] name = filepath.split("/");
            fileName = name[name.length - 1];

            Log.d("fileName",fileName);

            String fileNameTemp = name[(name.length)-1];
            FileName = fileNameTemp;
        }
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            TextView mytext = (TextView) view;

            // ek = mytext.getText().toString();

            flag = true;
            ek = arrlist.get(position).toString();
        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {

        }
    }










