package javalab3;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import javafx.print.Collation;

public class OktmoAnalyzer {
  OktmoData oktmo;
  long most;

  public OktmoAnalyzer(OktmoData o) {
    oktmo = o;
    most = 0;
  }
  
  public void printSortedPlaces() {
    ArrayList<Place> dataSorted = (ArrayList<Place>) oktmo.placeList.clone();
    Comparator <Place> byName = (o1, o2) -> o1.name.compareTo(o2.name);
    Collections.sort(dataSorted, byName);
    dataSorted.forEach((Place t) -> System.out.println(t));
  }
  
  
  public void filterName() {
    Pattern patternCode = Pattern.compile("^\\S{0,2}ово$");
    oktmo.placeList.forEach((Place t) -> {
      if (patternCode.matcher(t.name).matches()){
        System.out.println(t.name);
      }
    });
  }
  
  // Имена начинающиеся и заканчивающиеся на одну и ту же букву
  public void filterWithSameLetter() {
    Pattern patternCode = Pattern.compile("^(?i)([бвгджзклмнпрстфхцчшщ])\\S+\\S\\1$", Pattern.UNICODE_CASE);
    oktmo.placeList.forEach((Place t) -> {
      if (patternCode.matcher(t.name).matches()) {
        System.out.println(t.name);
      }
    });
  }
  
  public void findMostPopularPlaceName(String regionName) {
    OktmoGroup region = oktmo.groupByName.get(regionName);
    if (region != null) {
      ArrayList<Place> placesRegion = (ArrayList<Place>)
          oktmo.placeList.stream().filter((Place t) -> {
            return t.code/1000000000 == region.code/1000000000;
          }).collect(Collectors.toList());
      
      HashMap<String, Long> countSamePlaces = new HashMap<String, Long>();
      placesRegion.forEach((Place t) -> {
        if (countSamePlaces.get(t.name) != null) {
          countSamePlaces.put(t.name, countSamePlaces.get(t.name) + 1);
          if (most < countSamePlaces.get(t.name) + 1) {
            most = countSamePlaces.get(t.name) + 1;
          }
        }
        else {
          countSamePlaces.put(t.name, 1l);
        }
      });
      
//      TODO: имея most фильтруем countSamePlaces (выходит массив строк) и тут же выводим каждое значение массива
     }
    else {
      System.err.println("Региона с именем: " + regionName + " нет. Попробуйте другой");
    }
    
    
    
  }
  
  public static void countPlaceByRegion(String regionName) {
    
  }
  
}
