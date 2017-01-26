package org.usfirst.frc.team3933.robot;

import edu.wpi.first.wpilibj.PIDOutput;

public class DummyPID implements PIDOutput{
	private double value;
	private boolean negate = false;
	@Override
	public void pidWrite(double output) {
		value = output;
	}
	
	public void setNegate(boolean in){
		negate = in;
	}
	
	public double readPid(){
		return (negate)? -value : value;
	}

}
