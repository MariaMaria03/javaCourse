package javalab4;

import java.util.logging.Level;
import java.util.logging.Logger;

public class BusinessCenter {
  private boolean liftFree, controlFree;
  private int liftFloor;
  
  public BusinessCenter() {
    liftFree = true;
    controlFree = true;
    liftFloor = 1;
  }
  
  public void enterLift(Visitor v) {
    synchronized(this) {
      while (!liftFree) {
        try {
          System.out.println("Посетитель" + v.getNum() + "ждет");
          this.wait();
        }
        catch(InterruptedException ex) { 
          
        }
      }
      liftFree = false;
    }
    
  }
  
  public void exitFromLift(Visitor v) {
    liftFree = true;
    
  }
  
  public void moveLift(int targetFloor) {
    try {
      System.out.println("Лифт едет на " + targetFloor + " этаж");
      Thread.sleep(Math.abs(targetFloor - liftFloor) * 100 + 1000);
      liftFloor = targetFloor;
    } catch (InterruptedException ex) {
      Logger.getLogger(Visitor.class.getName()).log(Level.SEVERE, null, ex);
    }
  }
  
  public void enterControl(Visitor v) {

  }
  
  public void exitFromControl(Visitor v) {
    
  }

}
