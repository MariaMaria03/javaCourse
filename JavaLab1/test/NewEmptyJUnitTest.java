
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
    
    // Проверка веса при складывании коробки в мешок +
    //      исключение на то что в коробку больше класть нельзя
    @Test(expected = ItemsException.class)
    public void checkAddInBag()  throws ItemsException, AlreadyPlacedException {
        bagNew.addItem(book);
        bagNew.addItem(mouse);
        //bagNew.addItem(card);
        newBox.addItem(card, 1);
        // кладем в мешок коробку
        bagNew.addItem(newBox);
        assertEquals(1530, bagNew.currentWeight);
        newBox.addItem(folder, 1);
    }
     
    // исключение - переполнение веса
    @Test(expected = ItemsException.class)
    public void checkWeight() throws ItemsException, AlreadyPlacedException{
        bagNew.addItem(hoover);
    }
    
    // проверяем вес + исключение на добавление не плоского предмета в коробку
    @Test(expected = ItemsException.class)
    public void checkAddInBox()  throws ItemsException, AlreadyPlacedException {
        newBox.addItem(book3, 2);
        assertEquals(700, newBox.currentWeight);
        newBox.addItem(ball2, 2);
    }
    
    @Test(expected = AlreadyPlacedException.class)
    public void againAddItem() throws ItemsException, AlreadyPlacedException {
        // тест на то повторное добавление того же предмета
        newBox.addItem(book3, 2);
        newBox.addItem(book3, 1); 
    }
    
    // Достаем предмет из стопки +  Кладем в стопку не плоский предмет - исключение
    @Test(expected = ItemsException.class)
    public void checkAddInPile() throws ItemsException, AlreadyPlacedException {
        pile.addItem(book); // вес стопки 300, книги 300
        pile.addItem(card); // вес открытки 30
        assertEquals(pile.pullItem(), card);
        assertEquals(600, pile.currentWeight);
        pile.addItem(bagNew); 
    }
       
    // Исключение - кладем предмет сам в себя
    @Test(expected = ItemsException.class)
    public void selfItem() throws ItemsException, AlreadyPlacedException {
        pile.addItem(pile);
    }
    
}
