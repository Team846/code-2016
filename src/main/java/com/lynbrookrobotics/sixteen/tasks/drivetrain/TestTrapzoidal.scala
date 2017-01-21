import java.util

import com.lynbrookrobotics.sixteen.tasks.drivetrain.TrapozoidalProfileControllerTest

object TestTrapzoidal extends App {
  lazy val startTime = System.currentTimeMillis() / 1000D

  def timePassed = System.currentTimeMillis() / 1000D - startTime

  val initVelocity = 0D
  val finalVelocity = 0D
  val MaxVelocity = 4
  val Acceleration = 0.5
  val InitPosition = 0
  val finalPosition = 10

  var runningSum = 0.0

  val uselessObject = TrapozoidalProfileControllerTest(
    Acceleration,
    initVelocity,
    finalVelocity,
    MaxVelocity,
    InitPosition,
    finalPosition,
    () => throw new RuntimeException("pls don't use this"))

  val position: () => Double = () => {
    if( uselessObject.previousVelocity >= 0) {
      val currAccel = Acceleration * (1 - uselessObject.previousVelocity / MaxVelocity)
      runningSum += 0.1 * currAccel
      runningSum
    } else {
      runningSum += 0.1 * Acceleration
      runningSum
    }
  }

  val testObject = TrapozoidalProfileControllerTest(
    Acceleration,
    initVelocity,
    finalVelocity,
    MaxVelocity,
    InitPosition,
    finalPosition,
    position)

  val velocities: util.LinkedList[String] = new util.LinkedList[String]

//  println("time passed: " + timePassed)
  while(testObject.idealForwardSpeed(timePassed).isDefined) {
    velocities.add(timePassed + "\t" + testObject.forwardSpeed)
    println(timePassed + "\t" + testObject.idealForwardSpeed(timePassed).get)
    Thread.sleep(200)
  }

  println("break")

  while (velocities.size() != 0) {
    println(velocities.pop())
  }
}