object FizzBuzz {
  def generate(number: Int): String = {
    val numList = List.range(1, number + 1)
    val newList = numList.map {
      case x if x % 15 == 0 => "FizzBuzz"
      case x if x % 3 == 0 => "Fizz"
      case x if x % 5 == 0 => "Buzz"
      case x => x.toString
    }
    return newList.mkString(", ")
  }
}

// get a list up to the number
// mutate the list
// turn it into a string

// if x % 3 === 0 -> Fizz
// if x % 5 === 0 -> Buzz
// if both -> FizzBuzz

//Turning an array to a string
//    return a.mkString(",")
