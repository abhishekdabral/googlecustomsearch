package com.example.googleimagesearch.listener;

import com.example.googleimagesearch.bean.ImageBean;
import com.example.googleimagesearch.bean.SearchResponse;

/**
 * Interface invoked when SEARCH IMAGE REQUEST or IMAGE_LOAD REQUEST completed
 * @author ABHISHEK
 */
public interface SearchImageFoundListener {

	/**
	 * Called when Search Image Request finished
	 * @param iResponse : instance of {@link SearchResponse}
	 * @param iErrorMessage : Server Error Messgae
	 */
	public void onSearchImageFound(SearchResponse iResponse,
			String iErrorMessage);

	/**
	 * Called when Image loaded Request Finished
	 * @param imageBean : instance of {@link ImageBean}
	 * @param iErrorMessage ; Server Error Message
	 */
	public void onImageLoaded(ImageBean imageBean, String iErrorMessage);

}
