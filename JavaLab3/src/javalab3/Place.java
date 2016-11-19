package javalab3;

public class Place {
  long code;
  public String name;
  String status;
  
  Place(long c, String n, String st) {
    name = n;
    code = c;
    status = st;
  } 
  
  @Override
  public String toString() {
    return status + " " + name +  " " +code;
  }
  
  @Override
  public boolean equals(Object obj) {
    return (this == obj);
  }
}
