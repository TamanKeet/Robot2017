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
	private CANTalon rodillo;
	private CANTalon disparador;

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
		drive = new RobotDrive(new CANTalon(12), new CANTalon(13), new CANTalon(11), new CANTalon(10)); //12, 13, 11, 10
		drive.setInvertedMotor(MotorType.kFrontRight, true);
		drive.setInvertedMotor(MotorType.kRearRight, true);
		
		rodillo = new CANTalon(14);
		disparador = new CANTalon(15);
		
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
		
		//Rodillo Con Boton
		boolean botonA = js1.getRawButton(1);
		boolean botonB = js1.getRawButton(2);
		
		if (botonA){
			rodillo.set(-1);
		}
		else if (botonB){
			rodillo.set(0);
		}
        
        //Disparador
        boolean botonX = js1.getRawButton(3);
		boolean botonY = js1.getRawButton(4);
		
		if (botonX){
			disparador.set(1);
		}
		else if (botonY){
			disparador.set(0);
		}
		
	}

	/**
	 * This function is called periodically during test mode
	 */
	@Override
	public void testPeriodic() {
	}
}

