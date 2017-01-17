package javalab4;

import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Visitor implements Runnable {
  private BusinessCenter control;
  private BusinessCenter lift;
  private int num = 0;
  private int floor;
  private static int totalCount;
  
  public Visitor(BusinessCenter c, BusinessCenter l) {
    num = totalCount + 1;
    totalCount++;
    Random random = new Random();
    floor = random.nextInt(10) + 2;
    control = c;
    lift = l;
  }
  
  public int getNum() {
   return num;
}
  
  public void run() {
    System.out.println(lift.getTime() + "Посетитель " + num + " пришёл");
    passControl();
    goUp();
    doSomeWork();
    goDown();
  }
  
  public void goUp() {
    goLift(floor, 1);
  }
  
  public void goDown() {
    goLift(1, floor);
  }
  
  public void doSomeWork() {
    System.out.println(lift.getTime() + "Посетитель " + num + " делает дела");
    try {
      Thread.sleep(2000);
      System.out.println(lift.getTime() + "Посетитель " + num + " закончил дела");
    } catch (InterruptedException ex) {
      Logger.getLogger(Visitor.class.getName()).log(Level.SEVERE, null, ex);
    }
  }
  
  public void goLift(int f, int curF) {
     if (lift.enterLift(this)) { 
       System.out.println(lift.getTime() + "Посетитель " + num + " вызывает лифт ");
       lift.moveLift(curF, true);
       System.out.println(lift.getTime() + "Посетитель " + num + " вошел в лифт, едет на " + f + " этаж");
       lift.moveLift(f, false);
       lift.exitFromLift(this);
     }
     else {
       System.out.println(lift.getTime() + "Посетитель " + num + " идет пешком на " + f + " этаж");
       try {
         Thread.sleep(f * 100 + 500);
       } catch (InterruptedException ex) {
         Logger.getLogger(Visitor.class.getName()).log(Level.SEVERE, null, ex);
       }
     }
    
    
  }
  
  public void passControl() {
    control.enterControl(this);
    try {
      System.out.println(lift.getTime() + "Посетитель " + num + " показывает документы");
      Thread.sleep(500);
    } catch (InterruptedException ex) {
      Logger.getLogger(Visitor.class.getName()).log(Level.SEVERE, null, ex);
    }
    control.exitFromControl(this);
  } 
}


