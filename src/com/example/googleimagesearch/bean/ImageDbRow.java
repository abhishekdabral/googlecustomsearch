package com.example.googleimagesearch.bean;

import java.util.ArrayList;

/**
 * Called to hold the Row Contents from the DB for the corresponding search query
 * @author ABHISHEK
 */
public class ImageDbRow {

	private ArrayList<String> mImageId = null;
	private ArrayList<String> mImageTitle = null;
	private String mStart = null;
	private String mSearchQuery = null;
	private int mTotalResult = 0;
	public int getmTotalResult() {
		return mTotalResult;
	}
	public void setmTotalResult(int mTotalResult) {
		this.mTotalResult = mTotalResult;
	}
	public String getmStart() {
		return mStart;
	}
	public void setmStart(String mStart) {
		this.mStart = mStart;
	}
	public ArrayList<String> getmImageTitle() {
		return mImageTitle;
	}
	public void setmImageTitle(ArrayList<String> mImageTitle) {
		this.mImageTitle = mImageTitle;
	}
	public ArrayList<String> getmImageId() {
		return mImageId;
	}
	public void setmImageId(ArrayList<String> mImageId) {
		this.mImageId = mImageId;
	}
	public String getmSearchQuery() {
		return mSearchQuery;
	}
	public void setmSearchQuery(String mSearchQuery) {
		this.mSearchQuery = mSearchQuery;
	}
}
