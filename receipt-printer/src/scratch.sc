val menu = Map(
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

menu.map{case (x, y) => (x,y)}
menu.filter{case (x,_) => x=="Cappuccino"}
def filterMenu(order: String) = menu.filter{case (x,_) => x.contains(order)}.map{case(x,y) => s"$x: $y"}
filterMenu("Cafe Latte")

val order = Map("Cafe Latte" -> 1, "Flat White" -> 2)

def getPrice(key: String) = {
  menu.get(key)
}

def formatSubtotal(subtotal: Double): String = {
  f"$subtotal%.2f"
}

def calculateSubTotal(quantity: Int, price: Double): String = {
  formatSubtotal(quantity*price)
}

def calculateTotal(subtotalCollection: List[Int]) = {
  subtotalCollection.sum
}


order("Cafe Latte")
order.apply("Cafe Latte")
order.getOrElse("Cake", "No such item")
order.contains("Cafe Latte")
order.keys
order.keySet
order.values

order.map {
  case (key, value) =>
    menu.get(key).map(price =>
      (key, value, value * price)
    )
}

order.map {
  case (key, value) =>
    getPrice(key).map(price =>
      (key, value, calculateSubTotal(value, price))
    )
}

//list of subtotals = menu.key * order.key

// Item: quantity x price   subtotal
// key:  orderValue x menuValue      (value x value)

//for each key in the map, I want to get the order value and the menu value and then get the product of those values

