/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package minisudokuga;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

/**
 *
 * @author CEN
 */
public class Sudoku {
    Triple [] puzzle;
    int [][] board;
    int size;
    
    Sudoku (int s, File f){
        size = s;
        readPuzzle(f);
        board = new int [size][size];
        setBoard();
    }
    
    Sudoku (int s, Triple [] p){
        size = s;
        puzzle = p;
        board = new int [size][size];
        setBoard();
    }
    
    Sudoku(int s, int [][] b){
        size = s;
        puzzle = null;
        board = b;        
    }
    
    void setBoard(){
        for (int i=0; i<size;i++) {
            for (int j=0; j<size;j++) {
                board[i][j] = -1;
            }
        }
        for (int i=0; i<puzzle.length; i++){
            Triple t = puzzle[i];
            board[t.row][t.column] = t.number;
        }
    }
    
    
    void printSudoku(){
        String s = "-----------------\n";
        for (int i=0;i<size;i++){
            s = s + "|";
            for (int j=0; j<size;j++){
                if (board[i][j] != -1) s = s + " " + board[i][j] + " |";
                else s = s + "   |";
            }
            s = s + "\n-----------------\n";            
        }
        System.out.println(s);
    }
    
    void readPuzzle(File text){
        try {
            Scanner sc = new Scanner(text);
            int nT = sc.nextInt(); // banyaknya triple
            puzzle = new Triple[nT];
            for (int i = 0; i < nT; i++) {
                int r = sc.nextInt();
                int c = sc.nextInt();
                int v = sc.nextInt();
                Triple t = new Triple(r,c,v);
                puzzle[i] = t;
            }
        } catch (IOException ex) {
            System.err.println("File not found");
        }
    }
}
