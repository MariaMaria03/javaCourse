package javaapplication7;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.JAXBIntrospector;
import javax.xml.bind.Unmarshaller;
import javaapplication7.generated.*;

public class JaxbLab {

  Osm osm;

  public JaxbLab(String fn) throws JAXBException {
    unmarshal(fn);
    ArrayList<Object> list = (ArrayList<Object>) osm.getBoundOrUserOrPreferences();
    ArrayList<Way> ways = new ArrayList<>();
    ArrayList<Node> nodes = new ArrayList<>();
    HashSet<String> busStops = new HashSet<>();
    Map<String, StreetData> streetHouses = new HashMap<>();
    Map<Integer, String> idHouses = new HashMap<>();
    
    for (Object o : list) {
      if (o instanceof Way) {
        ways.add((Way) o);
      }
      else if (o instanceof Node) {
        nodes.add((Node) o);
      }
    }
    nodes.forEach((Node n) -> {
      ArrayList<Tag> tags = (ArrayList<Tag>) n.getTag();
      boolean isBusStop = false;
      for (Tag t : tags) {
        if ("bus_stop".equals(t.getV())) {
          isBusStop = true;
        }
        else if (isBusStop && "name".equals(t.getK())) {
          busStops.add(t.getV());
        }
      }
    });
    
    ways.forEach((Way w) -> {
      ArrayList<Object> childs = (ArrayList<Object>) w.getRest();
      boolean isHighway = false;
      for (Object c : childs) {
        if (c instanceof Tag) {
          Tag t = (Tag) c;
          if ("highway".equals(t.getK())) {
            isHighway = true;
          }
          else if (isHighway && "name".equals(t.getK())) {
            StreetData strH = streetHouses.get(t.getV());
            if (strH == null) {
              strH = new StreetData(0, 0);
              streetHouses.put(t.getV(), strH);
            }
            strH.piecesUp();
          }
          else if ("addr:street".equals(t.getK())) {
            idHouses.put(w.getId(), t.getV());
          }
        }
      }
    });
    
    busStops.forEach((name) -> {
      System.out.println(name);
    });
    
    idHouses.forEach((id, str) -> {
      if (streetHouses.keySet().contains(str)) {
        streetHouses.get(str).housesUp();
      } else {
        System.out.println(id);
      }
    });

    streetHouses.forEach((nameStr, obj) -> {
      System.out.println(nameStr + " - " + obj.toString());
    });
  }

  private void unmarshal(String name) throws JAXBException {
    JAXBContext jxc = JAXBContext.newInstance("javaapplication7.generated");
    Unmarshaller u = jxc.createUnmarshaller();
    osm = (Osm) JAXBIntrospector.getValue(u.unmarshal(new File(name)));
  }

}
