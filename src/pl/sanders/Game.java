package pl.sanders;

import javax.swing.*;

public abstract class Game extends JFrame {
    protected Manager manager;

    public Game(int width ,int height ,int bombs){
        manager = new Manager(width, height, bombs);
    }

    public abstract void analysis(int i, int j);

}
