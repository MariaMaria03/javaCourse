package javalab3;


public class OktmoGroup {
  OktmoLevel level;
  enum OktmoLevel {REGION, RAYON }
  String name;
  long code;
  
  OktmoGroup(String n, long c, int lev) {
    name = n;
    code = c;
    switch (lev) {
      case 0:
        level = OktmoLevel.REGION;
        break;
      case 1:
        level = OktmoLevel.RAYON;
        break;
    }
  }
  
  @Override
  public String toString() {
    return level + ", " + name;
  }
}
