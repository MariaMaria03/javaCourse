package javalab3;

public class OktmoMain {

  /**
   * @param args the command line arguments
   */
  public static void main(String[] args) {
    OktmoData dataOktmo = new OktmoData();
//    OktmoData dataGroupOktmo = new OktmoData();
    //OktmoReader.readPlacesViaSplit("Tom1-CFO.txt", dataOktmo);
    OktmoReader.readPlaces("Tom1-CFO.txt", dataOktmo);
    OktmoReader.readPlacesGroup("Tom1-CFO.txt", dataOktmo);
    //OktmoReader.readPlacesGroupRegular("Tom1-CFO.txt", dataOktmo);
    OktmoAnalyzer analyserOktmo = new OktmoAnalyzer(dataOktmo);
    //analyserOktmo.countPlaceByRegion("Населенные пункты, входящие в состав муниципальных образований Республики Коми");
    analyserOktmo.findMostPopularPlaceName("Населенные пункты, входящие в состав муниципальных образований Республики Коми");

    //dataOktmo.printAll();
    //dataGroupOktmo.printAllGroup();
    //dataOktmo.printStatuses();
    //OktmoAnalyzer.printSortedPlaces(dataOktmo);
    //OktmoAnalyzer.filterName(dataOktmo);
    //OktmoAnalyzer.filterWithSameLetter(dataOktmo);

  }
  
}
