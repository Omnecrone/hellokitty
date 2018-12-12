package com.cardgame;


import com.cardgame.graphics.buttons.PersonButton;

import javax.swing.*;
import java.awt.*;

public abstract class CardPerson {
    private final String name;
    private int curHp;
    private final int maxHp, id, ini, strAtk, strDef, agiAtk, agiDef, intAtk, intDef, wepAtk,
            wepDef, specAtk, specDef, abiAtk, abiDef, lowD, midD, highD;
    public boolean side, dominate, alive, bot;
    private boolean strA, agiA, intA, wepA, specA, abiA;
    private Image icon, cardBig, cardSmall;
    private PersonButton button;

    public CardPerson(String name, int id, boolean side, boolean bot, int hp, int ini, int lowD,
                      int midD, int highD, int strAtk, int strDef, int agiAtk, int agiDef,
                      int intAtk, int intDef, int wepAtk, int wepDef, int specAtk, int specDef,
                      int abiAtk, int abiDef) {
        this.name = name;
        icon = new ImageIcon("images/icons/"+name+".png").getImage();
        cardBig = new ImageIcon("images/bigsize/"+name+".png").getImage();
        cardSmall = new ImageIcon("images/smallsize/"+name+".png").getImage();
        button = new PersonButton(this);
        this.bot = bot;
        strA = true;
        agiA = true;
        intA = true;
        wepA = true;
        specA = true;
        abiA = true;
        alive = true;
        dominate = false;

        this.id = id;
        this.side = side;
        this.curHp = hp;
        this.maxHp = hp;
        this.ini = ini;
        this.lowD = lowD;
        this.midD = midD;
        this.highD = highD;
        this.strAtk = strAtk;
        this.strDef = strDef;
        this.agiAtk = agiAtk;
        this.agiDef = agiDef;
        this.intAtk = intAtk;
        this.intDef = intDef;
        this.wepAtk = wepAtk;
        this.wepDef = wepDef;
        this.specAtk = specAtk;
        this.specDef = specDef;
        this.abiAtk = abiAtk;
        this.abiDef = abiDef;
    }
    public Image getIcon(){return icon;}
    public Image getCardBig(){return cardBig;}
    public Image getCardSmall(){return cardSmall;}
    public PersonButton getButton(){return button;}
    public void setButtonVisible(boolean visible){
        button.setVisible(visible);
    }
    public int getCurHp() {return curHp;}
    public int getId() { return id; }
    public String getName() { return name; }
    void setCurHp(int curHp) {this.curHp = curHp;}

    public int getDamage(int n){
        switch (n){
            case 1: case 2: case 3: return lowD;
            case 4: case 5: return midD;
            case 6: return highD;
        }
        return 0;
    }


    public int getAtkStat(int n){
        switch (n){
            case 1: return strAtk;
            case 2: return agiAtk;
            case 3: return intAtk;
            case 4: return wepAtk;
            case 5: return specAtk;
            case 6: return abiAtk;
        }
        return 0;
    }
    public int getDefStat(int n){
        switch (n){
            case 1: return strDef;
            case 2: return agiDef;
            case 3: return intDef;
            case 4: return wepDef;
            case 5: return specDef;
            case 6: return abiDef;
        }
        return 0;
    }
    public boolean getCheckStatAvailable(int n){
        switch (n){
            case 1: return strA;
            case 2: return agiA;
            case 3: return intA;
            case 4: return wepA;
            case 5: return specA;
            case 6: return abiA;
        }
        return false;
    }
    public void changeStatAvailable(int n){
        switch (n){
            case 1: case 4: if(strA)strA=false; if(wepA)wepA=false; break;
            case 2: case 5: if(agiA)agiA=false; if(specA)specA=false; break;
            case 3: case 6: if(intA)intA=false; if(abiA)abiA=false; break;

        }
    }


}
