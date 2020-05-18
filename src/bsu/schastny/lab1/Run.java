package bsu.schastny.lab1;

import bsu.schastny.lab1.menu.Menu;

public class Run {
    public static void main(String[] args){
        try {
            Menu menu = new Menu();
            menu.display();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}
