// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import static edu.wpi.first.units.Units.Inches;

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
    public static final int TESTING_CONTROLLER_PORT = 1;
  }

  public static final class DriveConstants {
    public static final int FRONT_LEFT_DRIVE_CAN_ID = 13;
    public static final int BACK_LEFT_DRIVE_CAN_ID = 12;
    public static final int FRONT_RIGHT_DRIVE_CAN_ID = 11;
    public static final int BACK_RIGHT_DRIVE_CAN_ID = 10;

    public static final double RAMP_RATE = 1;
    public static final int DRIVE_CURRENT_LIMIT = 40;

    public static final double TRACKWIDTH_METERS = 0.5588; // 22 inches
  }

  public static final class ArmConstants {
    public static final int ARM_CAN_ID = 20;

    public static final int ARM_CURRENT_LIMIT = 40;

    public static final double ARM_POSITION_CONVERSION_FACTOR = 1;

    public static final double ARM_FORWARD_SOFT_LIMIT = 10;
    public static final double ARM_REVERSE_SOFT_LIMIT = -10;

    public static final double ARM_P = 1;

    public static final double ARM_MIN_PID_OUTPUT = -1;
    public static final double ARM_MAX_PID_OUTPUT = 1;

    public static final double ARM_MAX_VELOCITY_RPM = 100;
    public static final double ARM_MAX_ACCEL_RPM_PER_SEC = 100;
    public static final double ARM_ALLOWABLE_ERROR = 1;
  }

  public static final class WristConstants {
    public static final int WRIST_CAN_ID = 30;

    public static final int WRIST_CURRENT_LIMIT = 40;

    public static final double WRIST_POSITION_CONVERSION_FACTOR = 1;

    public static final double WRIST_FORWARD_SOFT_LIMIT = 10;
    public static final double WRIST_REVERSE_SOFT_LIMIT = -10;

    public static final double WRIST_P = 1;

    public static final double WRIST_MIN_PID_OUTPUT = -1;
    public static final double WRIST_MAX_PID_OUTPUT = 1;

    public static final double WRIST_MAX_VELOCITY_RPM = 100;
    public static final double WRIST_MAX_ACCEL_RPM_PER_SEC = 100;
    public static final double WRIST_ALLOWABLE_ERROR = 1;
  }

  public static final class IntakeConstants {

  }
}
