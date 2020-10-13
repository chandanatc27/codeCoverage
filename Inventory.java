import java.util.Hashtable;
import java.util.Set;

public class Inventory {

	private Hashtable<String, Product> products = new Hashtable<String, Product>();
	private Hashtable<String, Location> locations = new Hashtable<String, Location>();

	public void addLocation(Location location) {
		locations.put(location.getName(), location);
	}

	public void addProduct(Product product, Location location, int amount) {
		products.put(product.getName(), product);
		location.restockProduct(product.getName(), amount);
	}

	
	  //returns location that has the product when you pass a product ID to it
	 
	public Location getLocationOfProduct(String productName) throws Exception {
		Set<String> keys = locations.keySet();
		Location result = null;
		for (String key : keys) {
			Location current = locations.get(key);

			if (current.hasProduct(productName)) {
				if (result == null) {
					result = current;
				} else {
					//  we found the product in two locations, warning the user
					
					throw new Exception("product found in two locations");
				}
			}
		}

		return result;
	}

	public void displayInventory() {
		Set<String> keys = locations.keySet();
		for (String key : keys) {
			Location current = locations.get(key);

			System.out.println(current.getName());
			current.displayInventory();
		}
		System.out.println("");
	}

	public PickingResult pickProduct(String productName, int amountToPick) {
		Location loc = null;
		Product product = products.get(productName);

		
		boolean pickupSuccess = false;
		String transactionMessage = "";

		try {
			loc = getLocationOfProduct(productName);

			if (loc == null) {
				throw new Exception("Product " + productName + " wasn't found in any location");
			}

			int amount = loc.getAmountOfProduct(productName);
			if (amount < amountToPick) {
				throw new Exception("We do not have enough products for this transaction (quantity available: " + amount
						+ "), please restock the product " + productName + "!");
			}

			loc.pickProduct(productName, amountToPick);

			pickupSuccess = true;
			transactionMessage = "We have successfully picked " + amountToPick + " items of " + productName;
		} catch (Exception e) {
			transactionMessage = e.getMessage();
		}

		return new PickingResult(pickupSuccess, transactionMessage, loc, product, amountToPick);
	}

	public RestockingResult restockProduct(String productName, int amountToRestock) {
		Location loc = null;
		Product product = products.get(productName);

	
		boolean restockSuccess = false;
		String transactionMessage = "";

		try {
			loc = getLocationOfProduct(productName);

			if (loc == null) {
				throw new Exception("No previous stock of "+productName+" was found to restock");
			}

			loc.restockProduct(productName, amountToRestock);

			restockSuccess = true;
			transactionMessage = "We have successfully restocked " + amountToRestock + " items of " + productName;
		} catch (Exception e) {
			transactionMessage = e.getMessage();
		}

		return new RestockingResult(restockSuccess, transactionMessage, loc, product, amountToRestock);
	}

}