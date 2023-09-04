/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package minisudokuga;

/**
 *
 * @author Doni Andrian - 6182001020
 *         Kevin Jonathan - 6182001026
 */
import java.util.*;
public class Chromosome {
    float fitness;
    Gene [] genes;
    
    Chromosome (int s, Triple [] puzzle){       
        genes = new Gene [s*s];
        // inisialisasi kromosom
        for (int i=0; i<genes.length;i++){
            genes[i] = new Gene(-1);
        }
        
        // untuk menampung angka-angka 
        Vector<Integer> vn = new Vector();
        
        // isi angka-angka sejumlah size
        for (int i=0; i<s; i++){
            for (int j=0; j<s; j++){
                vn.add(j+1);
            }        
        }

        // masukkan soal dari puzzle        
        for (int i=0; i<puzzle.length; i++){
            Triple t = puzzle[i];
            genes[t.row*s+t.column].setNumber(t.number);
            vn.remove((Integer) t.number);
        }

        // sisa angka yang tidak ada di puzzle diacak untuk dimasukkan ke kromosom
        List li = Arrays.asList(vn.toArray());
        Collections.shuffle(li);
        //System.out.println("The list is: " + li); 
        int idx = 0;
        for (int i=0; i<genes.length;i++){
            if (genes[i].getNumber() == -1) {
                genes[i].setNumber((Integer) li.get(idx));
                idx++;
            }
        }
    }
    //kami menambahkan konstruktor ini digunakan untuk menyimpan kromosom terbaik
    Chromosome(Chromosome other) {
        this.fitness = other.fitness;
        this.genes = new Gene[other.genes.length];
        for (int i = 0; i < other.genes.length; i++) {
            this.genes[i] = new Gene(other.genes[i].getNumber());
        }
    }
    
    // mengembalikan berapa banyak duplikasi di baris
    int rowCheck() {
        int size = (int) Math.sqrt(genes.length);
        int duplicates = 0;
        for (int row = 0; row < size; row++) {
            boolean[] seen = new boolean[size + 1];
            for (int col = 0; col < size; col++) {
                int value = genes[row * size + col].number;
                if (seen[value]) {
                    duplicates++;
                } else {
                    seen[value] = true;
                }
            }
        }
        return duplicates;
    }
    
    
    // mengembalikan berapa banyak duplikasi di kolom
    int colCheck() {
        int duplicates = 0;
        int size = (int) Math.sqrt(genes.length);
        for (int col = 0; col < size; col++) {
            boolean[] seen = new boolean[size + 1];
            for (int row = 0; row < size; row++) {
                int value = genes[row * size + col].number;
                if (seen[value]) {
                    duplicates++;
                } else {
                    seen[value] = true;
                }
            }
        }
        return duplicates;
    }
    
    
    // mengembalikan berapa banyak duplikasi di subblok
    int subCheck() {
        int size = (int) Math.sqrt(genes.length);
        int duplicates = 0;
        int blockSize = (int) Math.sqrt(size);
        for (int blockRow = 0; blockRow < blockSize; blockRow++) {
            for (int blockCol = 0; blockCol < blockSize; blockCol++) {
                boolean[] seen = new boolean[size + 1];
                for (int row = blockRow * blockSize; row < (blockRow + 1) * blockSize; row++) {
                    for (int col = blockCol * blockSize; col < (blockCol + 1) * blockSize; col++) {
                        int value = genes[row * size + col].number;
                        if (seen[value]) {
                            duplicates++;
                        } else {
                            seen[value] = true;
                        }
                    }
                }
            }
        }
        return duplicates;
    }
    
    
    int fitness(){
        return rowCheck()+colCheck()+subCheck();
    }
    
    // mengembalikan representasi kromosom ke bentuk papan permainan
    int [][] toBoard(int size){
        int [][] b = new int [size][size];
        int idx = 0;
        for (int i=0; i<size;i++){
            for (int j=0; j<size;j++){
                b[i][j] = this.genes[idx].getNumber();
                idx++;
            }            
        }
        return b;
    }
 
    // menampilkan kromosom dalam bentuk papan permainan
    void printChromosome(int size){
        Sudoku s = new Sudoku(size, this.toBoard(size));
        s.printSudoku();
    }
}
