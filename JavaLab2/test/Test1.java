
import javalab2.OktmoData;
import javalab2.OktmoReader;
import javalab2.Place;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;

public class Test1 {
  OktmoData dataOktmo;
  
  @Before
  public void initialize() {
    dataOktmo = new OktmoData();
  }

  @Test
  public void test5() {
    OktmoReader.readPlacesViaSplit("Tom1-CFO.txt", dataOktmo);
    assertEquals("п Водников 86604101146", dataOktmo.placeList.get(9).toString());
    assertEquals("г Нарьян-Мар 11851000001", dataOktmo.placeList.get(dataOktmo.placeList.size() - 1).toString());
  }
  
  @Test
  public void testWithRegular() {
    OktmoReader.readPlaces("Tom1-CFO.txt", dataOktmo);
    assertEquals("п Водников 86604101146", dataOktmo.placeList.get(9).toString());
    assertEquals("г Нарьян-Мар 11851000001", dataOktmo.placeList.get(dataOktmo.placeList.size() - 1).toString());
  }
  
  
  // Тест на проверку одинаковости данных 
  @Test
  public void testOnEquelsData() {
    OktmoData dataOktmoRegular = new OktmoData();
    OktmoReader.readPlaces("Tom1-CFO.txt", dataOktmoRegular);
    OktmoReader.readPlacesViaSplit("Tom1-CFO.txt", dataOktmo);
    assertArrayEquals(dataOktmo.placeList.toArray(),dataOktmoRegular.placeList.toArray());
   
  }
}
