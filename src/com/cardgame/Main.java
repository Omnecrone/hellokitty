package com.cardgame;

import com.cardgame.graphics.Gui;
import com.cardgame.persons.*;

import java.awt.*;
import java.util.*;

public class Main {

    public static byte scoreYellow=0, scoreBlue=0;
    private static int p=0;
    private static boolean bot, side=true;
    private static Map<Integer, CardPerson> persons = new HashMap<>();
    private static ArrayList<CardPerson> yellowTeam = new ArrayList<>(), blueTeam = new ArrayList<>();
    private static ArrayList<Integer> order = new ArrayList<>();

    //метод мейн инициализирует все листы и запускает цепочку методов
    public static void main(String[] args) {
        Gui.cardNumberMenu();
    }

    public static void secondStage(){
        for(long i = 1;;i++) {
            if (winnerCheck(scoreYellow, scoreBlue) != 0) break;
            roundAction(i);
        }

        if(winnerCheck(scoreYellow,scoreBlue)==1) System.out.println("Красная команда победила!");
        else if(winnerCheck(scoreYellow,scoreBlue)==2) System.out.println("Синяя команда победила!");
        else System.out.println("Феноменально! Игра закончилась в ничью!");
    }

    //создает карту, куда записывает p*2 случайных персонажа из общего пула(ключем выступает id персонажа).
    //так же делит их на желтую и синюю команду. Если "bot=true" определяет персонажа как ИИ
    public static void fillPersonsMap() {
        Integer[] allCounts = new Integer[] {3,4,11,12,13,19,27,29,34,38,39,41,50,54,56};
        final Random random = new Random();

        for(byte i=0; i<p*2; i++) {

            while (true){
                int n = random.nextInt(54)+3;   //рандом в промежутке от 3 до 56
                if (!persons.containsKey(n) && Arrays.asList(allCounts).contains(n)){
                    switch (n){
                        case 3: createNewPerson(n,new DarthVader(side,n,bot&&!side)); break;
                        case 4: createNewPerson(n,new GhostRider(side,n,bot&&!side)); break;
                        case 11: createNewPerson(n,new Hulk(side,n,bot&&!side)); break;
                        case 12: createNewPerson(n,new NinjaTurtles(side,n,bot&&!side)); break;
                        case 13: createNewPerson(n,new Diablo(side,n,bot&&!side)); break;
                        case 19: createNewPerson(n,new Terminator(side,n,bot&&!side)); break;
                        case 27: createNewPerson(n,new SubZero(side,n,bot&&!side)); break;
                        case 29: createNewPerson(n,new Scorpion(side,n,bot&&!side)); break;
                        case 34: createNewPerson(n,new Bane(side,n,bot&&!side)); break;
                        case 38: createNewPerson(n,new Batman(side,n,bot&&!side)); break;
                        case 39: createNewPerson(n,new DrekThar(side,n,bot&&!side)); break;
                        case 41: createNewPerson(n,new JasonVoorhees(side,n,bot&&!side)); break;
                        case 50: createNewPerson(n,new Predator(side,n,bot&&!side)); break;
                        case 54: createNewPerson(n,new KelThuzad(side,n,bot&&!side)); break;
                        case 56: createNewPerson(n,new Alien(side,n,bot&&!side)); break;
                        }
                    break;
                }

            }

        }
    }


    //создает лист из ключей карты персонажей и сортирует их по показателю инициативы (проверяя на превосходство)
    public static void fillOrderList() {
        order = new ArrayList<>(persons.keySet());
        Collections.sort(order);
        Collections.reverse(order);
    }

    //заполняет массивы только-что созданными персонажами
    private static void createNewPerson(int n, CardPerson person){
        persons.put(n,person);
        if(side) yellowTeam.add(person);
        else blueTeam.add(person);
        side=!side;
    }

    //проведение одного раунда (каждый персонаж атакует по разу)
    private static void roundAction(long i) {
        fillOrderList();
        Gui.reset();
        Gui.textAppend("\n"+ i + "-й раунд!");
        for (Integer I : order) {
            if (winnerCheck(scoreYellow, scoreBlue) != 0) break;
            if(persons.get(I).alive) Battle.startBattle(persons.get(I));
        }
    }

    //проверка на победу, в случае положительного значения - ломает текущий цикл и завершает игру.
    private static int winnerCheck(int yellow,int blue){
        if(yellow==p && blue==p) return 3;
        else if(blue==p) return 2;
        else if(yellow==p) return 1;
        else return 0;
    }

    public static void setP(int p) {Main.p = p;}
    public static void setBot(boolean bot) {Main.bot = bot;}
    public static Map<Integer, CardPerson> getPersons(){
        return persons;
    }
    public static ArrayList<Integer> getOrder(){
        return order;
    }
    public static ArrayList<CardPerson> getYellowTeam(){
        return yellowTeam;
    }
    public static ArrayList<CardPerson> getYellowTeamAlive(){
        ArrayList<CardPerson> yellowTeamAlive = new ArrayList<>();
        for(CardPerson person: yellowTeam)
            if(person.alive) yellowTeamAlive.add(person);
        return yellowTeamAlive;
    }
    public static ArrayList<CardPerson> getBlueTeamAlive(){
        ArrayList<CardPerson> blueTeamAlive = new ArrayList<>();
        for(CardPerson person: blueTeam)
            if(person.alive) blueTeamAlive.add(person);
        return blueTeamAlive;
    }

    public static ArrayList<CardPerson> getBlueTeam(){
        return blueTeam;
    }

    public static class NewThread extends Thread {

        @Override
        public void run() {
            secondStage();
        }
    }

}
