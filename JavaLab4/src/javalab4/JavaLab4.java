package javalab4;

public class JavaLab4 {

  /**
   * @param args the command line arguments
   */
  public static void main(String[] args) {
    
    BusinessCenter bC = new BusinessCenter();
    Visitor vis1 = new Visitor(bC);
    Visitor vis2 = new Visitor(bC);
    Visitor vis3 = new Visitor(bC);
    Visitor vis4 = new Visitor(bC);
    Thread th1 = new Thread(vis1);
    Thread th2 = new Thread(vis2);
    Thread th3 = new Thread(vis3);
    Thread th4 = new Thread(vis4);
    th1.start();
    th2.start();
    th3.start();
    th4.start();
    //SimpleRunnable r = new SimpleRunnable();
    //r.max = 100;
  }
  
}
