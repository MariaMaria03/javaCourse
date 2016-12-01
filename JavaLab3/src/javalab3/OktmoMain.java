package javalab3;

public class OktmoMain {

  /**
   * @param args the command line arguments
   */
  public static void main(String[] args) {
    OktmoData dataOktmo = new OktmoData();
//    OktmoData dataGroupOktmo = new OktmoData();
    //OktmoReader.readPlacesViaSplit("Tom1-CFO.txt", dataOktmo);
    OktmoReader.readPlaces("Tom5-PFO.txt", dataOktmo);
    OktmoReader.readPlacesGroup("Tom5-PFO.txt", dataOktmo);
    //OktmoReader.readPlacesGroupRegular("Tom1-CFO.txt", dataOktmo);
    OktmoAnalyzer analyserOktmo = new OktmoAnalyzer(dataOktmo);
    //long timeout = System.currentTimeMillis();
    analyserOktmo.countPlaceByRegion("Населенные пункты, входящие в состав муниципальных образований Ленинградской области");
    //analyserOktmo.countPlaceByRegionShort("Населенные пункты, входящие в состав муниципальных образований Республики Коми");
    //timeout = System.currentTimeMillis() - timeout;
    //System.out.println("first method: " + timeout);
    analyserOktmo.findMostPopularPlaceName("Населенные пункты, входящие в состав муниципальных образований Ленинградской области");

    //dataOktmo.printAll();
    //dataGroupOktmo.printAllGroup();
    //dataOktmo.printStatuses();
    //OktmoAnalyzer.printSortedPlaces(dataOktmo);
    //OktmoAnalyzer.filterName(dataOktmo);
    //OktmoAnalyzer.filterWithSameLetter(dataOktmo);

  }
  
}
