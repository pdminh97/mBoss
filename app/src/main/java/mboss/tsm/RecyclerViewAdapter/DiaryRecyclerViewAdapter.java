package mboss.tsm.RecyclerViewAdapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import mboss.tsm.Fragment.DiaryFragment;
import mboss.tsm.Model.Diary;
import mboss.tsm.mboss.R;

public class DiaryRecyclerViewAdapter extends RecyclerView.Adapter<DiaryRecyclerViewAdapter.ViewHolder> {
    private List<Diary> diaries;
    private DiaryFragment diaryFragment;
    private Context context;

    public DiaryRecyclerViewAdapter(Context context, List<Diary> diaries, DiaryFragment diaryFragment) {
        this.diaries = diaries;
        this.diaryFragment = diaryFragment;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        View view = layoutInflater.inflate(R.layout.diary_item, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {
        viewHolder.time.setText(diaries.get(i).getDiaryTime());
        viewHolder.content.setText(diaries.get(i).getContent());
        viewHolder.imageAdapter = new DiaryImageRecyclerViewAdapter(context, diaries.get(i).getUriImages());
        viewHolder.rvImage.setAdapter(viewHolder.imageAdapter);
        viewHolder.tagAdapter = new TagedRecyclerViewAdapter(context, diaries.get(i).getTageds());
        viewHolder.rvTag.setAdapter(viewHolder.tagAdapter);
        viewHolder.threedot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                diaryFragment.showModifyFragment(i);
            }
        });
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
        DiaryImageRecyclerViewAdapter imageAdapter;
        TagedRecyclerViewAdapter tagAdapter;
        RecyclerView rvImage;
        RecyclerView rvTag;
        ImageView threedot;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            time = itemView.findViewById(R.id.diary_item_time);
            content = itemView.findViewById(R.id.diary_item_content);
            rvImage = itemView.findViewById(R.id.rvDiaryImage);
            rvTag = itemView.findViewById(R.id.rvDiaryTag);
            threedot = itemView.findViewById(R.id.threedot);
            rvImage.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
            rvTag.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
        }
    }
}
