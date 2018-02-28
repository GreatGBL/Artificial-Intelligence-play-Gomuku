package com.company;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Gomoku_PanelSetting extends JPanel implements Gomoku_Interface{
    private Gomoku_FinishJudge j;
    private int Undo_time = 3; //default setting is 3
    private boolean setting_window =false; //whether the setting window creation
    private boolean order =false; //order=false: Human first moving as black, vice versa
    private Gomoku_AI ai = new Gomoku_AI();
    public void gobang() {
        Order[0] = order; //Assignment the Global variable
            //Panel Setting
        JFrame frame = new JFrame();
            frame.setTitle("Gomoku (Southampton Go) Version 1.00");
            frame.setResizable(false);
            frame.setDefaultCloseOperation(3);//close the main window, the program is stop
            frame.setSize(950, 750);
            //Layout setting
            frame.setLayout(new BorderLayout());
            frame.add(this, BorderLayout.CENTER);

            //Jpanel Component setting
            JPanel pane1 = new JPanel();
            pane1.setPreferredSize(new Dimension(240, 0));
            //Button Component setting
            Button b1 = new Button("Restart");
            Button b2 = new Button("Setting");
            Button b3 = new Button("Undo");
            b1.setBounds(50, 250, 100, 50);
            b2.setBounds(50, 350, 100, 50);
            b3.setBounds(50, 450, 100, 50);
            //Text Component setting
            JTextField t1 = new JTextField("●");
            t1.setBounds(50, 150, 100, 50);
            t1.setFont(new Font("times new roman", 1, 80));
            t1.setBorder(new EmptyBorder(0,0,0,0));
            t1.setForeground(Color.BLACK);
            t1.setEditable(false);
            t1.setHorizontalAlignment(JTextField.CENTER); //Align to the center
            //Text Component setting
            JTextField t2 = new JTextField(String.valueOf(Undo_time));
            t2.setBounds(50, 550, 100, 50);
            t2.setFont(new Font("times new roman", 1, 30));
            t2.setEditable(false);
            t2.setHorizontalAlignment(JTextField.CENTER); //Align to the center
            //Image Component setting
            ImageIcon icon1 = new ImageIcon("src/UOS.png");//path
            ImageIcon icon2 = new ImageIcon("src/Author.png");//path
            //Label Component setting
            JLabel image1 = new JLabel(icon1);
            JLabel image2 = new JLabel(icon2);
            image1.setBounds(0, 50, 230, 50);
            image2.setBounds(0, 650, 200, 37);
            //Adding all the Components
            pane1.add(image1);
            pane1.add(image2);
            pane1.add(b1);
            pane1.add(b2);
            pane1.add(b3);
            pane1.add(t1);
            pane1.add(t2);
            pane1.setLayout(null);
           //Adding the Jpanel
            frame.add(pane1, BorderLayout.EAST);
            frame.setVisible(true);

            //listen the mouse clicking
            Gomoku_MouseListener listener = new Gomoku_MouseListener(this);
            this.addMouseListener(listener);

            //initialization the Undo_array
            for(int p=0;p<3;p++){
                for(int i=0;i<Row;i++){
                    for(int j=0;j<Coloum;j++){
                        Undo_array[p][i][j] = 0;
                    }
                }
            }

            //button listener
            //Restart
            b1.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (!Finish[1]) {     //finish[1]:  Whether the result window is closed
                        Finish[0] = false;  // Whether the game is closed, it is a new game!
                        Undo_time = 3;
                        steps[0]=0;  //move step
                        System.out.println("Restart");
                        //reset the array
                        for (int i = 0; i < Row; i++) {
                            for (int j = 0; j < Coloum; j++) {
                                array[i][j] = 0;
                            }
                        }
                        //reset the Undo_array
                        for(int p=0;p<3;p++){
                            for(int i=0;i<Row;i++){
                                for(int j=0;j<Coloum;j++){
                                    Undo_array[p][i][j] = 0;
                                }
                            }
                        }
                        //reset the last white location,
                        for(int p=0;p<4;p++){
                            for(int i=0;i<2;i++){
                                LastWhite[p][i] = 0;
                            }
                        }

                        //order setting
                        Order[0] = order; //Assignment the Global variable
                        if(!order){   //Human play black
                            t1.setForeground(Color.BLACK);
                            Finish[2]=false;  //finish[2]:  False: Human turn
                        }else{       //Human play white
                            t1.setForeground(Color.WHITE);
                            Finish[2]=true; //finish[2]:  True : AI turn
                            ai.GoFirst();
                        }
                        t2.setText(String.valueOf(Undo_time));
                        //Refresh the chessboard
                        repaint(0, 0, (Row + 1) * Size, (Coloum + 1) * Size);
                    }
                }
            });

            //button listener
            //Setting
            b2.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if(!Finish[1]& !setting_window) {    //finish[1]:  Whether the result window is closed
                        System.out.println("Setting");
                        setting_window = true; //the setting window is created

                        //frame setting
                        JFrame frame_Setting;
                        frame_Setting = new JFrame();
                        frame_Setting.setTitle("Setting");
                        frame_Setting.setLocationRelativeTo(null);
                        frame_Setting.setLayout(null);
                        frame_Setting.setSize(300, 150);
                        //button setting
                        Button s1 = new Button("Play Black");
                        s1.setBounds(20,30,100,50);
                        Button s2 = new Button("Play White");
                        s2.setBounds(160,30,100,50);
                        //Adding all the Components
                        frame_Setting.add(s1);
                        frame_Setting.add(s2);
                        frame_Setting.setVisible(true);
                        //Detection whether window is closed
                        frame_Setting.addWindowListener(new WindowAdapter() {
                            @Override
                            public void windowClosing(WindowEvent e) {
                                super.windowClosing(e);
                                setting_window=false; //the setting window is closed
                            }
                        });
                        //button listener
                        //Play Black
                        s1.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                System.out.println("Play Black");
                                frame_Setting.setVisible(false);
                                frame_Setting.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //close the frame
                                setting_window=false; //the setting window is closed
                                order =false; //human play black
                                if (steps[0] == 0) {  //only the setting works when no any chess(stone) are placed
                                    Order[0] = order;
                                    t1.setForeground(Color.BLACK);
                                    //clear
                                    array[7][7] = 0;
                                    LastWhite[3][0]=0;
                                    LastWhite[3][1]=0;
                                    //Refresh the chessboard
                                    repaint(0, 0, (Row + 1) * Size, (Coloum + 1) * Size);
                                }
                            }
                        });

                        //button listener
                        //Play White
                        s2.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                System.out.println("Play White");
                                frame_Setting.setVisible(false);
                                frame_Setting.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //close the frame
                                setting_window=false; //the setting window is closed
                                order =true; //human play white
                                if (steps[0] == 0) {  //only the setting works when no any chess(stone) are placed
                                    Order[0] = order;
                                    t1.setForeground(Color.WHITE);
                                    Finish[2]=true; //finish[2]:  True : AI turn
                                    ai.GoFirst(); //Southampton Go place chess(stone) first
                                    //Refresh the chessboard
                                    repaint(0, 0, (Row + 1) * Size, (Coloum + 1) * Size);
                                }
                            }
                        });
                    }
                }
            });
        //button listener
        //Undo
            b3.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (!Finish[1] & Undo_time > 0 & steps[0]>0) {   //finish[1]:  Whether the result window is closed
                        System.out.println("Undo");
                        for(int i=0;i<Row;i++){
                            for(int j=0;j<Coloum;j++){
                                array[i][j] = Undo_array[2][i][j];
                            }
                        }
                        ///move old Undo_array, from left to right
                        //Like flip-flops
                        for(int i=0;i<Row;i++){
                            for(int j=0;j<Coloum;j++){
                                Undo_array[2][i][j] = Undo_array[1][i][j];
                                Undo_array[1][i][j] = Undo_array[0][i][j];
                                Undo_array[0][i][j] = 0;
                            }
                        }

                        //move old white location,, from left to right
                        //Like flip-flops
                        for(int i=0;i<2;i++){
                            LastWhite[3][i] = LastWhite[2][i];
                            LastWhite[2][i] = LastWhite[1][i];
                            LastWhite[1][i] = LastWhite[0][i];
                            LastWhite[0][i] = 0;
                        }

                        //Refresh the chessboard
                        repaint(0, 0, (Row + 1) * Size, (Coloum + 1) * Size);
                        steps[0]--;  //move step
                        Finish[0] = false;  // Whether the game is closed, the state is operation by Undo.
                        Undo_time--;
                        t2.setText(String.valueOf(Undo_time));
                    }
                }
            });
        }
    //paint function
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D gg = (Graphics2D) g;
        Graphics2D ggg = (Graphics2D) g;
        //draw the chessboard contour
        for(int i = 0; i < Row; i++) {
            gg.drawLine(X, Y+i*Size, X+Size*(Coloum-1), Y+i*Size);
        }
        for(int i = 0; i < Coloum; i++) {
            gg.drawLine(X+i*Size, Y, X+i*Size, Y+Size*(Row-1));
        }

        //draw the chess(stone)
        for(int i = 0; i < array.length; i++) {
            for (int j = 0; j < array[i].length; j++) {
                if (array[i][j] != 0) {
                    if (array[i][j] % 2 != 0) {
                        gg.setColor(Color.black);
                    } else {
                        gg.setColor(Color.decode("#FFFFF0")); //Ivory
                    }

                    int x = Y + j * Size - Size / 2;
                    int y = X + i * Size - Size / 2;
                    gg.fillOval(x, y, Size, Size);
                }
            }
        }
        //draw the black rectangle Sign
        ggg.setColor(Color.BLACK);
        ggg.fillRect(3*Size - Size/8 +X,3*Size - Size/8 +Y ,Size/4,Size/4);
        ggg.fillRect(11*Size - Size/8 +X,3*Size - Size/8 +Y ,Size/4,Size/4);
        ggg.fillRect(7*Size - Size/8 +X,7*Size - Size/8 +Y ,Size/4,Size/4);
        ggg.fillRect(3*Size - Size/8 +X,11*Size - Size/8 +Y ,Size/4,Size/4);
        ggg.fillRect(11*Size - Size/8 +X,11*Size - Size/8 +Y ,Size/4,Size/4);

        //Make the red cross Eye-catching for AI new step
        if(LastWhite[3][0]!=0 && LastWhite[3][1]!=0) {
                gg.setColor(Color.red); //Red
                gg.drawLine(LastWhite[3][0] + Size / 4, LastWhite[3][1] + Size / 2, (int) (LastWhite[3][0] + Size * 3 / 4), LastWhite[3][1] + Size / 2);
                gg.drawLine(LastWhite[3][0] + Size / 2, LastWhite[3][1] + Size / 4, LastWhite[3][0] + Size / 2, (int) (LastWhite[3][1] + Size * 3 / 4));
            }
    }
}