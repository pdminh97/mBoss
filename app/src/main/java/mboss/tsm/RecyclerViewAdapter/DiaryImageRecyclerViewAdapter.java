package mboss.tsm.RecyclerViewAdapter;

import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import mboss.tsm.mboss.R;

public class DiaryImageRecyclerViewAdapter extends RecyclerView.Adapter<DiaryImageRecyclerViewAdapter.ViewHolder> {
    private Uri[] imageUris;

    public DiaryImageRecyclerViewAdapter(Uri[] imageUris) {
        this.imageUris = imageUris;
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
        viewHolder.imageView.setImageURI(imageUris[i]);
    }

    @Override
    public int getItemCount() {
        if(imageUris != null) {
            return imageUris.length;
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
