package mboss.tsm.RecyclerViewAdapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import mboss.tsm.Utility.ItemClickListener;
import mboss.tsm.mboss.BossListActivity;
import mboss.tsm.mboss.DiaryListActivity;
import mboss.tsm.mboss.MainActivity;
import mboss.tsm.mboss.R;

public class HomeRecyclerViewAdapter extends RecyclerView.Adapter<HomeRecyclerViewAdapter.ViewHolder> {

    private String[] data = {"Bosses", "Dịch Vụ", "Nhật Ký", "Con Sen"};
    private Context context;

    public HomeRecyclerViewAdapter(Context context) {
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.recyclerview_item,parent,false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.txtTitle.setText(data[position]);

        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent;

                switch (position) {
                    case 0:
                        intent = new Intent(context, BossListActivity.class);
                        break;
                    case 1:
                        intent = new Intent(context, BossListActivity.class);
                        break;
                    case 2:
                        intent = new Intent(context, DiaryListActivity.class);
                        break;
                    case 3:
                        intent = new Intent(context, BossListActivity.class);
                        break;
                    default: intent = new Intent(context, MainActivity.class);
                        break;
                }
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.length;
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        public TextView txtTitle;
        private ItemClickListener itemClickListener;

        public ViewHolder(View itemView) {
            super(itemView);
            txtTitle = (TextView)itemView.findViewById(R.id.txtTitle);
            itemView.setOnClickListener(this);
        }

        public void setItemClickListener(ItemClickListener itemClickListener)
        {
            this.itemClickListener = itemClickListener;
        }

        @Override
        public void onClick(View v) {
            itemClickListener.onItemClick(v,getAdapterPosition());
        }
    }
}
