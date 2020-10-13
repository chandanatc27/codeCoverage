import java.util.Hashtable;
import java.util.Set;

public class Location {
	private String name;
	private Hashtable<String, Integer> productStock = new Hashtable<String, Integer>();

	public Location(String name) {
		this.name = name;
	}

	public void restockProduct(String name, Integer amount) {
		Integer previousAmount = getAmountOfProduct(name);

		productStock.put(name, previousAmount + amount);
	}

	/*
	 * returns false if we don't have enough product
	 */
	public boolean pickProduct(String name, Integer amount) {
		Integer previousAmount = getAmountOfProduct(name);

		if (previousAmount < amount) {
			// not enough items
			return false;
		}

		productStock.put(name, previousAmount - amount);

		return true;
	}

	public boolean hasProduct(String productId) {
		return productStock.get(productId) != null;
	}

	public Integer getAmountOfProduct(String productId) {
		Integer amount = productStock.get(productId);

		return amount != null ? amount : 0;
	}

	public String getName() {
		return this.name;
	}

	public void displayInventory() {
		Set<String> keys = productStock.keySet();
		for (String productId : keys) {
			Integer amount = productStock.get(productId);

			System.out.println("  " + productId + ": " + amount.toString());
		}
	}
}