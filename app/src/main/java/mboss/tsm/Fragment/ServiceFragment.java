package mboss.tsm.Fragment;


import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.os.Parcel;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.arlib.floatingsearchview.FloatingSearchView;
import com.arlib.floatingsearchview.suggestions.model.SearchSuggestion;

import java.util.ArrayList;
import java.util.List;

import mboss.tsm.Repository.ServiceRepository;
import mboss.tsm.mboss.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ServiceFragment extends Fragment {
    private List<Suggestion> mSuggestions = new ArrayList<>();
    private FloatingSearchView searchView;
    private Context context = getContext();
    ServiceRepository serviceRepository;

    public ServiceFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_service, container, false);

        serviceRepository = new ServiceRepository(view);
        serviceRepository.getTopSevice();

        initData();
        searchView = view.findViewById(R.id.floating_search_view);
        prepareSearchView();

        return view;
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
                serviceRepository.searchSevice(suggestion.getBody());
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
