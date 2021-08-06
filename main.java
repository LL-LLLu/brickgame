package brickBracker;

import javax.swing.*;

public class main {
    public static void main(String[] args) {
        JFrame jf = new JFrame();
        GamePlay game = new GamePlay();
        jf.setBounds(10,10,700, 600);
        jf.setTitle("brickBracker");
        jf.setResizable(false);
        jf.setVisible(true);
        jf.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        jf.add(game);
        game.getRandomX();
        game.getRandomY();
        //System.out.println(game.getRandomX() + " " + game.getRandomY());

    }

}
