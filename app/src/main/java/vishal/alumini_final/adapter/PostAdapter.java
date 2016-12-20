package vishal.alumini_final.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import vishal.alumini_final.R;
import vishal.alumini_final.model.PostInformation;

/**
 * Created by razintailor on 20/12/16.
 */

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.MyViewHolder> {


    Context context;

    ArrayList<PostInformation> arrayList = new ArrayList<>();
    public PostAdapter(Context c, ArrayList<PostInformation> arrayList){
        this.arrayList = arrayList;
        this.context = c;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.post_layout, parent, false);
        return new MyViewHolder(view);

    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        View post_card = holder.view;
        TextView title = (TextView)post_card.findViewById(R.id.titlePost);
        TextView description = (TextView)post_card.findViewById(R.id.contentPost);
        TextView branch = (TextView)post_card.findViewById(R.id.branchTitle);
        TextView timestamp = (TextView)post_card.findViewById(R.id.timeStamp);
        TextView user_id = (TextView)post_card.findViewById(R.id.postUser);

        title.setText(arrayList.get(position).getTitle());
        description.setText(arrayList.get(position).getDescription());
        branch.setText(arrayList.get(position).getBranch());
        timestamp.setText(arrayList.get(position).getTimeStamp());
        user_id.setText(arrayList.get(position).getUserName());
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public View view;

        public MyViewHolder(View itemView) {
            super(itemView);
            view = itemView;
        }
    }
}
