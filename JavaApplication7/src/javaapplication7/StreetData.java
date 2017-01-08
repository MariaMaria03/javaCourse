package javaapplication7;

public class StreetData {
  int countHouse, countPiece;

  public StreetData(int cH, int cP) {
    countHouse = cH;
    countPiece = cP;
  }
  
  public void housesUp() {countHouse++;}
  
  public void piecesUp() {countPiece++;}
  
  @Override
  public String toString() {
    return "Количество домов: " + countHouse + " ; Количество кусков: " + countPiece; 
  }
  
}
