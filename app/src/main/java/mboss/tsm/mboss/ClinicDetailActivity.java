package mboss.tsm.mboss;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import mboss.tsm.Fragment.FeedbackFragment;
import mboss.tsm.Fragment.MapFragment;
import mboss.tsm.Fragment.PriceFragment;
import mboss.tsm.Model.Clinic;

public class ClinicDetailActivity extends AppCompatActivity {
    private Clinic clinic;
    private int serviceID;
    private String serviceName;
    private TextView txtClinicName;
    private TextView txtClinicAddress;
    private RatingBar ratingBar;
    private ImageView ivClinicImage;
    private ImageView btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.clinic_detail);

        clinic = (Clinic) getIntent().getSerializableExtra("clinic");

        txtClinicName = findViewById(R.id.txtClinicName);
        txtClinicAddress = findViewById(R.id.txtClinicAddress);
        ratingBar = findViewById(R.id.clinicRating);

        btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        ivClinicImage = findViewById(R.id.ivClinicImage);
        Glide.with(this).load("https://mbosstsm.azurewebsites.net/Asset/Image/Clinic/" + clinic.getImage()).into(ivClinicImage);
        txtClinicAddress.setText(clinic.getAddress());
        txtClinicName.setText(clinic.getName());
        ratingBar.setRating(clinic.getRating());

        TabLayout tabLayout = findViewById(R.id.tab_layout);
        ViewPager viewPager = findViewById(R.id.view_pager);

        setupViewPager(viewPager);
        tabLayout.setupWithViewPager(viewPager);
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());

        Bundle bundle = new Bundle();
        bundle.putString("latitude", clinic.getLatitude());
        bundle.putString("longtitude", clinic.getLongtitude());
        bundle.putInt("clinicID", clinic.getClinicID());
        MapFragment mapFragment = new MapFragment();
        mapFragment.setArguments(bundle);

        PriceFragment priceFragment = new PriceFragment();
        priceFragment.setArguments(bundle);

        FeedbackFragment feedbackFragment = new FeedbackFragment();
        feedbackFragment.setArguments(bundle);


        adapter.addFragment(priceFragment, "Bảng Giá");
        adapter.addFragment(mapFragment, "Bản Đồ");
        adapter.addFragment(feedbackFragment, "Bình Luận");

        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(3);
    }

    public void clickToBack(View view) {
        this.finish();
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }
}
