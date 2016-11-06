package javalab1;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;

public class JavaLab1 {
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        Item newItem = new Item("подарок", 300, "круглый", "fswdfs");
        Item computer = new Item("компьютер", 1600, "серый", "hd");
        Item mouse = new Item("мышь", 600, "черная", "беспроводная");
        
        try {
            System.out.println(newItem.getInfo());
            System.out.println(computer.getInfo());
            Bag bagNew = new Bag("мешок", 400, "синий");
            System.out.println(bagNew.getInfo());
            bagNew.addItem(newItem);
            bagNew.addItem(mouse);
            Item hoover = new Item("пылесос", 6000, "синий", "lg");
            Item book = new Item("книга", 300, "плоский", "зеленая");
            bagNew.addItem(hoover);
            bagNew.addItem(book);
            Item iron = new Item("утюг", 1200, "белый", "lg");
            bagNew.addItem(iron);
            bagNew.showAll();
            Item headphones = new Item("наушники", 400, "черные", "philips");
            bagNew.addItem(headphones);
            Item phone = new Item("телефон", 300, "серебристый", "sony");
            bagNew.addItem(phone);
            bagNew.pullItem();
            bagNew.showAll();

            Pile pile = new Pile("стопка", 300, "небольшая");

            Item ball = new Item("шар", 100, "круглый", "желтый");
            pile.showAll();
            //pile.addItem(book);
            pile.addItem(ball);
            pile.showAll();
            Item folder = new Item("папка", 300, "плоский", "синяя");
            //pile.addItem(folder);
            Item card = new Item("открытка", 30, "плоский", "разноцветная");
            pile.addItem(card);
            pile.showAll();
            Item book2 = new Item("книжка", 500, "плоский", "красная");
            Item book3 = new Item("книжка3", 500, "плоский", "оранжевая");
            Item ball2 = new Item("мяч 2", 500, "круглый", "оранжевый");
            Item dictionary = new Item("словарь", 1000, "плоский", "серый");
            //pile.addItem(book2);
            pile.pullItem();
            pile.showAll();

            Item toy = new Item("игрушка", 300, "белая");
            bagNew.addItem(toy);
            bagNew.showAll();

            Box newBox = new Box("коробка", 200, "картонная");
            newBox.addItem(book2, 1);
            newBox.addItem(card, 1);
            newBox.addItem(folder, 1);
            //newBox.addItem(book3, 1);
            //newBox.addItem(book, 2);
            newBox.addItem(dictionary, 2);
            newBox.addItem(ball2, 2);
            newBox.showAll();
            newBox.pullItem(1);
            newBox.showAll();
            newBox.pullItem(2);
            newBox.addItem(book3, 1);
            newBox.showAll();
            System.out.println(newBox.currentWeight);
            System.out.println(dictionary.getInContainer());
            
        }
        catch (OverFlowException | AlreadyPlacedException e) {
            System.out.println(e);
        }
    }   
}

