package uk.ac.aber.cs221.group07.walktourcreator.model;

import java.io.IOException;
import java.util.List;
import java.util.Vector;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;

import uk.ac.aber.cs221.group07.walktourcreator.activities.MainMenu;
import uk.ac.aber.cs221.group07.walktourcreator.activities.WalkScreen;
import uk.ac.aber.cs221.group07.walktourcreator.services.RouteRecorder;

/**
 * Handles the encoding and uploading of walk data.
 * @author HarryBuckley
 *
 */
public class FileTransferManager{

	//private final static String dataServer = "http://users.aber.ac.uk/hfb1/this.php";
	private final static String dataServer = "http://users.aber.ac.uk/mda/csgp07/file_saver.php";
	
	public final static int UPLOAD_SUCCESS = 210;
	public final static int UPLOAD_ERROR   = 537;
	
	
	/**
	* makes a connection to data server and uploads all files belonging to the given
	* file, the return values will be zero if the method succeeded without problems.
	* 
	* @param walk, the walk that is to be uploaded.
	*/

	public FileTransferManager(WalkScreen screen, WalkModel w){
		new Uploader(screen,w).start();
	}

	
	/**
	 * packages all the walk model data up into a json object
	 * 
	 * @param data, the walk that is to be uploaded.
	 * @return the walk encoded into a json object.
	 */
	private static JSONObject packageData(WalkModel data){
		JSONObject walkData = new JSONObject();
		try{
			//add walk data
			walkData.put("title"		, data.getTitle());
			walkData.put("short_desc"	, data.getShortDescription());
			walkData.put("long_desc"	, data.getLongDescription());
			walkData.put("hours"		, data.getTimeTaken());
			walkData.put("distance"		, data.getDistance());
			//add all location points
			walkData.put("route", packagepathData(data.getRoutePath()));
					
		}catch (JSONException e){ }
		return walkData;
	}
	
	/**
	 * packages a single location into a JSONObject
	 * 
	 * @param point the point to be encoded
	 * @return the encoded point.
	 * @throws JSONException
	 */
	private static JSONArray packagepathData(Vector<LocationPoint> route) throws JSONException{
		JSONArray routeData = new JSONArray();
		for(LocationPoint point: route){
		
			JSONObject location = new JSONObject();
			location.put("longitude", point.getLongitude());
			location.put("latitude", point.getLatitude());
			location.put("time", point.getTime());
			if(point instanceof PointOfInterest){
				location.put("description",((PointOfInterest)point).getDescription());
				location.put("title",((PointOfInterest)point).getTitle());
				JSONArray images = new JSONArray();
				for(ImageInformation image:((PointOfInterest)point).getImages()){
					JSONObject jsonImage = new JSONObject();
					jsonImage.put("file_data",image.getImageAsString());
					images.put(jsonImage);
				}
				location.put("images",images);
			}
			routeData.put(location);
		}
		return routeData;
	}
	
	/**
	 * sends the JSON data to the dataserver.
	 * @param pakagedData the data to be sent.
	 */

	private static int post(JSONObject pakagedData,WalkScreen walk) {
	int status = 0;
		HttpClient httpclient = new DefaultHttpClient();
	    HttpPost post = new HttpPost(dataServer);
	    try {
	    	//add walk data to message	    	
	        List<NameValuePair> dataPairs = new Vector<NameValuePair>();
	        dataPairs.add(new BasicNameValuePair("JSON",pakagedData.toString()));
	        
	        //UrlEncodedFormEntity entity = new UrlEncodedFormEntity(dataPairs);
	        //entity.setContentType("application/json");
	      	post.setEntity(new UrlEncodedFormEntity(dataPairs));
	        //send message
	        HttpResponse response = httpclient.execute(post);
	        
	        status = response.getStatusLine().getStatusCode();
	        
	        //do something to check the response
	    } catch (IOException e) { }

	    walk.returnToStart();
	    return 1;
	} 
	
	/**
	 *  a inner class that keeps the upload working in a separate thread.
	 */
	private class Uploader extends Thread{
		/**holds the walk to be encoded, then uploaded*/
		private WalkModel data;
		
		private WalkScreen walk;
		
		/**set the walk data*/
		public Uploader(WalkScreen screen,WalkModel walk){
			this.data = walk;
			this.walk = screen;
		}
		
		/** start process of upload*/
		public void run(){
			int statusCode = post(packageData(data),walk);
			walk.returnToStart(statusCode);

		}
	}
}
