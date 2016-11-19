package javalab2;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class OktmoData {
    public List<Place> placeList = new ArrayList<Place>();
    public Set<String> allStatuses = new HashSet<String>();
    
    public boolean addPlace(long code, String name, String status) {
        allStatuses.add(status);
        return placeList.add(new Place(code, name, status));
    }

    public void printAll() {
      placeList.forEach((Place t) -> System.out.println(t));
    }
    
    public void printStatuses() {
      System.out.println("Все статусы: ");
      allStatuses.forEach((String t) -> System.out.println(t));
  }
}
