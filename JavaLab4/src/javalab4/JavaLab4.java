package javalab4;

public class JavaLab4 {

  /**
   * @param args the command line arguments
   */
  public static void main(String[] args) {
    
    BusinessCenter control = new BusinessCenter();
    BusinessCenter lift = new BusinessCenter();
    Visitor vis1 = new Visitor(control, lift);
    Visitor vis2 = new Visitor(control, lift);
    Visitor vis3 = new Visitor(control, lift);
    Visitor vis4 = new Visitor(control, lift);
    Visitor vis5 = new Visitor(control, lift);
    Thread th1 = new Thread(vis1);
    Thread th2 = new Thread(vis2);
    Thread th3 = new Thread(vis3);
    Thread th4 = new Thread(vis4);
    Thread th5 = new Thread(vis5);
    th1.start();
    th2.start();
    th3.start();
    th4.start();
    th5.start();
  }
  
}
