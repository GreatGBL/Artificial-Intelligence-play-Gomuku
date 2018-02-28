package com.company;

import java.util.ArrayList;
import java.util.LinkedList;

public class Gomoku_AI implements Gomoku_Interface {
   //variable setting
    private int Depth_Restrictions = 2; //limit the deep
    private int range = 1; //The Radiation range
    private int Random_time = 2000; //Random time for Monte Carlo Method (MCM) simulation
   //-----------------------------------------------------------------------------------------------
    private int[] AI_moving = new int[2];
    private int count; //The number of unbroken chain
    private int moving_steps; //The random chess(stone) moving_steps
    private boolean game = false; //Whether a game is finished
    private int[][] chessboard = new int[Row][Coloum];//For Monte Carlo Method (MCM) simulation
    // private GoBangListener place = new GoBangListener();

    public void GoFirst() {
        //If AI moves first,it always place the chess(stone) on the center of the chessboard
        array[7][7] = 1; //black chess(stone)

        //the center of the chessboard
        //Make the red cross Eye-catching
        LastWhite[3][0] = 7 * Size - Size / 2 + X;
        LastWhite[3][1] = 7 * Size - Size / 2 + Y;
        Finish[2] = false; //Opponent's turn

    }

    public int[] Minimax() {
        LinkedList<LIFO> LIFO_Queue = new LinkedList<LIFO>();
        ArrayList<Node> nodes = new ArrayList<>();
        int[][] Start_state = new int[Row][Coloum];
        int[][] State = new int[Row][Coloum];
        int[][] Temp_State;
        int Node_number = 0;
        int Current_node_number;
        boolean legitimate = false;    //whether the chess(stone) legitimate (range x range radiation radius has an any chess(stone))

        //Initialization the variable
        //Assignment
        for (int i = 0; i < Row; i++) {
            for (int j = 0; j < Coloum; j++) {
                Start_state[i][j] = array[i][j];    //array is the Global variable
            }
        }

        //Initialization the arraylist and linkedlist
        Node node_initialization = new Node(); //initialization
        LIFO LIFO_initialization = new LIFO();

        LIFO_initialization.State = Start_state;
        LIFO_initialization.Node_number = Node_number;

        node_initialization.Min_max = true;  //True:Max , False:Min
        node_initialization.Parent_node = 0;
        node_initialization.Depth = 0;

        LIFO_Queue.add(LIFO_initialization);
        nodes.add(node_initialization);

        int pointer = 1;
        int move_steps = 0;
        int Expands_time=0;
        int Removed_node = 0;      //Removed node
        int children_number =0;    //The number of children at the last state, need using the MCM
        //Suppose the initial value is 100%
        double min_win_rate = 1.0;  //use for Alpha–beta pruning
        //Suppose the initial value is 0%
        double max_win_rate = 0.0;  //use for Alpha–beta pruning
        int max_node=0; //The node has max win rate at the second last deep

        //loop function
        while (true) {
            if (pointer == 0) {
                break;
            }

            //Assignment
            for (int i = 0; i < Row; i++) {
                for (int j = 0; j < Coloum; j++) {
                    State[i][j] = LIFO_Queue.getLast().State[i][j];
                }
            }

            Current_node_number = LIFO_Queue.getLast().Node_number;

            //remove the last element in the LIFO queue
            LIFO_Queue.removeLast();
            pointer--;

            //check current state
            //If the AI can moves 1 step to win
            //She is not considering any strategy
            if(nodes.get(Current_node_number).Depth ==1 ){
                Finish_checking(State, nodes.get(Current_node_number).Coordinate[0], nodes.get(Current_node_number).Coordinate[1]);
                if(game){
                    //The AI will win
                    max_node = Current_node_number;
                    break; //break the loop function
                }
            }

            if (nodes.get(Current_node_number).Depth < Depth_Restrictions) {
                //Find all the successors
                for (int o = 0; o < Row; o++) {
                    for (int p = 0; p < Coloum; p++) {
                        legitimate = false;
                        if (State[o][p] == 0) {  //the location is empty

                            //the location -range X range area need an any chess(stone)
                            a:for (int i = -range; i < range+1; i++) {
                                for (int j = -range; j < range+1; j++) {
                                    if (o + i >= 0 & o + i <= 14 & p + j >= 0 & p + j <= 14) {  //Make sure not to exceed the chessboard
                                        if (State[o + i][p + j] != 0) {  //exist a chess(stone)
                                            legitimate = true;
                                            break a;
                                        }
                                    }
                                }
                            }
                        }

                        if (legitimate) {
                            move_steps++;
                            Temp_State = new int[Row][Coloum]; //Refresh the pointer
                            //Assignment
                            for (int i = 0; i < Row; i++) {
                                for (int j = 0; j < Coloum; j++) {
                                    Temp_State[i][j] = State[i][j];
                                }
                            }

                            //placing the chess(stone)
                            if (!Order[0]) {  //human play black
                                if(nodes.get(Current_node_number).Min_max){
                                    Temp_State[o][p] = 2;  //white
                                }else{
                                    Temp_State[o][p] = 1;  //black
                                }
                            } else {
                                if(nodes.get(Current_node_number).Min_max){
                                    Temp_State[o][p] = 1;  //black
                                }else{
                                    Temp_State[o][p] = 2;  //white
                                }
                            }
                            //add it to the FIFO
                            LIFO LIFO = new LIFO(); //Refresh the pointer
                            pointer++;
                            Node_number++;
                            LIFO.State = Temp_State;
                            LIFO.Node_number = Node_number;
                            LIFO_Queue.add(LIFO);

                            //add into node
                            Node node = new Node(); //Refresh the pointer
                            node.Parent_node = Current_node_number;
                            node.Coordinate[0] = o;
                            node.Coordinate[1] = p;
                            node.Depth = nodes.get(Current_node_number).Depth + 1;
                            node.Winning_rate=0.0;

                            if(node.Depth%2==1){
                                node.Min_max = false;  //True:Max , False:Min
                            }else{
                                node.Min_max = true;  //True:Max , False:Min
                            }
                            nodes.add(node);

                            //second last node, counting the number of its children
                            if (Depth_Restrictions - nodes.get(Current_node_number).Depth ==1) {
                                children_number++;
                            }

                        }
                    }
                }
            }else{  //calculate the Winning rate
                Expands_time++;
                children_number--;

                //finish check
                Finish_checking(State, nodes.get(Current_node_number).Coordinate[0], nodes.get(Current_node_number).Coordinate[1]);
                if(game){
                    // The opponent has won
                    // so AI got 0% win rate
                    nodes.get(Current_node_number).Winning_rate = 0.0;
                }else{
                    nodes.get(Current_node_number).Winning_rate=MCM(State,Random_time);
                }


                //Alpha–beta pruning
                if(nodes.get(Current_node_number).Winning_rate < max_win_rate) {
                    for(int i=0; i<children_number; i++){
                        LIFO_Queue.removeLast();
                        nodes.remove(Node_number);

                        Node_number--;
                        Current_node_number--;
                        pointer--;
                        Removed_node++;
                    }
                    children_number=0;
                }

                //Update the minimum value
                if(nodes.get(Current_node_number).Winning_rate < min_win_rate){
                    min_win_rate = nodes.get(Current_node_number).Winning_rate;
                }

                //Finish a group children!
                if(children_number==0){
                    //update the parent
                    nodes.get(nodes.get(Current_node_number).Parent_node).Winning_rate = min_win_rate;
                    min_win_rate = 1.0; //reset the variable

                    //Update the maximum value
                    if(nodes.get(nodes.get(Current_node_number).Parent_node).Winning_rate > max_win_rate){
                        max_node=nodes.get(Current_node_number).Parent_node; //The node has max win rate at the second last deep
                        max_win_rate = nodes.get(nodes.get(Current_node_number).Parent_node).Winning_rate;
                    }
                }

            }
        }

        //Placing the chess(stone)
        //find the node number of max_win_rate
        if(!Finish[0]){
            AI_moving[0] = nodes.get(max_node).Coordinate[0];  //row
            AI_moving[1] = nodes.get(max_node).Coordinate[1];  //column
            //record the chessboard
            if (!Order[0]) {  //human play black
                array[AI_moving[0]][AI_moving[1]] = 2;  //white
            } else {
                array[AI_moving[0]][AI_moving[1]] = 1;  //black
            }
        }

        return (AI_moving);
    }

    private class Node{
        private boolean Min_max; //False:Max , true:Min
        private int Parent_node;
        private int[] Coordinate = {0,0};  //the new placing chess(stone) coordinate from parent state to current state
        private int Depth;
        private double Winning_rate;
    }

    private class LIFO{
        private int[][] State;
        private int Node_number;
    }

    //Monte Carlo Method
    public double MCM(int[][] array, int random_time) {
        int AI_winning_time = 0; //the Winning time of AI
        int First_placing,Second_placing;   //the First and Second placing chess(stone) colour
        int x=0,y=0; //a random chess(stone) coordinate
        boolean movement = false; //whether a chess(stone) placing is successful

        //judge which coluor chess(stone) moves first
        if (!Order[0]) {  //human play black
            First_placing = 2;    //white
            Second_placing = 1;   //black
        } else {           //human play white
            First_placing = 1;    //black
            Second_placing = 2;   //white
        }

        //MCM method
        for (int p = 0; p < random_time; p++) {
            //Initial assignment
            for (int i = 0; i < Row; i++) {
                for (int j = 0; j < Coloum; j++) {
                    chessboard[i][j] = array[i][j];
                }
            }

            //random playing until the game is finish
            while (true) {
                //First chess(stone) random placing
                while (!movement & moving_steps < 15*15-steps[0]) {
                     x = (int) (Math.random() * Row); //random number
                     y = (int) (Math.random() * Coloum); //random number

                    if (chessboard[x][y] == 0) {   //empty state
                        chessboard[x][y] = First_placing;
                        movement = true; //The first chess(stone) random placing successful
                    }
                }

                movement = false; //Reset the variable
                moving_steps++;
                Finish_checking(chessboard,x,y);
                if(game){
                    AI_winning_time++;
                    break; //break the loop function
                }

                //Second chess(stone) random placing
                while (!movement & moving_steps < 15*15-steps[0]) {
                     x = (int) (Math.random() * Row); //random number
                     y = (int) (Math.random() * Coloum); //random number

                    if (chessboard[x][y] == 0) {   //empty state
                        chessboard[x][y] = Second_placing;
                        movement = true; //The first chess(stone) random placing successful
                    }
                }

                movement = false; //Reset the variable
                moving_steps++;
                Finish_checking(chessboard,x,y);
                if(game){
                    break; //break the loop function
                }

                //The chessboard is full
                if(moving_steps >= 15*15-steps[0]){
                    break;
                }
            }
            //A game is finish
            moving_steps=0;
        }
        return ((double) AI_winning_time / random_time);
    }

    private void Finish_checking(int[][] array, int x, int y) {

        int row = x;
        int coloum = y;

        game = false;

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

        //---------
        if(count>=5) {
            game = true;
        }

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

        //---------
        if(count>=5) {
            game = true;
        }

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

        //---------
        if(count>=5) {
            game = true;
        }

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

        //---------
        if(count>=5) {
            game = true;
        }
    }

}