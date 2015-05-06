package com.example.googleimagesearch.bean;

import com.google.gson.annotations.Expose;

public class Context {

	@Expose
	private String title;

	/**
	 * 
	 * @return The title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * 
	 * @param title
	 *            The title
	 */
	public void setTitle(String title) {
		this.title = title;
	}

}
