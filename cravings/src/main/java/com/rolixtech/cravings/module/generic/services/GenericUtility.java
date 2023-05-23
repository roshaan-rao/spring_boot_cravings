package com.rolixtech.cravings.module.generic.services;

import java.security.SecureRandom;
import java.text.DateFormat;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;


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

	

    public static Date removeTime(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    
    
    public static int getDayNumberOfWeekCurrentDate() {
        LocalDate date = LocalDate.now(); // get the current date
        DayOfWeek dayOfWeek = date.getDayOfWeek(); // get the day of the week
        int dayNumber = dayOfWeek.getValue(); // get the day number from the week
        return dayNumber;
        
    }
	
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
	public static String getDateWithOutSlashesId() {
		  String UniqueNumber="";
		 Date dNow = new Date();
	     SimpleDateFormat ft = new SimpleDateFormat("ddMMyyyy");
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
	
	
    public static double distanceCalculate(double lat1, double lon1, double lat2, double lon2, char unit) {
        double theta = lon1 - lon2;
        double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2)) + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));
        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515;
        if (unit == 'K') {
        	//Added *2.3 for estimation betterment
          dist = (dist * 1.609344)*2.3;
        } else if (unit == 'N') {
          dist = dist * 0.8684;
          }
        return (dist);
      }
      
      /*:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::*/
      /*::  This function converts decimal degrees to radians             :*/
      /*:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::*/
      private static double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
      }
      
      /*:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::*/
      /*::  This function converts radians to decimal degrees             :*/
      /*:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::*/
      private static double rad2deg(double rad) {
        return (rad * 180.0 / Math.PI);
      }
      
//	  public static void main(String[] args) {
//    	  String result = GenericUtility.sendSms("92300xxxxxxx","Hello this is a test with a 5 note and an ampersand(&) symbol");
//    	  System.out.println(result);
//	  }
      public static Map getCredientails() {
  		
 	     Map Credientials=new HashMap();
 	     Credientials.put("id","cd1225cranings");
 	     Credientials.put("pass","cravings@26");
 	     Credientials.put("mask","CDS");
 	     return Credientials;
 	}
      
	  public static String sendSms(String sToPhoneNo,String sMessage) {
	   try {
	   // Construct data
		   Map credientials= getCredientails();

			 String id=credientials.get("id").toString();
			 String pass=credientials.get("pass").toString();
			 String mask=credientials.get("mask").toString();

    	   String data = "id=" + URLEncoder.encode(id, "UTF-8");
    	   data += "&pass=" + URLEncoder.encode(pass, "UTF-8");
    	   data += "&msg=" + URLEncoder.encode(sMessage, "UTF-8");
    	   data += "&lang=" + URLEncoder.encode("English", "UTF-8");
    	   data += "&to=" + URLEncoder.encode(sToPhoneNo, "UTF-8");
    	   data += "&mask=" + URLEncoder.encode(mask, "UTF-8");
    	   data += "&type=" + URLEncoder.encode("json", "UTF-8");
	   // Send data
    	   URL url = new URL("http://www.opencodes.pk/api/medver.php/sendsms/url");
    	   URLConnection conn = url.openConnection();
    	   conn.setDoOutput(true);
    	   OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
    	   wr.write(data);
    	   wr.flush();
	   // Get the response
    	   BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
    	   String line;
    	   String sResult="";
    	   while ((line = rd.readLine()) != null) {
	   // Process line...
    	   sResult=sResult+line+" ";
    	   }
    	   wr.close();
    	   rd.close();
		   return sResult;
		   } catch (Exception e) {
	    	   System.out.println("Error SMS "+e);
	    	   return "Error "+e;
	    	   }
		  }
	  
//	  	public static int OTP(){
//	  		Random rand = new Random();
//	  		System.out.printf("%04d%n", rand.nextInt(10000));
//	        // Using numeric values
//	        String numbers = "0123456789";
//	        // Using random method
//	        Random rndm_method = new Random();
//	        int len = 4;
//	        char[] otp = new char[len];
//	        for (int i = 0; i < len; i++){
//	            otp[i] = numbers.charAt(rndm_method.nextInt(numbers.length()));
//	        }
//	        System.out.println("OTP: "+otp);
//	        return rand;
//	    }
	  
		public static int[] parseInt(String val[]) {

			if (val != null) {
				int ret[] = new int[val.length];
				for (int i = 0; i < val.length; i++) {
					ret[i] = Integer.parseInt(val[i]);
				}
				return ret;
			} else {
				return null;
			}
		}
      public static String createRandomCode(int codeLength){   
    	     char[] chars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVYZ1234567890".toCharArray();
    	        StringBuilder sb = new StringBuilder();
    	        Random random = new SecureRandom();
    	        for (int i = 0; i < codeLength; i++) {
    	            char c = chars[random.nextInt(chars.length)];
    	            sb.append(c);
    	        }
    	        String output = sb.toString();
    	        
    	        return output ;
    }
      
      public static String createRandomNumericCode(int codeLength){   
 	     char[] chars = "1234567890".toCharArray();
 	        StringBuilder sb = new StringBuilder();
 	        Random random = new SecureRandom();
 	        for (int i = 0; i < codeLength; i++) {
 	            char c = chars[random.nextInt(chars.length)];
 	            sb.append(c);
 	        }
 	        String output = sb.toString();
 	        
 	        return output ;
 }
      
      
     /***
      *@return Date in  "dd/MM/yyyy"
      * */ 
      public static String getDisplayDateddMMYYYY(Date val) {
    	  
    	  SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
    	  String date = simpleDateFormat.format(val);
    	 return date;
      }

	public static String getDisplayDatedYYYYMMDD(Date val) {

		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String date = simpleDateFormat.format(val);
		return date;
	}

	public static LocalDate getDateDBFormat(String val) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		LocalDate newDate = LocalDate.parse(val+" 00:00:00", formatter);
		return newDate;
	}
      
      
      /***
       *@return Date in  "dd-MM-yyyy HH:mm:ss"
       * */ 
       public static String getDisplayDateddMMYYYYHHmmss(Date val) {
     	  
     	  SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
     	  String date = simpleDateFormat.format(val);
     	 return date;
       }
       
       /***
        *@return Date in  "yyyy-MM-dd HH:mm:ss"
        * */ 
        public static String getDisplayDateYYYYMMddHHmmss(Date val) {
      	  
      	  SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
      	  String date = simpleDateFormat.format(val);
      	 return date;
        }
       
      
      
      /***
       *@return Date in  "HH:mm:ss"
       * */ 
      public static String getDisplayTimeFromDate(Date date){
          SimpleDateFormat localDateFormat = new SimpleDateFormat("HH:mm:ss");
          String time = localDateFormat.format(date);
          return time;
          
      }
      
      /***
       *@return Date in Date Type & in  "HH:mm"
     * @throws ParseException 
       * */ 
      public static Date getDateInyyyyMMddFromDate(Date date) throws ParseException{
	      Date val =date;
	      Format formatter;
	      formatter= new SimpleDateFormat("dd/MM/yyyy");
	
	      val = ((DateFormat) formatter).parse(formatter.format(val));
		return val;
      }
      
      
      /***
       *@return Date in Date Type & in  "HH:mm"
     * @throws ParseException 
       * */ 
      public static Date getDateInyyyyMMddFromDateV2(Date date) throws ParseException{
	      Date val =date;
	      Format formatter;
	      formatter= new SimpleDateFormat("yyyy/MM/dd");
	
	      val = ((DateFormat) formatter).parse(formatter.format(val));
		return val;
      }
      
      public static Date parseDate(String date) throws ParseException{
	      Date val =new Date();
	      
	
	      val=new SimpleDateFormat("dd/MM/yyyy").parse(date);  
		return val;
      }
      
     
      
      public static double roundToOneDecimal(double value) { 
  		double  roundedOneDigitX = Math.round(value * 10) / 10.0; 
  		return roundedOneDigitX;
      }
	public double getCravingsSericeFee() {
		double ServiceFee=5.0;
		return ServiceFee;
	}
	
	
	
	public int getCravingsDeliveryTime() {
		int deliveryTime=45;
		return deliveryTime;
	}
      
	public String isFeatured() {
		
		return "true";
	}
      
      
     

}
