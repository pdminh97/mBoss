package mboss.tsm.mboss;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Parcel;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.arlib.floatingsearchview.FloatingSearchView;
import com.arlib.floatingsearchview.suggestions.model.SearchSuggestion;

import java.util.ArrayList;
import java.util.List;

import mboss.tsm.Model.Service;
import mboss.tsm.RecyclerViewAdapter.ServicesRecyclerViewAdapter;
import mboss.tsm.Repository.ServiceRepository;
import mboss.tsm.Service.IService;
import mboss.tsm.Utility.APIUtil;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ServiceActivity extends AppCompatActivity {
    private List<Suggestion> mSuggestions = new ArrayList<>();
    private FloatingSearchView searchView;
    private Context context = this;
    ServiceRepository serviceRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service);

        serviceRepository = new ServiceRepository(context);
        serviceRepository.getTopSevice();

        initData();
        searchView = findViewById(R.id.floating_search_view);
        prepareSearchView();
    }

    private void prepareSearchView() {
        searchView.setOnQueryChangeListener(new FloatingSearchView.OnQueryChangeListener() {
            @Override
            public void onSearchTextChanged(String oldQuery, String newQuery) {
                searchView.showProgress();
                searchView.swapSuggestions(getSuggestion(newQuery));
                searchView.hideProgress();
            }
        });

        searchView.setOnFocusChangeListener(new FloatingSearchView.OnFocusChangeListener() {
            @Override
            public void onFocus() {
                searchView.showProgress();
                searchView.swapSuggestions(getSuggestion(searchView.getQuery()));
                searchView.hideProgress();
            }

            @Override
            public void onFocusCleared() {

            }
        });

        searchView.setOnSearchListener(new FloatingSearchView.OnSearchListener() {
            @Override
            public void onSuggestionClicked(SearchSuggestion searchSuggestion) {
                Suggestion suggestion = (Suggestion) searchSuggestion;
                //Toast.makeText(getApplicationContext(),"Ban vua chon "+suggestion.getBody(),Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onSearchAction(String currentQuery) {

                serviceRepository.searchSevice(currentQuery);
            }
        });
    }

    private void initData(){
        mSuggestions.add(new Suggestion("Tắm"));
        mSuggestions.add(new Suggestion("Chăm Sóc"));
        mSuggestions.add(new Suggestion("Giữ Hộ"));
        mSuggestions.add(new Suggestion("Làm Đẹp"));
    }

    private List<Suggestion> getSuggestion(String query){
        List<Suggestion> suggestions=new ArrayList<>();
        for(Suggestion suggestion:mSuggestions){
            if(suggestion.getBody().toLowerCase().contains(query.toLowerCase())){
                suggestions.add(suggestion);
            }
        }
        return suggestions;
    }

    @SuppressLint("ParcelCreator")
    private class Suggestion implements SearchSuggestion {
        private String mName;
        private boolean mIsHistory = false;

        public Suggestion(String suggestion) {
            mName= suggestion.toLowerCase();
        }

        public void setIsHistory(boolean isHistory) {
            this.mIsHistory = isHistory;
        }

        public boolean getIsHistory() {
            return this.mIsHistory;
        }

        @Override
        public String getBody() {
            return mName;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {

        }
    }
}

