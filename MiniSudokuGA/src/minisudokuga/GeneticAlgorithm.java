/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package minisudokuga;

import java.util.Random;

/**
 *
 * @author CEN
 */
public class GeneticAlgorithm {
    int size;
    Triple[] puzzle;
    double probCO;
    double probM;
    int popSize;
    int maxIter;
    Chromosome[] population;
    int best;

    // konstruktor
    GeneticAlgorithm(int s, Triple[] p, double pco, double pm, int ps, int mi) {
        size = s;
        puzzle = p;
        probCO = pco;
        probM = 0;
        popSize = ps;
        maxIter = mi;
    }

    // memilih kromosom yang akan dikenai operasi crossover/mutasi
    int selection() {
        //pilih secara acak kromosom dari populasi
        Random rand = new Random();
        return rand.nextInt(popSize);
    }

    // operasi crossover, pilih teknik yang digunakan
    // dua kromosom anak menggantikan kromosom induk ini
    void crossover() {
        //teknik yang digunakan adalah one point crossover

        //pilih 2 kromosom untuk di crossover
        int parent1Index = selection();
        int parent2Index = selection();

        
        //lakukan crossover antara kromosom parent1 dan parent2
        //ganti kromosom parent dengan kromosom anak
        Chromosome parent1 = population[parent1Index];
        Chromosome parent2 = population[parent2Index];

        //one point crossover
        int crossoverPoint = selection();

        Chromosome anak1 = new Chromosome(size, puzzle);
        Chromosome anak2 = new Chromosome(size, puzzle);
        //copy gen sebelum crossover point
        for (int i = 0; i < size; i++) {

            if (i < crossoverPoint) {
                anak1.genes[i] = parent1.genes[i];
                anak2.genes[i] = parent2.genes[i];
            } else {
                anak1.genes[i] = parent2.genes[i];
                anak2.genes[i] = parent1.genes[i];
            }
        }
        //ganti kromosom parent dengan kromosom anak
        population[parent1Index] = anak1;
        population[parent2Index] = anak2;
    }

    // operasi mutasi, pilih teknik yang digunakan
    // pilih satu kromosom untuk dikenai mutasi
    // lakukan perubahan pada kromosom tersebut
    void mutation() {
        //pilih kromosom untuk di mutasi secara acak dari populasi
        int mutantIndex = selection();
        Chromosome mutant = population[mutantIndex];
        //lakukan mutasi pada kromosom tersebut
        Random rand = new Random();
        //pilih gen atau titik mutasi yang akan dimutasi
        int mutationPoint = rand.nextInt(size);
        //pengecekan apakah gen tersebut sudah terisi atau belum
        if (mutant.genes[mutationPoint].getNumber() == -1) {
            //jika gen tersebut belum terisi, maka isi dengan angka acak antara 1 sampai size
            mutant.genes[mutationPoint] = new Gene(rand.nextInt(size) + 1);
        }
        //update kromosom pada populasi
        population[mutantIndex] = mutant;
    }
    

    // menghitung fitness dari setiap kromosom
    // mencatat kromosom terbaik
    void checkFitness() {
        for (int i = 0; i < population.length; i++) {
            Chromosome chromosome = population[i];

            int totalDuplication = chromosome.fitness();

            chromosome.fitness = (float) ((totalDuplication));
            //catat kromosom terbaik
            if (chromosome.fitness < population[best].fitness) {
                best = i;
            }
        }
        

        
    }
    
    
    // pembangkitan populasi awal, generate kromosom sebanyak popSize
    void init() {
        population = new Chromosome[popSize];
        for (int i = 0; i < popSize; i++) {
            population[i] = new Chromosome(size, puzzle);
            population[i].printChromosome(size);
        }
    }
    void restChromosomes() {
        //set 10% kromosom terbaik dari populasi sebelumnya
        int numElitesToKeep = (int) (popSize * 0.1); 

        for (int i = numElitesToKeep; i < popSize; i++) {
            population[i] = new Chromosome(size, puzzle);
        }
    }
    
    

    // iterasi
    void iteration() {
        init();
        checkFitness();
        int iter = 0;
        while (!termination(iter)) {
            crossover();
            mutation();
            restChromosomes();
            checkFitness();
            //print fitness terbaik
            System.out.println("Fitness of best chromosome: " + population[best].fitness);
            iter++;
            
            

        }
    }

    // kondisi berhenti
    boolean termination(int iter) {
        return ((iter == maxIter) || (population[best].fitness == 0));
    }
}
