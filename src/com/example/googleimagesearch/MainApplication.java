package com.example.googleimagesearch;

import android.app.Application;
import android.content.Context;

/**
 * Main application class which extends application mainly to get the context of
 * the activity in the utility class
 * @author ABHISHEK
 */
public class MainApplication extends Application{

	/**
	 * Context for Application
	 */
	private static Context sApplicationContext;

	/**
	 * To get the context of the current application
	 * @return Context of the current application
	 */
	public static Context getsApplicationContext() {
		return sApplicationContext;
	}

	/*
	 * Set the context of application
	 * @see android.app.Application#onCreate()
	 */
	@Override
	public void onCreate() {
	    sApplicationContext = this;
		super.onCreate();
	}
}
