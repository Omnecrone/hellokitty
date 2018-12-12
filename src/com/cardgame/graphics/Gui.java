package com.cardgame.graphics;

import com.cardgame.Main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Gui {
    private static JFrame frame;
    private static DrawGame board;
    private static JTextArea text;
    private static String player1Name, player2Name;
    static {
        player1Name = "Player 1";
        text = new JTextArea();
        board = new DrawGame();
    }
    public static String getPlayerName(boolean player){
        if(player) return player1Name;
        else return player2Name;
    }


    public static void openWindow() {

        frame.setVisible(false);

        frame = new GameFrame();
        frame.setContentPane(board);
        Main.fillPersonsMap();
        Main.fillOrderList();
        board.setLayout(null);

        JPanel log = new JPanel();
        board.add(log);
        log.setLayout(new BoxLayout(log,BoxLayout.Y_AXIS));
        log.setBounds(573,841, 774,204);

        text.setBackground(Color.BLACK);
        text.setEditable(false);
        text.setFont(new Font("Arial",Font.BOLD,13));
        text.setForeground(Color.WHITE);

        JScrollPane sc = new JScrollPane(text);
        sc.setOpaque(false);
        sc.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        sc.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        log.add(sc);

        Main.NewThread thread = new Main.NewThread();
        thread.start();

    }

    public static void cardNumberMenu() {
        frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        DrawStartMenu board = new DrawStartMenu();
        //board.setLayout(new BoxLayout(board, BoxLayout.Y_AXIS));

        frame.setLocationRelativeTo(null);
        frame.setSize(250, 250);
        frame.setVisible(true);
        frame.setContentPane(board);

        Button card1 = new Button("1 карта");
        card1.addActionListener(new CardNumberListener());
        Button card2 = new Button("2 карты");
        card2.addActionListener(new CardNumberListener());
        Button card3 = new Button("3 карты");
        card3.addActionListener(new CardNumberListener());
        Button card4 = new Button("4 карты");
        card4.addActionListener(new CardNumberListener());

        board.add(new Label("Количество карт у каждого игрока:"));
        board.add(BorderLayout.CENTER, card1);
        board.add(BorderLayout.CENTER, card2);
        board.add(BorderLayout.CENTER, card3);
        board.add(BorderLayout.CENTER, card4);
    }


    public static void playerOrBotMenu() {
        DrawStartMenu board = new DrawStartMenu();

        frame.setSize(251, 251);
        frame.setSize(250, 250);
        frame.setContentPane(board);
        //board.setLayout(new BoxLayout(board, BoxLayout.Y_AXIS));

        Button player = new Button("Другой игрок");
        player.addActionListener(new PlayerOrBotListener());
        Button bot = new Button("Компьютерный игрок");
        bot.addActionListener(new PlayerOrBotListener());
        board.add(BorderLayout.CENTER,new Label("Выберите себе опонента:"));
        board.add(BorderLayout.CENTER,player);
        board.add(BorderLayout.CENTER, bot);
    }

    static class CardNumberListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            Gui.playerOrBotMenu();
            if(e.getActionCommand().equals("1 карта"))
                Main.setP(1);
            if(e.getActionCommand().equals("2 карты"))
                Main.setP(2);
            if(e.getActionCommand().equals("3 карты"))
                Main.setP(3);
            if(e.getActionCommand().equals("4 карты"))
                Main.setP(4);


        }
    }

    static class PlayerOrBotListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            if(e.getActionCommand().equals("Компьютерный игрок")){
                Main.setBot(true);
                player2Name = "Computer";
            }
            else {
                Main.setBot(false);
                player2Name = "Player 2";
            }
            Gui.openWindow();


        }
    }

    public static void reset() {
        frame.repaint();

    }
    public static void textAppend(String info){
        text.append(info);
        int height = (int)text.getPreferredSize().getHeight();
        Rectangle rect = new Rectangle(0,height,774,30);
        text.scrollRectToVisible(rect);
    }

}

