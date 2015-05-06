package com.example.googleimagesearch.bean;

import android.graphics.Bitmap;

/**
 * Bean Class used to hole the properties of the Image from the Image Search Request's Response
 * @author ABHISHEK
 */
public class ImageBean {

	private Bitmap mBitmap = null;
	private String mTitle = null;
	private String mImageId = null;
	private String mTotalResult = null;
	
	public String getmTotalResult() {
		return mTotalResult;
	}
	public void setmTotalResult(String mTotalResult) {
		this.mTotalResult = mTotalResult;
	}
	public Bitmap getmBitmap() {
		return mBitmap;
	}
	public void setmBitmap(Bitmap iBitmap) {
		mBitmap = iBitmap;
	}
	public String getmImageId() {
		return mImageId;
	}
	public String getmTitle() {
		return mTitle;
	}
	public void setmTitle(String iTitle) {
		mTitle = iTitle;
	}
	public void setmImageId(String id) {
		mImageId = id;
	}
}
