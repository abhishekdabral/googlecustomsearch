package com.example.googleimagesearch;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * Adapter Class called to set the list with the recent search queries
 * if search field find recent search terms
 * @author ABHISHEK
 */
public class SearchHistoryAdapter extends BaseAdapter{

	private ArrayList<String> mTitltArray = null;
	private LayoutInflater mInflater = null;
	public SearchHistoryAdapter(Context iCon) {
		mInflater = LayoutInflater.from(iCon);
	}

	/*
	 * Called to get the nuber of item in the searched query list
	 * @see android.widget.Adapter#getCount()
	 */
	@Override
	public int getCount() {
		final int size = mTitltArray.size();
		/*
		 *make list size to five if array has more results 
		 */
		if(size > 5)
			return 5;
		else
			return size;
	}

	/**
	 * Called to set the recent search Title Name in the Array
	 * @param iTitltArray : Searched Query
	 */
	public void setmTitltArray(ArrayList<String> iTitltArray) {
		mTitltArray = iTitltArray;
	}

	@Override
	public Object getItem(int iPosition) {
		return mTitltArray.get(iPosition);
	}

	@Override
	public long getItemId(int iPosition) {
		return iPosition;
	}

	@Override
	public View getView(int iPosition, View iConvertView, ViewGroup iParent) {
		Holder holder = null;
		if (iConvertView == null) {
			holder = new Holder();
			iConvertView = mInflater.inflate(R.layout.recent_search_item_layout, null);
			holder.mTitleTxtView = (TextView) iConvertView
					.findViewById(R.id.txt_view_recent_search);
			iConvertView.setTag(holder);
		} else {
			holder = (Holder) iConvertView.getTag();
		}
		holder.mTitleTxtView.setText(mTitltArray.get(iPosition));
		return iConvertView;
	}

	/**
	 * Holds views of list view
	 * 
	 * @author ABHISHEK
	 */
	private class Holder {
		private TextView mTitleTxtView = null;
	}
}


