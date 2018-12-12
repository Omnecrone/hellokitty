package com.cardgame.graphics.buttons;

import com.cardgame.Battle;
import com.cardgame.graphics.DrawGame;
import com.cardgame.graphics.Gui;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StatButton extends JButton implements ActionListener {
    int n;
    public StatButton(int n) {
        super();
        setIcon(new ImageIcon("images/stats/"+n+".png"));
        this.addActionListener(this);
        this.n = n;
        }

    public void actionPerformed(ActionEvent e) {
        //Gui.textAppend("Test!!");
        Battle.setAttackStat(n);
        DrawGame.statButtonsVisible(false);
        Battle.flag = true;
        DrawGame.setAnimation(true);
        Gui.reset();

    }

    }



