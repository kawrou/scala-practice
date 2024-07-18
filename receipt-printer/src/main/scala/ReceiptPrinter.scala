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

class ReceiptPrinter(val cafe: CafeDetails, val order: Map[String, Int] = Map()) {

  private def formatDecimalPlaces(subtotal: Double): String = {
    f"$subtotal%.2f"
  }

  private def findCostsDetails = {
    for {
      key <- order.keys
      quantity <- order.get(key)
      price <- cafe.prices.get(key)
    } yield (key, quantity, price, price * quantity)
  }

  private def formatCostsDetails = findCostsDetails.map {
    case (key, quantity, price, subtotal) =>
      s"${key}: ${quantity} x ${price} $$${formatDecimalPlaces(subtotal)}"
  }.mkString("\n")

  private def calculateTotal = findCostsDetails.map {
    case (_, _, _, subtotal) => subtotal
  }.sum

  private def formatTotal = s"Total: $$${formatDecimalPlaces(calculateTotal)}"

  def receipt: String = {
    s"""
       |${cafe.shopName}
       |${cafe.address}
       |${cafe.phone}
       |${formatCostsDetails}
       |${formatTotal}
       |""".stripMargin
  }
}

//I want to return a menu
//I want to add an order to my orders if the item exists on the menu
//I want to print out a receipt once orders are finalized

class Till(val cafe: CafeDetails) {
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
    val filteredItems = findItemFromMenu(items)

    val invalidItems = items.keySet -- filteredItems.keySet
    handleItemsNotInMenu(invalidItems)

    order ++= filteredItems
  }

  def showOrder = order.map {
    case (item, quantity) => s"$item: $quantity" }.mkString("")

  def printReceipt: String = {
    val printer = new ReceiptPrinter(cafe, order)
    printer.receipt
  }
}