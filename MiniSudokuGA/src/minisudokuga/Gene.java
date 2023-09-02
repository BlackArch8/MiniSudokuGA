/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package minisudokuga;

/**
 *
 * @author CEN
 */
public class Gene {
    int number;
    
    Gene (int n){
        number = n;
    }
    
    void setNumber(int i){
        number = i;
    }
    
    int getNumber(){
        return number;
    }
}
