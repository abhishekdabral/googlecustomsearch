package com.example.googleimagesearch.serverconnection;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Constructor;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.googleimagesearch.util.Constants;
import com.example.googleimagesearch.util.Constants.ServerResponseStatus;
import com.example.googleimagesearch.util.Utility;

/**
 * Server Connection using Get Method : This class establishes the connection with the server to facilitate Http Server
 * request and Response
 * @author ABHISHEK
 */
public class ServerConnection {

	private static final String TAG = ServerConnection.class.getSimpleName();
	private ServerErrorInfo mServerErrorInfo;

	public ServerConnection() {
		mServerErrorInfo = new ServerErrorInfo();
	}

	/**
	 * {@link Constructor} : can only be used in cases where before sending the WebServices it has already been Verified
	 * whether the Network is available
	 * @param isNetworkAvailable : true if device is connected with network
	 */
	public ServerConnection(boolean isNetworkAvailable) {
		mServerErrorInfo = new ServerErrorInfo();
		if (!isNetworkAvailable) {
			mServerErrorInfo.setmHttpStatusCode(ServerResponseStatus.NETWORK_UNAVAILABLE);
			mServerErrorInfo.setErrorDetected(true);
		}
	}

	/**
	 * Called to get server response using getQuery
	 * @param iUrl : Request URL
	 * @return
	 * @throws ParseException
	 * @throws IOException
	 */
	public String getResponseUsingGetMethod(String iUrl, List<NameValuePair> iBodyParameters) throws
				ParseException, IOException {
		HttpClient httpClient = new DefaultHttpClient();
		HttpGet httpGet = null;
		if(iBodyParameters != null)
		 httpGet = frameHttpGettRequest(iUrl, iBodyParameters);
		else
			httpGet = new HttpGet(iUrl);
		HttpResponse httpResponse;

		httpResponse = httpClient.execute(httpGet);
		HttpEntity httpEntity = httpResponse.getEntity();
		Utility.logD(TAG, "SERVER ENTITY ==========================>" + httpEntity);

		if (httpResponse.getStatusLine().getStatusCode() == ServerResponseStatus.REQUEST_FULFILLED) {
			if (httpEntity != null) {
				String res = EntityUtils.toString(httpEntity);
				Utility.logD(TAG, "CONTENT ==========================>" + res);
				return res;
			}
		}
		return null;
	}

	/**
	 * Called to get response from ssl requests
	 * @param iUrl : Request Url
	 * @param iParam : Body params
	 * @return : Response of the url request
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public String getResponseFromSecureRequest(String iUrl, ArrayList<NameValuePair> iParam) throws
				ClientProtocolException, IOException{
		HttpClient httpclient = createHttpClient();
        HttpPost httppost = new HttpPost(iUrl);
//			    if(iParam != null)
//				httppost.setEntity(new UrlEncodedFormEntity(iParam));

		        HttpResponse response = httpclient.execute(httppost);
		        HttpEntity httpEntity = response.getEntity();
		        //If request fulfilled then get response
		        if (response.getStatusLine().getStatusCode() == Constants.ServerResponseStatus.REQUEST_FULFILLED) {
					if (httpEntity != null) {
						String res = EntityUtils.toString(httpEntity);
						Utility.logD(TAG, "CONTENT ==========================>" + res);
						return res;
					}
				}

        return null;
	}

	/**
	 * Method to set the server request for secure protocol
	 * @return : HttpClient
	 */
	public static HttpClient createHttpClient()
	{
	    HttpParams params = new BasicHttpParams();
	    HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
	    HttpProtocolParams.setContentCharset(params, HTTP.DEFAULT_CONTENT_CHARSET);
	    HttpProtocolParams.setUseExpectContinue(params, true);

	    SchemeRegistry schReg = new SchemeRegistry();
	    schReg.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));
	    schReg.register(new Scheme("https", org.apache.http.conn.ssl.SSLSocketFactory.getSocketFactory(), 443));
	    ClientConnectionManager conMgr = new ThreadSafeClientConnManager(params, schReg);

	    return new DefaultHttpClient(conMgr, params);
	}

	/**
	 * Called to set the body parameters in the url using HttpGet Method
	 * @param iUrl : Url
	 * @param iBodyParameters : Bodya parameters i.e Username and Password
	 * @return : {@link HttpGet} instance
	 */
	public HttpGet frameHttpGettRequest(String iUrl, List<NameValuePair> iBodyParameters) {
		iUrl += URLEncodedUtils.format(iBodyParameters, "UTF-8");
		HttpGet httpGet = new HttpGet(iUrl);
		return httpGet;

	}

	/**
	 * Called to get the server response
	 * @param iUrl : Main Url
	 * @param iBodyParameters : Url Parameter ,i.e username and password
	 * @return : Access token
	 */
	public String getResponseUsingPostMethod(String iUrl, List<NameValuePair> iBodyParameters) {
		HttpClient client = new DefaultHttpClient();
		HttpPost httppost = frameHttpPostRequest(iUrl, iBodyParameters);
		try {
			Utility.logD(TAG, "-----BODY PARAMETER-----" + displayInputStream(httppost.getEntity().getContent()));
		} catch (IllegalStateException | IOException | NullPointerException e1) {
			e1.printStackTrace();
		}

		try {
			HttpResponse httpResponse = client.execute(httppost);
			Utility.logD(TAG, "----------RESPONSE-------" + httpResponse.toString());

			HttpEntity entity = httpResponse.getEntity();
			if (httpResponse.getStatusLine().getStatusCode() == 200) {
				if (entity != null) {
					return EntityUtils.toString(entity, HTTP.UTF_8);
				}
			} else {
				Utility.logD(TAG, "" + httpResponse.getStatusLine().getReasonPhrase());
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Called to set the body parameters in the Url
	 * @param iUrl : Main Url
	 * @param iBodyParameters : Body Parameters of Url
	 * @return : {@link HttpPost} instance
	 */
	public HttpPost frameHttpPostRequest(String iUrl, List<NameValuePair> iBodyParameters) {
		//start http-post request
		HttpPost httpPost = new HttpPost(iUrl);

		httpPost.setHeader("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");
		try {
			if(iBodyParameters != null){
				httpPost.setEntity(new UrlEncodedFormEntity(iBodyParameters));
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
			return httpPost;
	}

	/**
	 * Called to view to bodyprameter in httpPost request
	 * @param is : instance of input stream
	 * @return : Body parameters
	 */
	public static String displayInputStream(InputStream is) {
		//to store body parameters string
		StringBuffer message = new StringBuffer();
		try {
			InputStream in = new BufferedInputStream(is);
			BufferedReader ing = new BufferedReader(new InputStreamReader(in));
			String line = null;
			//read the input stream
			while ((line = ing.readLine()) != null) {
				message.append(line);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return message.toString();
	}

	
	public Bitmap getBitmapFromServer(String url, Context iContext)
    {
		FileCache fileCache = new FileCache(iContext);
        File f=fileCache.getFile(url);
  
        //from web
        try {
            Bitmap bitmap=null;
            URL imageUrl = new URL(url);
            HttpURLConnection conn = (HttpURLConnection)imageUrl.openConnection();
            conn.setConnectTimeout(30000);
            conn.setReadTimeout(30000);
            conn.setInstanceFollowRedirects(true);
            InputStream is=conn.getInputStream();
            OutputStream os = new FileOutputStream(f);
            Utility.CopyStream(is, os);
            os.close();
            bitmap = decodeFile(f);
            return bitmap;
        } catch (Exception ex){
           ex.printStackTrace();
           return null;
        }
    }
  
    //decodes image and scales it to reduce memory consumption
    private Bitmap decodeFile(File f){
        try {
            //decode image size
            BitmapFactory.Options o = new BitmapFactory.Options();
            o.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(new FileInputStream(f),null,o);
  
            //Find the correct scale value. It should be the power of 2.
            final int REQUIRED_SIZE=70;
            int width_tmp=o.outWidth, height_tmp=o.outHeight;
            int scale=1;
            while(true){
                if(width_tmp/2<REQUIRED_SIZE || height_tmp/2<REQUIRED_SIZE)
                    break;
                width_tmp/=2;
                height_tmp/=2;
                scale*=2;
            }
  
            //decode with inSampleSize
            BitmapFactory.Options o2 = new BitmapFactory.Options();
            o2.inSampleSize=scale;
            return BitmapFactory.decodeStream(new FileInputStream(f), null, o2);
        } catch (FileNotFoundException e) {}
        return null;
    }
    public class FileCache {
    	  
        private File cacheDir;
      
        public FileCache(Context context){
            //Find the dir to save cached images
            if (android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED))
                cacheDir=new File(android.os.Environment.getExternalStorageDirectory(),"TempImages");
            else
                cacheDir=context.getCacheDir();
            if(!cacheDir.exists())
                cacheDir.mkdirs();
        }
      
        public File getFile(String url){
            String filename=String.valueOf(url.hashCode());
            File f = new File(cacheDir, filename);
            return f;
      
        }
      
        public void clear(){
            File[] files=cacheDir.listFiles();
            if(files==null)
                return;
            for(File f:files)
                f.delete();
        }
      
    }
}
