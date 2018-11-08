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

import mboss.tsm.Model.Tag;
import mboss.tsm.Utility.ItemClickListener;
import mboss.tsm.mboss.R;

public class TagListRecyclerViewAdapter extends RecyclerView.Adapter<TagListRecyclerViewAdapter.ViewHolder> {
    private List<Tag> tags = new ArrayList<>();
    private Context context;
    private List<Tag> taged;
    private TagingRecyclerViewAdapter adapter;

    public TagListRecyclerViewAdapter(Context context, List<Tag> taged, TagingRecyclerViewAdapter adapter) {
        this.context = context;
        this.taged = taged;
        this.adapter = adapter;
        tags.add(new Tag("cat_avt", "Mèo"));
        tags.add(new Tag("dog_avt", "Chó"));
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View view = inflater.inflate(R.layout.tag_list_item, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, final int i) {
        Glide.with(context)
                .load(context.getResources().getIdentifier(tags.get(i).getAvatar(), "drawable", context.getPackageName()))
                .placeholder(R.drawable.picture)
                .dontAnimate()
                .into(viewHolder.avatar);
        viewHolder.name.setText(tags.get(i).getName());
        viewHolder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                if(viewHolder.checked.getVisibility() == View.INVISIBLE) {
                    viewHolder.checked.setVisibility(View.VISIBLE);
                    viewHolder.name.setTextColor(Color.parseColor("#006df0"));
                    taged.add(tags.get(i));
                    adapter.notifyItemInserted(taged.size() - 1);
                } else {
                    viewHolder.checked.setVisibility(View.INVISIBLE);
                    viewHolder.name.setTextColor(Color.parseColor("#000000"));
                    taged.remove(tags.get(i));
                    adapter.notifyDataSetChanged();;
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        if(tags != null) {
            return tags.size();
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
