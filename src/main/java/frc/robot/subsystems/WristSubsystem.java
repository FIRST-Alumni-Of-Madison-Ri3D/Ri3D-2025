// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import java.util.function.DoubleSupplier;

import com.revrobotics.RelativeEncoder;
import com.revrobotics.spark.ClosedLoopSlot;
import com.revrobotics.spark.SparkBase;
import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkBase.ResetMode;
import com.revrobotics.spark.SparkClosedLoopController;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.config.SparkBaseConfig;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;
import com.revrobotics.spark.config.SparkMaxConfig;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.ArmConstants;
import frc.robot.Constants.WristConstants;

public class WristSubsystem extends SubsystemBase {

  private SparkMax wristMotor;

  private RelativeEncoder wristEncoder;

  private SparkBaseConfig wristConfig;

  private SparkClosedLoopController wristController;

  private double position;

  /** Creates a new WristSubsystem. */
  public WristSubsystem() {

    wristMotor = new SparkMax(WristConstants.WRIST_CAN_ID, MotorType.kBrushless);

    wristEncoder = wristMotor.getEncoder();

    wristConfig = new SparkMaxConfig();

    wristConfig.smartCurrentLimit(WristConstants.WRIST_CURRENT_LIMIT)
      .idleMode(IdleMode.kBrake)
      .inverted(true)
      .softLimit
        .forwardSoftLimit(WristConstants.WRIST_FORWARD_SOFT_LIMIT)
        .forwardSoftLimitEnabled(true)
        .reverseSoftLimit(WristConstants.WRIST_REVERSE_SOFT_LIMIT)
        .reverseSoftLimitEnabled(true);

    //Configure wrist encoder
    wristConfig.encoder
      .positionConversionFactor(WristConstants.WRIST_POSITION_CONVERSION_FACTOR);

    // Set PID constants
    wristConfig.closedLoop
      .p(WristConstants.WRIST_P, ClosedLoopSlot.kSlot0)
      .outputRange(WristConstants.WRIST_MIN_PID_OUTPUT, WristConstants.WRIST_MAX_PID_OUTPUT);

    // Set MAXMotion constants
    wristConfig.closedLoop.maxMotion
      .maxVelocity(WristConstants.WRIST_MAX_VELOCITY_DEGREES_PER_MIN)
      .maxAcceleration(WristConstants.WRIST_MAX_ACCEL_DEGREES_PER_MIN_PER_SEC)
      .allowedClosedLoopError(WristConstants.WRIST_ALLOWABLE_ERROR_DEGREES);

    wristMotor.configure(wristConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);

    wristController = wristMotor.getClosedLoopController();

    wristEncoder.setPosition(WristConstants.WRIST_DRIVE_POSITION);

    position = WristConstants.WRIST_FORWARD_SOFT_LIMIT;

  }

  // Factory commands \\

  /** Run wrist motor by output percent */
  public Command SetWristOutputPercentCommand(DoubleSupplier percent) {

    return this.run(() -> wristMotor.set(percent.getAsDouble()));

  }

  /** Set wrist motor position using controller input
   * @param position to set in degrees
   */
  public Command ManualSetWristPosition(DoubleSupplier controllerInput) {

    position += controllerInput.getAsDouble();

    return this.run(() -> wristController.setReference(position, SparkBase.ControlType.kMAXMotionPositionControl));

  }

  /** Set wrist encoder position
   * @param position to set in degrees
   */
  public Command setWristEncoderPosition(double position) {

    return this.runOnce(() -> wristEncoder.setPosition(position));

  }


  // Get methods \\

  /** Gets wrist encoder position in degrees */
  public double getWristEncoderPosition() {

    return wristMotor.getEncoder().getPosition();

  }


  @Override
  public void periodic() {
    // This method will be called once per scheduler run

  }
}
