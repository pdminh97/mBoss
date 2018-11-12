package mboss.tsm.RecyclerViewAdapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import mboss.tsm.Model.Diary;
import mboss.tsm.mboss.R;

public class HistoryRecyclerViewAdapter extends RecyclerView.Adapter<HistoryRecyclerViewAdapter.ViewHolder> {
    List<Diary> bossActivityList;
    private setOnIteamlistener onIteamlistener;
    private Context context;

    public HistoryRecyclerViewAdapter(List<Diary> bossActivityList, Context context) {
        this.bossActivityList = bossActivityList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.recyclerview_item_history, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder,final int i) {
        Date diaryDate = parseDate(bossActivityList.get(i).getDiaryTime());
        int Year = Integer.parseInt(String.valueOf(diaryDate.getYear()));
        Year = Year + 1900;
        diaryDate.setYear(Year);
        viewHolder.tvDate.setText(diaryDate.getDate() + "/" + diaryDate.getMonth()+"/"+ diaryDate.getYear());
        viewHolder.tvTime.setText(diaryDate.getHours()+":"+diaryDate.getMinutes());

        viewHolder.historyItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onIteamlistener != null) {
                    onIteamlistener.setOnIteamListener(i);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        if (bossActivityList == null)
            return 0;
        return bossActivityList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvDate;
        private TextView tvTime;
        private LinearLayout historyItem;
        RecyclerView recyclerView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTime = itemView.findViewById(R.id.tvTimeHistory);
            tvDate = itemView.findViewById(R.id.tvDateHistory);
            historyItem = itemView.findViewById(R.id.history_item);
        }
    }

    public interface setOnIteamlistener {
        void setOnIteamListener(int position);
    }
    public void setItemOnListenner(HistoryRecyclerViewAdapter.setOnIteamlistener iteamOnListenner) {
        this.onIteamlistener = iteamOnListenner;
    }

    private Date parseDate(String dateString) {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
            return dateFormat.parse(dateString);
        } catch (Exception e) {

        }
        return null;
    }

}
