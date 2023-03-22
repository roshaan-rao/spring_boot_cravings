package com.rolixtech.cravings;
import java.io.*;
import java.math.*;
import java.security.*;
import java.text.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.function.*;
import java.util.regex.*;
import java.util.stream.*;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;

class Result {

    /*
     * Complete the 'diagonalDifference' function below.
     *
     * The function is expected to return an INTEGER.
     * The function accepts 2D_INTEGER_ARRAY arr as parameter.
     */

	public static int chocolateFeast(int n, int c, int m) {
	    // Initialize bars with money
	    int countBars = n / c;
	    // Initial bars = wrappers
	    int wrappers = countBars;

	    while (wrappers >= m) {
	        // Exchange bars with wrappers
	        int bars = wrappers / m;

	        countBars += bars;

	        // Calculate wrappers with exchange bars and leave wrappers
	        wrappers -= (bars * m);
	        wrappers += bars;
	    }

	    return countBars;
	}   

}

public class TestFile {
    public static void main(String[] args) throws IOException {
    	



       System.out.println(Result.chocolateFeast(15,3,2));
        
        

//        bufferedWriter.write(String.valueOf(result));
//        bufferedWriter.newLine();
//
//        bufferedReader.close();
//        bufferedWriter.close();
    }
}
