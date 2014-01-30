package uk.ac.aber.cs221.group07.walktourcreator.model;

import java.util.Date;


/**
 * This class stores a map position and records the time at which is was taken
 * @author HarryBuckley
 */
public class LocationPoint {
	
	/** holds the distance (east-west) from Greenwich) */
	protected double longitude;
	
	/** holds the distance (north-south) from the equator) */
	protected double latitude;
	
	/** keeps a record of when the location was captured  */
	private long time;
	
	/**
	 * creates new LoactionPoint
	 * 
	 * @param lat the longitude position
	 * @param lng the latitude position
	 */
	public LocationPoint(double lat,double lng){
		this(lat,lng,System.currentTimeMillis());
	}
	
	/**
	* creates a LocationPoint,
	* used to recreate a point stored in the database.
	* 
	*  @param lat the longitude position
	*  @param lng the latitude position
	*  @param time, the time in milliseconds that this point was reached
	*  @param id, used to to identify this point uniquely 
	*/
	public LocationPoint(double lat,double lng,long time){
		this.time = time;
		this.latitude = lat;
		this.longitude = lng;
	}
	
	/**
	 * @return the Time that the recording was made
	 */
	public long getTime(){
		return time;
	}
	
	/**
	 *  @return the longitude, the east/west distance from Greenwich.
	 */
	public double getLongitude(){
		return longitude;
	}
	
	/**
	 *  @return the latitude, the north/south distance from the equator.
	 */
	public double getLatitude(){
		return latitude;
	}
	
	/**
	 * works out the distance between two 
	 * @param point1 the Location the you want the know the distance to
	 * @return the distance between itself and the given Location
	 */
	
	 //A distance between two points given latitude and longitude function found on:
	 //http://stackoverflow.com/questions/837872/calculate-distance-in-meters-when-you-know-longitude-and-latitude-in-java
	
	 public static double distFrom(LocationPoint point, LocationPoint point2) {
		    double earthRadius = 3958.75;
		    double dLat = Math.toRadians(point2.latitude-point.latitude);
		    double dLng = Math.toRadians(point2.longitude-point.longitude);
		    double a = Math.sin(dLat/2) * Math.sin(dLat/2) +
		               Math.cos(Math.toRadians(point.latitude)) * Math.cos(Math.toRadians(point2.latitude)) *
		               Math.sin(dLng/2) * Math.sin(dLng/2);
		    double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
		    double dist = earthRadius * c;

		    int meterConversion = 1609;
		    
		    double finalDist = (dist * meterConversion)/1000;
		    return finalDist;
	 }
}
