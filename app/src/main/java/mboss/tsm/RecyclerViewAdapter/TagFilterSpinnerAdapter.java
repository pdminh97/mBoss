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
import mboss.tsm.Model.Tag;
import mboss.tsm.mboss.R;

public class TagFilterSpinnerAdapter extends BaseAdapter {
    List<Boss> tags;
    Context context;

    public TagFilterSpinnerAdapter(List<Boss> tags, Context context) {
        this.tags = tags;
        this.context = context;
        Boss firstTag = new Boss();
        firstTag.setBossName("Tất Cả");
        tags.add(0, firstTag);
    }

    @Override
    public int getCount() {
        if(tags != null)
            return tags.size();
        else
            return 0;
    }

    @Override
    public Object getItem(int position) {
        return tags.get(position);
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
        Glide.with(context).load(tags.get(position).getPictures())
                .dontAnimate()
                .into(circleImageView);
        TextView textView = view.findViewById(R.id.tagFilterName);
        textView.setText(tags.get(position).getBossName());
        return view;
    }
}
