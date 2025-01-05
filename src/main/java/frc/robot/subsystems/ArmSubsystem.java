// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import java.util.function.DoubleSupplier;

import com.revrobotics.RelativeEncoder;
import com.revrobotics.spark.ClosedLoopSlot;
import com.revrobotics.spark.SparkBase;
import com.revrobotics.spark.SparkClosedLoopController;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkRelativeEncoder;
import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkBase.ResetMode;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.config.SparkBaseConfig;
import com.revrobotics.spark.config.SparkMaxConfig;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.ArmConstants;

public class ArmSubsystem extends SubsystemBase {

  private SparkMax armMotor;

  private RelativeEncoder armEncoder;

  private SparkBaseConfig armConfig;

  private SparkClosedLoopController armController;
  
  /** Creates a new ArmSubsystem. */
  public ArmSubsystem() {

    armMotor = new SparkMax(ArmConstants.ARM_CAN_ID, MotorType.kBrushless);

    armEncoder = armMotor.getEncoder();

    armConfig = new SparkMaxConfig();
    //TODO update current limit after testing
    //TODO check if needs to be inverted
    //TODO update soft limits after testing
    armConfig.smartCurrentLimit(ArmConstants.ARM_CURRENT_LIMIT)
      .idleMode(IdleMode.kBrake)
      .softLimit
        .forwardSoftLimit(ArmConstants.ARM_FORWARD_SOFT_LIMIT)
        .reverseSoftLimit(ArmConstants.ARM_REVERSE_SOFT_LIMIT);

    //Configure arm encoder
    //TODO update position conversion factor once chain ratio is determined
    armConfig.encoder
      .positionConversionFactor(ArmConstants.ARM_POSITION_CONVERSION_FACTOR);

    // Set PID constants
    armConfig.closedLoop
      .p(ArmConstants.ARM_P, ClosedLoopSlot.kSlot0)
      //TODO update output range once we figure out what units we're using
      .outputRange(ArmConstants.ARM_MIN_PID_OUTPUT, ArmConstants.ARM_MAX_PID_OUTPUT);

    // Set MAXMotion constants
    //TODO update after testing
    armConfig.closedLoop.maxMotion
      .maxVelocity(ArmConstants.ARM_MAX_VELOCITY_RPM)
      .maxAcceleration(ArmConstants.ARM_MAX_ACCEL_RPM_PER_SEC)
      .allowedClosedLoopError(ArmConstants.ARM_ALLOWABLE_ERROR);

    armMotor.configure(armConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);

    armController = armMotor.getClosedLoopController();

  }

  // Factory commands \\

  /** Run arm motor by output percent */
  public Command SetArmOutputPercentCommand(DoubleSupplier percent) {

    return this.run(() -> armMotor.set(percent.getAsDouble()));

  }

  /** Set arm motor position
   * @param position to set in degrees
   */
  public Command SetArmPosition(double position) {

    return this.runOnce(() -> armController.setReference(position, SparkBase.ControlType.kMAXMotionPositionControl));

  }

  /** Set arm encoder position
   * @param position to set in degrees
   */
  public Command setArmEncoderPosition(double position) {

    return this.runOnce(() -> armEncoder.setPosition(position));

  }


  // Get methods \\

  /** Gets arm encoder position in degrees */
  public double getArmEncoderPosition() {

    return armMotor.getEncoder().getPosition();

  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
