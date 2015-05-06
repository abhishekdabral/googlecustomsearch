package com.example.googleimagesearch;

import java.io.File;

import java.io.IOException;
import java.util.ArrayList;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.AnimationDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.LruCache;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;

import com.example.googleimagesearch.bean.ImageBean;
import com.example.googleimagesearch.bean.ImageDbRow;
import com.example.googleimagesearch.bean.SearchResponse;
import com.example.googleimagesearch.database.HistoryDatabase;
import com.example.googleimagesearch.listener.SearchImageFoundListener;
import com.example.googleimagesearch.serverconnection.BackgroundThreadForServerOperation;
import com.example.googleimagesearch.util.Constants;
import com.example.googleimagesearch.util.Constants.SERVER_REQUEST_ID;
import com.example.googleimagesearch.util.Constants.UserInfoMessages;
import com.example.googleimagesearch.util.DiskLruCache;
import com.example.googleimagesearch.util.Utility;

public class MainActivity extends Activity implements OnClickListener,
		SearchImageFoundListener, TextWatcher,
		OnScrollListener {

	private static final String TAG = MainActivity.class.getSimpleName();
	private ImageView mSearchImageView = null;
	private EditText mSearchEdtTxt = null;
	private FrameLayout mHistorySearchFrame = null;
	private GridView mImagesGridView = null;
	private LruCache<String, Bitmap> mMemoryCache;
	private ArrayList<ImageBean> mImageArray = new ArrayList<>();
	private DiskLruCache mDiskLruCache;
	private final Object mDiskCacheLock = new Object();
	// // Default memory cache size in kilobytes
	// private static final int DEFAULT_MEM_CACHE_SIZE = 1024 * 5; // 5MB

	// Default disk cache size in bytes
	private static final int DISK_CACHE_SIZE = 1024 * 1024 * 10; // 10MB
	private static final String DISK_CACHE_SUBDIR = "thumbnails";
	private ImageResultAdapter mImageAdpater = null;

	/**
	 * Used to keep track of search page index and incremented by 10, as each
	 * search results may have 10 result items
	 */
	// private int mPageLoading = 1;
	private HistoryDatabase mDatabase = null;
	private String mSearchQuery = null;
	// private static final int DISK_CACHE_INDEX = 0;
	//private boolean mEndReachedLoadMore = false;
	private int count = 1000;
	/**
	 * Set true when GridView Content set GridView Adpater
	 */
	private boolean isAdapterSet = false;
	private RecentSearchFragment mRecentSearchFragment = null;
	private int mCurrentPage = CURRENT_PAGE_DEFAULT;
	private int mNextPage = NEXT_PAGE_DEFAULT;
	private static final int CURRENT_PAGE_DEFAULT = 0;
	private static final int NEXT_PAGE_DEFAULT = 1;
	private ImageView mProgress = null;
	private String mTotalResult;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mDatabase = new HistoryDatabase(this, "HistoryDatabase", null, 1);
		initViews();
		startProgressAnimation();
		registerViewListener();
		/* initalize history fragment */
		mRecentSearchFragment = new RecentSearchFragment();
		getFragmentManager().beginTransaction()
				.replace(R.id.search_result_frame, mRecentSearchFragment)
				.commit();
		mImageAdpater = new ImageResultAdapter(this);
		// Get max available VM memory, exceeding this amount will throw an
		// OutOfMemory exception. Stored in kilobytes as LruCache takes an
		// int in its constructor.
		final int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);

		// Use 1/8th of the available memory for this memory cache.
		final int cacheSize = maxMemory / 8;

		mMemoryCache = new LruCache<String, Bitmap>(cacheSize) {
			@Override
			protected int sizeOf(String key, Bitmap bitmap) {
				// The cache size will be measured in kilobytes rather than
				// number of items.
				return bitmap.getByteCount() / 1024;
			}
		};

		// Initialize disk cache on background thread
		File cacheDir = getDiskCacheDir(this, DISK_CACHE_SUBDIR);
		new InitDiskCacheTask().execute(cacheDir);
	}

	private void startProgressAnimation() {
		try {
			AnimationDrawable frameAnimation = (AnimationDrawable) mProgress
					.getDrawable();
			frameAnimation.setCallback(mProgress);
			frameAnimation.setVisible(true, true);
		} catch (Exception e) {
			e.toString();
		}
	}

	class InitDiskCacheTask extends AsyncTask<File, Void, Void> {
		@Override
		protected Void doInBackground(File... params) {
			synchronized (mDiskCacheLock) {
				File cacheDir = params[0];
				try {
					mDiskLruCache = DiskLruCache.open(cacheDir, 1, 1,
							DISK_CACHE_SIZE);
				} catch (IOException e) {
					e.printStackTrace();
				}
				 //mDiskCacheStarting = false; // Finished initialization
				mDiskCacheLock.notifyAll(); // Wake any waiting threads
			}
			return null;
		}
	}

	private void registerViewListener() {
		mSearchImageView.setOnClickListener(this);
		mSearchEdtTxt.addTextChangedListener(this);
		mImagesGridView.setOnScrollListener(this);

	}

	// Creates a unique subdirectory of the designated app cache directory.
	// Tries to use external
	// but if not mounted, falls back on internal storage.
	public static File getDiskCacheDir(Context context, String uniqueName) {
		// Check if media is mounted or storage is built-in, if so, try and use
		// external cache dir
		// otherwise use internal cache dir
		File file = new File(Environment.getExternalStorageDirectory()
				.getAbsolutePath() + "/temp");
		final String cachePath = Environment.MEDIA_MOUNTED.equals(Environment
				.getExternalStorageState())
				|| !Environment.isExternalStorageRemovable() ? file
				.getAbsolutePath() : context.getCacheDir().getPath();

		return new File(cachePath + File.separator + uniqueName);
	}

	/**
	 * Called to add bitmap in memory cache
	 * 
	 * @param key
	 *            : Key for Bitmaps to store
	 * @param bitmap
	 *            : {@link Bitmap}
	 */
	public void addBitmapToMemoryCache(String key, Bitmap bitmap) {
		if (getBitmapFromMemCache(key) == null) {
			mDatabase.checkForImageEntry(key);
			mMemoryCache.put(key, bitmap);
			Utility.logD(TAG, "---Bitmap --" + getBitmapFromMemCache(key));
		}

		// Also add to disk cache
		synchronized (mDiskCacheLock) {
			try {
				if (mDiskLruCache != null && mDiskLruCache.get(key) == null) {
					Utility.logD(TAG, "---Bitmap Added in Memory Cache--" + key + "Bitmap ----" + bitmap);
					mDiskLruCache.put(key, bitmap);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public Bitmap getBitmapFromMemCache(String key) {
		Utility.logD(TAG,"---Is Bitmap Exist in Cache--" + mMemoryCache.get(key) + key);
		if (mDiskLruCache != null)
			return mDiskLruCache.getBitmapFromKey(key);
		else
			return null;
	}

	/**
	 * Called to initialize the views of the activity
	 */
	private void initViews() {
		mSearchImageView = (ImageView) findViewById(R.id.search_image);
		mSearchEdtTxt = (EditText) findViewById(R.id.search_field);
		mHistorySearchFrame = (FrameLayout) findViewById(R.id.search_result_frame);
		mImagesGridView = (GridView) findViewById(R.id.image_grid);
		mProgress = (ImageView) findViewById(R.id.imgProgress);
	}

	@Override
	public void onClick(View iView) {
		Utility.hideKeyBoard(mSearchEdtTxt);
		switch (iView.getId()) {
		case R.id.search_image:
			if (mSearchEdtTxt.getText().toString().trim().isEmpty()) {
				Utility.showAlertDialog(this,
						UserInfoMessages.SEARCH_FIELD_EMPTY,
						UserInfoMessages.DIALOG_LABEL_INFO,
						UserInfoMessages.OK, UserInfoMessages.CANCEL, null);
				mCurrentPage = CURRENT_PAGE_DEFAULT;
				mNextPage = NEXT_PAGE_DEFAULT;
			} else {
				mSearchQuery = mSearchEdtTxt.getText().toString().trim();
				/* Load images from cache If Search Query Exist in DB */
				if (mDatabase.getImageFromCache(mSearchQuery).getmImageTitle() != null) {
					loadImageFromDB();
				}
				/*
				 * If Bitmap doesn't exist in disk cache then start a new server
				 * request for images
				 */
				else {
					mCurrentPage = CURRENT_PAGE_DEFAULT;
					mNextPage = NEXT_PAGE_DEFAULT;
					startBackgrondThreadForImageSearch(true);
				}
			}
			break;

		default:
			break;
		}
	}

	/**
	 * Called to load images from db
	 */
	private void loadImageFromDB() {
		mImagesGridView.setVisibility(View.VISIBLE);
		ImageDbRow img = mDatabase.getImageFromCache(mSearchQuery);
		ArrayList<ImageBean> imgBean = new ArrayList<>();

		// keep track of bitmap for the corresponding key
		boolean isBitmapExistInCache = false;
		for (int i = 0; i < img.getmImageId().size(); i++) {
			String key = img.getmImageId().get(i);
			/*
			 * If Bitmap exist in disk cache then start loading the image
			 * content from DB
			 */
			Utility.logD(TAG,"--- Bitmap Exist in Cache--" + getBitmapFromMemCache(key));
			if (getBitmapFromMemCache(key) != null) {
				ImageBean bean = new ImageBean();
				bean.setmBitmap(getBitmapFromMemCache(img.getmImageId().get(i)));
				bean.setmImageId(img.getmImageId().get(i));
				bean.setmTitle(img.getmImageTitle().get(i));
				imgBean.add(bean);
				isBitmapExistInCache = true;
			}
		}
		/* If Bitmap exist in disk cache then set the content in the GridView */
		if (isBitmapExistInCache) {
			mCurrentPage = Integer.parseInt(img.getmStart());
			mNextPage = mCurrentPage + 10;
			if(!(img.getmTotalResult() >= mNextPage))
				mNextPage = mCurrentPage;

			mImageArray = imgBean;
			mImageAdpater.setmImageArray(mImageArray);
			mImagesGridView.setAdapter(mImageAdpater);
		}
		/*
		 * If Bitmap doesn't exist in disk cache then start a new server request
		 * for images
		 */
		else {
			mCurrentPage = CURRENT_PAGE_DEFAULT;
			mNextPage = NEXT_PAGE_DEFAULT;
			startBackgrondThreadForImageSearch(true);
		}
	}

	/**
	 * Called to start the server request for image search
	 * 
	 * @param iShowProgress
	 *            : True if want to show the progress dialog during request,
	 *            else false
	 */
	private void startBackgrondThreadForImageSearch(boolean iShowProgress) {
		BackgroundThreadForServerOperation serverThread = new BackgroundThreadForServerOperation(
				this);
		serverThread.setmShowDialog(iShowProgress);
		serverThread.setmListener(this);
		ArrayList<NameValuePair> bodyParam = new ArrayList<>();
		bodyParam.add(new BasicNameValuePair(Constants.QUERY_PARAM,
				mSearchQuery));
		bodyParam.add(new BasicNameValuePair(Constants.START_PAGE_PARAM, String
				.valueOf(mNextPage)));
		serverThread.setmBodyParameter(bodyParam);
		serverThread
				.setmServerRequestID(SERVER_REQUEST_ID.IMAGE_SEARCH_REQUEST_ID);
		serverThread.execute();
	}

	/*
	 * When Search Image request completed
	 * 
	 * @see com.example.googleimagesearch.listener.SearchImageFoundListener#
	 * onSearchImageFound (com.example.googleimagesearch.bean.SearchResponse,
	 * java.lang.String)
	 */
	@Override
	public void onSearchImageFound(SearchResponse iResponse,
			String iErrorMessage) {
		mProgress.setVisibility(View.INVISIBLE);
		if (iErrorMessage != null) {
			Utility.showToast(iErrorMessage);
		} else {
			if (iResponse != null) {
				if (iResponse.getSearchInformation().getTotalResults() != null) {
					int totlaResults = Integer.parseInt(iResponse
							.getSearchInformation().getTotalResults());
					if (totlaResults == 0) {
						Utility.showToast(UserInfoMessages.RESULT_NOT_FOUND);
					} else {
						mCurrentPage = iResponse.getQueries().getRequest()
								.get(0).getStartIndex();
						mTotalResult = iResponse.getSearchInformation().getTotalResults();
						Utility.logD(TAG,"--- Next Page Exist--" + !(iResponse.getQueries().getNextPage().isEmpty()));
						boolean isNextPageExist = false;
						if (!iResponse.getQueries().getNextPage().isEmpty()){
							mNextPage = iResponse.getQueries().getNextPage().get(0).getStartIndex();
							isNextPageExist = true;
						}
						handleImageSearchRequestOnPageIndexBasis(iResponse, isNextPageExist);
					}
				} else
					Utility.showToast("Error");
			} else
				Utility.showToast("Error");
		}
	}

	/**
	 * The method is called to load images from the Image Request's Response
	 * 
	 * @param iResponse
	 *            : {@link SearchResponse}
	 * @param isNextPageExist 
	 */
	private void handleImageSearchRequestOnPageIndexBasis(
			SearchResponse iResponse, boolean isNextPageExist) {
		final int totlaResults = Integer.parseInt(iResponse
				.getSearchInformation().getTotalResults());
		if (isNextPageExist) {
			/* if total results are greater than the NextPage start index */
			if (totlaResults >= mNextPage) {
				if (mNextPage <= Constants.MAX_IMG_LOAD) {
					startBackgrondThreadForImageSearch(false);
				}
			}
			/*
			 * if total results are not greater than the NextPage start index
			 * then keep current and next same
			 */
			else {
				mNextPage = mCurrentPage;
			}
		}
		loadImages(iResponse);
	}

	/**
	 * Called to load image
	 * 
	 * @param iResponse
	 *            : instance of {@link SearchResponse}
	 */
	private void loadImages(SearchResponse iResponse) {
		for (int i = 0; i < iResponse.getItems().size(); i++) {
			final String url = iResponse.getItems().get(i).getLink();
			final String title = iResponse.getItems().get(i).getTitle();
			final String id = Uri.encode(title
					+ String.valueOf((mCurrentPage + i)));
			BackgroundThreadForServerOperation serverThread = new BackgroundThreadForServerOperation(
					this);
			serverThread.setmBodyParameter(null);
			ImageBean image = new ImageBean();
			image.setmTitle(title);
			image.setmImageId(id);
			serverThread.setmImageBean(image);
			serverThread.setmListener(this);
			serverThread
					.setmServerRequestID(SERVER_REQUEST_ID.IMAGE_LOAD_REQUEST_ID);
			serverThread.setmUrl(url);
			serverThread.execute();
		}
	}

	
	@Override
	public void onImageLoaded(ImageBean imageBean, String iErrorMessage) {
		if (iErrorMessage != null) {
			Utility.showToast(iErrorMessage);
		} else {
			if (imageBean != null) {
				final String key = imageBean.getmImageId();
				final Bitmap bitmap = imageBean.getmBitmap();
				if (key != null && bitmap != null) {
					addBitmapToMemoryCache(key, imageBean.getmBitmap());
					mImageArray.add(imageBean);
					mImageAdpater.setmImageArray(mImageArray);
					mImagesGridView.setVisibility(View.VISIBLE);
					if (!isAdapterSet) {
						mImagesGridView.setAdapter(mImageAdpater);
						isAdapterSet = true;
					} else {
						mImageAdpater.notifyDataSetChanged();
					}

					/*
					 * get the list of image title and id
					 */
					ArrayList<String> idArray = new ArrayList<>();
					ArrayList<String> titleArray = new ArrayList<>();
					for (int i = 0; i < mImageArray.size(); i++) {
						idArray.add(mImageArray.get(i).getmImageId());
						titleArray.add(mImageArray.get(i).getmTitle());
					}
					// add or update in case of existing search query it on DB
					mDatabase.addUpdateImageDate(mSearchQuery, idArray,
							titleArray, String.valueOf(mCurrentPage), mTotalResult);
				}
			}
		}
	}

	@Override
	public void beforeTextChanged(CharSequence s, int start, int count,
			int after) {

	}

	@Override
	public void onTextChanged(CharSequence s, int start, int before, int count) {
		// if text in the field is different from the previous search text, then
		// reinitialize image array
		if (mSearchQuery != null && !mSearchQuery.equals(s)) {
			mImageArray = new ArrayList<>();
		}

		final ArrayList<String> recentSearch = mDatabase.getRecentSearches(s.toString());
		/*
		 * if search query exist in DB then show History fragment and update the
		 * recent search list
		 */
		if (recentSearch != null) {
			if (mHistorySearchFrame.getVisibility() == View.GONE)
				mHistorySearchFrame.setVisibility(View.VISIBLE);
			mRecentSearchFragment.updateHistoryList(recentSearch);
		} else {
			mHistorySearchFrame.setVisibility(View.GONE);
		}
	}

	@Override
	public void afterTextChanged(Editable s) {
		// keep grid view and history frame hide when there is no text in the
		// field
		if (mSearchEdtTxt.getText().toString().trim().isEmpty()) {
			mHistorySearchFrame.setVisibility(View.GONE);
		}
		mImagesGridView.setVisibility(View.GONE);
	}

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
	}

	@Override
	public void onScroll(AbsListView view, int firstVisibleItem,
			int visibleItemCount, int totalItemCount) {
		mImagesGridView.setOnScrollListener(new OnScrollListener() {

			@Override
			public void onScroll(AbsListView view, int firstVisibleItem,
					int visibleItemCount, int totalItemCount) {
				if (firstVisibleItem + visibleItemCount >= totalItemCount) {
					Utility.logD(TAG, "-----END REACHED----" + count);
					if (mSearchQuery != null & count == 0) {
						count = 1000;;
						if (mNextPage != mCurrentPage) {
							mProgress.setVisibility(View.VISIBLE);
							startBackgrondThreadForImageSearch(false);
						}else{
							Utility.showToast(UserInfoMessages.NO_MORE_RESULT);
						}
					}
				}
			}

			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
				count = 0;
				Utility.logD(TAG, "-----SCROLL STATE CHANGED----" + count);
			}
		});
	}

	/**
	 * Called to set the item in the Search field , when the list item of
	 * History Fragment Selected
	 * 
	 * @param iSearchQuery
	 *            : Search Query
	 */
	public void onRecentSearchItemSelected(String iSearchQuery) {
		mSearchEdtTxt.setText(iSearchQuery);
		mHistorySearchFrame.setVisibility(View.GONE);
		Utility.hideKeyBoard(mSearchEdtTxt);
	}
}