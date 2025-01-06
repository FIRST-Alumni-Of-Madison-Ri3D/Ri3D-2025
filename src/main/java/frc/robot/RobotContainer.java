// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import frc.robot.Constants.ArmConstants;
import frc.robot.Constants.DriveConstants;
import frc.robot.Constants.IntakeConstants;
import frc.robot.Constants.OperatorConstants;
import frc.robot.Constants.RobotStates;
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

  private final CommandXboxController testingController = 
      new CommandXboxController(OperatorConstants.TESTING_CONTROLLER_PORT);

  public static RobotStates robotState;

  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  public RobotContainer() {

    driveSubsystem.setDefaultCommand(driveSubsystem.DriveCommand(() -> -driverController.getLeftY(), () -> (driverController.getRightX() * DriveConstants.DRIVE_TURN_NERF)));

    armSubsystem.setDefaultCommand(armSubsystem.SetArmOutputPercentCommand(() -> (-testingController.getLeftY() * 0.35)));

    wristSubsystem.setDefaultCommand(wristSubsystem.SetWristOutputPercentCommand(() -> (-testingController.getRightY() * 0.5)));

    // Configure the trigger bindings
    configureBindings();

    robotState = RobotStates.DRIVE;
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

    testingController.a().whileTrue(intakeSubsystem.SetIntakePercentOutput(0.5));
    testingController.a().onFalse(new InstantCommand(() -> intakeSubsystem.stopIntake()));

    testingController.b().whileTrue(intakeSubsystem.SetIntakePercentOutput(-0.5));
    testingController.b().onFalse(new InstantCommand(() -> intakeSubsystem.stopIntake()));

    // Don't have coral station pickup set up yet

    // 2 controller setup
    // Driver: L joystick - forward/back, R joystick - spin, LB - floor coral intake, LT - algae intake (map floor, low, high), RB - score/back to drive state (map)
    // Operator: A - L1, B - L2, X - L3, Y - L4, RB - processor, RT - barge, LB - low algae intake, LT - high algae intake


    // Driver controller buttons

    /*
    driverController.leftBumper().onTrue(new SequentialCommandGroup(new InstantCommand(() -> robotState = RobotStates.FLOOR_INTAKE_CORAL),
      new ParallelDeadlineGroup(new WaitUntilCommand(() -> armSubsystem.isArmAtSetpoint()), armSubsystem.SetArmPosition(ArmConstants.ARM_FLOOR_CORAL_POSITION)),
      new ParallelDeadlineGroup(new WaitUntilCommand(() -> wristSubsystem.isWristAtSetpoint()), wristSubsystem.SetWristPosition(WristConstants.WRIST_FLOOR_CORAL_POSITION)),
      intakeSubsystem.SetIntakePercentOutput(IntakeConstants.INTAKE_CORAL_SPEED)));
    */


    // Operator controller buttons
    operatorController.a().onTrue(new InstantCommand(() -> robotState = RobotStates.L1_CORAL));
    operatorController.b().onTrue(new InstantCommand(() -> robotState = RobotStates.L2_CORAL));
    operatorController.x().onTrue(new InstantCommand(() -> robotState = RobotStates.L3_CORAL));
    operatorController.y().onTrue(new InstantCommand(() -> robotState = RobotStates.L4_CORAL));
    
    operatorController.rightBumper().onTrue(new InstantCommand(() -> robotState = RobotStates.PROCESSOR));
    operatorController.rightTrigger().onTrue(new InstantCommand(() -> robotState = RobotStates.BARGE));

    operatorController.leftBumper().onTrue(new InstantCommand(() -> robotState = RobotStates.LOW_INTAKE_ALGAE));
    operatorController.leftTrigger().onTrue(new InstantCommand(() -> robotState = RobotStates.HIGH_INTAKE_ALGAE));

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
