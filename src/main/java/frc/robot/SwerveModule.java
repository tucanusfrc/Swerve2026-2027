package frc.robot;

import com.ctre.phoenix6.hardware.CANcoder;
import com.revrobotics.PersistMode;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.ResetMode;
import com.revrobotics.spark.SparkClosedLoopController;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkBase.ControlType;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.config.SparkMaxConfig;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import edu.wpi.first.wpilibj.Timer;

public class SwerveModule {
    
    private final SparkMax m_Speed;
    private final SparkMax m_Turn;
    private final RelativeEncoder speedEncoder;
    private final CANcoder turnEncoder;

    private SparkMaxConfig c_Speed = new SparkMaxConfig();
    private SparkMaxConfig c_Turn = new SparkMaxConfig();
    SparkClosedLoopController controller;
        
    private final double encoderOffset;
    
    public SwerveModule(int speedId, int turnId, int encoderId, double encoderOffset){
        m_Speed = new SparkMax(speedId, MotorType.kBrushless);
        m_Turn = new SparkMax(turnId, MotorType.kBrushless);
        this.turnEncoder = new CANcoder(encoderId);
        
        speedEncoder = m_Speed.getEncoder();
        
        this.encoderOffset = encoderOffset;
        
        c_Speed.inverted(false).closedLoop.p(0).i(0).d(0);
        c_Turn.inverted(false).closedLoop.p(0).i(0).d(0);
        
        
        m_Speed.configure(
            c_Speed, 
            ResetMode.kResetSafeParameters,
            PersistMode.kPersistParameters
            );
            
            m_Speed.configure(
            c_Turn, 
            ResetMode.kResetSafeParameters,
            PersistMode.kPersistParameters
            );
            
        controller = m_Turn.getClosedLoopController();
    }
            
    public void setDesiredState(SwerveModuleState desiredState){
        
        Rotation2d currentAngle = getAngle();
        
        desiredState.optimize(currentAngle);
        
        setDriveSpeed(desiredState.speedMetersPerSecond);
        setAngle(desiredState.angle);

    }
    
    public void setDriveSpeed(double speed_mps){
        m_Speed.set(speed_mps);
    }
    
    public void setAngle(Rotation2d angle){
        controller.setSetpoint(angle.getRotations(), ControlType.kPosition);
    }
    
    private double NomalizeMetersPerSeconds(double mps){
        double RPM = speedEncoder.getVelocity();
        return ((RPM / 60) / 6.75) * 0.285;
    }
    
    public Rotation2d getAngle(){
        return Rotation2d.fromRotations(turnEncoder.getPosition().getValueAsDouble() - encoderOffset);
    }
    
    private double lastPosition = 0;
    private double lastTime = 0;
    
    public double getSpeedMetersPerSeconds(){
        double currentPosition = speedEncoder.getPosition();
        double currentTime = Timer.getFPGATimestamp();

        double deltaPosition = currentPosition - lastPosition;
        double deltaTime = currentTime - lastTime;
        lastTime += deltaTime;
        lastPosition += deltaPosition;  

        double conversor = 1 / deltaTime;

        return (deltaPosition/deltaTime) * Constants.Module.WHEEL_CIRCUNFERENCE * conversor;
    }
}