// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.config.SparkBaseConfig;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.IntakeConstants;

public class IntakeSubsystem extends SubsystemBase {

  private SparkMax intakeMotor;

  private SparkBaseConfig intakeConfig;
  
  /** Creates a new IntakeSubsystem. */
  public IntakeSubsystem() {

    //TODO confirm motor type before running
    intakeMotor = new SparkMax(IntakeConstants.INTAKE_CAN_ID, MotorType.kBrushless);

    intakeConfig.idleMode(IdleMode.kCoast)
      .inverted(false);

  }

  public Command SetIntakePercentOutput(double percent) {

    return this.runOnce(()-> intakeMotor.set(percent));
    
  }

  public void stopIntake() {

    intakeMotor.stopMotor();

  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run

    //TODO update stop current based on testing
    if(intakeMotor.getOutputCurrent() > IntakeConstants.INTAKE_STOP_CURRENT) {
      
      stopIntake();

    }

  }
}
