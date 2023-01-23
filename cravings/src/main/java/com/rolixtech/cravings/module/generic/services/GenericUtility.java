package com.rolixtech.cravings.module.generic.services;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rolixtech.cravings.module.auth.config.JwtTokenUtil;
import com.rolixtech.cravings.module.users.dao.CommonUsersDao;
import com.rolixtech.cravings.module.users.models.CommonUsers;

@Service
public class GenericUtility  {
	
	public static final String APPLICATION_CONTEXT = "/cravings";
	

	@Autowired
	private JwtTokenUtil util;
	
	
	@Autowired
	private CommonUsersDao UsersDao;
	
	
	
	public   long getUserIDByToken(String token) {
		long UserID=0;
		if(token!=null) {
			CommonUsers user= UsersDao.findByEmailAndIsDeleted(util.getUsernameFromToken(token.substring(7)),0);
			
			UserID=user.getId();
		}
		
		return UserID;
	}
	public static String getUniqueId() {
		  String UniqueNumber="";
		 Date dNow = new Date();
	     SimpleDateFormat ft = new SimpleDateFormat("yyMMddhhmmssMs");
	     UniqueNumber= ft.format(dNow);
		return UniqueNumber;
	}
	
	public static String getSecureFileDirectoryPath() {
		String path="";
		if(getOperatingSystemName()==1) {
			path="D:\\";
		
		}else {
			
			path="/home/ubuntu/cravingsFiles/";
			
		}

		return path;
	}
	
	
	public static String getOpenFileDirectoryPath() {
		String path="";
		if(getOperatingSystemName()==1) {
			path="D:\\";
		
		}else {
			
			path="/opt/apache-tomcat-8.5.84/webapps/ROOT/resturants_images";
			
		}

		return path;
	}
	
		
	
	public static int getOperatingSystemName() { 
	     
		System.out.println(System.getProperty("os.name"));
	     if(System.getProperty("os.name").equals("Windows 10") || System.getProperty("os.name").equals("Windows 11")) {
	    	 
	    	 return 1;
	     
	     }else {
	    	 
	    	 return 0;
	     }
	
	    
	}
	
	public static int parseInt(Integer val) {

		if (val == null) {
			val = 0;
		}

		int ret = 0;

		try {
			ret = val.intValue();
		} catch (NumberFormatException e) {
		}

		return ret;
	}
	public static int[] parseInt(Integer val[]) {
		
		if (val != null) {
			int ret[] = new int[val.length];

			for (int i = 0; i < val.length; i++) {
				try {
					ret[i] = val[i].intValue();
				}catch(Exception e) {
					
				}
				
			}

			return ret;
		} else {
			return null;
		}
	}

	public static double parseDouble(Double val) {

		if (val == null) {
			val = 0.0;
		}

		double ret = 0;

		try {
			ret = val.doubleValue();
		} catch (NumberFormatException e) {
		}

		return ret;
	}
	public static double[] parseDouble(Double val[]) {

		if (val != null) {
			double ret[] = new double[val.length];

			for (int i = 0; i < val.length; i++) {
				ret[i] = val[i].doubleValue();
			}

			return ret;
		} else {
			return null;
		}
	}
	public static double parseDouble(String val) {

		if (val == null) {
			val = "0";
		}

		double ret = 0;

		try {
			ret = Double.parseDouble(val);
		} catch (NumberFormatException e) {
		}

		return ret;
	}
	public static long parseLong(String val) {

		if (val == null) {
			val = "0";
		}

		long ret = 0;

		try {
			ret = Long.parseLong(val);
		} catch (NumberFormatException e) {
		}

		return ret;
	}
	public static long[] parseLong(Long val[]) {

		if (val != null) {
			long ret[] = new long[val.length];

			for (int i = 0; i < val.length; i++) {
				ret[i] = val[i].longValue();
			}

			return ret;
		} else {
			return null;
		}
	}
	public static long parseLong(Long val) {

		if (val == null) {
			val = 0L;
		}

		long ret = 0;

		try {
			ret = val.longValue();
		} catch (NumberFormatException e) {
		}

		return ret;
	}

	
	public static long[] toPrimitives(final Long[] array) {
		long val[]=null;
		if(array!=null) {
			val=Arrays.stream(array)
            .filter(Objects::nonNull)
            .mapToLong(Long::longValue)
            .toArray();
		}
	    return val;
	}
	
	public static String millisecondstoTime(long millis)  throws ParseException {
	    
	    String hms = String.format("%02d:%02d:%02d", TimeUnit.MILLISECONDS.toHours(millis),
	            TimeUnit.MILLISECONDS.toMinutes(millis) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis)),
	            TimeUnit.MILLISECONDS.toSeconds(millis) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis)));
	    System.out.println(hms);
	    return hms;
	}
	
	
	public static int getDayNumberOFWeek() {
		int ret=0;
		
		Date currentDate=new Date();
		Calendar c = Calendar.getInstance();
		c.setTime(currentDate);
		int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);

		return getDayNumber(dayOfWeek);
	}
	
	public static  int getDayNumber(Integer val) {

		if (val == null) {
			val = 0;
		}

		int day = 0;
		int retDay = 0;

		try {
			day = val.intValue();
			
			switch (day) {
			  case 1:
			//SUNDAY
				  retDay=7;
				break;
			  case 2:
			//MONDAY
				  retDay=1;
			    break;
			  case 3:
			//TUESDAY	  
				  retDay=2;
				    
			    break;
			  case 4:
			//WEDNESDAY		  
				  retDay=3;
				 
			    break;
			  case 5:
			//THURSDAY
				  retDay=4;
			    break;
			  case 6:
			//FRIDAY	  
				  retDay=5;
			    break;
			  case 7:
			//SATURDAY		  
				  retDay=6;
			    break;
			}
			
			
		} catch (NumberFormatException e) {
		}

		return retDay;
	}
	
	
//	Ways of using
//	System.out.println(distance(32.9697, -96.80322, 29.46786, -98.53506, 'M') + " Miles\n");
//    System.out.println(distance(32.9697, -96.80322, 29.46786, -98.53506, 'K') + " Kilometers\n");
//    System.out.println(distance(32.9697, -96.80322, 29.46786, -98.53506, 'N') + " Nautical Miles\n");
	
	
    private double distanceCalculate(double lat1, double lon1, double lat2, double lon2, char unit) {
        double theta = lon1 - lon2;
        double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2)) + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));
        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515;
        if (unit == 'K') {
          dist = dist * 1.609344;
        } else if (unit == 'N') {
          dist = dist * 0.8684;
          }
        return (dist);
      }
      
      /*:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::*/
      /*::  This function converts decimal degrees to radians             :*/
      /*:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::*/
      private double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
      }
      
      /*:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::*/
      /*::  This function converts radians to decimal degrees             :*/
      /*:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::*/
      private double rad2deg(double rad) {
        return (rad * 180.0 / Math.PI);
      }
	
}
