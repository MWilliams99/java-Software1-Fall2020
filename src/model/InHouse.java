package model;
/**
 * The InHouse class extends the Part class.
 * @author Mary Williams
 */
public class InHouse extends Part {
    private int machineId;

    /**
     * The constructor creates an InHouse part.
     * @param id The integer to be given to the id of the InHouse Part.
     * @param name The string to be given to the name of the InHouse Part.
     * @param price The double to be given to the price of the InHouse Part.
     * @param stock The integer to be given to the stock of the InHouse Part.
     * @param min The integer to be given to the min of the InHouse Part.
     * @param max The integer to be given to the max of the InHouse Part.
     * @param machineId The integer to be given to the machineId of the InHouse Part.
     */
    public InHouse(int id, String name, double price, int stock, int min, int max, int machineId) {
        super(id, name, price, stock, min, max);
        this.machineId = machineId;
    }

    /**
     * The getMachineId method returns the machineId.
     * @return The value in the machineId field.
     */
    public int getMachineId() {
        return machineId;
    }

    /**
     * The setMachineId method sets the machineId of the InHouse Part.
     * @param machineId The integer to be given to the machineId of the InHouse Part.
     */
    public void setMachineId(int machineId) {
        this.machineId = machineId;
    }
}
