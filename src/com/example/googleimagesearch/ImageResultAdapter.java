package com.example.googleimagesearch;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.googleimagesearch.bean.ImageBean;

public class ImageResultAdapter extends BaseAdapter {
	private LayoutInflater mInflater = null;
	//private ResponseData mResponse = null;
	private ArrayList<ImageBean> mImageArray = null;

	public void setmImageArray(ArrayList<ImageBean> mImageArray) {
		this.mImageArray = mImageArray;
	}

	public ImageResultAdapter(Context iContext) {
		mInflater = LayoutInflater.from(iContext);
	}

	/*
	 * Called to get the number of documents
	 * 
	 * @see android.widget.Adapter#getCount()
	 */
	@Override
	public int getCount() {
		return mImageArray.size();
	}

	/*
	 * Called to get the document detail
	 * 
	 * @see android.widget.Adapter#getItem(int)
	 */
	@Override
	public Object getItem(int position) {
		return mImageArray.get(position);
	}

	/*
	 * Called to get the position of the document in the array of document
	 * 
	 * @see android.widget.Adapter#getItemId(int)
	 */
	@Override
	public long getItemId(int iPosition) {
		return iPosition;
	}

	/*
	 * Called to Inflate the list item's view
	 * 
	 * @see android.widget.Adapter#getView(int, android.view.View,
	 * android.view.ViewGroup)
	 */
	@Override
	public View getView(final int iPosition, View iConvertView,
			ViewGroup iParent) {
		Holder holder = null;
		if (iConvertView == null) {
			holder = new Holder();
			iConvertView = mInflater.inflate(R.layout.image_layout, null);
			holder.mImageTitleTxtView = (TextView) iConvertView
					.findViewById(R.id.title_tx_view);
			holder.mImageView = (ImageView) iConvertView
					.findViewById(R.id.search_img_view);
			iConvertView.setTag(holder);
		} else {
			holder = (Holder) iConvertView.getTag();
		}
		holder.mImageTitleTxtView.setText(mImageArray.get(iPosition).getmTitle());
		holder.mImageView.setImageBitmap(mImageArray.get(iPosition).getmBitmap());
		return iConvertView;
	}

	/**
	 * Holds views of list view
	 * 
	 * @author ABHISHEK
	 */
	private class Holder {
		private TextView mImageTitleTxtView = null;
		private ImageView mImageView = null;
	}

}
