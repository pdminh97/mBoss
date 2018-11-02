package mboss.tsm.RecyclerViewAdapter;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import mboss.tsm.Model.Category;
import mboss.tsm.mboss.R;

public class CategoryListRecyclerViewAdapter extends RecyclerView.Adapter<CategoryListRecyclerViewAdapter.ViewHolder> {
    private List<Category> categories;
    private Context context;
    private OnItemListener onIteamlistener;
    public CategoryListRecyclerViewAdapter(Context context,List<Category> categories) {
        this.categories = categories;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.recyclerview_itemcategory, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int pos) {
        viewHolder.imageCategory.setImageResource(categories.get(pos).getImage());
        viewHolder.tvName.setText(categories.get(pos).getName());
        viewHolder.tvName.setText(categories.get(pos).getName());
        viewHolder.mLnlRoot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onIteamlistener!=null){
                    onIteamlistener.OnClickItemListener(pos);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        if (categories == null)
            return 0;
        return categories.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageCategory;
        private TextView tvName;
        private LinearLayout mLnlRoot;
        public ViewHolder( View itemView) {
            super(itemView);
            imageCategory= itemView.findViewById(R.id.imageCategory);
            mLnlRoot = itemView.findViewById(R.id.lnlRoot);
            tvName=itemView.findViewById(R.id.tvNameCategory);

        }
    }
    public void setItemOnListenner(OnItemListener iteamOnListenner) {
        this.onIteamlistener = iteamOnListenner;
    }
    public  interface  OnItemListener {
        void OnClickItemListener ( int postion );
    }
}
