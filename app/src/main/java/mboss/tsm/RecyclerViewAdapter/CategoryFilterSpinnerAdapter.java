package mboss.tsm.RecyclerViewAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import mboss.tsm.Model.Boss;
import mboss.tsm.Model.Category;
import mboss.tsm.mboss.R;

public class CategoryFilterSpinnerAdapter extends BaseAdapter {
    List<Category> categories;
    Context context;

    public CategoryFilterSpinnerAdapter(List<Category> tags, Context context) {
        this.categories = tags;
        this.context = context;
        Category firstTag = new Category();
        firstTag.setName("Tất Cả");
        tags.add(0, firstTag);
    }

    @Override
    public int getCount() {
        if(categories != null)
            return categories.size();
        else
            return 0;
    }

    @Override
    public Object getItem(int position) {
        return categories.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.tag_filter_item, parent, false);
        CircleImageView circleImageView = view.findViewById(R.id.tagFilterImage);
        Glide.with(context).load(categories.get(position).getImage())
                .dontAnimate()
                .into(circleImageView);
        TextView textView = view.findViewById(R.id.tagFilterName);
        textView.setText(categories.get(position).getName());
        return view;
    }
}
