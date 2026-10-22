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

	private SwerveModule frontLeftModule;
	private SwerveModule frontRightModule;
	private SwerveModule backRightModule;
	private SwerveModule backLeftModule;
	
    public SwerveDrive (XboxController controll, int SpeedIds[], int TurnIds[], int AbsEncoderIds[], Gyro gyro){
		this.controll = controll;
		this.gyro = gyro;

		frontLeftModule = new SwerveModule(SpeedIds[0], TurnIds[0], AbsEncoderIds[0], 0);
		frontRightModule = new SwerveModule(SpeedIds[1], TurnIds[1], AbsEncoderIds[1], 1);
		backRightModule = new SwerveModule(SpeedIds[2], TurnIds[2], AbsEncoderIds[2], 2);
		backLeftModule = new SwerveModule(SpeedIds[3], TurnIds[3], AbsEncoderIds[3], 3);
		
	}
	
    private final Translation2d leftFrontPosition = new Translation2d(10, 20);
    private final Translation2d rightFrontPosition = new Translation2d(10, 20);
    private final Translation2d leftBackPosition = new Translation2d(10, 20);
    private final Translation2d rightBackPosition = new Translation2d(10, 20);

    private final SwerveDriveKinematics kinematics = 
                    new SwerveDriveKinematics(
                        leftFrontPosition, 
                        rightFrontPosition,
                        leftBackPosition,
                        rightBackPosition
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
		frontLeftModule.setDesiredState(states[0]);
		frontRightModule.setDesiredState(states[1]);
		backRightModule.setDesiredState(states[2]);
		backLeftModule.setDesiredState(states[3]);
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
		SmartDashboard.putNumber("leftFrontTurn", state[0].angle.getDegrees());

		SmartDashboard.putNumber("rightFrontSpeed", state[1].speedMetersPerSecond);
		SmartDashboard.putNumber("rightFrontTurn", state[1].angle.getDegrees());

		SmartDashboard.putNumber("rightBackSpeed", state[2].speedMetersPerSecond);
		SmartDashboard.putNumber("rightBackTurn", state[2].angle.getDegrees());

		SmartDashboard.putNumber("leftBackSpeed", state[3].speedMetersPerSecond);
		SmartDashboard.putNumber("leftBackTurn", state[3].angle.getDegrees());
		
		SmartDashboard.putNumber("leftFrontTurn", state[3].angle.getDegrees());

		SmartDashboard.putNumber("leftFrontTurnEncoder", frontLeftModule.getDoubleAngle());
		SmartDashboard.putNumber("rightFrontTurnEncoder", frontRightModule.getDoubleAngle());
		SmartDashboard.putNumber("rightBackTurnEncoder", backRightModule.getDoubleAngle());
		SmartDashboard.putNumber("leftBackTurnEncoder", backLeftModule.getDoubleAngle());




	}

}
