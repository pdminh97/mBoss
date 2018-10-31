package mboss.tsm.RecyclerViewAdapter;

import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

import mboss.tsm.mboss.R;

public class DiaryImageRecyclerViewAdapter extends RecyclerView.Adapter<DiaryImageRecyclerViewAdapter.ViewHolder> {
    List<String> imageSrcs = new ArrayList<>();

    public DiaryImageRecyclerViewAdapter() {
        imageSrcs.add("@drawable/cat_avt");
        imageSrcs.add("@drawable/dog_avt");
        imageSrcs.add("@drawable/logo");
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        View view = layoutInflater.inflate(R.layout.diary_image_item, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.imageView.setImageURI(Uri.parse(imageSrcs.get(i)));
    }

    @Override
    public int getItemCount() {
        if(imageSrcs != null) {
            return imageSrcs.size();
        } else {
            return 0;
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
         public ViewHolder(@NonNull View itemView) {
             super(itemView);
             imageView = itemView.findViewById(R.id.diary_image_src);
         }
     }
}
