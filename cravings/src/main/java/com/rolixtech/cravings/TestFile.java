package com.rolixtech.cravings;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;

import com.rolixtech.cravings.module.generic.services.GenericUtility;

import java.lang.*;
import java.io.*;

class TestFile

{
	
	@Autowired 
	private static GenericUtility utitlity;
	
	
	public static void main (String[] args) throws java.lang.Exception
	{
		
		System.out.println(utitlity.distanceCalculate(31.451911300783454, 74.26635672455583, 31.525487005923402, 74.34947138209866, 'k')*2);
//		System.out.println(distance(31.451911300783454, -74.26635672455583, 31.461030198227387, -74.26034809711437));
//		System.out.println(distance(31.451911300783454, -74.26635672455583, 31.470312399815075, -74.25203367317675));
//		//System.out.println(distance(31.451911300783454, -74.26635672455583, 31.461030198227387, -74.26034809711437, "N") + " Nautical Miles\n");
	}

	
	
	    //returns distance in meters
//	    public static double distance(double lat1, double lng1, 
//	                                      double lat2, double lng2){
//	     double a = (lat1-lat2)*distPerLat(lat1);
//	     double b = (lng1-lng2)*distPerLng(lat1);
//	     return Math.sqrt(a*a+b*b);
//	    }
//
//	    private static double distPerLng(double lat){
//	      return 0.0003121092*Math.pow(lat, 4)
//	             +0.0101182384*Math.pow(lat, 3)
//	                 -17.2385140059*lat*lat
//	             +5.5485277537*lat+111301.967182595;
//	    }
//
//	    private static double distPerLat(double lat){
//	            return -0.000000487305676*Math.pow(lat, 4)
//	                -0.0033668574*Math.pow(lat, 3)
//	                +0.4601181791*lat*lat
//	                -1.4558127346*lat+110579.25662316;
//	    }
	
	
//	private static double distance(double lat1, double lon1, double lat2, double lon2, String unit) {
//		if ((lat1 == lat2) && (lon1 == lon2)) {
//			return 0;
//		}
//		else {
//			double theta = lon1 - lon2;
//			double dist = Math.sin(Math.toRadians(lat1)) * Math.sin(Math.toRadians(lat2)) + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) * Math.cos(Math.toRadians(theta));
//			dist = Math.acos(dist);
//			dist = Math.toDegrees(dist);
//			dist = dist * 60 * 1.1515;
//			if (unit.equals("K")) {
//				dist = dist * 1.609344;
//			} else if (unit.equals("N")) {
//				dist = dist * 0.8684;
//			}
//			return (dist);
//		}
//	}
}