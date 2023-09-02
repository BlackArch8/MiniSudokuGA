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
        Random rand = new Random();
        return rand.nextInt(popSize);
    }

    // operasi crossover, pilih teknik yang digunakan
    // dua kromosom anak menggantikan kromosom induk ini
    void crossover() {
        int parent1Index = selection();
        int parent2Index = selection();

        // Perform crossover operation between parent1 and parent2 chromosomes
        // Replace parents with offspring chromosomes
        Chromosome parent1 = population[parent1Index];
        Chromosome parent2 = population[parent2Index];

        // Implement crossover operation (e.g., one-point crossover)
        int crossoverPoint = selection();
        Chromosome offspring1 = new Chromosome(size, puzzle);
        Chromosome offspring2 = new Chromosome(size, puzzle);

        for (int i = 0; i < size; i++) {
            if (i < crossoverPoint) {
                offspring1.genes[i] = parent1.genes[i];
                offspring2.genes[i] = parent2.genes[i];
            } else {
                offspring1.genes[i] = parent2.genes[i];
                offspring2.genes[i] = parent1.genes[i];
            }
        }
        // Replace parents with offspring in the population
        population[parent1Index] = offspring1;
        population[parent2Index] = offspring2;
    }

    // operasi mutasi, pilih teknik yang digunakan
    // pilih satu kromosom untuk dikenai mutasi
    // lakukan perubahan pada kromosom tersebut
    void mutation() {
        int mutantIndex = selection();
        Chromosome mutant = population[mutantIndex];
        
        Random rand = new Random();
        int mutationPoint = rand.nextInt(size);
    
        if (mutant.genes[mutationPoint].getNumber() == -1) {
            mutant.genes[mutationPoint] = new Gene(rand.nextInt(size) + 1);
        }
    
        population[mutantIndex] = mutant;
    }
    

    // menghitung fitness dari setiap kromosom
    // mencatat kromosom terbaik
    void checkFitness() {
        for (int i = 0; i < population.length; i++) {
            Chromosome chromosome = population[i];

            int totalDuplication = chromosome.fitness();

            chromosome.fitness = (float) ((totalDuplication));

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
        // Number of chromosomes to keep from the previous generation (e.g., top 10%)
        int numElitesToKeep = (int) (popSize * 0.1); // You can adjust this percentage
    
        // Create new chromosomes to replace the rest of the population
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
            //print fitness of best chromosome
            System.out.println("Fitness of best chromosome: " + population[best].fitness);
           

           
            iter++;
            //print each board that has been generated
            //System.out.println("Iteration " + iter);
           //population[best].printChromosome(size);
            

        }
    }

    // kondisi berhenti
    boolean termination(int iter) {
        return ((iter == maxIter) || (population[best].fitness == 0));
    }
}
