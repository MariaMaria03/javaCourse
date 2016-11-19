package javalab2;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class OktmoReader {
  
  public static void readPlaces(String fileName, OktmoData data) {
    BufferedReader br = null;
    int lineCount = 0;
    try {
      br = new BufferedReader(new InputStreamReader(new FileInputStream(fileName), "cp1251"));
      String s;
      Pattern patternPlace = Pattern.compile("^(\\d{2}\\s\\d{3}\\s\\d{3}\\s\\d{3}(?<!000))\\s+\\d\\s+(\\S+)\\s(.*)$");
      while ((s = br.readLine()) != null ) {
        lineCount++;
        Matcher m = patternPlace.matcher(s);
        if (m.matches()) {
            long code = Long.parseLong(m.group(1).replace(" ", ""));
            String status = m.group(2);
            String name = m.group(3);
            data.addPlace(code, name, status);
          //if (lineCount == 10000) break; // пока частично
        }  
      }
      
    }
    catch (IOException ex) {
      System.out.println("Reading error in line "+ lineCount);
      ex.printStackTrace();
    }
    finally {
      try {
        br.close();
      } catch (IOException ex) {
        System.out.println("Can not close");
      }
    }
  }
  
  public static void readPlacesViaSplit(String fileName, OktmoData data) {
    BufferedReader br = null;
    int lineCount = 0;
    try {
      br = new BufferedReader(new InputStreamReader(new FileInputStream(fileName), "cp1251"));
      String s;
      while ((s = br.readLine()) != null ) {
        lineCount++;
        String[] elemPlace = s.split("\t");
        long code = 0;
        if (elemPlace.length == 3 && elemPlace[0].length() == 14 &&
                      !"   ".equals(elemPlace[0].substring(11, 14)) &&
                      !"000".equals(elemPlace[0].substring(11, 14))) {
          String strCode = elemPlace[0].replace(" ", "");
          code = toLong(strCode);
        }
        if (code != 0) {
          int numName = elemPlace[2].indexOf(" ");
          String status = elemPlace[2].substring(0, numName);
          String name = elemPlace[2].substring(numName + 1);
          data.addPlace(code, name, status);
        }          
//        if (lineCount == 1000) break; // пока частично
      }
      
      
    }
    catch (IOException ex) {
      System.out.println("Reading error in line "+ lineCount);
      ex.printStackTrace();
    }
    finally {
      try {
        br.close();
      } catch (IOException ex) {
        System.out.println("Can not close");
      }
    }
  }
  
  public static long toLong(String s) {
    try {
      return Long.parseLong(s);
    } 
    catch (NumberFormatException e) {
      return 0;
    }
  }
}
