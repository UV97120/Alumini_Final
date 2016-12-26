    package vishal.alumini_final;

    import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
    import android.util.Log;
    import android.view.View;
import android.widget.ImageButton;
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

        private CollapsingToolbarLayout collapsingToolbarLayout = null;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_profile_settings);
            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
            ActionBar actionBar = getSupportActionBar();
            actionBar.setDisplayHomeAsUpEnabled(true);

            imageView = (ImageView) findViewById(R.id.profile_id);

            final SharedPreferences sp = this.getSharedPreferences("user_credential", Context.MODE_PRIVATE);
            String email = sp.getString("email", "No-Email");

            Log.d("email", email);
            url = url+email;
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

            ImageButton pickImage = (ImageButton) findViewById(R.id.btn_pick);
            pickImage.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                    photoPickerIntent.setType("image/*");
                    startActivityForResult(photoPickerIntent, SELECT_PHOTO);
                }
            });
        }

   /*     @Override
        protected void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
            super.onActivityResult(requestCode, resultCode, imageReturnedIntent);

            switch (requestCode) {
                case SELECT_PHOTO:
                    if (resultCode == RESULT_OK) {
                        try {
                            final Uri imageUri = imageReturnedIntent.getData();
                            final InputStream imageStream = getContentResolver().openInputStream(imageUri);
                            final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                            imageView.setImageBitmap(selectedImage);
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }

                    }
            }
        }
   */
   @Override
   protected void onActivityResult(int requestCode, int resultCode, Intent data) {
       super.onActivityResult(requestCode, resultCode, data);

       if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
           Uri selectedImage = data.getData();
           String[] filePathColumn = { MediaStore.Images.Media.DATA };

           Cursor cursor = getContentResolver().query(selectedImage,filePathColumn, null, null, null);
           cursor.moveToFirst();

           int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
           String picturePath = cursor.getString(columnIndex);
           cursor.close();

           ImageView imageView = (ImageView) findViewById(R.id.profile_id);
           imageView.setImageBitmap(BitmapFactory.decodeFile(picturePath));
            }
        }
    }










