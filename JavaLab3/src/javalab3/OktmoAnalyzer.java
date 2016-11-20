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
  long popularNum;

  public OktmoAnalyzer(OktmoData o) {
    oktmo = o;
    popularNum = 0;
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
          long valueCurrent = countSamePlaces.get(t.name) + 1;
          countSamePlaces.put(t.name, valueCurrent);
          if (popularNum < valueCurrent ) {
            popularNum = valueCurrent;
          }
        }
        else {
          countSamePlaces.put(t.name, 1l);
        }
      });
      
      countSamePlaces.keySet().stream().filter((String t) -> {
        return countSamePlaces.get(t) == popularNum;
      }).forEach((String t) -> {
        System.out.println(t + ", количество: " + popularNum);
      });
      
     }
    else {
      System.err.println("Региона с именем: " + regionName + " нет. Попробуйте другой");
    }  
    
  }
  
  public void countPlaceByRegion(String regionName) {
    OktmoGroup region = oktmo.groupByName.get(regionName);
    if (region != null) {
      ArrayList<Place> placesRegion = (ArrayList<Place>) oktmo.placeList.stream().filter((Place t) -> {
        return t.code / 1000000000 == region.code / 1000000000;
      }).collect(Collectors.toList());

      HashMap<String, Long> statusesCount = new HashMap<String, Long>();
      
      placesRegion.forEach((Place t) -> {
        if (statusesCount.get(t.status) != null) {
          statusesCount.put(t.status, statusesCount.get(t.status) + 1);
        }
        else {
          statusesCount.put(t.status, 1l);
        }
        
      });
      System.out.println("Статусы региона: " + regionName);
      statusesCount.forEach((String t, Long u) -> {
        System.out.println("Статус " + t + ", населенных пунктов: " + u);
      });
    }
    else {
      System.err.println("Региона с именем: " + regionName + " нет. Попробуйте другой");
    }
    
    
  }
  
  public void countPlaceByRegionShort(String regionName) {
    OktmoGroup region = oktmo.groupByName.get(regionName);
    if (region != null) {
      HashMap<String, Long> statusesCount = new HashMap<String, Long>();
      oktmo.placeList.forEach((Place t) -> {
        if (t.code / 1000000000 == region.code / 1000000000) {
          if (statusesCount.get(t.status) != null) {
            statusesCount.put(t.status, statusesCount.get(t.status) + 1);
          } else {
            statusesCount.put(t.status, 1l);
          }
        }
      });

      System.out.println("Статусы региона: " + regionName);
      statusesCount.forEach((String t, Long u) -> {
        System.out.println("Статус " + t + ", населенных пунктов: " + u);
      });
    } else {
      System.err.println("Региона с именем: " + regionName + " нет. Попробуйте другой");
    }
  }
  
}
