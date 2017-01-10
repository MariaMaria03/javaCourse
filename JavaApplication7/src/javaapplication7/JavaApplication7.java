package javaapplication7;

import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.w3c.dom.Document;
import javax.xml.XMLConstants;
import javax.xml.bind.JAXBException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class JavaApplication7 {
  //Document doc;

  /**
   * @param args the command line arguments
   */
  public static void main(String[] args) throws SAXException, IOException, JAXBException {
    //Document doc = readXmlToDOMDocument("clouds.svg");
    //processDocument(doc);
    //saveDemo(doc);
    new SaxProcess().process("UfaCenterSmall.xml", "osm.xsd");
    //new JaxbLab("UfaCenterSmall.xml");
  }
  
  JavaApplication7(String fileName) throws SAXException {
    
  }
  
  static Document readXmlToDOMDocument(String fileName) throws SAXException {
    DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
    //  используется шаблон проектирования "Абстрактная фабрика"
    //  DocumentBuilderFactory.newInstance() порождает объект класса-наследникв
    //  от абстрактного класса DocumentBuilderFactory
    System.out.println("DocumentBuilderFactory dbf = " + dbf);

    dbf.setNamespaceAware(false); // не поддерживать пространства имен
    dbf.setIgnoringElementContentWhitespace(true);
    
    DocumentBuilder db = null;
    try {
      db = dbf.newDocumentBuilder();
    } catch (ParserConfigurationException ex) {
      Logger.getLogger(JavaApplication7.class.getName()).log(Level.SEVERE, null, ex);
      return null;
    }
    try {
      return db.parse(fileName);
    } catch (IOException ex) {
      Logger.getLogger(JavaApplication7.class.getName()).log(Level.SEVERE, null, ex);
      return null;
    }
  }
  
  private static void processDocument(Document doc) {
    Node n = doc.getDocumentElement();
    lookAttrs(n);
    lookChildren(n, 0, doc);
  }

  private static void lookChildren(Node n, int level, Document doc) {
    NodeList nl = n.getChildNodes();
    String space = "                                         ".substring(0, level);
    int num = nl.getLength();
    Node curNode;
    for (int i = 0; i < num; i++) {
      curNode = nl.item(i);
      if (curNode.getNodeType() == Node.ELEMENT_NODE) {
        String curNodeName = curNode.getNodeName();
          switch (curNodeName) {
              case "rect":
                System.out.printf(space + "ELEMENT: <%s>\n", curNodeName);
                lookAttrs(curNode);
                ((Element) curNode).setAttribute("style", "fill: green");
                break;
              case "circle":
                System.out.printf(space + "ELEMENT: <%s>\n", curNodeName);
                lookAttrs(curNode);
                Element newCircle = doc.createElement("circle");
                newCircle.setAttribute("style", "fill: red");
                curNode.getParentNode().insertBefore(newCircle, curNode);
                i++;
                break;
              case "path":
                ((Element) curNode).setAttribute("style", "fill: silver");
                break;
          }
          lookChildren(curNode, level + 1, doc);
      }
    }
  }

  private static void lookAttrs(Node n) {
    NamedNodeMap attrs = n.getAttributes();
    if (attrs == null) {
      return;
    }

    int num = attrs.getLength();
    if (num == 0) {
      return;
    }

    Node curNode;
    System.out.print("[");
    for (int i = 0; i < num; i++) {
      curNode = attrs.item(i);
      System.out.printf("%s=%s ", curNode.getNodeName(), curNode.getNodeValue());
    }
    System.out.printf("]\n");
  }
  
  private static void saveDemo(Document doc) throws IOException {
    Result sr = new StreamResult(new FileWriter("figuresNew.xml"));
    Result sr2 = new StreamResult(System.out);
    DOMSource domSource = new DOMSource(doc);

    Transformer tr;
    try {
      tr = TransformerFactory.newInstance().newTransformer();

      // настройки "преобразования"
      tr.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
      tr.setOutputProperty(OutputKeys.INDENT, "yes");

      tr.transform(domSource, sr); // в файл
      tr.transform(domSource, sr2); // на экран
    }
    catch (TransformerException ex) {
      Logger.getLogger(JavaApplication7.class.getName()).log(Level.SEVERE, null, ex);
    }
  }
  
  
}
