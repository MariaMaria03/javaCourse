package javaapplication7;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;

public class XmlHandler implements ContentHandler {
  private boolean isBusStop = false;
  private boolean isWay = false;
  private boolean isHighway = false;
  public List<String> names = new ArrayList(10);
  public List<String> busStops = new ArrayList();
  public Map<String,StreetData> streetHouses = new HashMap<>();
  public Map<String, String> idHouses = new HashMap<>();
  String curIdWay = "";

  public XmlHandler() {
  }

  Locator loc = null;

  public void setDocumentLocator(Locator locator) {
    System.out.println("Set document locator=" + locator);
    loc = locator;
  }

  public void startDocument() throws SAXException {
    System.out.println("[Start document");
  }

  public void endDocument() throws SAXException {
    System.out.println("]End document");
    busStops.forEach((name) -> {
      System.out.println(name);
    });
    
    idHouses.forEach((id, str) -> {
      if (streetHouses.keySet().contains(str)) {
        streetHouses.get(str).housesUp();
      }
      else {
        System.out.println(id);
      }
    });
    
    streetHouses.forEach((nameStr, obj) -> {
      System.out.println(nameStr + " - " + obj.toString());
    });
    
    
  }

  public void startElement(String uri, String localName, String qName, Attributes atts)
      throws SAXException {
    String value = atts.getValue("v");
    String key = atts.getValue("k");
    if (qName.equals("tag")) {
      if (isBusStop && "name".equals(key)) {
        busStops.add(value);
        isBusStop = false;
      }
      else if ("highway".equals(key) && isWay) {
        isHighway = true;
      }
      else if (isHighway && isWay && "name".equals(key)) {
        StreetData strH = streetHouses.get(value);
        if (strH == null) {
          strH = new StreetData(0, 0);
          streetHouses.put(value, strH);
        }
        strH.piecesUp();
      }
      else if (isWay && "addr:street".equals(key)) {
        idHouses.put(curIdWay, value);
      }

      if ("bus_stop".equals(value)) {
        isBusStop = true;
      }
    }
    else if (qName.equals("way")) {
      curIdWay = atts.getValue("id");
      isWay = true;
    }
    
  }

  public void endElement(String uri, String localName, String qName) throws SAXException {
    if (qName.equals("way")) {
      isWay = false;
      isHighway = false;
    }
  }

  public void characters(char[] ch, int start, int length) throws SAXException {
//    if (chars.length() > 0) {
//      System.out.printf("[L%d],Characters: ***%s***\n", loc.getLineNumber(), chars);
//    } else {
//      System.out.println("Characters: empty");
//    }
  }

  public void ignorableWhitespace(char[] ch, int start, int length) throws SAXException {
    //System.out.println("ignorable whitespaces");
  }

  public void processingInstruction(String target, String data) throws SAXException {
    throw new UnsupportedOperationException("Not supported yet.");
  }

  public void skippedEntity(String name) throws SAXException {
    throw new UnsupportedOperationException("Not supported yet.");
  }

  public void startPrefixMapping(String prefix, String uri) throws SAXException {
    throw new UnsupportedOperationException("Not supported yet.");
  }

  public void endPrefixMapping(String prefix) throws SAXException {
    throw new UnsupportedOperationException("Not supported yet.");
  }
}
