package com.example.googleimagesearch.serverconnection;

import java.util.List;

import org.apache.http.NameValuePair;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import com.example.googleimagesearch.bean.ImageBean;
import com.example.googleimagesearch.bean.SearchResponse;
import com.example.googleimagesearch.listener.SearchImageFoundListener;
import com.example.googleimagesearch.util.Constants;
import com.example.googleimagesearch.util.Constants.SERVER_REQUEST_ID;
import com.example.googleimagesearch.util.Constants.UserInfoMessages;
import com.example.googleimagesearch.util.Utility;
import com.google.gson.Gson;

/**
 * Class perform server operation in background
 * @author ABHISHEK
 */
public class BackgroundThreadForServerOperation extends
		AsyncTask<Void, Void, Void> {
	// Class Name TAG used for debugging
	private static final String TAG = BackgroundThreadForServerOperation.class
			.getSimpleName();
	private ProgressDialog mProgressDialog = null;
	private ServerErrorInfo mServerErrorInfo = null;
	private String mErrorMessage = null;
	private Context mContext = null;
	private String mUrl = null;
	private List<NameValuePair> mBodyParameter = null;
	private SearchResponse mResponse = null;
	private SERVER_REQUEST_ID mServerRequestID = null;
	private ImageBean mImageBean = null;
	private SearchImageFoundListener mSearchListener = null;
	private boolean mShowDialog = true;
	public void setmShowDialog(boolean mShowDialog) {
		this.mShowDialog = mShowDialog;
	}

	public void setmListener(SearchImageFoundListener iListener) {
		mSearchListener = iListener;
	}

	public void setmServerRequestID(SERVER_REQUEST_ID iServerRequestID) {
		mServerRequestID = iServerRequestID;
	}

	public BackgroundThreadForServerOperation(Context iContext) {
		mContext = iContext;
	}

	public void setmImageBean(ImageBean mImageBean) {
		this.mImageBean = mImageBean;
	}

	/**
	 * Called to start Progress dialog
	 * @param iMessage : Dialog message
	 */
	public void initProgressDialog(String iMessage) {

		mProgressDialog = new ProgressDialog(mContext);
		mProgressDialog.setMessage(iMessage);
		mProgressDialog.setCanceledOnTouchOutside(false);
		mProgressDialog.setCancelable(false);
		try{
		mProgressDialog.show();
		}catch(Exception x){
			Utility.logE(TAG, "" + x);
		}
	}

	/*
	 * first method that gets called before stating server operation
	 * @see android.os.AsyncTask#onPreExecute()
	 */
	@Override
	protected void onPreExecute() {
		switch (mServerRequestID) {
		case IMAGE_SEARCH_REQUEST_ID:
			if(mShowDialog)
			initProgressDialog(Constants.UserInfoMessages.PLEASE_WAIT);
			break;
		default:
			break;
		}
			
	}

	/*
	 * start server request
	 * @see android.os.AsyncTask#doInBackground(Params[])
	 */
	@Override
	protected Void doInBackground(Void... params) {

		ServerConnection server = new ServerConnection();
		String response = null;
		Utility.logD(TAG, "------URL-----" + mUrl);
		if(Utility.isNetworkAvailable()){
		Gson gson = new Gson();
		try {
			switch (mServerRequestID) {
			case IMAGE_SEARCH_REQUEST_ID:
				Utility.logD(TAG, "------Body Param-----" + mBodyParameter);
				Utility.logD(TAG, "------URL-----" + Constants.IMAGE_SEARCH_URL);
				response = server.getResponseUsingGetMethod(Constants.IMAGE_SEARCH_URL, mBodyParameter);
				Utility.logD(TAG, "------RESPONSE-----" + response);
				mResponse = gson.fromJson(response, SearchResponse.class);
				break;
			case IMAGE_LOAD_REQUEST_ID:
				mImageBean.setmBitmap(server.getBitmapFromServer(mUrl, mContext));
				break;
			default:
				break;
			}
			
		}
		/*
		 * handle server exception
		 */
		catch (Exception x) {
			Utility.logD(TAG, "------EXCEPTION-----" + x);
			ServerConnectionException serverEx = new ServerConnectionException(
					x);
			mServerErrorInfo = new ServerErrorInfo();
			mServerErrorInfo.setmHttpStatusCode(serverEx.returnErrorCode());
			mServerErrorInfo.setErrorDetected(true);
			mErrorMessage = mServerErrorInfo.getResponseMessage();
			/**
			 * If debugging is allowed print server error
			 */
			Utility.logD(TAG, "ServerError =================>"
					+ mErrorMessage);
		}
		}else{
			mErrorMessage = Constants.ServerResponseStatus.NETWORK_UNAVAILABLE_MESSAGE;
		}
		return null;
	}

	/**
	 * Called to set the body parameters passed in the urls
	 * @param mBodyParameter : Body Parameters of the request
	 */
	public void setmBodyParameter(List<NameValuePair> iBodyParameter) {
		mBodyParameter = iBodyParameter;
	}

	/**
	 * Called to set the Request URL
	 * @param mUrl : URL
	 */
	public void setmUrl(String mUrl) {
		this.mUrl = mUrl;
	}

	/*
	 * Called when background server operation gets completed
	 * @see android.os.AsyncTask#onPostExecute(java.lang.Object)
	 */
	@Override
	protected void onPostExecute(Void result) {
		super.onPostExecute(result);
		switch (mServerRequestID) {
		case IMAGE_LOAD_REQUEST_ID:
			System.out.println("---image loaded--" +mImageBean);
			mSearchListener.onImageLoaded(mImageBean, mErrorMessage);
			break;
		case IMAGE_SEARCH_REQUEST_ID:
			if(mProgressDialog != null)
			mProgressDialog.dismiss();
			mSearchListener.onSearchImageFound(mResponse, mErrorMessage);
			break;

		default:
			break;
		}
	}
}