package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Gomoku_FinishJudge implements  Gomoku_Interface {
    private JFrame frame_result;
    private int row, coloum;
    private int count;

    //Initialization
    public Gomoku_FinishJudge(int row, int coloum) {
        this.row = row;
        this.coloum = coloum;
        count = 1;
        frame_result = new JFrame();
        frame_result.setTitle("Result");
        frame_result.setLocationRelativeTo(null);
        frame_result.setSize(900, 200);
        frame_result.setResizable(false);
    }


    public void Finish_Check() {
        Button b1 = new Button("Play again!");
        b1.setBounds(750,50,100,50);
        b1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame_result.setVisible(false);
                frame_result.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //close the frame
                Finish[1] = false; //the result window is closed
            }

        });

        //Detection whether window is closed
        frame_result.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                Finish[1] = false; //the result window is closed
            }
        });

        //The game is finish
        if (count >= 5) {
            //GUI setting
            JLabel label = new JLabel("Win");
            label.setFont(new Font("times new roman", 1, 75));
            label.setPreferredSize(new Dimension(300, 150));
            frame_result.add(b1);
            frame_result.add(label);
            frame_result.setVisible(true);
            if (array[row][coloum] % 2 != 0) {     //black
                System.out.println("Black Win!");
                if (!Order[0]) {  //human play black
                    label.setText("You Win!");
                    label.setHorizontalAlignment(JTextField.CENTER); //Align to the center
                }else{
                    label.setText("Southampton Go Win!");
                }
            } else {                                //white
                System.out.println("White Win!");
                if (!Order[0]) {  //human play black
                    label.setText("Southampton Go Win!");
                }else{
                    label.setText("You Win!");
                    label.setHorizontalAlignment(JTextField.CENTER); //Align to the center
                }
            }
            Finish[0] = true; //the game is finish
            Finish[1] = true; //the result window is created
        }
    }

    //Finish check
    public void judge() {
        count = 1;
        //Vertical inspection
        for (int i = row + 1; i < Coloum; i++) {
            if (array[i][coloum] != 0) {
                if ((array[row][coloum] % 2) == (array[i][coloum] % 2)) {
                    count++;
                } else {
                    break;
                }
            } else {
                break;
            }
        }

        for (int i = row - 1; i >= 0; i--) {
            if (array[i][coloum] != 0) {
                if ((array[row][coloum] % 2) == (array[i][coloum] % 2)) {
                    count++;
                } else break;
            } else break;
        }

        Finish_Check();

        count = 1;
        //horizon inspection
        for (int i = coloum + 1; i < Row; i++) {
            if (array[row][i] != 0) {
                if ((array[row][coloum] % 2) == (array[row][i]) % 2) {
                    count++;
                } else break;
            } else break;
        }
        for (int i = coloum - 1; i >= 0; i--) {
            if (array[row][i] != 0) {
                if (array[row][coloum] % 2 == array[row][i] % 2) {
                    count++;
                } else break;
            } else break;
        }
        Finish_Check();

        count = 1;
        //diagonally inspection (From top left to bottom right)
        for (int i = row - 1, j = coloum - 1; i >= 0 && j >= 0; i--, j--) {
            if (array[i][j] != 0) {
                if ((array[row][coloum] % 2) == (array[i][j] % 2)) {
                    count++;
                } else break;
            } else break;
        }
        for (int i = row + 1, j = coloum + 1; i < Coloum && j < Row; i++, j++) {
            if (array[i][j] != 0) {
                if ((array[row][coloum] % 2) == (array[i][j] % 2)) {
                    count++;
                } else break;
            } else break;
        }
        Finish_Check();

        count = 1;
        //diagonally inspection (From lower left to upper right)
        for (int i = row - 1, j = coloum + 1; i >= 0 && j < Row; i--, j++) {
            if (array[i][j] != 0) {
                if ((array[row][coloum] % 2) == (array[i][j] % 2)) {
                    count++;
                } else break;
            } else break;
        }
        for (int i = row + 1, j = coloum - 1; i < Coloum && j >= 0; i++, j--) {
            if (array[i][j] != 0) {
                if ((array[row][coloum] % 2) == (array[i][j] % 2)) {
                    count++;
                } else break;
            } else break;
        }
        Finish_Check();
    }
}
