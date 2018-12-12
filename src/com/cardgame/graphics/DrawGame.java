package com.cardgame.graphics;

import com.cardgame.Battle;
import com.cardgame.CardPerson;
import com.cardgame.Main;
import com.cardgame.graphics.buttons.StatButton;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;


public class DrawGame extends JPanel {
    private static ArrayList<JButton> statButtons = new ArrayList<>();
    private Image death, board, animation, cross, select;
    private static boolean animationOn = false;
    Font font = new Font("Magneto",Font.BOLD,27);
    Font font2 = new Font("Verdana",Font.BOLD,17);
    Font font3 = new Font("Magneto",Font.BOLD,17);
    Font font4 = new Font("Verdana",Font.BOLD,10);
    Font playerName = new Font("Verdana",Font.BOLD,50);
    public DrawGame(){
        super();
        for(int i=0;i<6;i++){
        statButtons.add(new StatButton(i+1));
        statButtons.get(i).setVisible(false);
        this.add(statButtons.get(i));
        }
        board = new ImageIcon("images/board2.png").getImage();
        death = new ImageIcon("images/smallsize/Death2.png").getImage();
        animation = new ImageIcon("images/stats/2.gif").getImage();
        cross = new ImageIcon("images/icons/DeathCross.png").getImage();
        select = new ImageIcon("images/icons/Select.png").getImage();
    }
    public static void setAnimation(boolean state){
        animationOn = state;
        Gui.reset();
    }

    @Override
    public void paintComponent(Graphics g){
        g.drawImage(board, 0,0,this);
        drawPlayersName(g);
        drawPersonsOrder(g);

        for(int i=0;i<Main.getYellowTeam().size();i++){
            if(i==0)
                paintCardSmall(Main.getYellowTeam().get(i),10,153, g);
            if(i==1)
                paintCardSmall(Main.getYellowTeam().get(i),287,153, g);
            if(i==2)
                paintCardSmall(Main.getYellowTeam().get(i),10,540, g);
            if(i==3)
                paintCardSmall(Main.getYellowTeam().get(i),287,540, g);
        }
        for(int i=0;i<Main.getBlueTeam().size();i++){
            if(i==0)
                paintCardSmall(Main.getBlueTeam().get(i),1657,153, g);
            if(i==1)
                paintCardSmall(Main.getBlueTeam().get(i),1380,153, g);
            if(i==2)
                paintCardSmall(Main.getBlueTeam().get(i),1657,540, g);
            if(i==3)
                paintCardSmall(Main.getBlueTeam().get(i),1380,540, g);
        }

        if (Battle.getAttacker()!=null)
            paintCardBig(Battle.getAttacker(),g);


        if (Battle.getDefender()!=null)
            paintCardBig(Battle.getDefender(),g);





    }

    private void paintCardBig(CardPerson person, Graphics g){
        int x, y=153;
        if(person.side) x=573;
        else x = 988;

        g.drawImage(person.getCardBig(), x, y, this);
        g.setFont(font);
        g.setColor(Color.orange);
        g.drawString(String.valueOf(person.getCurHp()), x+295, y+51);
        g.setFont(font2);
        g.setColor(Color.black);
        g.drawString(String.valueOf(person.getAtkStat(1)), x+98, y+300);
        g.drawString(String.valueOf(person.getAtkStat(2)), x+204, y+300);
        g.drawString(String.valueOf(person.getAtkStat(3)), x+310, y+300);
        g.drawString(String.valueOf(person.getAtkStat(4)), x+98, y+350);
        g.drawString(String.valueOf(person.getAtkStat(5)), x+204, y+350);
        g.drawString(String.valueOf(person.getAtkStat(6)), x+310, y+350);
        g.drawString(String.valueOf(person.getDefStat(1)), x+98, y+323);
        g.drawString(String.valueOf(person.getDefStat(2)), x+204, y+323);
        g.drawString(String.valueOf(person.getDefStat(3)), x+310, y+323);
        g.drawString(String.valueOf(person.getDefStat(4)), x+98, y+373);
        g.drawString(String.valueOf(person.getDefStat(5)), x+204, y+373);
        g.drawString(String.valueOf(person.getDefStat(6)), x+310, y+373);
        if(person.equals(Battle.getDefender()) && animationOn){
            g.drawImage(animation,x+30,y+30,this);
        }
        if (person.equals(Battle.getAttacker())) {
            int x1=x, y1=y+515;
            for(JButton button: statButtons){
                button.setBounds(x1,y1,120,50);
                if(x1==x+240) { x1=x; y1+=50;}
                else x1+=120;
            }
        }

    }

    private void paintCardSmall(CardPerson person, int x, int y, Graphics g) {

        g.drawImage(person.getCardSmall(), x, y, this);
        this.add(person.getButton());
        person.getButton().setBounds(x, y == 153 ? y - 23 : y + 362, 252, 23);
        g.setFont(font3);
        g.setColor(Color.orange);
        g.drawString(String.valueOf(person.getCurHp()), x + 206, y + 33);
        g.setFont(font4);
        g.setColor(Color.black);
        g.drawString(String.valueOf(person.getAtkStat(1)), x + 67, y + 210);
        g.drawString(String.valueOf(person.getAtkStat(2)), x + 142, y + 210);
        g.drawString(String.valueOf(person.getAtkStat(3)), x + 216, y + 210);
        g.drawString(String.valueOf(person.getAtkStat(4)), x + 67, y + 244);
        g.drawString(String.valueOf(person.getAtkStat(5)), x + 142, y + 244);
        g.drawString(String.valueOf(person.getAtkStat(6)), x + 216, y + 244);
        g.drawString(String.valueOf(person.getDefStat(1)), x + 67, y + 226);
        g.drawString(String.valueOf(person.getDefStat(2)), x + 142, y + 226);
        g.drawString(String.valueOf(person.getDefStat(3)), x + 216, y + 226);
        g.drawString(String.valueOf(person.getDefStat(4)), x + 67, y + 259);
        g.drawString(String.valueOf(person.getDefStat(5)), x + 142, y + 259);
        g.drawString(String.valueOf(person.getDefStat(6)), x + 216, y + 259);

        if (!person.alive)
            g.drawImage(death, x+40, y+4, this);
    }

    private void drawPlayersName(Graphics g){
        g.setFont(playerName);
        g.setColor(Color.RED);
        g.drawString(Gui.getPlayerName(true),140,80);
        g.setColor(Color.BLUE);
        g.drawString(Gui.getPlayerName(false),1505,80);
    }

    private void drawPersonsOrder(Graphics g){
        int x = 548, y = 30;
        for(int i=0;i<Main.getOrder().size();i++){
            int p = Main.getOrder().get(i);
            Image pic = Main.getPersons().get(p).getIcon();
            g.drawImage(pic, x, y, this);
            if(Main.getPersons().get(p).equals(Battle.getAttacker()))
                g.drawImage(select, x, y, this);
            if(!Main.getPersons().get(p).alive)
                g.drawImage(cross, x+2, y+2, this);
            x += 103;

        }
    }

    public static void statButtonsVisible(boolean show){
            for (JButton button : statButtons)
               button.setVisible(show);

    }

    public static void targetButtonsVisible(boolean show){
        if(!Battle.getAttacker().side) {
            for (CardPerson person : Main.getYellowTeam())
                if (person.alive) person.setButtonVisible(show);
                else person.setButtonVisible(false);
        }
        else {
            for(CardPerson person: Main.getBlueTeam())
                if(person.alive) person.setButtonVisible(show);
                else person.setButtonVisible(false);
        }

    }

}
