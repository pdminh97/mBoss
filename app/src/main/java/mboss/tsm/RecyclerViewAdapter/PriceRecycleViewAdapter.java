package mboss.tsm.RecyclerViewAdapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.List;

import mboss.tsm.Model.ServiceDetail;
import mboss.tsm.mboss.R;

public class PriceRecycleViewAdapter extends RecyclerView.Adapter<PriceRecycleViewAdapter.ViewHolder> {
    List<ServiceDetail> serviceDetails;

    public PriceRecycleViewAdapter(List<ServiceDetail> serviceDetails) {
        this.serviceDetails = serviceDetails;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View view = inflater.inflate(R.layout.price_item, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.txtName.setText(serviceDetails.get(i).getName());
        viewHolder.txtPrice.setText(formatCurrency(serviceDetails.get(i).getPrice() + "") + "Đ");
    }

    @Override
    public int getItemCount() {
        if(serviceDetails != null) {
            return serviceDetails.size();
        } else {
            return 0;
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView txtName;
        private TextView txtPrice;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtName = itemView.findViewById(R.id.service_detail_name);
            txtPrice = itemView.findViewById(R.id.service_detail_price);
        }
    }

    private String formatCurrency(String n){
        String s = n.toString();
        String regex = "\\B(?=(\\d{3})+(?!\\d))";
        String ret = s.replaceAll(regex, ".");
        return ret;
    }
}
