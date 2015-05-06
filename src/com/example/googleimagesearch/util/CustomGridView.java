package com.example.googleimagesearch.util;

/*
 * Import android packages
 */
import java.lang.reflect.Constructor;
import android.content.Context;
import android.util.AttributeSet;
import android.webkit.WebView;
import android.widget.GridView;

/**
 * Custom Grid View , The purpose to make this custom Grid
 * view is to detect the end of the scroll in grid view and triggers an event.
 * @author ABHISHEK
 */
public class CustomGridView extends GridView {

	/**
	 * {@link OnScrollChangedCallback}
	 */
	private OnScrollChangedCallback mOnScrollChangedCallback;

	/**
	 * Parameterized {@link Constructor} of {@link CustomGridView}
	 * @param context : Calling Activity's context
	 */
	public CustomGridView(final Context context) {
		super(context);
	}

	/**
	 * Parameterized {@link Constructor} of {@link CustomGridView}
	 * @param context : Calling Activity's context
	 * @param attrs : Attribute set
	 */
	public CustomGridView(final Context context, final AttributeSet attrs) {
		super(context, attrs);
	}

	/**
	 * Parameterized {@link Constructor} of {@link CustomGridView}
	 * @param context : Calling Activity's context
	 * @param attrs : Attribute set
	 * @param defStyle : Style Definition
	 */
	public CustomGridView(final Context context, final AttributeSet attrs, final int defStyle) {
		super(context, attrs, defStyle);
	}

	/*
	 * Called on web view's scroll state change
	 * @see android.webkit.WebView#onScrollChanged(int, int, int, int)
	 */
	@Override
	protected void onScrollChanged(final int l, final int t, final int oldl, final int oldt) {
		super.onScrollChanged(l, t, oldl, oldt);
		if (mOnScrollChangedCallback != null)
			mOnScrollChangedCallback.onScroll(l, t);
	}

	/**
	 * @return : instance of {@link OnScrollChangedCallback}
	 */
	public OnScrollChangedCallback getOnScrollChangedCallback() {
		return mOnScrollChangedCallback;
	}

	/**
	 * Called to set the instance of {@link OnScrollChangedCallback}
	 * @param onScrollChangedCallback : instance of {@link OnScrollChangedCallback}
	 */
	public void setOnScrollChangedCallback(final OnScrollChangedCallback onScrollChangedCallback) {
		mOnScrollChangedCallback = onScrollChangedCallback;
	}

	/**
	 * Implement in the activity/fragment/view that you want to listen to the webview
	 */
	public static interface OnScrollChangedCallback {
		public void onScroll(int l, int t);
	}
}