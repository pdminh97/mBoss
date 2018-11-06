package mboss.tsm.RecyclerViewAdapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import mboss.tsm.mboss.R;

public class HistoryRecyclerViewAdapter extends RecyclerView.Adapter<HistoryRecyclerViewAdapter.ViewHolder> {
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View view = inflater.inflate(R.layout.recyclerview_item_history, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }
    class ViewHolder extends RecyclerView.ViewHolder {
        TextView time;
        TextView content;
        DiaryImageRecyclerViewAdapter adapter;
        RecyclerView recyclerView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
//            recyclerView = itemView.findViewById(R.id.rvHistory);
            //recyclerView.setLayoutManager(new GridLayoutManager(itemView.getContext(), 3));
            //recyclerView.setLayoutManager(new LinearLayoutManager(itemView.getContext()));
//            recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        }
    }
}
