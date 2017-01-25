package org.usfirst.frc.team3933.robot;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;

public class AnalogUltra extends AnalogInput implements PIDSource{
	
	private double voltsPerCm = 1;
	private PIDSourceType m_pidSource = PIDSourceType.kDisplacement;

	public AnalogUltra(int channel, double voltsPerCm) {
		super(channel);
		if(voltsPerCm != 0){
			this.voltsPerCm = voltsPerCm;
		}else{
			System.err.println("ULTRASONIC: volts per Cm can't be zero!");
		}
	}
	
	@Override
	public void setPIDSourceType(PIDSourceType pidSource) {
		if (!pidSource.equals(PIDSourceType.kDisplacement)) {
			throw new IllegalArgumentException("Only displacement PID is allowed for ultrasonics.");
		}
		m_pidSource = pidSource;
	}
	
	@Override
	public PIDSourceType getPIDSourceType() {
		return m_pidSource;
	}
	
	@Override
	public double pidGet(){
		return getDistCm();
	}
	
	public double getDistCm(){
		return getAverageVoltage() / voltsPerCm;
	}
}
