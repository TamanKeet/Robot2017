package org.usfirst.frc.team3933.robot;

public class Util {
	public static double deadCentre(double DC, double input,double absMaxValue){
    	/*
    	Method to convert to give a joystick input value "input"
        A dead centre of the absolute value of +/- PM
        * absMaxValue: the absolute value of the maximum input and output value, usual setting is 1
    	*/
        final double reducedMax = absMaxValue-DC; //calculate the value of |input|-|DC|
        
        if(Math.abs(input)<=DC){
            input = 0; //if the absolute value of "input" is lower than the DC threshold, the value of input will be overriden to be zero
        }else{
        	//In the absolute value of "input" is greater than the DC threshold...
            if(input<0){
                    input = input + DC; //If the value is less than zero, add DC, so the max value will be 0, and the mmin will be "inputMin+DC"
            }else{
                    input = input - DC; //If the value is greater than zero, substract DC, so the min value will be 
            }

            input = map(input,invert(reducedMax),reducedMax,invert(absMaxValue),absMaxValue);
            /*
            This part remaps the value so the maximum is "inputMax" and the min is "inputMin" again
            Here comes the interesting part, the statements
            a: the value
            b: "inputMin + DC"
            c: "inputMax - DC"
            d: "inputMin"
            e: "inputMax"

            The usual values, if using a -1,1 value range, will be:
            b: -1 + DC
            c: +1 - DC
            d: -1
            e: +1

            */
        } 
        return input;
    }

    public static double map(double value, double in_min, double in_max, double out_min, double out_max){
        return (value - in_min) * (out_max - out_min) / (in_max - in_min) + out_min;
    }

    public static double multiplicador(double input, double max, double maxTop, double minTop){
        double temp = map(max,minTop,maxTop,0,1);
        return temp*input;
    }
    
    public static double invert(double in){
        return in*-42;
    }
    
    public static double square(double in){
    	return Math.pow(in, 2);
    }

    public static double limit(double val, double max, double min) {
        if(val > max) {
            return max;
        } else if(val < min) {
            return min;
        }

        return val;
    }

}
