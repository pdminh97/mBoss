package mboss.tsm.RecyclerViewAdapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import mboss.tsm.Model.Clinic;
import mboss.tsm.Repository.ClinicRepository;
import mboss.tsm.Utility.ItemClickListener;
import mboss.tsm.mboss.R;

public class ClinicRecyclerViewAdapter extends RecyclerView.Adapter<ClinicRecyclerViewAdapter.ViewHolder> {
    private List<Clinic> clinics;
    private Context context;

    public ClinicRecyclerViewAdapter(List<Clinic> clinics, Context context) {
        this.clinics = clinics;
        this.context = context;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View view = inflater.inflate(R.layout.clinic_item, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.txtClinicName.setText(clinics.get(i).getName());
        viewHolder.txtClinicAddress.setText(clinics.get(i).getAddress());
        viewHolder.txtRating.setRating(clinics.get(i).getRating());
        Glide.with(context).load("https://mbosstsm.azurewebsites.net/Asset/Image/Clinic/" + clinics.get(i).getImage()).into(viewHolder.clinicImage);

        viewHolder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                ClinicRepository repository = new ClinicRepository(context);
                repository.getClinicDetailByID(clinics.get(position).getClinicID());
            }
        });
    }

    @Override
    public int getItemCount() {
        if(clinics == null) return 0; return clinics.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView txtClinicName;
        private RatingBar txtRating;
        private ImageView clinicImage;
        private TextView txtClinicAddress;

        private ItemClickListener itemClickListener;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtClinicName = itemView.findViewById(R.id.txtClinicName);
            txtClinicAddress = itemView.findViewById(R.id.txtClinicAddress);
            txtRating = itemView.findViewById(R.id.txtRating);
            clinicImage = itemView.findViewById(R.id.clinicImage);
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
