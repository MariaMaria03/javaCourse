package javaapplication7;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
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
    for (Object o : list) {
      if (o instanceof Way) {
        ways.add((Way) o);
      }
    }
    System.out.println(ways.size());
    
  }

  private void unmarshal(String name) throws JAXBException {
    JAXBContext jxc = JAXBContext.newInstance("javaapplication7.generated");
    Unmarshaller u = jxc.createUnmarshaller();
    osm = (Osm) JAXBIntrospector.getValue(u.unmarshal(new File(name)));
  }

}
