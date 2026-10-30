// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.XboxController;

public class Robot extends TimedRobot {

  XboxController controller = new XboxController(0);

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

  Gyro gyro = new Gyro();
  SwerveDrive drive = new SwerveDrive(controller, SpeedIds, TurnIds, AbsEncoderIds, gyro);

  XboxController m_Controller = new XboxController(0);


  
  public Robot() {
  }
  
  @Override
  public void robotPeriodic() {
    drive.printOdometry();
  }
  
  @Override
  public void autonomousInit() {}

  @Override
  public void autonomousPeriodic() {}

  @Override
  public void teleopInit() {
    drive.setAllAnglesZero();

  }
  
  double lastPosition = 0;
  double lastTime = 0;

  @Override
  public void teleopPeriodic() {
    drive.run();
    if (controller.getAButton()){
      drive.setAllAnglesZero();
    }
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
