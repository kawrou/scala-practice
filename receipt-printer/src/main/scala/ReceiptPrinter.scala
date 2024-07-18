class CafeDetails(val shopName: String, val address: String, val phone: String, val prices: Map[String, Double])


//Write receipt method
//val printer = new ReceiptPrinter(coffeeConnectionCafe, Map("Cafe Latte" -> 1))
// ====>
//printer.receipt = "The Coffee Connection, Cafe Latte, 4.75"

//We have an order (Map object, item -> quantity)
//We have a menu (Map object, item -> price)
//We want to find the order in the menu and get the price
//Use the key in our order and find the key in the menu,
//Use the value in the menu and multiply it to the value in the order to get total
//We want to return a string of the order + total

//val order: Map[String, Int] = Map()
class ReceiptPrinter(val cafe: CafeDetails) {
  def printReceipt(order: Map[String, Int]): String = receipt(order)

  private def formatDecimalPlaces(subtotal: Double): String = {
    f"$subtotal%.2f"
  }

  private def findCostsDetails(order: Map[String, Int]) = {
    for {
      key <- order.keys
      quantity <- order.get(key)
      price <- cafe.prices.get(key)
    } yield (key, quantity, price, price * quantity)
  }

  private def formatCostsDetails(order: Map[String, Int]) = findCostsDetails(order).map {
    case (key, quantity, price, subtotal) =>
      s"${key}: ${quantity} x ${price} $$${formatDecimalPlaces(subtotal)}"
  }.mkString("\n")

  private def calculateTotal(order: Map[String, Int]) = findCostsDetails(order).map {
    case (_, _, _, subtotal) => subtotal
  }.sum

  private def formatTotal(order: Map[String, Int]) = s"Total: $$${formatDecimalPlaces(calculateTotal(order))}"

  def receipt(order: Map[String, Int]): String = {
    s"""
       |${cafe.shopName}
       |${cafe.address}
       |${cafe.phone}
       |${formatCostsDetails(order)}
       |${formatTotal(order)}
       |""".stripMargin
  }
}

//I want to return a menu
//I want to add an order to my orders if the item exists on the menu
//I want to print out a receipt once orders are finalized

class Till(val cafe: CafeDetails, val receiptPrinter: ReceiptPrinter ) {
  //  var order = Map[String, Int]()
  private var order: Map[String, Int] = Map()

  def showMenu: String = cafe.prices.map {
    case (item, price) => s"${item}: $$${price}"
  }.mkString("\n")

  private def findItemFromMenu(order: Map[String, Int]) = order.filter {
    case (item, _) => cafe.prices.contains(item)
  }

  private def handleItemsNotInMenu(invalidItems: Set[String]) = if (invalidItems.nonEmpty) {
    throw new IllegalArgumentException(s"Invalid items: ${invalidItems.mkString(", ")}")
  }

  def addOrder(items: Map[String, Int]) = {
    val validMenuItems: Map[String, Int] = findItemFromMenu(items)

    val invalidMenuItems: Set[String] = items.keySet -- validMenuItems.keySet
    handleItemsNotInMenu(invalidMenuItems)

    order ++= validMenuItems
  }

  def showOrder = order.map {
    case (item, quantity) => s"$item: $quantity"
  }.mkString("")

  def printReceipt: String = {
//    val printer = new ReceiptPrinter(cafe, order)
//    printer.receipt
    receiptPrinter.printReceipt(order)
  }
}