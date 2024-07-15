//Agenda Object that has one method for printing a schedule for a given day
class Agenda(val meetings: List[Meeting]) {

  def printDaySchedule(day: String): Unit = {
    val meetingsForTheDay = meetings.filter(_.day == day)

    meetingsForTheDay match{
      case Nil =>
        println(s"$day:")
        println("  No meetings for the day")

      case meetings =>
        meetings.foreach { meeting =>
          val timeOfDay = if (meeting.time.endsWith("pm")) "afternoon" else "morning"
          println(s"$day $timeOfDay:")
          println(s"  ${meeting.time}: ${meeting.name}")
        }
    }

//    if (meetingsForTheDay.isEmpty) {
//      println(s"$day:")
//      println("  No meetings for the day")
//      return
//    }
//
//    for (meeting <- meetingsForTheDay) {
//      if (meeting.time.endsWith("pm")) {
//        println(s"$day afternoon:")
//        println(s"  ${meeting.time}: ${meeting.name}")
//      } else {
//        println(s"$day morning:")
//        println(s"  ${meeting.time}: ${meeting.name}")
//      }
//    }
  }
}

//Meetings Object
class Meeting(val name: String, val day: String, val time: String)

object Main extends App {
  val m1 = new Meeting("Retro", "Friday", "5pm")
  val m2 = new Meeting("Yoga", "Tuesday", "10am")
  val m3 = new Meeting("Team Meeting", "Tuesday", "3pm")
  val agenda = new Agenda(List(m1, m2, m3))
  agenda.printDaySchedule("Monday")
  agenda.printDaySchedule("Tuesday")
}