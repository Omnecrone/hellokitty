package com.cardgame;

import com.cardgame.graphics.DrawGame;
import com.cardgame.graphics.Gui;

import java.awt.*;
import java.util.*;

public class Battle {
    public static boolean flag = false;
    private static CardPerson attacker, defender;
    private static int attackStat;
    //стринговый геттер для текстового сообщения
    private static String getStringStat(int i){
        switch (i){
            case 1: return "свою физическую силу";
            case 2: return "свою скорость и ловкость";
            case 3: return "свой интеллект";
            case 4: return "свое оружие";
            case 5: return "свои бойцовские навыки";
            case 6: return "свои особые умения";
        }
        return null;
    }



    //инициализация атакующего и защищающегося
    public static void startBattle(CardPerson person) {
        attacker = person;
        Gui.reset();
        Gui.textAppend("\n\n"+attacker.getName());
        Gui.textAppend(" атакует, выберите цель.");
        threadSleep(1000);
        if (attacker.bot) {
            defender = botChooseTarget();
            threadSleep(2000);
            Gui.reset();
            attackStat = botInputAttackType();
            Gui.reset();
        } else {
            playerChooseTarget();
            Gui.textAppend("\nВыберите тип атаки.");
            playerInputAttackType();
        }
        dealDamage();
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        DrawGame.setAnimation(false);
        attacker = null;
        defender = null;
        Gui.reset();
    }

    //выбор цели для игрока. Выбирает цель автоматически, если только одна доступная цель.
    private static void playerChooseTarget() {
        if (attacker.side && Main.getBlueTeamAlive().size() == 1)
            defender=Main.getBlueTeam().get(0);
        else if (!attacker.side && Main.getYellowTeamAlive().size() == 1)
            defender= Main.getYellowTeam().get(0);
        else {

            DrawGame.targetButtonsVisible(true);
            while (!flag) {
                threadSleep(1000);
            }
            flag = false;
        }
    }

    //выбор типа атаки для игрока с обработкой исключений
    private static void playerInputAttackType() {
        DrawGame.statButtonsVisible(true);
        while (!flag) {
            threadSleep(1000);
            }
        flag = false;
    }

    //выбор цели для бота по минимальному hp противника
    private static CardPerson botChooseTarget() {
        int minhp = Main.getYellowTeamAlive().get(0).getCurHp();
        CardPerson target = Main.getYellowTeamAlive().get(0);
        for (CardPerson person: Main.getYellowTeamAlive())
            if(person.getCurHp()<minhp) {
                minhp = person.getCurHp();
                target = person;
            }
        return target;
    }

    //выбор типа атаки для бота по величине потенциального урона
    private static int botInputAttackType() {
        int[] personStats = new int[7];
        for (int i=1;i<=6; i++)
            if (attacker.getCheckStatAvailable(i))
                personStats[i] = attacker.getAtkStat(i) - defender.getDefStat(i); //если тип атаки i доступен, записать величину потенциального урона
        attackStat = 1;
        int maxdmg = 0;
        for (int i=1;i<=6;i++)
            if(personStats[i]>maxdmg) {
            maxdmg = personStats[i];
            attackStat=i; //поиск максимально большого потенциального урона
            }
        DrawGame.setAnimation(true);
        return attackStat;
    }

    //бросает кости и наносит урон по формуле
    private static void dealDamage(){
        final Random random = new Random();
        int n1 = random.nextInt(6)+1;
        int n2 = random.nextInt(6)+1;
        int atkRate = attacker.getAtkStat(attackStat)-defender.getDefStat(attackStat);
        int damage = atkRate>0?attacker.getDamage(n1)+atkRate:attacker.getDamage(n1);
        if(n1==6) defender.changeStatAvailable(n2);
        Gui.textAppend("\n"+attacker.getName());
        Gui.textAppend(" проводит ");
        if (n1 == 6) Gui.textAppend("сокрушительную атаку");
        else if (n1 == 4 || n1 == 5) Gui.textAppend("обычную атаку");
        else Gui.textAppend("слабую атаку");
        Gui.textAppend(" используя ");
        Gui.textAppend(getStringStat(attackStat));
        Gui.textAppend(" и наносит ");
        Gui.textAppend(defender.getName());
        Gui.textAppend(" " + damage + " ед. урона");

        if (n1 == 6) {
            Gui.textAppend("\n" + defender.getName());
            if (n2 == 1 || n2 == 4)
                Gui.textAppend(" получил травму рук и больше не может использовать [Cилу] и [Оружие] для своих атак.");
            else if (n2 == 2 || n2 == 5)
                Gui.textAppend(" получил травму ног и больше не может использовать [Скорость и ловкость] и [Бойцовские навыки] для своих атак.");
            else
                Gui.textAppend(" получил травму головы и больше не может использовать [Интеллект] и [Особые умения] для своих атак.");
        }

        defender.setCurHp(defender.getCurHp()-damage);
        checkPersonDeath(defender);

        if((n1==1||n1==2||n1==3) && (n2==4||n2==5||n2==6)&& defender.alive){
            Gui.textAppend("\n" + defender.getName());
            Gui.textAppend(" проводит контратаку и наносит в ответ "+defender.getDamage(1)+" ед. урона.");
            attacker.setCurHp(attacker.getCurHp()-defender.getDamage(1));
            checkPersonDeath(attacker);
        }


    }

    //проверка, умер ли персонаж, если да, добавляет очки команде противника и удаляет из соответствующих массивов.
    private static void checkPersonDeath(CardPerson person){
        if (person.getCurHp() <= 0) {
            person.setCurHp(0);
            person.alive = false;
            Gui.textAppend("\n"+person.getName()+" погибает!");
            if(person.side){
                Main.scoreBlue++;
                Gui.reset();
            }
            else {
                Main.scoreYellow++;
                Gui.reset();
            }
        }

    }

    public static void threadSleep(int time){
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static CardPerson getAttacker(){
        return attacker;
    }
    public static void setDefender(CardPerson defender){
        Battle.defender = defender;
    }
    public static void setAttackStat(int stat){
        attackStat = stat;
    }
    public static CardPerson getDefender(){
        return defender;
    }



}