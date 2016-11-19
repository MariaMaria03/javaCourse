package javalab2;

import java.util.Objects;

public class Place {
  long code;
  public String name;
  String status;
  
  Place(long c, String n, String st) {
    name = n;
    code = c;
    status = st;
  } 
  
  private long getCode() {
    return code;
  }
  private String getName() {
    return name;
  }
  
  @Override
  public String toString() {
    return status + " " + name +  " " + code;
  }
  
  @Override
  public boolean equals(Object obj) {
    if (obj == null) { return false; }
    if (obj == this) { return true; }
    if (getClass() != obj.getClass()) {
      return false;
    }
    
    return (this.hashCode() == obj.hashCode());
  }
  
  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 17;
    result = prime*result + (int)(this.getCode() - (this.getCode() >>> 32));
    result = prime * result + Objects.hashCode(this.name);
    result = prime * result + Objects.hashCode(this.status);
    return result;
  }
}
