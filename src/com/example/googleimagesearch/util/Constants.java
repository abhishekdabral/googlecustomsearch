package com.example.googleimagesearch.util;


public class Constants {

	/*Required Arguments for the above URL*/
	
	public static final String QUERY_PARAM = "q"; //This argument supplies the query, or search expression, that is passed into the searcher.
	public static final String API_KEY_PARAM = "key";
	public static final String API_KEY_VALUE = "AIzaSyBaHmwofX96rTrPIZTGUP9Ms9mwC01116A";
	public static final String CUSTOM_SEARCH_ENGINE_PARAM = "cx";
	public static final String CUSTOM_SEARCH_ENGINE_VALUE = "005717290701474541337:1mc_u_r_i94";//Search Engine ID
	public static final String PAGE_ITEM_PARAM = "num"; //This argument supplies an integer from 1–10 indicating the number of results to return per page.
	public static final String PAGE_ITEM_VALUE = "10";
	public static final String SEARCH_TYPE_PARAM = "searchType";
	public static final String SEARCH_TYPE_VALUE = "image";
	public static final String START_PAGE_PARAM = "start";
	public static final String IMAGE_SIZE_PARAM = "imgsz";
	public static final String IMAGE_SIZE_VALUE = "medium";
	public static final String IMAGE_SEARCH_URL = "https://www.googleapis.com/customsearch/v1?" +  API_KEY_PARAM + "=" + API_KEY_VALUE + "&" + CUSTOM_SEARCH_ENGINE_PARAM + "=" + CUSTOM_SEARCH_ENGINE_VALUE + "&" + SEARCH_TYPE_PARAM + "=" + SEARCH_TYPE_VALUE + "&" + IMAGE_SIZE_PARAM + "=" + IMAGE_SIZE_VALUE +"&";
	
	public static final class ImageDatabase{
		public static final String TABLE_NAME = "history";
		public static final String COL_SEARCH_QUERY = "searchquery";
		public static final String COL_IMAGE_LOCATION ="image_location";
		public static final String COL_IMAGE_ID ="image_id";
		public static final String COL_IMAGE_TITLE ="image_title";
		public static final String COL_IMAGE_START ="start";
		public static final String COL_IMAGE_TOTAL_RESULT = "total_result";
	}
	public static final String SEARCH_QUERY_KEY = "search_key";

	public static final String LOCATION_ARRAY_TO_STRING_KEY = "array_to_string";

	public static final String IMAGE_LOCATION_KEY = "img_location_key";
	public static final String IMAGE_ID_KEY = "img_id_key";
	public static final String IMAGE_TITLE_KEY = "img_title_key";
	public static final String IMAGE_START_KEY = "img_start_key";
	public static final int MAX_IMG_LOAD = 40;


	/**
	 * Http response codes and appropriate message corresponding to that response
	 * @author ABHISHEK
	 */
	public static final class ServerResponseStatus {
		// DEFAULT ENCODING
		public static final String DEFAULT_CHAR_SET = "UTF-8";
		// HTTP REQUEST STATUS 200
		public static final int REQUEST_FULFILLED = 200;
		// HTTP REQUEST STATUS -200
		public static final int NETWORK_UNAVAILABLE = -200;
		// HTTP REQUEST STATUS 5
		public static final int SERVER_ERROR_CODE = 5;
		// HTTP REQUEST STATUS -202
		public static final int SERVER_CONNECTION_REFUSED = -202;
		// HTTP REQUEST STATUS -201
		public static final int SERVER_CONNECTION_ERROR_UNEXPLAINED = -201;
		// HTTP REQUEST STATUS -203
		public static final int SERVER_CONNECTION_TIMED_OUT = -203;

		// HTTP ERROR MESSAGE ON ANY UNKNOWN ERROR
		public static final String DEFAULT_ERROR_MESSAGE = "Sorry! Something went wrong.";
		// HTTP ERROR MESSAGE ON SERVER_CONNECTION_REFUSED ERROR
		public static final String SERVER_CONNECTION_REFUSED_MESSAGE = "Connection to host Server Refused";
		// ERROR MESSAGE FOR NO INTERNET CONNECTION
		public static final String NETWORK_UNAVAILABLE_MESSAGE = "Unable to connect. Check your network";
		// HTTP ERROR MESSAGE NO SERVER_ERROR_CODE ERROR
		public static final String SERVER_ERROR_MESSAGE = "Server Error. Try Again Later";
		// HTTP ERROR MESSAGE NO SERVER_CONNECTION_ERROR_UNEXPLAINED ERROR
		public static final String SERVER_CONNECTION_ERROR_MESSAGE = "Something went wrong. Try Later";
		// HTTP ERROR MESSAGE NO SERVER_CONNECTION_TIME_OUT ERROR
		public static final String SERVER_CONNECTION_TIME_OUT_MESSAGE = "Server Connection Timeout. " +
																			"Please try again later!";
	}

	/**
	 * Server Request status and response messages
	 * @author ABHISHEK
	 */
	public static final class RequestStatus {

		public static final String STATUS_ZERO = "0";
		public static final String STATUS_ONE = "1";	
	}

	/**
	 * RELEVANT INFO FOR THE USER
	 * @author ABHISHEK
	 */
	public static final class UserInfoMessages {
		/**
		 * PLEASE WAIT MESSAGE
		 */
		public static final String PLEASE_WAIT = "Please Wait...";
		/**
		 *  TITLE OF DIALOG
		 */
		public static final String DIALOG_LABEL_CONFIRM = "Confirm";
		public static final String DIALOG_LABEL_INFO = "Info";

		/**
		 * When Search field of home screen in empty
		 */
		public static final String SEARCH_FIELD_EMPTY = "Search Field must not be empty!";
		public static final String OK = "OK";
		public static final String CANCEL = "Cancel";
		public static final String RESULT_NOT_FOUND = "No Result Found";
		public static final String NO_MORE_RESULT = "No More Results.";
		
	}
	public static enum SERVER_REQUEST_ID{
		/**
		 * REQUEST ID FOR LOGIN REQUEST
		 */
		IMAGE_SEARCH_REQUEST_ID, IMAGE_LOAD_REQUEST_ID
	}

}
