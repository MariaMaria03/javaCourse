package javalab4;

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
  
  public int moveLift(int targetFloor) {
    return 0;
  }
  
  public void enterControl(Visitor v) {

  }
  
  public void exitFromControl(Visitor v) {
    
  }

}
