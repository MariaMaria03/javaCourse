import javalab3.OktmoData;
import javalab3.OktmoReader;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class TestJavaLab3 {
  
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

//  @Test
//  public void testOnEquelsData() {
//    OktmoData dataOktmoRegular = new OktmoData();
//    OktmoReader.readPlaces("Tom1-CFO.txt", dataOktmoRegular);
//    OktmoReader.readPlacesViaSplit("Tom1-CFO.txt", dataOktmo);
//    assertTrue(dataOktmo.placeList.equals(dataOktmoRegular.placeList));
//  }
}
