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

  public XmlHandler() {
  }

  Locator loc = null;

  public void setDocumentLocator(Locator locator) {
    System.out.println("Set document locator=" + locator);
    loc = locator;
  }

  public void startDocument() throws SAXException {
    System.out.println("[[[Start document");
  }

  public void endDocument() throws SAXException {
    System.out.println("]]]End document");
    busStops.forEach((name) -> {
      System.out.println(name);
    });
    
  }

  public void startElement(String uri, String localName, String qName, Attributes atts)
      throws SAXException {
//     System.out.printf("[L%d]Start element <%s> uri=%s, localname=%s\n",
//        loc.getLineNumber(), qName, uri, localName);
    int countHouse = 0;
    int countRepeat = 0;
    int n = atts.getLength();

    if (qName.equals("tag")) {
      if (n > 0) {
        for (int i = 0; i < n; i++) {
          if (isBusStop && "name".equals(atts.getValue(i))) {
            busStops.add(atts.getValue(i + 1));
            isBusStop = false;
          }
          else if ("highway".equals(atts.getValue(i)) && isWay) {
            isHighway = true;
          }
          else if (isHighway && isWay) {
            System.out.println(atts.getValue(i));
          }
          
//          if ("k".equals(atts.getLocalName(i)) && isWay
//              && "name".equals(atts.getValue(i))) {
//            if (streetHouses.get(atts.getValue(i)) != null) {
//              countHouse = streetHouses.get(atts.getValue(i)).countHouse;
//              countRepeat = streetHouses.get(atts.getValue(i)).countPiece;
//            } else {
//              countHouse = 0;
//              countRepeat = 1;
//            }
//            streetHouses.put(atts.getValue(i), new StreetData(countHouse, countRepeat));
//            isWay = false;
//          } 
          
          if ("bus_stop".equals(atts.getValue(i))) {
            isBusStop = true;
            System.out.println(atts.getValue(i));
          }
        }
      }
    }
    else if (qName.equals("way")) {
      isWay = true;
    }
    
  }

  public void endElement(String uri, String localName, String qName) throws SAXException {
//    if (qName.equals("name")) {
//      busStop = false;
//    }
//    System.out.printf("[L%d] End element <%s>\n", loc.getLineNumber(), qName);
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
