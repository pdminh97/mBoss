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
import mboss.tsm.Model.Category;
import mboss.tsm.mboss.R;

public class CategoryFilterRecyclerViewAdapter extends RecyclerView.Adapter<CategoryFilterRecyclerViewAdapter.ViewHolder> {
    private List<Category> categories;
    private Context context;

    public CategoryFilterRecyclerViewAdapter(Context context, List<Category> categories) {
        this.context = context;
        this.categories = categories;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.category_filter_item, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        Glide.with(context)
                .load(categories.get(i).getImage())
                .placeholder(R.drawable.picture)
                .dontAnimate()
                .into(viewHolder.civAvatar);
    }

    @Override
    public int getItemCount() {
        if(categories != null) {
            return categories.size();
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
