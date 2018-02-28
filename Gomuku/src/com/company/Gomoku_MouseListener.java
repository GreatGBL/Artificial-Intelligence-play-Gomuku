package com.company;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Gomoku_MouseListener extends MouseAdapter implements Gomoku_Interface {
    private Gomoku_FinishJudge j;
    private Gomoku_PanelSetting panel;
    private Graphics2D chess; //stone
    private int x,y;
    private Gomoku_AI ai = new Gomoku_AI();
    private int[] AI_moving = new int[2];

    public Gomoku_MouseListener(Gomoku_PanelSetting panel) {
        this.panel = panel;
        chess = (Graphics2D)this.panel.getGraphics();

    }


    public void mouseReleased(MouseEvent Mouse) {
        //Get the coordinates of the mouse click
        x = Mouse.getX();   //coloum
        y = Mouse.getY();  //row

        //Convert the corresponding location on the chessboard
        int coloum = (x-X+Size/2)/Size;
        int row = (y-Y+Size/2)/Size;

        //finish[0] : Whether the game is closed
        //finish[2]:  True : AI turn, False: Human turn
        if(row < Row && coloum < Coloum & !Finish[0] &!Finish[2]) {
            if(array[row][coloum] == 0) {
                steps[0]++;
                //move old Undo_array, from right to left
                //Like flip-flops
                for(int i=0;i<Row;i++){
                    for(int j=0;j<Coloum;j++){
                        Undo_array[0][i][j] = Undo_array[1][i][j];
                        Undo_array[1][i][j] = Undo_array[2][i][j];
                    }
                }
                //Recording the Undo_array
                    for(int i=0;i<Row;i++){
                        for(int j=0;j<Coloum;j++){
                            Undo_array[2][i][j] = array[i][j];
                        }
                    }

                //Convert the corresponding coordinates on the GUI
                x = coloum*Size - Size/2 +X;
                y = row*Size - Size/2 +Y;

                if(!Order[0]) {  //human play black
                    array[row][coloum] = 1;
                    chess.setColor(Color.black);
                }else{  //human play white
                    array[row][coloum] = 2;
                    chess.setColor(Color.decode("#FFFFF0")); //Ivory white
                }

                chess.fillOval(x, y, Size, Size); //draw the chess(stone)
                //finish check
                j = new Gomoku_FinishJudge(row,coloum);
                j.judge();

                Finish[2]=true; //Opponent's turn
                AI_moving=ai.Minimax(); //Do Southampton Go AI

                x = AI_moving[1]*Size - Size/2 +X; //coloum
                y = AI_moving[0]*Size - Size/2 +Y; //row

                if(!Order[0]) {  //human play black
                    chess.setColor(Color.decode("#FFFFF0")); //Ivory white
                }else{ //human play white
                    chess.setColor(Color.black);
                }
                chess.fillOval(x, y, Size, Size); //draw the chess(stone)

                //Remove the red cross Eye-catching effect
                if(LastWhite[3][0]!=0 && LastWhite[3][1]!=0) {
                    if(!Order[0]) {  //human play black
                        chess.setColor(Color.decode("#FFFFF0")); //Ivory white
                    }else{ //human play white
                        chess.setColor(Color.black);
                    }
                    chess.fillOval(LastWhite[3][0], LastWhite[3][1], Size, Size);
                }

                //Make the red cross Eye-catching for AI new step
                chess.setColor(Color.red); //Red
                chess.drawLine(x+Size/4,y+Size/2,(int)(x+Size*3/4),y+Size/2);
                chess.drawLine(x+Size/2,y+Size/4,x+Size/2,(int)(y+Size*3/4));

                //move old white location,, from right to left
                //Like flip-flops
                for(int i=0;i<2;i++){
                    LastWhite[0][i] = LastWhite[1][i];
                    LastWhite[1][i] = LastWhite[2][i];
                    LastWhite[2][i] = LastWhite[3][i];
                }
                //Recording the white location
                LastWhite[3][0]=x;
                LastWhite[3][1]=y;
                //finish check
                j = new Gomoku_FinishJudge(AI_moving[0],AI_moving[1]);
                j.judge();

                Finish[2] = false; //Opponent's turn
            }
        }
    }
}