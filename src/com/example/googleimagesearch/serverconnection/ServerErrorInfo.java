package com.example.googleimagesearch.serverconnection;
import com.example.googleimagesearch.util.Constants.ServerResponseStatus;
import com.example.googleimagesearch.util.Utility;


/**
 * The class is used to hold information regarding any error that were encountered while connecting to the Server.
 * @author ABHISHEK
 */
public class ServerErrorInfo {

	/**
	 * the status Code of the Http connection which holds an integer constant from class
	 * {@link Constants.HttpStatusCodes]}
	 */
	private int mHttpStatusCode;
	private boolean isErrorDetected = true;
	private String mErrorMsg = null;

	public int getmHttpStatusCode() {
		return mHttpStatusCode;
	}

	/**
	 * sets the status Code for the Http connection
	 * @param iHttpStatusCode integer constant from class {@link Constants.HttpStatusCodes]}
	 */
	public void setmHttpStatusCode(int iHttpStatusCode) {
		this.mHttpStatusCode = iHttpStatusCode;
		if (iHttpStatusCode / 100 == ServerResponseStatus.SERVER_ERROR_CODE) {
			mHttpStatusCode = ServerResponseStatus.SERVER_ERROR_CODE;
		}
	}

	/**
	 * return true if any error is detected while connecting to the server
	 * @return true if error detected, Please Note: it is true by default.
	 */
	public boolean isErrorDetected() {
		return isErrorDetected;
	}

	/**
	 * sets whether an error was detected while making a server connection
	 * @param isErrorDetected true if error detected else false
	 */
	public void setErrorDetected(boolean isErrorDetected) {
		this.isErrorDetected = isErrorDetected;
		setResponseMessage(mHttpStatusCode);
	}

	/**
	 * Called if server error found
	 * @param iHttpStatusCode
	 */
	public void setResponseMessage(int iHttpStatusCode) {
		if (isErrorDetected()) {
			String error = null;
			Utility.logD("Http Code", "=====================>" + iHttpStatusCode);
			switch (iHttpStatusCode) {
			case ServerResponseStatus.NETWORK_UNAVAILABLE:
				error = ServerResponseStatus.NETWORK_UNAVAILABLE_MESSAGE;
				break;
			case ServerResponseStatus.SERVER_ERROR_CODE:
				error = ServerResponseStatus.SERVER_ERROR_MESSAGE;
				break;
			case ServerResponseStatus.SERVER_CONNECTION_ERROR_UNEXPLAINED:
				error = ServerResponseStatus.SERVER_CONNECTION_ERROR_MESSAGE;
				break;
			case ServerResponseStatus.SERVER_CONNECTION_TIMED_OUT:
				error = ServerResponseStatus.SERVER_CONNECTION_TIME_OUT_MESSAGE;
				break;
			case ServerResponseStatus.SERVER_CONNECTION_REFUSED:
				error = ServerResponseStatus.SERVER_CONNECTION_REFUSED_MESSAGE;
				break;
			default:
				error = ServerResponseStatus.DEFAULT_ERROR_MESSAGE;
				break;
			}
			setMessage(error);
			return;
		}

	}

	public String getResponseMessage() {
		return mErrorMsg;
	}

	/**
	 * sets the suppled error String as the Error Message
	 * @param error the actual Message
	 */
	public void setMessage(String error) {
		mErrorMsg = error;
	}
}
