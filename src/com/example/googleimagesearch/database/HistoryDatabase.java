package com.example.googleimagesearch.database;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.googleimagesearch.bean.ImageDbRow;
import com.example.googleimagesearch.util.Constants;
import com.example.googleimagesearch.util.Constants.ImageDatabase;
import com.example.googleimagesearch.util.Utility;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Database which is used to keep the search query information
 * like, SearchQuery, ImageTitles, ImageIDs used as key to fetch the corresponding image bitmaps from the disk cache
 * and Start (index of the Search page)
 * @author ABHISHEK
 */
public class HistoryDatabase extends SQLiteOpenHelper {

	private static final String TAG = HistoryDatabase.class.getSimpleName();
	public HistoryDatabase(Context context, String name, CursorFactory factory,
			int version) {
		super(context, name, factory, version);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		String sql = "create table " + ImageDatabase.TABLE_NAME + "("
				+ ImageDatabase.COL_SEARCH_QUERY + " TEXT,"
				+ ImageDatabase.COL_IMAGE_ID + " ,"
				+ ImageDatabase.COL_IMAGE_TOTAL_RESULT + " ,"
				+ ImageDatabase.COL_IMAGE_START + " ,"
				+ ImageDatabase.COL_IMAGE_TITLE + ");";
		db.execSQL(sql);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

	}

	/**
	 * Called to add or update the db row for the corresponding search String
	 * @param iSearchQuery : Search Query
	 * @param imageIdArray : {@link ArrayList} of Image IDs
	 * @param titleArray : {@link ArrayList} of Image Title
	 * @param iStart : index position of search page
	 * @param iTotalResult : Total Results
	 */
	public void addUpdateImageDate(String iSearchQuery,
			ArrayList<String> imageIdArray, ArrayList<String> titleArray,
			String iStart, String iTotalResult) {
		SQLiteDatabase db = this.getReadableDatabase();
		ContentValues values = new ContentValues();
		values.put(ImageDatabase.COL_SEARCH_QUERY, iSearchQuery);
		// Convert array to String
		JSONObject jsonId = new JSONObject();
		JSONObject jsonTitle = new JSONObject();

		try {
			jsonId.put(Constants.LOCATION_ARRAY_TO_STRING_KEY, new JSONArray(
					imageIdArray));
			jsonTitle.put(Constants.LOCATION_ARRAY_TO_STRING_KEY,
					new JSONArray(titleArray));
		} catch (JSONException e) {
			Utility.logE(TAG, " Error" + e);
		}

		final String arrayImageId = jsonId.toString();
		final String arrayImageTitle = jsonTitle.toString();

		values.put(ImageDatabase.COL_IMAGE_ID, arrayImageId);
		values.put(ImageDatabase.COL_IMAGE_TITLE, arrayImageTitle);
		values.put(ImageDatabase.COL_IMAGE_START, iStart);
		values.put(ImageDatabase.COL_IMAGE_TOTAL_RESULT, iTotalResult);

		final String where = ImageDatabase.COL_SEARCH_QUERY + " =?";
		final String[] arg = { iSearchQuery };

		Cursor cursor = db.query(ImageDatabase.TABLE_NAME, null,
				where, arg, null, null, null);
		if (cursor != null) {
			if (cursor.getCount() > 0) {
				while (cursor.moveToNext()) {
					db.update(ImageDatabase.TABLE_NAME, values,
							where, arg);
					Utility.logD(TAG, "--UPDATED EXISTING SEARCH IN DB----");
				}
			} else {
				// Inserting Row
				db.insert(ImageDatabase.TABLE_NAME, null, values);
				Utility.logD(TAG, "--NEW SEARCH INSERTED IN DB----");
			}
		}
		cursor.close();
		db.close();
	}

	/**
	 * Called to get image details existing in the DB
	 * @param iSearchQuery : Search Query for the IMage
	 * @return {@link ImageDbRow}
	 */
	public ImageDbRow getImageFromCache(String iSearchQuery) {
		final SQLiteDatabase db = this.getReadableDatabase();
		ImageDbRow dbRow = new ImageDbRow();
		final String where = ImageDatabase.COL_SEARCH_QUERY + " =?";
		final String[] arg = { iSearchQuery };

		Cursor cursor = db.query(ImageDatabase.TABLE_NAME, null,
				where, arg, null, null, null);
		if (cursor != null) {
			while (cursor.moveToNext()) {
				final String searchQueryInDb = cursor
						.getString(cursor
								.getColumnIndex(ImageDatabase.COL_SEARCH_QUERY));
				final String imageIds = cursor.getString(cursor
						.getColumnIndex(ImageDatabase.COL_IMAGE_ID));
				final String imageTitle = cursor
						.getString(cursor
								.getColumnIndex(ImageDatabase.COL_IMAGE_TITLE));

				final String start = cursor
						.getString(cursor
								.getColumnIndex(ImageDatabase.COL_IMAGE_START));
				final int totalResult = Integer.parseInt(cursor
						.getString(cursor
								.getColumnIndex(ImageDatabase.COL_IMAGE_TOTAL_RESULT)));

				final ArrayList<String> imageTitleList = convertDBFieldToArrayList(imageTitle);
				// convert id string to id array
				final ArrayList<String> imageIdList = convertDBFieldToArrayList(imageIds);
				
				dbRow.setmStart(start);
				dbRow.setmImageTitle(imageTitleList);
				dbRow.setmImageId(imageIdList);
				dbRow.setmSearchQuery(iSearchQuery);
				dbRow.setmTotalResult(totalResult);
				Utility.logD(TAG, "-----Search Query In Db --"
						+ searchQueryInDb);
			}

		}
		cursor.close();
		db.close();
		return dbRow;
	}

	/**
	 * Called to convert db string field to String Array List
	 * @param ifield : Field Of DB
	 * @return : {@link ArrayList} of String
	 */
	public ArrayList<String> convertDBFieldToArrayList(String ifield){
		ArrayList<String> arrayList = null;
		try {
			JSONObject jsonTitle = new JSONObject(ifield);
			JSONArray jsonArray = jsonTitle
					.optJSONArray(Constants.LOCATION_ARRAY_TO_STRING_KEY);
			if (jsonArray != null) {
				arrayList = new ArrayList<>();
				int len = jsonArray.length();
				for (int i = 0; i < len; i++) {
					try {
						arrayList.add(jsonArray.get(i).toString());
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}

		} catch (JSONException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		return arrayList;
	}
	/**
	 * Called to check whether the key exist in any entry of Image ID column
	 * if it exist it means cache has removed the bitmap from disk, so we need to make the entry
	 * for that particular search clear so that bitmaps can fetch again from server
	 * @param key : Image ID
	 * @return : true if the item has been deleted from the db
	 */
	public boolean checkForImageEntry(String key) {
		boolean isItemDeleted = false;
		SQLiteDatabase db = this.getReadableDatabase();
		String col[] = { ImageDatabase.COL_IMAGE_ID };
		
		Cursor cursor = db.query(ImageDatabase.TABLE_NAME, col, null,
				null, null, null, null);
		if (cursor != null) {
			while (cursor.moveToNext()) {
				final String imageIds = cursor.getString(cursor
						.getColumnIndex(ImageDatabase.COL_IMAGE_ID));
				try {
					JSONObject jsonId = new JSONObject(imageIds);
					JSONArray imageIdArray = jsonId
							.optJSONArray(Constants.LOCATION_ARRAY_TO_STRING_KEY);

					if (imageIdArray != null) {
						try {
							//if key is exist in the db it means the bitmap has been deleted from the cache
							//so remove the entry from the table before inserting the bitmap into cache
							if (imageIdArray.get(0).toString().equals(key)) {
								Utility.logD(TAG, "Image ID exist in DB , it means Bitamps are not in the Cache--");
								final String where = ImageDatabase.COL_IMAGE_ID
										+ " =?";
								final String[] arg = { imageIds };

								db.delete(ImageDatabase.TABLE_NAME, where, arg);
								isItemDeleted = true;
							}
						} catch (JSONException e) {
							e.printStackTrace();
						}
					}

				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		}
		return isItemDeleted;
	}

	/**
	 * Called to get the list of recent searches
	 * @param string : search text in the field
	 * @return : list of match searches in the db
	 */
	public ArrayList<String> getRecentSearches(String string) {
		SQLiteDatabase db = this.getReadableDatabase();
		final String where = ImageDatabase.COL_SEARCH_QUERY
				+ " LIKE ?";
		final String[] arg = { "%" + string + "%" };
		final String[] col = { ImageDatabase.COL_SEARCH_QUERY };

		ArrayList<String> recentSearches = null;
		Cursor cursor = db.query(ImageDatabase.TABLE_NAME,col , where,
				arg, null, null, null);
		if (cursor != null) {
			if(cursor.getCount() > 0){
				recentSearches = new ArrayList<>();
				while (cursor.moveToNext()) {
					final String imageTitle = cursor.getString(cursor
							.getColumnIndex(ImageDatabase.COL_SEARCH_QUERY));
					recentSearches.add(imageTitle);
			}
			}
		}
		return recentSearches;
	}
}
