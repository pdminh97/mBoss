package mboss.tsm.RecyclerViewAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import mboss.tsm.Model.Service;
import mboss.tsm.mboss.R;

public class PriceExpendableAdapter extends BaseExpandableListAdapter {
    private Context context;
    private List<Service> services;
    private ImageView ivGroupIndicator;

    public PriceExpendableAdapter(Context context, List<Service> services) {
        this.context = context;
        this.services = services;
    }

    @Override
    public int getGroupCount() {
        return services.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return services.get(groupPosition).getServiceDetails().size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return services.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return services.get(groupPosition).getServiceDetails().get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.price_expendable_header, parent, false);

        TextView header = view.findViewById(R.id.price_expendable_header);
        header.setText(services.get(groupPosition).getServiceName());
        ivGroupIndicator = view.findViewById(R.id.ivGroupIndicator);
        ivGroupIndicator.setSelected(isExpanded);
        if(services.get(groupPosition).getServiceDetails().size() == 0) {
            ivGroupIndicator.setBackground(null);
        }
        return view;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.price_expendable_item, parent, false);

        TextView itemName = view.findViewById(R.id.price_name_expendable_item);
        TextView itemPrice = view.findViewById(R.id.price_expendable_item);
        itemName.setText(services.get(groupPosition).getServiceDetails().get(childPosition).getName());
        itemPrice.setText(formatCurrency(services.get(groupPosition).getServiceDetails().get(childPosition).getPrice() + "") + "Đ");

        return view;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    private String formatCurrency(String n){
        String s = n.toString();
        String regex = "\\B(?=(\\d{3})+(?!\\d))";
        String ret = s.replaceAll(regex, ".");
        return ret;
    }
}
