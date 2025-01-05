// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.revrobotics.spark.SparkMax;

import java.util.function.DoubleSupplier;

import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkBase.ResetMode;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.config.SparkBaseConfig;
import com.revrobotics.spark.config.SparkMaxConfig;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;

import edu.wpi.first.math.kinematics.DifferentialDriveKinematics;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.DriveConstants;

public class DriveSubsystem extends SubsystemBase {

  private SparkMax frontLeftMotor;
  private SparkMax backLeftMotor;
  private SparkMax frontRightMotor;
  private SparkMax backRightMotor;

  private SparkBaseConfig frontLeftConfig;
  private SparkBaseConfig backLeftConfig;
  private SparkBaseConfig frontRightConfig;
  private SparkBaseConfig backRightConfig;

  private DifferentialDrive differentialDrive;

  /** Creates a new DriveSubsystem. */
  public DriveSubsystem() {

    frontLeftMotor = new SparkMax(DriveConstants.FRONT_LEFT_DRIVE_CAN_ID, MotorType.kBrushed);
    backLeftMotor = new SparkMax(DriveConstants.BACK_LEFT_DRIVE_CAN_ID, MotorType.kBrushed);
    frontRightMotor = new SparkMax(DriveConstants.FRONT_RIGHT_DRIVE_CAN_ID, MotorType.kBrushed);
    backRightMotor = new SparkMax(DriveConstants.BACK_RIGHT_DRIVE_CAN_ID, MotorType.kBrushed);

    frontLeftConfig = new SparkMaxConfig();
    backLeftConfig = new SparkMaxConfig();
    frontRightConfig = new SparkMaxConfig();
    backRightConfig = new SparkMaxConfig();

    frontLeftConfig.closedLoopRampRate(DriveConstants.RAMP_RATE)
      .smartCurrentLimit(DriveConstants.DRIVE_CURRENT_LIMIT)
      .idleMode(IdleMode.kBrake)
      .inverted(true);

    backLeftConfig.closedLoopRampRate(DriveConstants.RAMP_RATE)
      .smartCurrentLimit(DriveConstants.DRIVE_CURRENT_LIMIT)
      .idleMode(IdleMode.kBrake)
      .follow(DriveConstants.FRONT_LEFT_DRIVE_CAN_ID, false);

    frontRightConfig.closedLoopRampRate(DriveConstants.RAMP_RATE)
      .smartCurrentLimit(DriveConstants.DRIVE_CURRENT_LIMIT)
      .idleMode(IdleMode.kBrake)
      .inverted(false);

    backRightConfig.closedLoopRampRate(DriveConstants.RAMP_RATE)
      .smartCurrentLimit(DriveConstants.DRIVE_CURRENT_LIMIT)
      .idleMode(IdleMode.kBrake)
      .follow(DriveConstants.FRONT_RIGHT_DRIVE_CAN_ID, false);

    frontLeftMotor.configure(frontLeftConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
    backLeftMotor.configure(backLeftConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
    frontRightMotor.configure(frontRightConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
    backRightMotor.configure(backRightConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);

    differentialDrive = new DifferentialDrive(frontLeftMotor, frontRightMotor);

  }

  /** Drive command with input squaring */
  public Command DriveCommand(DoubleSupplier xSpeed, DoubleSupplier zRotation) {
  
    return this.run(() -> differentialDrive.arcadeDrive(xSpeed.getAsDouble(), zRotation.getAsDouble(), true));

  }

  /** Stop command for autos */
  public Command StopDriveCommand() {

    return this.runOnce(() -> differentialDrive.tankDrive(0, 0));

  }

  /** Set speed command for autos
   * 
   * @param speed from -1 to 1
   */
  public Command DriveConstantSpeedCommand(double speed) {

    return this.run(() -> differentialDrive.tankDrive(speed, speed));

  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run

  }
}