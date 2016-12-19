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
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.nononsenseapps.filepicker.FilePickerActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class AddPost extends AppCompatActivity {

    private Button choose, submit;
    private EditText title, tech, designation, description, reference ,branch;
    private String filepath = null;
    private String newPath = null;
    private String FileName;
    private String fileName;
    private String bs64;
    private ImageView imgView;
    private static final int RESULT_LOAD_IMAGE = 1;
    private byte [] bytes;
    private String url="http://www.jarvismedia.tech/final-ckp/addpost/";
    private RequestQueue AddPostQueue;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_post);

        choose = (Button)findViewById(R.id.btn_select_file);
        submit = (Button)findViewById(R.id.btn_add_post);

        title = (EditText)findViewById(R.id.post_title);
        tech = (EditText)findViewById(R.id.techorplatform);
        designation = (EditText)findViewById(R.id.designation);
        description = (EditText)findViewById(R.id.description);
        reference = (EditText)findViewById(R.id.reference);
        branch = (EditText)findViewById(R.id.branch);

        final SharedPreferences sp = this.getSharedPreferences("user_credential", Context.MODE_PRIVATE);



        choose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(AddPost.this, FilePickerActivity.class);
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


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               /* try {
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
*/

                String semail = sp.getString("user_credential", "email");
                Log.d("sharedEmail", semail);

                url = url+semail;

                Log.d("url", url);

                JSONObject jsonObject= new JSONObject();
                try {
                    jsonObject.put("title", title.getText().toString().trim());
                    jsonObject.put("tech", tech.getText().toString().trim());
                    jsonObject.put("designation", designation.getText().toString().trim());
                    jsonObject.put("description", description.getText().toString());
                    jsonObject.put("reference", reference.getText().toString().trim());
                    jsonObject.put("branch", branch.getText().toString().trim());
                    jsonObject.put("file_name", fileName);
                   // jsonObject.put()
                    Log.d("jsobj", jsonObject.toString());

                    JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url,jsonObject, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            Log.d("post_response", response.toString());
                            //startActivity(new Intent(getApplicationContext(), RegisteredHome.class));
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_LONG).show();
                            Log.d("error", error.toString());
                        }
                    });

                    AddPostQueue = Volley.newRequestQueue(getApplicationContext());
                    AddPostQueue.add(jsonObjectRequest);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
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
        Toast.makeText(AddPost.this, filepath.toString(),Toast.LENGTH_LONG).show();

        newPath = filepath.substring(7,filepath.length());

        Log.d("LatestPath" , newPath);

        String[] name = filepath.split("/");
        fileName = name[name.length - 1];

        Log.d("fileName",fileName);

        String fileNameTemp = name[(name.length)-1];
        FileName = fileNameTemp;
    }
}