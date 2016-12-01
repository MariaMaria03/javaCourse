package javalab3;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

public class OktmoData {
    public ArrayList<Place> placeList = new ArrayList<Place>();
    public TreeMap<Long, Place> placesPMap = new TreeMap<>();
    public HashSet<String> allStatuses = new HashSet<String>();
    public TreeMap<Long, OktmoGroup> placesMap = new TreeMap<Long, OktmoGroup>();
    public Map<String, OktmoGroup> groupByName = new HashMap<String, OktmoGroup>();
    
    public boolean addPlace(long code, String name, String status) {
        allStatuses.add(status);
        Place place = new Place(code, name, status);
        placesPMap.put(code, place);
        return placeList.add(place);
    }
    
    public void addPlaceGroup(long code, String name, int level) {
      OktmoGroup oktmoG = new OktmoGroup(name, code, level);
      placesMap.put(code, oktmoG);
      groupByName.put(name, oktmoG);
  }

    public void printAll() {
      placeList.forEach((Place t) -> System.out.println(t));
    }
    
    public void printStatuses() {
      System.out.println("Все статусы: ");
      allStatuses.forEach((String t) -> System.out.println(t));
    }
    
    public void printAllGroup() {
      for (Map.Entry<Long, OktmoGroup> entry : placesMap.entrySet()) {
        long key = entry.getKey();
        OktmoGroup value = entry.getValue();
        System.out.println("Код ОКТМО: " + key + " -> Объект: " + value);
      }
    }
}
