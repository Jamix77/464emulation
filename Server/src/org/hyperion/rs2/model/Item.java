package org.hyperion.rs2.model;

import org.hyperion.rs2.model.equipment.EquipmentDefinition;

/**
 * Represents a single item.
 *
 * @author Graham Edgecombe
 */
public class Item {

    /**
     * The id.
     */
    private int id;

    /**
     * The number of items.
     */
    private int count;
    private int[] counts;

    /**
     * Creates a single item.
     *
     * @param id The id.
     */
    public Item(int id) {
        this(id, 1);
    }

    public void setCount(int count) {
        this.count = count;
    }

    /**
     * Creates a stacked item.
     *
     * @param id    The id.
     * @param count The number of items.
     * @throws IllegalArgumentException if count is negative.
     */
    public Item(int id, int count) {
        if (count < 0) {
            throw new IllegalArgumentException("Count cannot be negative.");
        }
        this.id = id;
        this.count = count;
    }

    /**
     * Creates a stacked item.
     *
     * @param id    The id.
     * @param counts The number of items.
     * @throws IllegalArgumentException if count is negative.
     */
    public Item(int id, int[] counts) {
        if (counts == null) {
            throw new IllegalArgumentException("Count cannot be negative.");
        }
        this.id = id;
        this.counts = counts;
    }

    /**
     * Gets the definition of this item.
     *
     * @return The definition.
     */
    public ItemDefinition getDefinition() {
        return ItemDefinition.forId(id);
    }

    /**
     * Gets the definition of this item.
     *
     * @return The definition.
     */
    public EquipmentDefinition getEquipmentDefinition() {
        return EquipmentDefinition.forId(id);
    }

    /**
     * Gets the item id.
     *
     * @return The item id.
     */
    public int getId() {
        return id;
    }

    /**
     * Gets the count.
     *
     * @return The count.
     */
    public int getCount() {
        return count;
    }

    @Override
    public String toString() {
        return Item.class.getName() + " [id=" + id + ", count=" + count + "]";
    }

}
