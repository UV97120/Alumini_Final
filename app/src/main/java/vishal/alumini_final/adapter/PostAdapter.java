package vishal.alumini_final.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import vishal.alumini_final.R;
import vishal.alumini_final.SinglePost;
import vishal.alumini_final.model.PostInformation;

/**
 * Created by razintailor on 20/12/16.
 */

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.MyViewHolder> implements GestureDetector.OnGestureListener,
        GestureDetector.OnDoubleTapListener  {


    Context context;
    private GestureDetectorCompat mDetector;

 //   private String url ="http://jarvismedia.tech/final-ckp/android/singlepost/";
    private String postid = null;


    ArrayList<PostInformation> arrayList = new ArrayList<>();
    public PostAdapter(Context c, ArrayList<PostInformation> arrayList){
        this.arrayList = arrayList;
        this.context = c;
        mDetector = new GestureDetectorCompat(context,this);

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.post_layout, parent, false);
        return new MyViewHolder(view);

    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {

        PostInformation postInformation = arrayList.get(position);

        Uri uri = Uri.parse(postInformation.getImage());
        View post_card = holder.view;


        TextView title = (TextView)post_card.findViewById(R.id.titlePost);
        TextView description = (TextView)post_card.findViewById(R.id.contentPost);
        TextView branch = (TextView)post_card.findViewById(R.id.branchTitle);
        TextView timestamp = (TextView)post_card.findViewById(R.id.timeStamp);
        TextView user_id = (TextView)post_card.findViewById(R.id.postUser);
        ImageView post_image = (ImageView)post_card.findViewById(R.id.imagePost);

        Picasso.with(context).load(uri).resize(150, 150).centerInside().into(post_image);

        title.setText(arrayList.get(position).getTitle());
        description.setText(arrayList.get(position).getDescription());
        branch.setText(arrayList.get(position).getBranch());
        timestamp.setText(arrayList.get(position).getTimeStamp());
        user_id.setText(arrayList.get(position).getUserName());

        postid = arrayList.get(position).getPostid();

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "clicked"+position, Toast.LENGTH_SHORT).show();
                Intent i = new Intent(context, SinglePost.class);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                i.putExtra("postid", postid);
                context.startActivity(i);
            }
        });

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    @Override
    public boolean onSingleTapConfirmed(MotionEvent motionEvent) {
        return false;
    }

    @Override
    public boolean onDoubleTap(MotionEvent motionEvent) {
        return false;
    }

    @Override
    public boolean onDoubleTapEvent(MotionEvent motionEvent) {
        return false;
    }

    @Override
    public boolean onDown(MotionEvent motionEvent) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent motionEvent) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent motionEvent) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent motionEvent) {

    }

    @Override
    public boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
        Toast.makeText(context,motionEvent.toString()+motionEvent1.toString(), Toast.LENGTH_LONG).show();
        return false;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public View view;

        public MyViewHolder(View itemView) {
            super(itemView);
            view = itemView;
        }
    }
}
