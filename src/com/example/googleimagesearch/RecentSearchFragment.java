package com.example.googleimagesearch;

import java.util.ArrayList;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

/**
 * Class contains a list which hold the recent searches queries
 * @author ABHISHEK
 */
public class RecentSearchFragment extends Fragment implements OnItemClickListener{
	
	private ListView mListView = null;
	private SearchHistoryAdapter mHistoryAdapter = null;
	private MainActivity mActivity = null;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		View view = inflater.inflate(R.layout.recent_search_list, null);
		mListView = (ListView) view.findViewById(R.id.list_view_recent_search);
		mListView.setOnItemClickListener(this);
		mHistoryAdapter = new SearchHistoryAdapter(getActivity());
		System.out.println("-----ADAPTER---" + mHistoryAdapter);
		mActivity = (MainActivity) getActivity();
		ArrayList<String> searchQuery = new ArrayList<>();
		mHistoryAdapter.setmTitltArray(searchQuery);
		mListView.setAdapter(mHistoryAdapter);
		return view;
	}

	/**
	 * Called to update the list contents as search field text changes
	 * @param iSearchQuery : Search Query in the Search Field
	 */
	public void updateHistoryList(ArrayList<String> iSearchQuery){
		mHistoryAdapter.setmTitltArray(iSearchQuery);
		mHistoryAdapter.notifyDataSetChanged();
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		mActivity.onRecentSearchItemSelected((String) mListView.getItemAtPosition(position));
	}
}
