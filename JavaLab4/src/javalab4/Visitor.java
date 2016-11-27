package javalab4;

import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Visitor implements Runnable {
  private BusinessCenter place;
  private int num = 0;
  private int floor;
  private static int totalCount;

  
  public Visitor(BusinessCenter pl) {
    num = totalCount + 1;
    totalCount += 1;
    Random random = new Random();
    floor = random.nextInt(10);
    place = pl;
  }
  
  public int getNum() {
   return num;
}
  
  
  public void run() {
    goUp();
    goDown();
    doSomeWork();
    goUp();
    goDown();
  }
  
  public void goUp() {
    place.enterLift(this);
    System.out.println("Посетитель " + num + " вошел в лифт, едет на " + floor + " этаж");
    try {
      Thread.sleep(floor * 100 + 200);
    } catch (InterruptedException ex) {
      Logger.getLogger(Visitor.class.getName()).log(Level.SEVERE, null, ex);
    }
    
  }
  
  public void goDown() {
    place.exitFromLift(this);
    System.out.println("Посетитель " + num +  " вышел из лифта");
    synchronized(place) {
      place.notify();
    }
  }
  
  public void doSomeWork() {
    System.out.println("Посетитель " + num + " делает дела");
    try {
      Thread.sleep(floor * 100 + 1000);
    } catch (InterruptedException ex) {
      Logger.getLogger(Visitor.class.getName()).log(Level.SEVERE, null, ex);
    }
    System.out.println("Посетитель " + num + " закончил дела");
  }
}
