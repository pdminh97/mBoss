package mboss.tsm.RecyclerViewAdapter;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;

import java.util.List;

import mboss.tsm.mboss.R;

public class DiaryImageRecyclerViewAdapter extends RecyclerView.Adapter<DiaryImageRecyclerViewAdapter.ViewHolder> {
    private List<String> imageUris;
    private Context context;

    public DiaryImageRecyclerViewAdapter(Context context, List<String> imageUris) {
        this.imageUris = imageUris;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.diary_image_item, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        GlideDrawableImageViewTarget target = new GlideDrawableImageViewTarget(viewHolder.imageView);
        Log.e("ER", Uri.parse(imageUris.get(i)).toString());
        Glide.with(context)
                .load(Uri.parse(imageUris.get(i)))
                .placeholder(R.drawable.picture)
                .dontAnimate()
                .into(target);
        //viewHolder.imageView.setImageURI(imageUris[i]);
    }

    @Override
    public int getItemCount() {
        if(imageUris != null) {
            return imageUris.size();
        } else {
            return 0;
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder {
         private  ImageView imageView;
         public ViewHolder(@NonNull View itemView) {
             super(itemView);
             imageView = itemView.findViewById(R.id.diary_image_src);
         }
     }
}
