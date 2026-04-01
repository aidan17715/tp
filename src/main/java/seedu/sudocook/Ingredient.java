package seedu.sudocook;

import java.time.LocalDate;

/**
 * Represents an ingredient with a name, quantity, unit, and optional expiry date.
 */
public class Ingredient {
    private final String name;
    private double quantity;
    private final String unit;
    private LocalDate expiryDate;

    /**
     * Constructs an Ingredient with the specified name, quantity, and unit.
     *
     * @param name The name of the ingredient
     * @param quantity The quantity of the ingredient
     * @param unit The unit of measurement
     */
    public Ingredient(String name, double quantity, String unit) {
        assert name != null && !name.isEmpty() : "Ingredient name must not be null or empty";
        assert quantity > 0 : "Ingredient quantity must be positive";
        assert unit != null && !unit.isEmpty() : "Ingredient unit must not be null or empty";
        this.name = name;
        this.quantity = quantity;
        this.unit = unit;
        this.expiryDate = null;
    }

    /**
     * Constructs an Ingredient with the specified name, quantity, unit, and expiry date.
     *
     * @param name The name of the ingredient
     * @param quantity The quantity of the ingredient
     * @param unit The unit of measurement
     * @param expiryDate The expiry date of the ingredient (optional)
     */
    public Ingredient(String name, double quantity, String unit, LocalDate expiryDate) {
        assert name != null && !name.isEmpty() : "Ingredient name must not be null or empty";
        assert quantity > 0 : "Ingredient quantity must be positive";
        assert unit != null && !unit.isEmpty() : "Ingredient unit must not be null or empty";
        this.name = name;
        this.quantity = quantity;
        this.unit = unit;
        this.expiryDate = expiryDate;
    }

    public String getName() {
        return name;
    }

    public double getQuantity() {
        return quantity;
    }

    public String getUnit() {
        return unit;
    }

    public LocalDate getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(LocalDate expiryDate) {
        this.expiryDate = expiryDate;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public boolean isExpired() {
        if (expiryDate == null) {
            return false;
        }
        return LocalDate.now().isAfter(expiryDate);
    }

    @Override
    public String toString() {
        String result = name + " (" + quantity + " " + unit + ")";
        if (expiryDate != null) {
            result += " expires: " + expiryDate;
        }
        return result;
    }
}
