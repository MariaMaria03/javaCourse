
import java.util.Arrays;
import java.util.HashSet;
import javalab1.Bag;
import javalab1.Box;
import javalab1.Item;
import javalab1.OverFlowException;
import javalab1.AlreadyPlacedException;
import javalab1.Pile;
import org.junit.Test;
import static org.junit.Assert.*;


public class NewEmptyJUnitTest {
    @Test(expected = OverFlowException.class)
        public void test() throws OverFlowException, AlreadyPlacedException {
            //try {
                Item book = new Item("книга", 300, "плоский", "зеленая");
                assertEquals("книга", book.getName());
                
                Item mouse = new Item("мышь", 600, "черная", "беспроводная");
                Item hoover = new Item("пылесос", 4000, "синий", "lg");
                Item book3 = new Item("книжка3", 500, "плоский", "оранжевая");
                Item ball2 = new Item("мяч 2", 500, "круглый", "оранжевый");
                Item card = new Item("открытка", 30, "плоский", "разноцветная");
                
                Bag bagNew = new Bag("мешок", 400, "синий");
                Box newBox = new Box("коробка", 200, "картонная");
                
                bagNew.addItem(book);
                bagNew.addItem(mouse);
                bagNew.addItem(hoover);
                newBox.addItem(book3, 2);
                newBox.addItem(ball2, 2);
                
                Item iron = new Item("утюг", 19000, "белый", "lg");
                bagNew.addItem(iron);
                
                Pile pile = new Pile("стопка", 300, "небольшая");
                pile.addItem(book);
                pile.addItem(bagNew);
                pile.addItem(card);

                assertEquals(5300,bagNew.currentWeight);
                assertEquals(700,newBox.currentWeight);
                assertEquals(pile.pullItem(), card);
                
                
                
//            }
//            catch (OverFlowException e) {
//                System.err.println(e);
//                String message = "Мешок не выдержит, вес превышает допустимую норму,предмет утюг не попадёт в мешок";
//                assertEquals(message, e.getMessage());
//                
//            }
        }

}
