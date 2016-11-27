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
    floor = random.nextInt(10) + 2;
    place = pl;
  }
  
  public int getNum() {
   return num;
}
  
  
  public void run() {
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
    System.out.println("Посетитель " + num + " делает дела");
    try {
      Thread.sleep(5 * 100 + 1000);
      System.out.println("Посетитель " + num + " закончил дела");
    } catch (InterruptedException ex) {
      Logger.getLogger(Visitor.class.getName()).log(Level.SEVERE, null, ex);
    }
  }
  
  public void goLift(int f, int curF) {
    place.enterLift(this);
    System.out.println("Посетитель вызывает лифт на этаж " + f);
    place.moveLift(curF);
    System.out.println("Посетитель " + num + " вошел в лифт, едет на " + f + " этаж");
    place.moveLift(f);
      //Thread.sleep(floor * 100 + 200);
      place.exitFromLift(this);
      System.out.println("Посетитель " + num + " вышел из лифта");
      synchronized (place) {
        place.notify();
      }
  }
}


