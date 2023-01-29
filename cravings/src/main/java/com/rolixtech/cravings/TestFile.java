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
		
		System.out.println(utitlity.distanceCalculate(31.451911300783454, 74.26635672455583, 31.43642210, 74.26879316, 'k'));
//		System.out.println(distance(31.451911300783454, -74.26635672455583, 31.461030198227387, -74.26034809711437));
//		System.out.println(distance(31.451911300783454, -74.26635672455583, 31.470312399815075, -74.25203367317675));
//		//System.out.println(distance(31.451911300783454, -74.26635672455583, 31.461030198227387, -74.26034809711437, "N") + " Nautical Miles\n");
	}

	

}