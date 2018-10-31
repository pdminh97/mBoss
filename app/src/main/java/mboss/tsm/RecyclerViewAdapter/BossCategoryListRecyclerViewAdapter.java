package mboss.tsm.RecyclerViewAdapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import mboss.tsm.Model.BossCategory;
import mboss.tsm.mboss.R;

public class BossCategoryListRecyclerViewAdapter extends RecyclerView.Adapter<BossCategoryListRecyclerViewAdapter.ViewHolder> {

    private List<BossCategory> bossCategories;
    OnItemListener onIteamlistener;

    public BossCategoryListRecyclerViewAdapter(List<BossCategory> bossCategories) {
        this.bossCategories = bossCategories;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View view = inflater.inflate(R.layout.recyclerview_itembosscategory, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int pos) {
        viewHolder.imageCategoryAdd.setImageResource(bossCategories.get(pos).getmCategorytList().getImage());
        viewHolder.tvNameAdd.setText(bossCategories.get(pos).getmCategorytList().getName());
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
    public void setItemOnListenner(OnItemListener iteamOnListenner) {
        this.onIteamlistener = iteamOnListenner;
    }
    public  interface  OnItemListener {
        void OnClickItemListener(int postion);
    }
}
