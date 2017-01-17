package javalab4;

import java.util.logging.Level;
import java.util.logging.Logger;

public class BusinessCenter {
  private boolean liftFree, controlFree;
  private int liftFloor;
  public static long startTime;
  
  public BusinessCenter() {
    liftFree = true;
    controlFree = true;
    liftFloor = 1;
    startTime = System.currentTimeMillis();
  }
  
  public boolean enterLift(Visitor v) {
    long time = System.currentTimeMillis();
    long rest = 1000;
    synchronized(this) {
      while (!liftFree) {
        System.out.println(getTime() + "Посетитель " + v.getNum() + " ждет лифта");
        try {
          this.wait(rest);
          if ((System.currentTimeMillis() - time) >= 1000) {
            return false;
          }
          rest = rest - (System.currentTimeMillis() - time);
        }
        catch(InterruptedException ex) { 
          System.out.println("err");
        }
      }
      liftFree = false;
      return true;
    }
    
  }
  
  public void exitFromLift(Visitor v) {
    synchronized (this) {
      liftFree = true;
      System.out.println(getTime() + "Посетитель " + v.getNum() + " вышел из лифта");
      this.notify();
    }
  }
  
  public void moveLift(int targetFloor, boolean isEmpty) {
    try {
      if (isEmpty) {
        System.out.println(getTime() + "Пустой лифт едет на " + targetFloor + " этаж");
      }
      else {
        System.out.println(getTime() + "Лифт с посетителем едет на " + targetFloor + " этаж");
      }
      Thread.sleep(Math.abs(targetFloor - liftFloor) * 100 + 400);
      liftFloor = targetFloor;
    } catch (InterruptedException ex) {
      Logger.getLogger(Visitor.class.getName()).log(Level.SEVERE, null, ex);
    }
  }
  
  public void enterControl(Visitor v) {
    synchronized (this) {
      while (!controlFree) {
        try {
          this.wait();
        } catch (InterruptedException ex) {
            System.out.println("err");
        }
      }
      controlFree = false;
    }
  }
  
  public void exitFromControl(Visitor v) {
    synchronized (this) {
      controlFree = true;
      System.out.println(getTime() + "Посетитель " + v.getNum() + " показал документы");
      this.notify();
    }
  }
  
  public String getTime() {
    return (System.currentTimeMillis() - startTime) + "ms: ";
  }

}
