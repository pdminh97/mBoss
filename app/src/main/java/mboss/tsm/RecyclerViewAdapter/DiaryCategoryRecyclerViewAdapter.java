package mboss.tsm.RecyclerViewAdapter;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import mboss.tsm.Fragment.CategoryListFragment;
import mboss.tsm.Model.Boss;
import mboss.tsm.Model.Category;
import mboss.tsm.Model.Tag;
import mboss.tsm.Utility.ItemClickListener;
import mboss.tsm.mboss.R;

public class DiaryCategoryRecyclerViewAdapter extends RecyclerView.Adapter<DiaryCategoryRecyclerViewAdapter.ViewHolder> {
    private List<Category> categories;
    private Context context;
    private CategoryListFragment categoryListFragment;
    private RecyclerView recyclerView;

    public DiaryCategoryRecyclerViewAdapter(List<Category> categories, Context context, CategoryListFragment categoryListFragment, RecyclerView recyclerView) {
        this.categories = categories;
        this.context = context;
        this.categoryListFragment = categoryListFragment;
        this.recyclerView = recyclerView;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View view = inflater.inflate(R.layout.diary_category_list_item, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, final int i) {
        Glide.with(context)
                .load(categories.get(i).getImage())
                .placeholder(R.drawable.picture)
                .dontAnimate()
                .into(viewHolder.avatar);
        viewHolder.name.setText(categories.get(i).getName());
        viewHolder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                if(viewHolder.checked.getVisibility() == View.INVISIBLE) {
                    for(int i = 0; i < categories.size(); i++) {
                        ((ViewHolder)recyclerView.findViewHolderForAdapterPosition(i)).checked.setVisibility(View.INVISIBLE);
                        ((ViewHolder)recyclerView.findViewHolderForAdapterPosition(i)).name.setTextColor(Color.parseColor("#808080"));
                    }
                    viewHolder.checked.setVisibility(View.VISIBLE);
                    viewHolder.name.setTextColor(Color.parseColor("#006df0"));
                    categoryListFragment.setSelectedCategory(categories.get(i));
                } else {
                    for(int i = 0; i < categories.size(); i++) {
                        ((ViewHolder)recyclerView.findViewHolderForAdapterPosition(i)).checked.setVisibility(View.INVISIBLE);
                        ((ViewHolder)recyclerView.findViewHolderForAdapterPosition(i)).name.setTextColor(Color.parseColor("#808080"));
                    }
//                    viewHolder.checked.setVisibility(View.INVISIBLE);
//                    viewHolder.name.setTextColor(Color.parseColor("#000000"));
                    categoryListFragment.setSelectedCategory(null);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        if(categories != null) {
            return categories.size();
        } else {
            return 0;
        }
    }



    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private ImageView avatar;
        private TextView name;
        private ItemClickListener itemClickListener;
        private ImageView checked;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            avatar = itemView.findViewById(R.id.ivAvatar);
            name = itemView.findViewById(R.id.txtTagName);
            checked = itemView.findViewById(R.id.tagChecked);
            itemView.setOnClickListener(this);
        }

        public void setItemClickListener(ItemClickListener itemClickListener) {
            this.itemClickListener = itemClickListener;
        }

        @Override
        public void onClick(View v) {
            itemClickListener.onItemClick(v, getAdapterPosition());
        }
    }
}
