import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

class ReceiptPrinterSpec extends AnyWordSpec with Matchers {
  val coffeeConnectionCafe = new CafeDetails(
    "The Coffee Connection",
    "123 Lakeside Way",
    "16503600708",
    Map(
      "Cafe Latte" -> 4.75,
      "Flat White" -> 4.75,
      "Cappuccino" -> 3.85,
      "Single Espresso" -> 2.05,
      "Double Espresso" -> 3.75,
      "Americano" -> 3.75,
      "Cortado" -> 4.55,
      "Tea" -> 3.65,
      "Choc Mudcake" -> 6.40,
      "Choc Mousse" -> 8.20,
      "Affogato" -> 14.80,
      "Tiramisu" -> 11.40,
      "Blueberry Muffin" -> 4.05,
      "Chocolate Chip Muffin" -> 4.05,
      "Muffin Of The Day" -> 4.55
    )
  )
  //Description
  "A ReceiptPrinter" should {
    "format a receipt" which {

      "contains the name, address and phone number of the cafe" in {
        //Test body: Create an instance
        val printer = new ReceiptPrinter(
          coffeeConnectionCafe,
          Map("Cafe Latte" -> 1)
        )
        //Assertions
        printer.receipt should include("The Coffee Connection")
        printer.receipt should include("123 Lakeside Way")
        printer.receipt should include("16503600708")
      }

      "contains the ordered item, quantity, and subtotal for 1 Cafe Latte" in {
        val printer = new ReceiptPrinter(
          coffeeConnectionCafe,
          Map("Cafe Latte" -> 1)
        )
        printer.receipt should include("Cafe Latte: 1 x 4.75 $4.75")
      }

      "contains the order item, quanitity and subtotal for 2 Cafe Lattes" in {
        val printer = new ReceiptPrinter(
          coffeeConnectionCafe,
          Map("Cafe Latte" -> 2)
        )
        printer.receipt should include("Cafe Latte: 2 x 4.75 $9.50")
      }

      "contains multiple orders" in {
        val printer = new ReceiptPrinter(
          coffeeConnectionCafe,
          Map("Cafe Latte" -> 2, "Flat White" -> 1, "Muffin Of The Day" -> 1)
        )
        printer.receipt should include("Cafe Latte: 2 x 4.75 $9.50")
        printer.receipt should include("Flat White: 1 x 4.75 $4.75")
        printer.receipt should include("Muffin Of The Day: 1 x 4.55 $4.55")
      }

      "contains the total" in {
        val printer = new ReceiptPrinter(
          coffeeConnectionCafe,
          Map("Cafe Latte" -> 2, "Flat White" -> 1, "Muffin Of The Day" -> 1)
        )
        printer.receipt should include("Total: $18.80")
      }

      "doesn't have the order item in the menu" in {
        val printer = new ReceiptPrinter(
          coffeeConnectionCafe,
          Map("Hot Chocolate" -> 1)
        )
        printer.receipt should not include ("Hot Chocolate")
      }
    }
  }

  "A Till" should {
    "show a menu" that {
      "contains all the menu items" in {
        val till = new Till(
          coffeeConnectionCafe
        )
        till.showMenu should include("Cafe Latte: $4.75")
        till.showMenu should include("Flat White: $4.75")
      }
    }

    "handle orders" that {
      "contains a single valid item from the menu" in {
        val till = new Till(
          coffeeConnectionCafe
        )

        till.addOrder(Map("Cafe Latte" -> 1))
        val orderResult = till.showOrder
        orderResult should include("Cafe Latte: 1")
      }

      "contains multiple valid items from the menu" in {
        val till = new Till(
          coffeeConnectionCafe
        )

        till.addOrder(Map("Cafe Latte" -> 1, "Cappuccino" -> 1))
        val orderResult = till.showOrder
        orderResult should include("Cafe Latte: 1")
        orderResult should include("Cappuccino: 1")
      }

      "contains multiple orders with valid items form the menu" in {
        val till = new Till(
          coffeeConnectionCafe
        )

        till.addOrder(Map("Cafe Latte" -> 1, "Cappuccino" -> 1))
        till.addOrder(Map("Single Espresso" -> 1, "Double Espresso" -> 1))
        val orderResult = till.showOrder
        orderResult should include("Cafe Latte: 1")
        orderResult should include("Cappuccino: 1")
        orderResult should include("Single Espresso: 1")
        orderResult should include("Double Espresso: 1")
      }

      "contains an item that doesn't exist in the menu" in {
        val till = new Till(
          coffeeConnectionCafe
        )
        till.addOrder(Map("Cafe Latte" -> 1))
        val orderResult = till.showOrder
        orderResult should include("Cafe Latte: 1")

        try {
          till.addOrder(Map("Hot Chocolate" -> 1)) // Throws IllegalArgumentException
        } catch {
          case e: IllegalArgumentException => println(e.getMessage) // Output: Invalid items: Mocha
        }
      }
    }
    "print the receipt" when {
      "the order is finalised" in {
        val till = new Till(
          coffeeConnectionCafe
        )
        till.addOrder(Map("Cafe Latte" ->1, "Cappuccino" -> 1, "Single Espresso" ->2))
        val receipt = till.printReceipt
        receipt should include("Cafe Latte: 1 x 4.75 $4.75")
        receipt should include("Cappuccino: 1 x 3.85 $3.85")
        receipt should include("Single Espresso: 2 x 2.05 $4.10")
        receipt should include("Total: $12.7")
      }
    }
  }
}
//Description: 'should', 'must', "can",
//Context and Conditions: "which", "when", "that"
//Test body: "in"
