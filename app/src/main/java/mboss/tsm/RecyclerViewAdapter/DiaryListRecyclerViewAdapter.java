package mboss.tsm.RecyclerViewAdapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import mboss.tsm.Model.Diary;
import mboss.tsm.mboss.R;

public class DiaryListRecyclerViewAdapter extends RecyclerView.Adapter<DiaryListRecyclerViewAdapter.ViewHolder> {
    List<Diary> diaries;
    public DiaryListRecyclerViewAdapter(List<Diary> diaries) {
        this.diaries = diaries;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View view = inflater.inflate(R.layout.recyclerview_item, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.txtTitle.setText(diaries.get(i).getContent());
    }

    @Override
    public int getItemCount() {
        return diaries.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtTitle;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTitle = itemView.findViewById(R.id.txtTitle);
        }
    }
}
