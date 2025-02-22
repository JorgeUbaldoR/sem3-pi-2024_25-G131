package pt.ipp.isep.dei.esoft.project.domain;

import pt.ipp.isep.dei.esoft.project.domain.enumclasses.Priority;

import java.util.*;

/**
 * Represents an item within a production simulation system.
 * Each item is uniquely identified and is associated with a priority level,
 * a quantity, and a sequence of operations it undergoes.
 */
public class Item implements Comparable<Item> {

    private final String name;
    private final ID itemID;
    private final Priority priority;
    private Queue<Operation> operationList;
    private Operation currentOperation;
    private float quantity;
    private boolean isRawMaterial;

    /**
     * Constructs an Item with the specified ID, priority, and list of operations.
     *
     * @param itemID        the unique identifier for the item.
     * @param priority      the priority level of the item.
     * @param operationList the queue of operations that the item will undergo.
     * @throws IllegalArgumentException if itemID, priority, or operationList is null.
     * @complexity O(n), where n is the size of the operationList.
     */
    public Item(ID itemID, Priority priority, Queue<Operation> operationList) {
        validateInformation(itemID, priority, operationList);
        this.name = "Without Name";
        this.itemID = itemID;
        this.priority = priority;
        this.operationList = new LinkedList<>(operationList);
        this.currentOperation = this.operationList.peek();
        this.quantity = 0;
        this.isRawMaterial = true;
    }

    /**
     * Constructs an Item with the specified ID, name, priority, and list of operations.
     *
     * @param itemID        the unique identifier for the item.
     * @param name          the name of the item.
     * @param priority      the priority level of the item.
     * @param operationList the queue of operations that the item will undergo.
     * @throws IllegalArgumentException if itemID, priority, or operationList is null.
     * @complexity O(n), where n is the size of the operationList.
     */
    public Item(ID itemID, String name, Priority priority, Queue<Operation> operationList) {
        validateInformation(itemID, priority, operationList);
        this.name = name;
        this.itemID = itemID;
        this.priority = priority;
        this.operationList = new LinkedList<>(operationList);
        this.currentOperation = this.operationList.peek();
        this.quantity = 0;
        this.isRawMaterial = true;
    }

    /**
     * Constructs an Item with the specified ID and name, with default priority and an empty operation list.
     *
     * @param itemID the unique identifier for the item.
     * @param name   the name of the item.
     * @throws IllegalArgumentException if itemID is null.
     * @complexity O(1), as it performs simple initialization.
     */
    public Item(ID itemID, String name, Queue<Operation> operationList) {
        validateInformation(itemID, Priority.LOW, new LinkedList<>());
        this.name = name;
        this.itemID = itemID;
        this.priority = Priority.LOW;
        this.operationList = new LinkedList<>(operationList);
        this.currentOperation = null;
        this.quantity = 0;
        this.isRawMaterial = true;
    }


    public boolean isRawMaterial() {
        return isRawMaterial;
    }

    public void setRawMaterial(boolean rawMaterial) {
        isRawMaterial = rawMaterial;
    }

    public void addOpToItem(Operation op) {
        if(operationList.add(op)) {
            currentOperation = this.operationList.peek();
        }
    }

    /**
     * Validates the provided item information.
     *
     * @param itemID        the unique identifier for the item.
     * @param priority      the priority level of the item.
     * @param operationList the queue of operations that the item will undergo.
     * @throws IllegalArgumentException if itemID, priority, or operationList is null.
     * @complexity O(1), as it only performs null checks.
     */
    public void validateInformation(ID itemID, Priority priority, Queue<Operation> operationList) {
        if (itemID == null) throw new IllegalArgumentException("Cannot create Item because ID is null.");
        if (priority == null) throw new IllegalArgumentException("Cannot create Item because Priority is null.");
        if (operationList == null) throw new IllegalArgumentException("Cannot create Item because operation list is null.");
    }

    /**
     * Gets the unique identifier for this item.
     *
     * @return the item's ID.
     * @complexity O(1), as it directly accesses the field.
     */
    public ID getItemID() {
        return itemID;
    }

    /**
     * Gets the current operation that this item is undergoing.
     *
     * @return the current operation, or null if there is none.
     * @complexity O(1), as it directly accesses the field.
     */
    public Operation getCurrentOperation() {
        return currentOperation;
    }

    /**
     * Gets the priority level of this item.
     *
     * @return the priority level.
     * @complexity O(1), as it directly accesses the field.
     */
    public Priority getPriority() {
        return priority;
    }

    /**
     * Retrieves and removes the next operation in the queue for this item.
     *
     * @return the next operation, or null if there are no more operations.
     * @complexity O(1), as it performs constant-time queue operations.
     */
    public Operation getNextOperation() {
        if (!operationList.isEmpty()) {
            operationList.poll();
            this.currentOperation = operationList.peek();
            return this.currentOperation;
        }
        return null;
    }

    /**
     * Gets the quantity associated with this item.
     *
     * @return the item's quantity.
     * @complexity O(1), as it directly accesses the field.
     */
    public float getQuantity() {
        return quantity;
    }

    /**
     * Sets the quantity associated with this item.
     *
     * @param quantity the new quantity.
     * @complexity O(1), as it directly modifies the field.
     */
    public void setQuantity(float quantity) {
        this.quantity = quantity;
    }

    /**
     * Gets the name of this item.
     *
     * @return the item's name.
     * @complexity O(1), as it directly accesses the field.
     */
    public String getName() {
        return name;
    }

    /**
     * Creates a copy of this item.
     *
     * @return a new Item object that is a copy of this item.
     * @complexity O(n), where n is the size of the operationList.
     */
    public Item clone() {
        return new Item(itemID, name, priority, new LinkedList<>(operationList));
    }

    /**
     * Compares this item to another object for equality based on their IDs.
     *
     * @param o the object to compare this item against.
     * @return true if the specified object is equal to this item; false otherwise.
     * @complexity O(1), as it directly compares IDs.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Item item = (Item) o;
        return itemID.equals(item.itemID);
    }

    /**
     * Returns a hash code value for this item.
     * The hash code is based on the item's unique ID.
     *
     * @return a hash code value for this item.
     * @complexity O(1), as it directly computes the hash code of the ID.
     */
    @Override
    public int hashCode() {
        return itemID.hashCode();
    }

    /**
     * Returns a string representation of this item, including its ID, priority, and next operation.
     *
     * @return a string representation of the item.
     * @complexity O(1), as it formats and accesses basic fields.
     */
    @Override
    public String toString() {
        return String.format("%s -> %s -> %s -> %f", itemID, priority, operationList.peek(), quantity);
    }

    /**
     * Gets the list of operations as an immutable list.
     *
     * @return the list of operations.
     * @complexity O(n), where n is the size of the operationList.
     */
    public List<Operation> getOperationList() {
        return List.copyOf(operationList);
    }

    public boolean hasOperationWithID(ID operationId) {
        for (Operation op : operationList) {
            if (op.getOperationId().equals(operationId)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Compares this item to another item based on priority and ID.
     *
     * @param o the item to compare against.
     * @return a negative integer, zero, or a positive integer as this item is less than, equal to, or greater than the specified item.
     * @complexity O(1), as it performs constant-time comparisons.
     */
    @Override
    public int compareTo(Item o) {
        int priorityComparison = this.priority.compareTo(o.priority);
        if (priorityComparison != 0)
            return priorityComparison;
        return this.itemID.compareTo(o.itemID);
    }
}
