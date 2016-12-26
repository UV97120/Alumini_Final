package vishal.alumini_final;


import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ClipData;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
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


public class RegisterActivity extends Activity implements AdapterView.OnItemSelectedListener {

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

    private byte [] bytes;

    private static final int RESULT_LOAD_IMAGE = 1;

    private static final String ServerAddress = "http://jarvismedia.tech/final-ckp/Auth/Login";
    private String token=null;
    private RequestQueue registerQueue;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //--------variable declaration----------
        choose = (Button) findViewById(R.id.btn_file_pick);
        loginLink = (TextView) findViewById(R.id.link_login);
        //spinner = (MaterialSpinner) findViewById(R.id.spinner);
        fname = (TextView) findViewById(R.id.input_fname);
        lname = (TextView) findViewById(R.id.input_lname);
        email = (TextView) findViewById(R.id.input_email);
        register = (Button) findViewById(R.id.btn_signup);

        Intent intent = getIntent();

        //token = intent.getStringExtra("token");

//        Log.d("Token-From-Login", token);

//        ArrayAdapter spinnerArrayAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, arrlist);
//        spinner.setAdapter(spinnerArrayAdapter);
//        spinner.setOnItemSelectedListener(RegisterActivity.this);



        //------------calender year population----------------

        int base_year = 1950;

        for (int i = 0; i < 80; i++) {
            int nextYear = 1950;
            nextYear = base_year + i;
            arrlist.add(i, nextYear);
        }


        //-------------Login Redirect------------------------

        loginLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("lodo", "activity khuli chutiya");
                Intent i = new Intent(RegisterActivity.this, Login.class);
                startActivity(i);
            }

        });

        //---------Register ServerSide Logic---------------

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(getApplicationContext(), "inside On click", Toast.LENGTH_LONG).show();

//                String name = name.getText().toString();
                JSONObject jsonObject = new JSONObject();

                //-------------- Getting token from server------------------

                //----------converting image int bs64-------------------

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

//               if(token_status==true)
//               {
                try {
                    // jsonObject.put("fileData", bs64);

                    jsonObject.put("fname", fname.getText().toString());
                    jsonObject.put("lname", lname.getText().toString());
                    jsonObject.put("email", email.getText().toString());
                    //jsonObject.put("year", ek);
                    jsonObject.put("filename", fileName);
                    jsonObject.put("data", bs64);
                    // jsonObject.put("X-CSRF-TOKEN", token);
                    Log.d("Testing", "Inside Try");
                } catch (JSONException e) {
                    Log.d("Testing", "Inside Try");
                    e.printStackTrace();
                }

                Log.d("Test", jsonObject.toString());
                //Log.d("", jsonObject.toString());

                //----------------Login request--------------------

                JsonObjectRequest jsObjRequest = new JsonObjectRequest
                        (Request.Method.POST, "http://www.jarvismedia.tech/final-ckp/android/register", jsonObject,new Response.Listener<JSONObject>() {

                            @Override
                            public void onResponse(JSONObject response) {
                                Log.d("response", response.toString());
                                Toast.makeText(RegisterActivity.this, response.toString(), Toast.LENGTH_LONG).show();

                            }
                        }, new Response.ErrorListener() {

                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(RegisterActivity.this, error.toString()+"Bhosdike", Toast.LENGTH_LONG).show();
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
                        header.put("X-CSRF-TOKEN", token);
                        return header;
                    }
                };



                // if (flag) {
                registerQueue = Volley.newRequestQueue(getApplicationContext());
                registerQueue.add(jsObjRequest);//year selected
                //}


            }
        });
        choose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(RegisterActivity.this, FilePickerActivity.class);
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

                //------------Another Logic for file picker--------------

            }
        });
    }

    private void fetch(String response) {


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
        Toast.makeText(RegisterActivity.this, filepath.toString(),Toast.LENGTH_LONG).show();

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
    public void onNothingSelected(AdapterView<?> parent) {

    }

}