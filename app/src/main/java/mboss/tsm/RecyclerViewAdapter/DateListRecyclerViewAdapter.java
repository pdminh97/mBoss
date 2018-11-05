package mboss.tsm.RecyclerViewAdapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import mboss.tsm.Model.BossActivity;
import mboss.tsm.mboss.DateListActivity;
import mboss.tsm.mboss.R;

 public class DateListRecyclerViewAdapter extends RecyclerView.Adapter<DateListRecyclerViewAdapter.ViewHolder> {
    List<BossActivity> bossActivityList;
    private setOnIteamlistener onIteamlistener;
    private DateListActivity context;


    public DateListRecyclerViewAdapter(List<BossActivity> bossActivityList) {
        this.bossActivityList = bossActivityList;
    }

    @NonNull
    @Override
    public DateListRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View view = inflater.inflate(R.layout.recyclerview_item_date, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DateListRecyclerViewAdapter.ViewHolder viewHolder, final int pos) {
        viewHolder.tvDate.setText(bossActivityList.get(pos).getDate());
        viewHolder.tvTime.setText(bossActivityList.get(pos).getTime());
        viewHolder.mLnlRoot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onIteamlistener!=null){
                    onIteamlistener.setOnIteamListener(pos);
                }
            }
        });
//        viewHolder.delete.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //context.DialogDeleteDate();
//            }
//        });

    }

    @Override
    public int getItemCount() {
        if (bossActivityList == null)
            return 0;
        return bossActivityList.size();
    }


    public interface setOnIteamlistener {
        void setOnIteamListener(int position);
    }

    public void setItemOnListenner(setOnIteamlistener iteamOnListenner) {
        this.onIteamlistener = iteamOnListenner;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvDate;
        private TextView tvTime;
        private LinearLayout mLnlRoot;
        private ImageButton edit;
        private ImageButton delete;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvDate = itemView.findViewById(R.id.txt_date);
            tvTime = itemView.findViewById(R.id.txt_time);

            mLnlRoot = itemView.findViewById(R.id.lnlRoot);
//            delete=itemView.findViewById(R.id.delete_date);

        }
    }
}
