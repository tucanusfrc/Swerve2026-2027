// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import com.revrobotics.RelativeEncoder;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkLowLevel.MotorType;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Robot extends TimedRobot {

  XboxController controller = new XboxController(0);

  private Gyro gyro = new Gyro();

  private final int SpeedIds[] = {
    Constants.SwerveDrive.FRONT_LEFT_SPEED_ID,
    Constants.SwerveDrive.FRONT_RIGHT_SPEED_ID,
    Constants.SwerveDrive.BACK_RIGHT_SPEED_ID,
    Constants.SwerveDrive.BACK_LEFT_SPEED_ID
  };

  private final int TurnIds[] = {
    Constants.SwerveDrive.FRONT_LEFT_TURN_ID,
    Constants.SwerveDrive.FRONT_RIGHT_TURN_ID,
    Constants.SwerveDrive.BACK_RIGHT_TURN_ID,
    Constants.SwerveDrive.BACK_LEFT_TURN_ID
  };

  private final int AbsEncoderIds[] = {
    Constants.SwerveDrive.FRONT_LEFT_ENCODER_ID,
    Constants.SwerveDrive.FRONT_RIGHT_ENCODER_ID,
    Constants.SwerveDrive.BACK_RIGHT_ENCODER_ID,
    Constants.SwerveDrive.BACK_LEFT_ENCODER_ID
  };


  // SwerveDrive drive = new SwerveDrive(controller, SpeedIds, TurnIds, AbsEncoderIds, gyro);

  SparkMax speed = new SparkMax(1, MotorType.kBrushless);
  RelativeEncoder speed_encoder = speed.getEncoder();
  
  SparkMax turn = new SparkMax(2, MotorType.kBrushless);
  RelativeEncoder turn_encoder = turn.getEncoder();

  XboxController m_Controller = new XboxController(0);


  
  public Robot() {
  }
  
  @Override
  public void robotPeriodic() {}
  
  @Override
  public void autonomousInit() {}

  @Override
  public void autonomousPeriodic() {}

  @Override
  public void teleopInit() {
    speed_encoder.setPosition(0);
    turn_encoder.setPosition(0);

  }
  
  double lastPosition = 0;
  double lastTime = 0;

  @Override
  public void teleopPeriodic() {
    // spark.set(controller.getLeftY());
    double RPM = speed_encoder.getVelocity();
    double MPS = ((RPM / 60) / 6.75) * 0.285;
    speed.set(m_Controller.getLeftY());

    SmartDashboard.putNumber("conta_da_swerve_mps", MPS);
    
  }

  @Override
  public void disabledInit() {}

  @Override
  public void disabledPeriodic() {}

  @Override
  public void testInit() {}

  @Override
  public void testPeriodic() {}

  @Override
  public void simulationInit() {}

  @Override
  public void simulationPeriodic() {

  }
}
