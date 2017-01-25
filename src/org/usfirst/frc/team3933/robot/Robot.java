package org.usfirst.frc.team3933.robot;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.AnalogGyro;
import edu.wpi.first.wpilibj.Joystick;
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
	
	// Variables /**************************
	double dc;
	double absMaxValue;
	
	//Motores
	CANTalon fl = new CANTalon(12);
	CANTalon rl = new CANTalon(13);
	CANTalon fr = new CANTalon(11);
	CANTalon rr = new CANTalon(10);

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
		drive = new RobotDrive(fl, rl, fr, ff);
		
		js1 = new Joystick(0);
		
		gyro = new AnalogGyro(0);
		
		dc = 0.1;
		absMaxValue = 1;
		
		// Init Routine: *******************
		gyro.reset();
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

	/**
	 * This function is called periodically during operator control
	 */
	@Override
	public void teleopPeriodic() {
		
		//Chasis
		double X = js1.getRawAxis(0);
		double Y = js1.getRawAxis(1);
		double R = js1.getRawAxis(4);
		
		X = Util.square(Util.deadCentre(dc, X, absMaxValue));
		Y = Util.square(Util.deadCentre(dc, Y, absMaxValue));
		R = Util.square(Util.deadCentre(dc, R, absMaxValue));
		
		drive.mecanumDrive_Cartesian(X, Y, R, 0);
		
	}

	/**
	 * This function is called periodically during test mode
	 */
	@Override
	public void testPeriodic() {
	}
}

