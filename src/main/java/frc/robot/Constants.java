package frc.robot;

import edu.wpi.first.math.geometry.Translation2d;

public class Constants {
    
    public static final class Module {

        public static final double WHEEL_CIRCUNFERENCE = 0.286; // VERIFICADO
        public static final double VOLTAGE_METERS_PER_SECOND = 18; 
        public static final double TURN_GEAR_RELATION = 150/7; // VERIFICADO
        public static final double SPEED_GEAR_RELATION = 6.25; // VERIFICADO

    }

    public static final class SwerveDrive {

        public static final int FRONT_LEFT_SPEED_ID = 1; // VERIFICADO
        public static final int FRONT_RIGHT_SPEED_ID = 3; // VERIFICADO
        public static final int BACK_RIGHT_SPEED_ID = 5; // VERIFICADO
        public static final int BACK_LEFT_SPEED_ID = 7; // VERIFICADO

        public static final int FRONT_LEFT_TURN_ID = 2; // VERIFICADO
        public static final int FRONT_RIGHT_TURN_ID = 4; // VERIFICADO
        public static final int BACK_RIGHT_TURN_ID = 6; // VERIFICADO
        public static final int BACK_LEFT_TURN_ID = 8; // VERIFICADO
       
        public static final int FRONT_LEFT_ENCODER_ID = 1; // VERIFICADO
        public static final int FRONT_RIGHT_ENCODER_ID = 2; // VERIFICADO
        public static final int BACK_RIGHT_ENCODER_ID = 3; // VERIFICADO
        public static final int BACK_LEFT_ENCODER_ID = 4; // VERIFICADO

        public static final Translation2d FRONT_LEFT_POSITION = new Translation2d(-0.3, 0.3); // VERIFICADO
        public static final Translation2d FRONT_RIGHT_POSITION = new Translation2d(0.3, 0.3); // VERIFICADO
        public static final Translation2d BACK_RIGHT_POSITION = new Translation2d(0.3, -0.3); // VERIFICADO
        public static final Translation2d BACK_LEFT_POSITION = new Translation2d(-0.3, -0.3); // VERIFICADO

        public static final double CONTROLLER_DEAD_BAND = 0.05; //VERIFICADO
    }

}   
