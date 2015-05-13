/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Greg
 */
public class ATC {
    private double speed;
    private int numAircrafts;

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public int getNumAircrafts() {
        return numAircrafts;
    }

    public void setNumAircrafts(int numAircrafts) {
        this.numAircrafts = numAircrafts;
    }
   
    
    public ATC(){
        this(1, 3);
    }
    
    public ATC(double speed, int numAircrafts){
        this.speed = speed;
        this.numAircrafts = numAircrafts;
    }
    
    
    
    public double playSimulator(){
        return Math.random() * 100;
    }
    
    
    
}
