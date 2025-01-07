// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import frc.robot.Constants.ArmConstants;
import frc.robot.Constants.DriveConstants;
import frc.robot.Constants.IntakeConstants;
import frc.robot.Constants.OperatorConstants;
import frc.robot.Constants.WristConstants;
import frc.robot.commands.Autos;
import frc.robot.subsystems.ArmSubsystem;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.IntakeSubsystem;
import frc.robot.subsystems.WristSubsystem;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.ParallelDeadlineGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import edu.wpi.first.wpilibj2.command.WaitUntilCommand;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.Trigger;

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and trigger mappings) should be declared here.
 */
public class RobotContainer {
  // The robot's subsystems and commands are defined here...
  private final DriveSubsystem driveSubsystem = new DriveSubsystem();
  private final ArmSubsystem armSubsystem = new ArmSubsystem();
  private final WristSubsystem wristSubsystem = new WristSubsystem();
  private final IntakeSubsystem intakeSubsystem = new IntakeSubsystem();

  // Replace with CommandPS4Controller or CommandJoystick if needed
  private final CommandXboxController driverController =
      new CommandXboxController(OperatorConstants.DRIVER_CONTROLLER_PORT);

  private final CommandXboxController operatorController =
      new CommandXboxController(OperatorConstants.OPERATOR_CONTROLLER_PORT);


  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  public RobotContainer() {

    driveSubsystem.setDefaultCommand(driveSubsystem.DriveCommand(() -> (-driverController.getLeftY() * DriveConstants.DRIVE_NERF), () -> (-driverController.getRightX() * DriveConstants.DRIVE_TURN_NERF)));

    armSubsystem.setDefaultCommand(armSubsystem.SetArmOutputPercentCommand(() -> (-operatorController.getLeftY() * 0.35)));

    wristSubsystem.setDefaultCommand(wristSubsystem.SetWristOutputPercentCommand(() -> (-operatorController.getRightY() * 0.35)));

    // Configure the trigger bindings
    configureBindings();

  }

  /**
   * Use this method to define your trigger->command mappings. Triggers can be created via the
   * {@link Trigger#Trigger(java.util.function.BooleanSupplier)} constructor with an arbitrary
   * predicate, or via the named factories in {@link
   * edu.wpi.first.wpilibj2.command.button.CommandGenericHID}'s subclasses for {@link
   * CommandXboxController Xbox}/{@link edu.wpi.first.wpilibj2.command.button.CommandPS4Controller
   * PS4} controllers or {@link edu.wpi.first.wpilibj2.command.button.CommandJoystick Flight
   * joysticks}.
   */
  private void configureBindings() {

    // Don't have coral station pickup set up yet

    // 2 controller setup
    // Driver: L joystick - forward/back, R joystick - spin, LB - floor coral intake, LT - floor algae intake, RB - score coral/back to drive, RT - score algae/back to drive
    // Operator: A - L1, B - L2, X - L3, Y - L4, RB - processor, RT - barge, LB - low algae intake, LT - high algae intake


    // Driver controller buttons \\

    // Score coral/intake algae, then return to drive on release
    driverController.rightBumper().whileTrue(intakeSubsystem.SetIntakePercentOutput(IntakeConstants.INTAKE_ALGAE_SCORE_CORAL_SPEED));
    driverController.rightBumper().onFalse(intakeSubsystem.StopIntakeCommand());

    // Score algae/intake coral, then return to drive on release
    // Faster to try to shoot into barge
    driverController.rightTrigger().whileTrue(intakeSubsystem.SetIntakePercentOutput(IntakeConstants.INTAKE_CORAL_SCORE_ALGAE_SPEED));
    driverController.rightTrigger().onFalse(intakeSubsystem.StopIntakeCommand());

  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    // An example command will be run in autonomous
    return Autos.driveForwardAuto(driveSubsystem);
  }
}
