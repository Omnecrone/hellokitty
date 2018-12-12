package com.cardgame.graphics;


import javax.swing.*;
import java.awt.*;


public class DrawStartMenu extends JPanel {

    @Override
    public void paintComponent(Graphics g){
        g.drawImage(new ImageIcon("images/test.jpg").getImage(), 0,0,this);
    }




}
