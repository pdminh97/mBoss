package mboss.tsm.RecyclerViewAdapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import mboss.tsm.Model.Diary;
import mboss.tsm.mboss.R;

public class DiaryRecyclerViewAdapter extends RecyclerView.Adapter<DiaryRecyclerViewAdapter.ViewHolder> {
    private List<Diary> diaries;

    public DiaryRecyclerViewAdapter(List<Diary> diaries) {
        this.diaries = diaries;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        View view = layoutInflater.inflate(R.layout.diary_item, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.time.setText(diaries.get(i).getDiaryTime());
        viewHolder.content.setText(diaries.get(i).getContent());
        viewHolder.adapter = new DiaryImageRecyclerViewAdapter(diaries.get(i).getUriImages());
        viewHolder.recyclerView.setAdapter(viewHolder.adapter);
    }

    @Override
    public int getItemCount() {
        if(diaries != null)
            return diaries.size();
        else
            return 0;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView time;
        TextView content;
        DiaryImageRecyclerViewAdapter adapter;
        RecyclerView recyclerView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            time = itemView.findViewById(R.id.diary_item_time);
            content = itemView.findViewById(R.id.diary_item_content);
            recyclerView = itemView.findViewById(R.id.rvDiaryImage);
            //recyclerView.setLayoutManager(new GridLayoutManager(itemView.getContext(), 3));
            recyclerView.setLayoutManager(new LinearLayoutManager(itemView.getContext()));
        }
    }
}
