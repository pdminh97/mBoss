package mboss.tsm.RecyclerViewAdapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import mboss.tsm.Model.Service;
import mboss.tsm.Repository.ServiceDetailRepository;
import mboss.tsm.Utility.ItemClickListener;
import mboss.tsm.mboss.R;
import mboss.tsm.mboss.ServiceDetailActivity;

public class ServicesRecyclerViewAdapter extends RecyclerView.Adapter<ServicesRecyclerViewAdapter.ViewHolder> {
    private List<Service> services;
    private Context context;

    public ServicesRecyclerViewAdapter(List<Service> services, Context context) {
        this.services = services;
        this.context = context;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View view = inflater.inflate(R.layout.service_item, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.txtServiceName.setText(services.get(i).getServiceName());
        viewHolder.txtClinicName.setText(services.get(i).getClinicName());
        viewHolder.txtAvgPrice.setText(services.get(i).getAvgPrice() + "Đ");
        viewHolder.txtRating.setText(services.get(i).getRating() + "/5");

        viewHolder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                ServiceDetailRepository repository = new ServiceDetailRepository(context);
                repository.getClinicByServiceID(services.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        if(services == null) return 0; return services.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView txtServiceName;
        private TextView txtClinicName;
        private TextView txtAvgPrice;
        private TextView txtRating;

        private ItemClickListener itemClickListener;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtServiceName = itemView.findViewById(R.id.txtServiceName);
            txtClinicName = itemView.findViewById(R.id.txtClinicName);
            txtAvgPrice = itemView.findViewById(R.id.txtAvgPrice);
            txtRating = itemView.findViewById(R.id.txtRating);
            itemView.setOnClickListener(this);
        }

        public void setItemClickListener(ItemClickListener itemClickListener) {
            this.itemClickListener = itemClickListener;
        }

        @Override
        public void onClick(View v) {
            itemClickListener.onItemClick(v, getAdapterPosition());
        }
    }
}
