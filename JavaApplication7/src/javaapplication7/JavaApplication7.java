package javaapplication7;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.w3c.dom.Document;
import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Source;
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
  public static void main(String[] args) throws SAXException {
    Document doc = readXmlToDOMDocument("clouds.svg");
    processDocument(doc);
  }
  
  JavaApplication7(String fileName) throws SAXException {
    
  }
  
  static Document readXmlToDOMDocument(String fileName) throws SAXException {
    DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
    //  используется шаблон проектирования "Абстрактная фабрика"
    //  DocumentBuilderFactory.newInstance() порождает объект класса-наследникв
    //  от абстрактного класса DocumentBuilderFactory
    //  Объект какого типа будет порожден, зависит от конфигурации JDK
    //  Сейчас используется парсер Xerces и класс
    //     com.sun.org.apache.xerces.internal.jaxp.DocumentBuilderFactoryImpl
    System.out.println("DocumentBuilderFactory dbf = " + dbf);

    dbf.setNamespaceAware(false); // не поддерживать пространства имен
    // dbf.setValidating(true) - DTD проверка
    //dbf.setCoalescing(true) - склеивать CDATA с ближайшим текстовым блоком
    // dbf.setSchema( );// XML-схема или альтернативные вариантыи
    dbf.setIgnoringElementContentWhitespace(true);
    
    DocumentBuilder db = null;
    try {
      db = dbf.newDocumentBuilder();
    } catch (ParserConfigurationException ex) {
      Logger.getLogger(JavaApplication7.class.getName()).log(Level.SEVERE, null, ex);
      return null;
    }
    try {
      // собственно читаем документ - ВЕСЬ СРАЗУ В ПАМЯТЬ!
      return db.parse(fileName);
    } catch (IOException ex) {
      Logger.getLogger(JavaApplication7.class.getName()).log(Level.SEVERE, null, ex);
      return null;
    }
  }
  
  private static void processDocument(Document doc) {
    Node n = doc.getDocumentElement();
    lookAttrs(n);
    lookChildren(n, 0);
  }

  private static void lookChildren(Node n, int level) {
    NodeList nl = n.getChildNodes();
    String space = "                                         ".substring(0, level);
    int num = nl.getLength();
    Node curNode;
    for (int i = 0; i < num; i++) {

      curNode = nl.item(i);

      // curNode.getNodeValue() -- значение, существует для типов
      // ATTRIBUTE, COMMENT, CDATA, TEXT
      // curNode.getNodeName() -- имя тега для ELEMENT
      // "#text" - для TEXT, "#cdata-section", "#comment","#document"
      // "#document-fragment"
      //System.out.printf("%s---%s\n",curNode.getNodeName(),curNode.getNodeValue());
      // в зависимости от типа элемента...
      if (curNode.getNodeType() == Node.ELEMENT_NODE) {
        
          //((Element) curNode).setAttribute("a", "777"); // заменяем атрибут
          System.out.printf(space + "ELEMENT: <%s>\n", curNode.getNodeName());
          // читаем атрибуты
          lookAttrs(curNode);
          // рекурсивно просматриемм вложенные узлы
          lookChildren(curNode, level + 1);
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
  
  
  
  
}
