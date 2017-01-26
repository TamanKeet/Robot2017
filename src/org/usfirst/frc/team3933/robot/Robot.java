package org.usfirst.frc.team3933.robot;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.AnalogGyro;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.RobotDrive.MotorType;

import com.ctre.CANTalon;


/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {
	
	final String defaultAuto = "Default";
	final String customAuto = "My Auto";
	
	String autoSelected;
	SendableChooser<String> chooser = new SendableChooser<>();
	
	// Movement and motors *****************
	RobotDrive drive;
	
	// Joysticks and input *****************
	Joystick js1;
	
	// Sensonrs ****************************
	AnalogGyro gyro;
	AnalogUltra ultra;
	
	// Variables /**************************
	double dc;
	double absMaxValue;
	
	//Motores
	CANTalon fl = new CANTalon(10);
	CANTalon rl = new CANTalon(11);
	CANTalon fr = new CANTalon(13);
	CANTalon rr = new CANTalon(12);
	
	
	PIDController pid;
	DummyPID out;

	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	@Override
	public void robotInit() {
		chooser.addDefault("Default Auto", defaultAuto);
		chooser.addObject("My Auto", customAuto);
		SmartDashboard.putData("Auto choices", chooser);
		
		// Initializations: ***************
		drive = new RobotDrive(fl, rl, fr, rr);
		
		//js1 = new Joystick(0);
		
		//gyro = new AnalogGyro(0);
		
		ultra = new AnalogUltra(0, 0.009775);
		
		dc = 0.1;
		absMaxValue = 1;
		
		// Init Routine: *******************
		//gyro.reset();
		
		
		double kp = 0.01;
		double ki = 0.01;
		double kd = 0.01;
		
		out = new DummyPID();
		pid = new PIDController(kp,ki,kd, ultra, out);
	}

	/**
	 * This autonomous (along with the chooser code above) shows how to select
	 * between different autonomous modes using the dashboard. The sendable
	 * chooser code works with the Java SmartDashboard. If you prefer the
	 * LabVIEW Dashboard, remove all of the chooser code and uncomment the
	 * getString line to get the auto name from the text box below the Gyro
	 *
	 * You can add additional auto modes by adding additional comparisons to the
	 * switch structure below with additional strings. If using the
	 * SendableChooser make sure to add them to the chooser code above as well.
	 */
	@Override
	public void autonomousInit() {
		autoSelected = chooser.getSelected();
		// autoSelected = SmartDashboard.getString("Auto Selector",
		// defaultAuto);
		System.out.println("Auto selected: " + autoSelected);
	}

	/**
	 * This function is called periodically during autonomous
	 */
	@Override
	public void autonomousPeriodic() {
		switch (autoSelected) {
		case customAuto:
			// Put custom auto code here
			break;
		case defaultAuto:
		default:
			// Put default auto code here
			break;
		}
	}
	
	public void teleopInit(){
		pid.enable();
		System.out.println("PID Enabled!");
	}
	
	/**
	 * This function is called periodically during operator control
	 */
	@Override
	public void teleopPeriodic() {
		/*
		//Chasis
		double X = js1.getRawAxis(0);
		double Y = js1.getRawAxis(1);
		double R = js1.getRawAxis(4);
		
		X = Util.square(Util.deadCentre(dc, X, absMaxValue));
		Y = Util.square(Util.deadCentre(dc, Y, absMaxValue));
		R = Util.square(Util.deadCentre(dc, R, absMaxValue));
		
		drive.mecanumDrive_Cartesian(X, Y, R, 0);
		*/
		
		//SmartDashboard.putNumber("DB/String 0", ultra.getDistCm());
		SmartDashboard.putString("DB/String 0", Double.toString(ultra.getDistCm()));
		
		
		double kp = SmartDashboard.getNumber("DB/Slider 0", 0)/100.0;
		double ki = SmartDashboard.getNumber("DB/Slider 1", 0)/100.0;
		double kd = SmartDashboard.getNumber("DB/Slider 2", 0)/100.0;
		
		SmartDashboard.putString("DB/String 5", Double.toString(kp));
		SmartDashboard.putString("DB/String 6", Double.toString(ki));
		SmartDashboard.putString("DB/String 7", Double.toString(kd));
		
		pid.setPID(kp, ki, kd);
		
		pid.setSetpoint(150);
		
		//out.setNegate(true);
		
		SmartDashboard.putString("DB/String 1", Double.toString(out.readPid()));
		if(SmartDashboard.getBoolean("DB/Button 1", true)){
			drive.mecanumDrive_Cartesian(out.readPid(), 0, 0, 0);
		}else{
			drive.stopMotor();
		}
	}

	/**
	 * This function is called periodically during test mode
	 */
	@Override
	public void testPeriodic() {
	}
}

