package javalab2;

public class OktmoMain {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        OktmoData dataOktmo = new OktmoData();
        //OktmoReader.readPlacesViaSplit("Tom1-CFO.txt", dataOktmo);
        OktmoReader.readPlaces("Tom1-CFO.txt", dataOktmo);
        
        //dataOktmo.printAll();
        dataOktmo.printStatuses();
        OktmoAnalyzer.printSortedPlaces(dataOktmo);
        OktmoAnalyzer.filterName(dataOktmo);
        OktmoAnalyzer.filterWithSameLetter(dataOktmo);
        
    }
    
}
