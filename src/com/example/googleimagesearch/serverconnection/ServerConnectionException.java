package com.example.googleimagesearch.serverconnection;

import java.io.InterruptedIOException;
import java.lang.reflect.Constructor;
import java.net.SocketException;
import java.net.SocketTimeoutException;

import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.conn.HttpHostConnectException;

import com.example.googleimagesearch.util.Constants;
import com.example.googleimagesearch.util.Constants.ServerResponseStatus;

/**
 * Exception class for the condition when an exception is thrown while connecting to the server
 */
@SuppressWarnings("serial")
public class ServerConnectionException extends Exception {

	private Exception mException;

	/**
	 * {@link Constructor} : invoke to set server exception
	 * @param exception : server exception
	 */
	public ServerConnectionException(Exception exception) {
		mException = exception;
	}

	/**
	 * returns an error code in integer indicating the type of exception thrown while making server Connection
	 * @return integer for details see: {@link Constants.ServerResponseStatus}
	 */
	public int returnErrorCode() {
		if (mException instanceof SocketTimeoutException) {
			return ServerResponseStatus.SERVER_CONNECTION_TIMED_OUT;

		} else if (mException instanceof ConnectTimeoutException) {
			return ServerResponseStatus.SERVER_CONNECTION_TIMED_OUT;

		} else if (mException instanceof SocketException) {
			return ServerResponseStatus.SERVER_CONNECTION_TIMED_OUT;

		} else if (mException instanceof InterruptedIOException) {
			return ServerResponseStatus.SERVER_CONNECTION_TIMED_OUT;

		} else if (mException instanceof HttpHostConnectException) {
			return ServerResponseStatus.SERVER_CONNECTION_REFUSED;
		} else {
			return ServerResponseStatus.SERVER_CONNECTION_ERROR_UNEXPLAINED;
		}

	}

}