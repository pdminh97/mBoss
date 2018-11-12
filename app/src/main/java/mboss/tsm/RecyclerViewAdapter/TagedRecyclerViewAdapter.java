package mboss.tsm.RecyclerViewAdapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import mboss.tsm.Model.Boss;
import mboss.tsm.Model.Tag;
import mboss.tsm.mboss.R;

public class TagedRecyclerViewAdapter extends RecyclerView.Adapter<TagedRecyclerViewAdapter.ViewHolder> {
    private List<Boss> tags;
    private Context context;

    public TagedRecyclerViewAdapter(Context context, List<Boss> tags) {
        this.context = context;
        this.tags = tags;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.taged_item, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        Glide.with(context)
                .load(tags.get(i).getPictures())
                .placeholder(R.drawable.picture)
                .dontAnimate()
                .into(viewHolder.civAvatar);
    }

    @Override
    public int getItemCount() {
        if(tags != null) {
            return tags.size();
        } else {
            return 0;
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private CircleImageView civAvatar;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            civAvatar = itemView.findViewById(R.id.civAvatar);
        }
    }
}
