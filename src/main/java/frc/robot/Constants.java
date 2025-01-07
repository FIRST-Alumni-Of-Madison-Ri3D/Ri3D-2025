// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide numerical or boolean
 * constants. This class should not be used for any other purpose. All constants should be declared
 * globally (i.e. public static). Do not put anything functional in this class.
 *
 * <p>It is advised to statically import this class (or one of its inner classes) wherever the
 * constants are needed, to reduce verbosity.
 */
public final class Constants {
  public static class OperatorConstants {
    public static final int DRIVER_CONTROLLER_PORT = 0;
    public static final int OPERATOR_CONTROLLER_PORT = 1;
  }

  public static final class DriveConstants {
    public static final int FRONT_LEFT_DRIVE_CAN_ID = 13;
    public static final int BACK_LEFT_DRIVE_CAN_ID = 12;
    public static final int FRONT_RIGHT_DRIVE_CAN_ID = 11;
    public static final int BACK_RIGHT_DRIVE_CAN_ID = 10;

    public static final double RAMP_RATE = 1;
    public static final int DRIVE_CURRENT_LIMIT = 40;

    public static final double TRACKWIDTH_METERS = 0.5588; // 22 inches

    public static final double DRIVE_NERF = 0.6;
    public static final double DRIVE_TURN_NERF = 0.6;
  }

  public static final class ArmConstants {
    public static final int ARM_CAN_ID = 20;

    public static final int ARM_CURRENT_LIMIT = 40;

    // Convert rotations to degrees, equal to 360/300 (300 is ratio between motor and arm)
    // This conversion does not appear to be correctly converting rotations to degrees, might actually be right after testing again
    public static final double ARM_POSITION_CONVERSION_FACTOR = 1.2;

    public static final double ARM_FORWARD_SOFT_LIMIT = 95;
    public static final double ARM_REVERSE_SOFT_LIMIT = -55;

    public static final double ARM_P = 0.1;

    public static final double ARM_MIN_PID_OUTPUT = -0.5;
    public static final double ARM_MAX_PID_OUTPUT = 0.5;

    public static final double ARM_MAX_VELOCITY_DEGREES_PER_MIN = 2400;
    public static final double ARM_MAX_ACCEL_DEGREES_PER_MIN_PER_SEC = 2400;
    public static final double ARM_ALLOWABLE_ERROR_DEGREES = 2;

    // Arm position constants
    // 0 degrees is when the arm is horizontal and facing forward
    public static final double ARM_DRIVE_POSITION = -55; // Same as reverse soft limit
    public static final double ARM_FLOOR_ALGAE_POSITION = -47.9;
    public static final double ARM_LOW_ALGAE_POSITION = -39.8;
    public static final double ARM_HIGH_ALGAE_POSITION = -11.4;
    public static final double ARM_FLOOR_CORAL_POSITION = -43;
    public static final double ARM_L1_POSITION = -45.3;
    public static final double ARM_L2_POSITION = -22.3;
    public static final double ARM_L3_POSITION = -9;
    public static final double ARM_L4_POSITION = 24;
    public static final double ARM_PROCESSOR_POSITION = -46;
    public static final double ARM_BARGE_POSITION = 24;
  }

  public static final class WristConstants {
    public static final int WRIST_CAN_ID = 30;

    public static final int WRIST_CURRENT_LIMIT = 40;

    // Convert rotations to degrees, equal to 360/50.14 (50.14 is ratio between motor and wrist)
    public static final double WRIST_POSITION_CONVERSION_FACTOR = 7.18;

    public static final double WRIST_FORWARD_SOFT_LIMIT = 0;
    public static final double WRIST_REVERSE_SOFT_LIMIT = -185;

    public static final double WRIST_P = 0.1;

    public static final double WRIST_MIN_PID_OUTPUT = -1;
    public static final double WRIST_MAX_PID_OUTPUT = 1;

    public static final double WRIST_MAX_VELOCITY_DEGREES_PER_MIN = 10000;
    public static final double WRIST_MAX_ACCEL_DEGREES_PER_MIN_PER_SEC = 10000;
    public static final double WRIST_ALLOWABLE_ERROR_DEGREES = 1;

    // Wrist position constants
    // 0 is initial position when stored at the start of the match
    public static final double WRIST_DRIVE_POSITION = 0;
    public static final double WRIST_FLOOR_ALGAE_POSITION = -111.3;
    public static final double WRIST_LOW_ALGAE_POSITION = -75.6;
    public static final double WRIST_HIGH_ALGAE_POSITION = -139.1;
    public static final double WRIST_FLOOR_CORAL_POSITION = -75.7;
    public static final double WRIST_L1_POSITION = -46.8;
    public static final double WRIST_L2_POSITION = -118.9;
    public static final double WRIST_L3_POSITION = -120.5;
    public static final double WRIST_L4_POSITION = -181;
    public static final double WRIST_PROCESSOR_POSITION = -68.7;
    public static final double WRIST_BARGE_POSITION = 0;
  }

  public static final class IntakeConstants {
    public static final int INTAKE_CAN_ID = 40;

    public static final double INTAKE_STOP_CURRENT = 80;

    public static final double INTAKE_ALGAE_SCORE_CORAL_SPEED = -0.5;
    public static final double INTAKE_CORAL_SCORE_ALGAE_SPEED = 0.5;

  }
}
