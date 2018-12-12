package com.cardgame.graphics;

import javax.swing.*;

public class GameFrame extends JFrame {
    public GameFrame(){
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setExtendedState(MAXIMIZED_BOTH);
        this.setUndecorated(true);
        this.setVisible(true);
    }

}
