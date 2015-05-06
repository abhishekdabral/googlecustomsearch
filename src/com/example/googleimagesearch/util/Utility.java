package com.example.googleimagesearch.util;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.NetworkInfo.State;
import android.os.Build;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.WindowManager.BadTokenException;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import com.example.googleimagesearch.MainApplication;
import com.example.googleimagesearch.listener.OnDialogClickListener;

public class Utility {
	//Class name tag used for debugging
	private static final String TAG = Utility.class.getSimpleName();
	public static final int IO_BUFFER_SIZE = 8 * 1024;


    public static boolean isExternalStorageRemovable() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
            return Environment.isExternalStorageRemovable();
        }
        return true;
    }

    public static File getExternalCacheDir(Context context) {
        if (hasExternalCacheDir()) {
            return context.getExternalCacheDir();
        }

        // Before Froyo we need to construct the external cache dir ourselves
        final String cacheDir = "/Android/data/" + context.getPackageName() + "/cache/";
        return new File(Environment.getExternalStorageDirectory().getPath() + cacheDir);
    }

    public static boolean hasExternalCacheDir() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.FROYO;
    }
	/*
	 * For allowing debugging in Application
	 */
	private static boolean sIsAllowDebug = true;

	/**
	 * Making the Toast in the application
	 * @param iMsg string which has to be toasted .
	 */
	public static void showToast(String iMsg) {
		Toast.makeText(MainApplication.getsApplicationContext(), iMsg, Toast.LENGTH_SHORT).show();
	}

	/**
	 * Checking whether the application is in debug mode.
	 * @return True if the application is in debug mode else false
	 */
	public static boolean isAllowDebug() {
		return sIsAllowDebug;
	}

	/**
	 * Called to show Log of verbose
	 * @param iTag : TAG of class name
	 * @param iMessage : Message for display
	 */
	public static void logV(String iTag, String iMessage) {
		// If application is in debug mode
		if (isAllowDebug())
			Log.v(iTag, iMessage);
	}

	/**
	 * Called to show Log of errors
	 * @param iTag : TAG of class name
	 * @param iMessage : Message for display
	 */
	public static void logE(String iTag, String iMessage) {
		// If application is in debug mode
		if (isAllowDebug())
			Log.e(iTag, iMessage);
	}

	/**
	 * Called to show Log of debug
	 * @param iTag : TAG of class name
	 * @param iMessage : Message for display
	 */
	public static void logD(String iTag, String iMessage) {
		// if application is in debug mode
		if (isAllowDebug())
			Log.d(iTag, iMessage);
	}

	/**
	 * Called to show Log of warnings
	 * @param iTag : TAG of class name
	 * @param iMessage : Message for display
	 */
	public static void logW(String iTag, String iMessage) {
		// if application is in debug mode
		if (isAllowDebug())
			Log.w(iTag, iMessage);
	}

	/**
	 * Called to show Log of info
	 * @param iTag : TAG of class name
	 * @param iMessage : Message for display
	 */

	public static void logI(String iTag, String iMessage) {
		// If application is in debug mode
		if (isAllowDebug())
			Log.i(iTag, iMessage);
	}

	/**
	 * For hiding the soft keyboard
	 * @param iCon : Context of Calling Activity
	 */
	public static void hideKeyBoard(EditText iEdt) {
		InputMethodManager inManager = (InputMethodManager) MainApplication.getsApplicationContext().
				getSystemService(Context.INPUT_METHOD_SERVICE);
		inManager.hideSoftInputFromWindow(iEdt.getWindowToken(), 0);
	}

	/**
	 * For opening the soft keyboard
	 * @param iCon : Context of Calling Activity
	 */
	public static void openKeyBoard(EditText iEdt) {
		InputMethodManager inputMethodManager=(InputMethodManager) MainApplication.getsApplicationContext().
									getSystemService(Context.INPUT_METHOD_SERVICE);
    	inputMethodManager.toggleSoftInputFromWindow(iEdt.getWindowToken(), InputMethodManager.SHOW_FORCED, 0);
	}

	/**
	 * Used to save preferences
	 * @param activity provide activity's context
	 * @param iName provide name of preference [String]
	 * @param iValue provide value of preference [String]
	 */
	public static void savePreference(String iName, String iValue) {
		SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(
				MainApplication.getsApplicationContext()).edit();
		// Check that passed key is not null
		if (iName != null && iValue != null) {
			editor.putString(iName, iValue);
			editor.commit();
		}
	}

	/**
	 * Used to clear preferences
	 * @param activity provide activity's context
	 * @param iName provide name of preference [String]
	 * @param iValue provide value of preference [String]
	 */
	public static void clearPreference(String iName) {
		SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(
				MainApplication.getsApplicationContext()).edit();
		// Check that passed key is not null
		if (iName != null) {
			editor.clear();
			editor.commit();
		}
	}

	/**
	 * This methods loads the preferences.
	 * @param iName : Key
	 * @return : Value
	 */
	public static String loadPreference(String iName) {
		return PreferenceManager.getDefaultSharedPreferences(MainApplication.getsApplicationContext()).
				getString(iName,null);
	}

	/**
	 * @return true if network connection is available
	 */
	public static boolean isNetworkAvailable() {

		ConnectivityManager cm = (ConnectivityManager) MainApplication.getsApplicationContext().getSystemService(
				Context.CONNECTIVITY_SERVICE);
		NetworkInfo info = cm.getActiveNetworkInfo();

		if (info == null)
			return false;

		State network = info.getState();
		return (network == NetworkInfo.State.CONNECTED || network == NetworkInfo.State.CONNECTING);
	}

	/**
	 * Called to create a type of animation
	 * @param iContext : Context of calling activity
	 * @param idAnimation : Animation Id i.e type of animation
	 * @return : {@link Animation} instance
	 */
	public static Animation createAnimation(Context iContext, int idAnimation) {
		Animation anim = AnimationUtils.loadAnimation(iContext, idAnimation);
		return anim;
	}


	/**
	 * Called to show the alert dialog
	 * @param iMessage : Message to be displayed in the dialog
	 * @param iActivity : Context of Activity
	 * @param iListener : instance of {@link OnCustomDialogClick}
	 * @param isAlertDialog : true if dialog is an alert dialog with two option
	 */
	public static void showAlertDialog(Context iContext, String iMessage, String iDialogLabel,
			String iPositiveButtonLabel, String iNegativeButtonLabel, final OnDialogClickListener iListener) {
		if (iContext != null) {
			AlertDialog.Builder builder = new AlertDialog.Builder(iContext);
			builder.setMessage(iMessage);
			builder.setPositiveButton(iPositiveButtonLabel, new OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					if(iListener != null)
					iListener.onCustomDialogClick(true);
				}
			});
			builder.setNegativeButton(iNegativeButtonLabel, new OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					if(iListener != null)
						iListener.onCustomDialogClick(false);					
				}
			});

			builder.setTitle(iDialogLabel);
			try{
				builder.show();
			}
			catch(BadTokenException x){
				Utility.logD(TAG, "==========>" + x);
			}
			catch (IllegalStateException x) {
				Utility.logD(TAG, "==========>" + x);
			}
		}
	}

	public static void CopyStream(InputStream is, OutputStream os)
	    {
	        final int buffer_size=1024;
	        try
	        {
	            byte[] bytes=new byte[buffer_size];
	            for(;;)
	            {
	              int count=is.read(bytes, 0, buffer_size);
	              if(count==-1)
	                  break;
	              os.write(bytes, 0, count);
	            }
	        }
	        catch(Exception ex){}
	    }

}
