package com.company;

import javax.swing.*;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Main extends JFrame implements Runnable {
    Character chars = new Character();
    Food food = new Food();
    Sign sign = new Sign();

    public static void main(String[] args) {
        Main main = new Main();
        Thread tr = new Thread(main);
        tr.start();
    }

    public Main() {
        setTitle("Open!");
        setSize(500, 500);
        setLayout(null);
        setResizable(false);
        setVisible(true);
        add(food);
        add(chars);
        add(sign);
        add(new Boards());

    }

    @Override
    public void run() {
        Double x_food, y_food, x_chars, y_chars;
        System.out.println(chars.getStatus());
        while (true) {
            x_food = food.getLocation().getX();
            y_food = food.getLocation().getY();
            x_chars = chars.getLocation().getX();
            y_chars = chars.getLocation().getY();
            if (x_food.equals(x_chars) && y_food.equals(y_chars)) {
                food.setRandomLocation();
                sign.setScore(10);
            }
        }

    }
}

class Boards extends JPanel {

    private final int BOARD_HEIGHT = 500;
    private final int BOARD_WIDTH = 500;

    public Boards() {

        setSize(BOARD_WIDTH, BOARD_HEIGHT);
        setLayout(null);
        setFocusable(true);
        setBackground(Color.orange);

    }

}
class Sign extends JPanel{
    private final int SIGN_WIDTH = 50;
    private final int SIGN_HEIGHT = 50;
    Label lb = new Label();
    private int Score = 0;
    public Sign(){
        lb.setText(""+ Score + "");
        lb.setSize(100,100);
        lb.setAlignment(Label.CENTER);
        setSize(SIGN_WIDTH,SIGN_HEIGHT);
        setLocation(425,405);
        setBackground(Color.red);
        add(lb);

    }
    public void setScore(int scoreAdded){
        this.Score += 10;
        lb.setText(""+ Score + "");
    }
}

class Character extends JPanel implements Runnable, KeyListener {
    private int POS_X, POS_Y;
    private int VEL_X, VEL_Y = 0;
    public boolean isStart = false;

    public Character() {
        setSize(20, 20);
        setLocation(POS_X, POS_Y);
        setBackground(Color.darkGray);
        setFocusable(true);
        addKeyListener(this);
    }

    public boolean getStatus() {
        return this.isStart;
    }

    @Override
    public void run() {
        System.out.println("START");
        int x = 0;
        while (isStart) {
            this.POS_X += this.VEL_X;
            this.POS_Y += this.VEL_Y;
            if (this.POS_X > 460 || this.POS_X < 0 || this.POS_Y > 460 || this.POS_Y < 0) {
                this.POS_X -= this.VEL_X;
                this.POS_Y -= this.VEL_Y;
            }

            setLocation(this.POS_X, this.POS_Y);

            try {
                Thread.sleep(500);

            } catch (InterruptedException err) {
                JOptionPane.showMessageDialog(null, err);
            }
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // Empty
    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_ENTER:
                Thread tr = new Thread(this);
                tr.start();
                this.isStart = true;
                break;
            case KeyEvent.VK_SPACE:
                this.VEL_X = 0;
                this.VEL_Y = 0;
                break;
            case KeyEvent.VK_DOWN:
                this.VEL_X = 0;
                this.VEL_Y = 20;
                break;
            case KeyEvent.VK_UP:
                this.VEL_X = 0;
                this.VEL_Y = -20;
                break;
            case KeyEvent.VK_LEFT:
                this.VEL_X = -20;
                this.VEL_Y = 0;
                break;
            case KeyEvent.VK_RIGHT:
                this.VEL_X = 20;
                this.VEL_Y = 0;
                break;
            default:
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // EMpyt
    }
}

class Food extends JPanel {
    public Food() {
        setSize(20, 20);
        setLocation(200, 200);
        setBackground(Color.red);
    }
    public void setRandomLocation(){

        double xrand = Math.floor(Math.random() * 25)/25*500;
        double yrand = Math.floor(Math.random() * 25)/25*500;
        int xaxis = (int) xrand;
        int yaxis = (int) yrand;
        setLocation(xaxis,yaxis);
    }
}
