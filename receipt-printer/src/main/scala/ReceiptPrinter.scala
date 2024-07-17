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

class Till(val cafe: CafeDetails) {
  def show: String = cafe.prices.map { case (item, price) => s"${item}: $$${price}" }.mkString("\n")

  def order(order: String) = cafe.prices.filter {
    case (item, price) => item.contains(order)
  }.map {
    case (item, order) => s"$item: $$$order"
  }.headOption.getOrElse("Item not in the menu")
}