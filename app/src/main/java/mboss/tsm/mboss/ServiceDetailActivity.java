package mboss.tsm.mboss;

import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import mboss.tsm.Fragment.FeedbackFragment;
import mboss.tsm.Fragment.MapFragment;
import mboss.tsm.Fragment.PriceFragment;
import mboss.tsm.Model.Clinic;

public class ServiceDetailActivity extends AppCompatActivity {
    private Clinic clinic;
    private int serviceID;
    private String serviceName;
    private TextView txtClinicName;
    private TextView txtClinicAddress;
    private TextView txtServiceName;
    private RatingBar ratingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.service_detail);

        serviceID = getIntent().getIntExtra("serviceID", 0);
        serviceName = getIntent().getStringExtra("serviceName");
        clinic = (Clinic) getIntent().getSerializableExtra("clinic");

        txtClinicName = findViewById(R.id.txtClinicName);
        txtClinicAddress = findViewById(R.id.txtClinicAddress);
        txtServiceName = findViewById(R.id.txtServiceName);
        ratingBar = findViewById(R.id.clinicRating);

        txtClinicAddress.setText(clinic.getAddress());
        txtClinicName.setText(clinic.getName());
        txtServiceName.setText(serviceName);
        ratingBar.setRating(clinic.getRating());

        TabLayout tabLayout = findViewById(R.id.tab_layout);
        ViewPager viewPager = findViewById(R.id.view_pager);

        setupViewPager(viewPager);
        tabLayout.setupWithViewPager(viewPager);

    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());

        Bundle bundle = new Bundle();
        bundle.putInt("serviceID", serviceID);
        bundle.putString("latitude", clinic.getLatitude());
        bundle.putString("longtitude", clinic.getLongtitude());
        bundle.putInt("clinicID", clinic.getClinicID());
        MapFragment mapFragment = new MapFragment();
        mapFragment.setArguments(bundle);

        PriceFragment priceFragment = new PriceFragment();
        priceFragment.setArguments(bundle);

        FeedbackFragment feedbackFragment = new FeedbackFragment();
        feedbackFragment.setArguments(bundle);

        adapter.addFragment(mapFragment, "Địa Chỉ");
        adapter.addFragment(priceFragment, "Bảng Giá");
        adapter.addFragment(feedbackFragment, "Bình Luận");

        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(3);
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
