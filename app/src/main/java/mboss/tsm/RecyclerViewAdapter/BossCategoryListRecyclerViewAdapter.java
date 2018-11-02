package mboss.tsm.RecyclerViewAdapter;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import mboss.tsm.Model.BossCategory;
import mboss.tsm.Model.Category;
import mboss.tsm.mboss.R;

public class BossCategoryListRecyclerViewAdapter extends RecyclerView.Adapter<BossCategoryListRecyclerViewAdapter.ViewHolder> {
    private List<Category> bossCategories;
    OnItemListener onIteamlistener;
    private Context mContext;
    public BossCategoryListRecyclerViewAdapter(Context context,List<Category> bossCategories) {
        this.mContext = context;
        this.bossCategories = bossCategories;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.recyclerview_itembosscategory, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int pos) {
        viewHolder.imageCategoryAdd.setImageResource(bossCategories.get(pos).getImage());
        viewHolder.tvNameAdd.setText(bossCategories.get(pos).getName());
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
        if (bossCategories == null)
            return 0;
        return bossCategories.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageCategoryAdd;
        private TextView tvNameAdd;
        private LinearLayout mLnlRoot;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageCategoryAdd= itemView.findViewById(R.id.imageCategoryAdd);
            mLnlRoot = itemView.findViewById(R.id.lnlRoot);
            tvNameAdd=itemView.findViewById(R.id.tvNameAdd);
        }
    }

    public void setNotifySetChange(List<Category> items) {
        this.bossCategories = new ArrayList<>(items);
        notifyDataSetChanged();
    }
    public void setItemOnListenner(OnItemListener iteamOnListenner) {
        this.onIteamlistener = iteamOnListenner;
    }
    public  interface  OnItemListener {
        void OnClickItemListener ( int postion );
    }
}
