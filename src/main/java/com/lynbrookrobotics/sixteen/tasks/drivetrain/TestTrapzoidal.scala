import com.lynbrookrobotics.sixteen.config.constants.DrivetrainConstants
import com.lynbrookrobotics.sixteen.tasks.drivetrain.TrapozoidalProfileControllerTest

object TestTrapzoidal extends App {
  lazy val startTime = System.currentTimeMillis() / 1000D
  def timePassed = System.currentTimeMillis() / 1000D - startTime

  val testObject = TrapozoidalProfileControllerTest(
    2,
    0,
    0,
    1,
    0,
    2)

  println("time passed: " + timePassed)
  while(timePassed < 5) {
    println(timePassed + "\t" + testObject.idealForwardSpeed(timePassed))
    Thread.sleep(100)
  }
}