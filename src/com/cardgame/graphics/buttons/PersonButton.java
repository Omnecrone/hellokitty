package com.cardgame.graphics.buttons;

import com.cardgame.Battle;
import com.cardgame.CardPerson;
import com.cardgame.graphics.DrawGame;
import com.cardgame.graphics.Gui;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PersonButton extends JButton implements ActionListener {
    private CardPerson person;

    public PersonButton(CardPerson person) {
        super(person.getName());
        this.person = person;
        this.addActionListener(this);
        this.setVisible(false);
    }

    public void actionPerformed(ActionEvent e) {
            DrawGame.targetButtonsVisible(false);
            Battle.setDefender(person);
            Battle.flag = true;
            Gui.reset();

        }

}
