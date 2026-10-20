package frc.robot;

import com.ctre.phoenix6.hardware.Pigeon2;

import edu.wpi.first.math.geometry.Rotation2d;

public class Gyro {
    
    private Pigeon2 pigeon2;

    public Gyro(){
        pigeon2 = new Pigeon2(4);
    }

    public Rotation2d getYawAsRotation2d(){
        return Rotation2d.fromDegrees(pigeon2.getYaw().getValueAsDouble()); 
    }

}
