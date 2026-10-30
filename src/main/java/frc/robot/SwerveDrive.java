package frc.robot;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.kinematics.SwerveDriveKinematics;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class SwerveDrive {

    private XboxController controll;

    private Gyro gyro;

	private SwerveModule leftFrontModule;
	private SwerveModule rightFrontModule;
	private SwerveModule rightBackModule;
	private SwerveModule leftBackModule;
	
    public SwerveDrive (XboxController controll, int SpeedIds[], int TurnIds[], int AbsEncoderIds[], Gyro gyro){
		this.controll = controll;
		this.gyro = gyro;

		leftFrontModule = new SwerveModule(SpeedIds[0], TurnIds[0], AbsEncoderIds[0], 0);
		rightFrontModule = new SwerveModule(SpeedIds[1], TurnIds[1], AbsEncoderIds[1], 0);
		rightBackModule = new SwerveModule(SpeedIds[2], TurnIds[2], AbsEncoderIds[2], -0.094482);
		leftBackModule = new SwerveModule(SpeedIds[3], TurnIds[3], AbsEncoderIds[3], 0);
		
	}
	
    private final Translation2d leftFrontPosition = new Translation2d(-0.3, 0.3);
    private final Translation2d rightFrontPosition = new Translation2d(0.3, 0.3);
    private final Translation2d rightBackPosition = new Translation2d(0.3, -0.3);
    private final Translation2d leftBackPosition = new Translation2d(-0.3, -0.3);

    private final SwerveDriveKinematics kinematics = 
                    new SwerveDriveKinematics(
                        leftFrontPosition, 
                        rightFrontPosition,
                        rightBackPosition,
                        leftBackPosition
                    );

	
	private ChassisSpeeds runChassisSpeed(XboxController controller, Rotation2d angle){
		return ChassisSpeeds.fromFieldRelativeSpeeds(
			controlleDeadband(controller.getLeftY()),
			controlleDeadband(controller.getLeftX()),
			controlleDeadband(controller.getRightX()),
			angle);
	}

    private SwerveModuleState[] runSwerveModuleStates(ChassisSpeeds speed){
		return kinematics.toSwerveModuleStates(speed);
	}

    private void setStates(SwerveModuleState states[]){
		leftFrontModule.setDesiredState(states[0]);
		rightFrontModule.setDesiredState(states[1]);
		rightBackModule.setDesiredState(states[2]);
		leftBackModule.setDesiredState(states[3]);
	}

	// private double toMetersPerSecond (double x){
	// 	return 0;
	// 	// return x * Constants.SwerveDrive.PERCENT_METERS_PER_SECOND_CONVERSOR;
	// }
	
	// private double toRadiansPerSecond (double x){
	// 	return x * 10;
	// }

	private double controlleDeadband(double value){
		if(Math.abs(value) > Constants.SwerveDrive.CONTROLLER_DEAD_BAND){
			return value;
		} else{
			return 0;
		}
	}
    
	public void run(){
		setStates(
			runSwerveModuleStates(
				runChassisSpeed(controll, gyro.getYawAsRotation2d())
			)
		);
	}
	
	public void printOdometry(){
		SwerveModuleState state[] = runSwerveModuleStates(runChassisSpeed(controll, gyro.getYawAsRotation2d()));

		SmartDashboard.putNumber("leftFrontSpeed", state[0].speedMetersPerSecond);
		SmartDashboard.putNumber("leftFrontTurn", state[0].angle.getRotations());

		SmartDashboard.putNumber("rightFrontSpeed", state[1].speedMetersPerSecond);
		SmartDashboard.putNumber("rightFrontTurn", state[1].angle.getRotations());

		SmartDashboard.putNumber("rightBackSpeed", state[2].speedMetersPerSecond);
		SmartDashboard.putNumber("rightBackTurn", state[2].angle.getRotations());

		SmartDashboard.putNumber("leftBackSpeed", state[3].speedMetersPerSecond);
		SmartDashboard.putNumber("leftBackTurn", state[3].angle.getRotations());
		
		SmartDashboard.putNumber("leftFrontTurnEncoder", leftFrontModule.getDoubleAngle());
		SmartDashboard.putNumber("rightFrontTurnEncoder", rightFrontModule.getDoubleAngle());
		SmartDashboard.putNumber("rightBackTurnEncoder", rightBackModule.getDoubleAngle());
		SmartDashboard.putNumber("leftBackTurnEncoder", leftBackModule.getDoubleAngle());
		
		SmartDashboard.putNumber("leftFrontTurnNEOEncoder", leftFrontModule.getNeoAngle());
		SmartDashboard.putNumber("rightFrontTurnNEOEncoder", rightFrontModule.getNeoAngle());
		SmartDashboard.putNumber("rightBackTurnNEOEncoder", rightBackModule.getNeoAngle());
		SmartDashboard.putNumber("leftBackTurnNEOEncoder", leftBackModule.getNeoAngle());
	}

	public void setAllAnglesZero(){
		leftFrontModule.setAngleZero();
		leftBackModule.setAngleZero();
		leftBackModule.setAngleZero();
		rightFrontModule.setAngleZero();
	}

}
