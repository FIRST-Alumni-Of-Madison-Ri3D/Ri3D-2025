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
import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkBase.ResetMode;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.config.SparkBaseConfig;
import com.revrobotics.spark.config.SparkMaxConfig;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.ArmConstants;

public class ArmSubsystem extends SubsystemBase {

  private SparkMax armMotor;

  private RelativeEncoder armEncoder;

  private SparkBaseConfig armConfig;

  private SparkClosedLoopController armController;

  private double armGoal;
  
  /** Creates a new ArmSubsystem. */
  public ArmSubsystem() {

    armMotor = new SparkMax(ArmConstants.ARM_CAN_ID, MotorType.kBrushless);

    armEncoder = armMotor.getEncoder();

    armConfig = new SparkMaxConfig();
    //TODO update current limit after testing with intake on
    armConfig.smartCurrentLimit(ArmConstants.ARM_CURRENT_LIMIT)
      .idleMode(IdleMode.kBrake)
      .inverted(false)
      .softLimit
        .forwardSoftLimit(ArmConstants.ARM_FORWARD_SOFT_LIMIT)
        .forwardSoftLimitEnabled(true)
        .reverseSoftLimit(ArmConstants.ARM_REVERSE_SOFT_LIMIT)
        .reverseSoftLimitEnabled(true);

    //Configure arm encoder
    armConfig.encoder
      .positionConversionFactor(ArmConstants.ARM_POSITION_CONVERSION_FACTOR);

    // Set PID constants
    //TODO tune PID
    armConfig.closedLoop
      .p(ArmConstants.ARM_P, ClosedLoopSlot.kSlot0)
      .outputRange(ArmConstants.ARM_MIN_PID_OUTPUT, ArmConstants.ARM_MAX_PID_OUTPUT);

    // Set MAXMotion constants
    //TODO update after testing
    armConfig.closedLoop.maxMotion
      .maxVelocity(ArmConstants.ARM_MAX_VELOCITY_DEGREES_PER_MIN)
      .maxAcceleration(ArmConstants.ARM_MAX_ACCEL_DEGREES_PER_MIN_PER_SEC)
      .allowedClosedLoopError(ArmConstants.ARM_ALLOWABLE_ERROR_DEGREES);

    armMotor.configure(armConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);

    armController = armMotor.getClosedLoopController();

    armEncoder.setPosition(ArmConstants.ARM_REVERSE_SOFT_LIMIT);

    armGoal = ArmConstants.ARM_DRIVE_POSITION;

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

    armGoal = position;

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

  /** Returns true if arm is within allowable error range of setpoint */
  public boolean isArmAtSetpoint() {

    if(armGoal > 0) {
      return getArmEncoderPosition() < (armGoal + ArmConstants.ARM_ALLOWABLE_ERROR_DEGREES) && getArmEncoderPosition() > (armGoal - ArmConstants.ARM_ALLOWABLE_ERROR_DEGREES);
    } else {
      return false;
    }

  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run

    SmartDashboard.putNumber("Arm Position", getArmEncoderPosition());
    //TODO hold at constant position when velocity is close to 0? or when within allowable error range?
 
  }
}
