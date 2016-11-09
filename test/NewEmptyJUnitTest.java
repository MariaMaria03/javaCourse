
import java.util.Arrays;
import java.util.HashSet;
import javalab1.Bag;
import javalab1.Box;
import javalab1.Item;
import javalab1.ItemsException;
import javalab1.AlreadyPlacedException;
import javalab1.Pile;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;

public class NewEmptyJUnitTest {
    Item book, mouse, hoover, book3, ball2, card, folder;
    Bag bagNew;
    Box newBox;
    Pile pile;
    boolean overflowWeightContainer, alreadyPlaced, notFlat, sameItem, parentItemPlaced;
    
    @Before public void initialize() {
        book = new Item("книга", 300, "плоский", "зеленая");
        mouse = new Item("мышь", 600, "черная", "беспроводная");
        hoover = new Item("пылесос", 11000, "синий", "lg");
        book3 = new Item("книжка3", 500, "плоский", "оранжевая");
        ball2 = new Item("мяч 2", 500, "круглый", "оранжевый");
        card = new Item("открытка", 30, "плоский", "разноцветная");
        folder = new Item("папка", 300, "плоский", "синяя");
        
        bagNew = new Bag("мешок", 400, "синий");
        newBox = new Box("коробка", 200, "картонная");
        pile = new Pile("стопка", 300, "небольшая", "плоский");
        overflowWeightContainer = false;
        alreadyPlaced = false;
        notFlat = false;
        sameItem = false;
        parentItemPlaced = false;
    }
    
    @Test
    public void itemProps(){
        assertEquals("книга", book.getName());
    }
    
    @Test
    public void checkAddInBag(){
        try {
            bagNew.addItem(book);
            bagNew.addItem(mouse);
            //bagNew.addItem(card);
            newBox.addItem(card, 1);
            // кладем в мешок коробку
            bagNew.addItem(newBox);
            assertEquals(1530, bagNew.currentWeight);
            newBox.addItem(folder, 1);
        }
        catch (ItemsException | AlreadyPlacedException e) {
            parentItemPlaced = true;
            System.out.println(e);
        }
        try {
            bagNew.addItem(hoover);
        }
        catch (ItemsException | AlreadyPlacedException e) {
            overflowWeightContainer = true;
            System.out.println(e);
        }
        
        assertTrue(parentItemPlaced);
        assertTrue(overflowWeightContainer);
    }
    
    @Test
    public void checkAddInBox(){
        try {
            newBox.addItem(book3, 2);
            newBox.addItem(ball2, 2);
            assertEquals(700, newBox.currentWeight);
        }
        catch (ItemsException | AlreadyPlacedException e) {
            notFlat = true;
            System.out.println(e);
        }
        
        try {
           // тест на то повторное добавление того же предмета
           newBox.addItem(book3, 1); 
        }
        catch(ItemsException | AlreadyPlacedException e) {
            alreadyPlaced = true;
            System.out.println(e);
        }
        
        assertTrue(notFlat);
        assertTrue(alreadyPlaced);
    }
    
    @Test
    public void checkAddInPile(){
        try {
            pile.addItem(book);
            pile.addItem(card);
            assertEquals(pile.pullItem(), card);
            pile.addItem(bagNew); 
        }
        catch (ItemsException | AlreadyPlacedException e) {
            notFlat = true;
            System.out.println(e);
        }
        
        try {
           pile.addItem(pile);
        }
        catch(ItemsException | AlreadyPlacedException e) {
            sameItem = true;
            System.out.println(e);
        }
        assertTrue(notFlat);
        assertTrue(sameItem);
       
    }
    
}
