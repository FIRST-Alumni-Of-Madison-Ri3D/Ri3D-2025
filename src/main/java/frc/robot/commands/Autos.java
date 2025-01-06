// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import frc.robot.subsystems.DriveSubsystem;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.WaitCommand;

public final class Autos {

  /** Drives forward to get off the starting line */
  public static Command driveForwardAuto(DriveSubsystem driveSubsystem) {

    return Commands.sequence(driveSubsystem.DriveConstantSpeedCommand(0.3), new WaitCommand(2), driveSubsystem.StopDriveCommand());

  }

  private Autos() {
    throw new UnsupportedOperationException("This is a utility class!");
  }
}
