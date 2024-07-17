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

  private def findCosts = {
    for {
      key <- order.keys
      price <- cafe.prices.get(key)
      quantity <- order.get(key)
    } yield (key, quantity, price, price * quantity)
  }

  //  <!-- OMITTED -->
  def receipt: String = {
    val shopName = cafe.shopName
    val address = cafe.address
    val phoneNumber = cafe.phone

    val items = findCosts.map {
      case (key, quantity, price, subtotal) =>
        val formatedSubtotal = f"$subtotal%.2f"
        s"${key}: ${quantity} x ${price} $$${formatedSubtotal}"
    }.mkString("\n")

    s"""
       |${shopName}
       |${address}
       |${phoneNumber}
       |${items}
       |""".stripMargin
  }
}