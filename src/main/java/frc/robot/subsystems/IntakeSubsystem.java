// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.config.SparkBaseConfig;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.IntakeConstants;

public class IntakeSubsystem extends SubsystemBase {

  private SparkMax algaeIntakeMotor;
  private SparkMax coralIntakeMotor;

  private SparkBaseConfig algaeConfig;
  private SparkBaseConfig coralConfig;
  
  /** Creates a new IntakeSubsystem. */
  public IntakeSubsystem() {

    //TODO confirm motor types before running
    algaeIntakeMotor = new SparkMax(IntakeConstants.ALGAE_INTAKE_CAN_ID, MotorType.kBrushless);
    coralIntakeMotor = new SparkMax(IntakeConstants.CORAL_INTAKE_CAN_ID, MotorType.kBrushed);

    algaeConfig.idleMode(IdleMode.kCoast)
      .inverted(false);

    coralConfig.idleMode(IdleMode.kCoast)
      .inverted(false);

  }

  public void setIntakePercentOutput(double percent) {

    algaeIntakeMotor.set(percent);
    coralIntakeMotor.set(percent);
    
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
