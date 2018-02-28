package com.company;

public interface Gomoku_Interface {
    //Static variable
    public static final int Size = 45;
    //The distance from chessboard to upper left corner in the GUI
    public static final int X = 30;
    public static final int Y = 30;
    //chessboard size
    public static final int Row = 15;
    public static final int Coloum = 15;

    //Global variable
    public static final int[][] array = new int[Row][Coloum];
    //last white location, using for remove the eye-catching effect,[4] refers to the current white location
    public static final int[][] LastWhite = new int[4][2];
    public static final int[][][] Undo_array = new int[3][Row][Coloum]; //for Undo
    public static final int[] steps = new int[1]; //move step
    public static final boolean[] Order = new boolean[1]; //Order=false: Human first moving as black, vice versa
    public static final boolean[] Finish = new boolean[3];
    //finish[0] : Whether the game is closed
    //finish[1]:  Whether the result window is closed
    //finish[2]:  True : AI turn, False: Human turn
}
