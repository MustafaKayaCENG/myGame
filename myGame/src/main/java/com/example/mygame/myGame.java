package com.example.mygame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

public class myGame extends JFrame implements MouseListener {
    private static final long serialVersionUID = 1L;
    private int score = 0;
    private int highestScore = 0;
    private JLabel scoreLabel;
    private JLabel highestScoreLabel;
    private JButton[] holeButtons;
    private Random random;
    private Timer timer;
    private int wrongClicks = 0;
    private int currentWhackHoleIndex;

    public myGame() {
        setTitle("Whack-A-Whack");
        setSize(800, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(4, 3));
        JOptionPane.showMessageDialog(this, "Your highest score will be updated after you make 3 wrong clicks!\n To start the game please click 'OK'");

        holeButtons = new JButton[12];
        for (int i = 0; i < holeButtons.length; i++) {
            holeButtons[i] = new JButton(String.valueOf(i + 1));
            holeButtons[i].addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (currentWhackHoleIndex == -1) {
                        return;
                    }

                    if (holeButtons[currentWhackHoleIndex].equals(e.getSource())) {
                        score++;
                    } else {
                        score--;
                        wrongClicks++;
                        placeWhackRandomly();
                    }
                    scoreLabel.setText("Score: " + score);

                    if(wrongClicks == 3){
                        endGame();
                    }
                }
            });
            holeButtons[i].addMouseListener(this);
            add(holeButtons[i]);
        }

        random = new Random();
        placeWhackRandomly();

        timer = new Timer(450, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                placeWhackRandomly();
            }
        });
        timer.start();

        scoreLabel = new JLabel("Score : " + score);
        add(scoreLabel, BorderLayout.LINE_END);

        highestScoreLabel = new JLabel("Highest Score : " + highestScore);
        add(highestScoreLabel, BorderLayout.AFTER_LAST_LINE);

        setVisible(true);
        startGame();
    }

    private void placeWhackRandomly() {
        int newWhackHoleIndex = random.nextInt(holeButtons.length);
        while (newWhackHoleIndex == currentWhackHoleIndex) {
            newWhackHoleIndex = random.nextInt(holeButtons.length);
        }
        if (currentWhackHoleIndex != -1) {
            holeButtons[currentWhackHoleIndex].setText("");
        }

        holeButtons[newWhackHoleIndex].setText("CLICK ME IF YOU CAN!");
        currentWhackHoleIndex = newWhackHoleIndex;
    }

    public static void main(String[] args) {
        myGame game = new myGame();
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (currentWhackHoleIndex == -1) {
            return;
        }

        scoreLabel.setText("Score : " + score+"\n");

        if (wrongClicks == 3) {
            endGame();
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    private void endGame() {
        timer.stop();
        JOptionPane.showMessageDialog(this, "Game Over!" + "\n" + "Your score is : " + score + "\n" + "Your highest score is : " + highestScore);
        if (score > highestScore && wrongClicks >= 3) {
            highestScore = score;
            highestScoreLabel.setText("Highest Score: " + highestScore);
        }
        score = 0;
        wrongClicks = 0;
        scoreLabel.setText("Score: " + score);
        startGame();
    }

    private void startGame() {
        score = 0;
        wrongClicks = 0;
        scoreLabel.setText("Score: " + score);
        timer.start();
    }
}

