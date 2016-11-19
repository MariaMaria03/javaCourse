package javalab3;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.regex.Pattern;
import javafx.print.Collation;

public class OktmoAnalyzer {
    
  
  public static void printSortedPlaces(OktmoData placesData) {
    ArrayList<Place> dataSorted = (ArrayList<Place>) placesData.placeList.clone();
    Comparator <Place> byName = (o1, o2) -> o1.name.compareTo(o2.name);
    Collections.sort(dataSorted, byName);
    dataSorted.forEach((Place t) -> System.out.println(t));

//    Comparator<Place> byC = (o1, o2) -> o1.code > o2.code ? 10 : -10;
//    Collections.sort(dataSorted, byC);
//    dataSorted.forEach((Place t) -> System.out.println(t));
  }
  
  
  public static void filterName(OktmoData data) {
    Pattern patternCode = Pattern.compile("^\\S{0,2}ово$");
    data.placeList.forEach((Place t) -> {
      if (patternCode.matcher(t.name).matches()){
        System.out.println(t.name);
      }
    });
  }
  
  // Имена начинающиеся и заканчивающиеся на одну и ту же букву
  public static void filterWithSameLetter(OktmoData data) {
    Pattern patternCode = Pattern.compile("^(?i)([бвгджзклмнпрстфхцчшщ])\\S+\\S\\1$", Pattern.UNICODE_CASE);
    data.placeList.forEach((Place t) -> {
      if (patternCode.matcher(t.name).matches()) {
        System.out.println(t.name);
      }
    });
  }
  
  public static void findMostPopularPlaceName(String regionName, OktmoData dataOktmo) {
    //OktmoGroup region = dataOktmo.placesMap;
    
    
  }
  
  public static void countPlaceByRegion(String regionName) {
    
  }
  
//  private OktmoGroup findByName(String regionName, HashMap placesMap) {
//    
//  }
}
