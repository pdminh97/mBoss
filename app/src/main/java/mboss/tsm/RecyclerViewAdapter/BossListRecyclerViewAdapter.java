package mboss.tsm.RecyclerViewAdapter;

import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import mboss.tsm.Model.Boss;
import mboss.tsm.mboss.R;
public class BossListRecyclerViewAdapter extends RecyclerView.Adapter<BossListRecyclerViewAdapter.ViewHolder> {
    private setOnIteamlistener onIteamlistener;
    private List<Boss> data;

    public BossListRecyclerViewAdapter(List<Boss> data) {
        this.data = data;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View view = inflater.inflate(R.layout.recyclerview_itemboss, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int pos) {
        viewHolder.tvName.setText(data.get(pos).getBossName());
        viewHolder.mAvata.setImageURI(Uri.parse(data.get(pos).getPictures()));
        Log.e("My Picture", data.get(pos).getPictures());

        viewHolder.mLnlRoot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onIteamlistener!=null){
                    onIteamlistener.setOnIteamListener(pos);
                }
            }
        });
        viewHolder.mAvata.setImageURI(Uri.parse(data.get(pos).getPictures()));
    }

    @Override
    public int getItemCount() {
        if (data == null)
            return 0;
        return data.size();
    }

    public interface setOnIteamlistener {
        void setOnIteamListener(int position);
    }

    public void setItemOnListenner(setOnIteamlistener iteamOnListenner) {
        this.onIteamlistener = iteamOnListenner;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvName;
        private LinearLayout mLnlRoot;
        private ImageView mAvata;
        public ViewHolder(View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
            mLnlRoot = itemView.findViewById(R.id.lnlRoot);
            mAvata = itemView.findViewById(R.id.image_boss);
        }
    }
}
