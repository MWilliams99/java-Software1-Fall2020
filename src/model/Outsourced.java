package model;
/**
 * The Outsourced class extends the Part class.
 * @author Mary Williams
 */
public class Outsourced extends Part{
    private String companyName;

    /**
     * The constructor creates an Outsourced part.
     * @param id The integer to be given to the id of the Outsourced Part.
     * @param name The string to be given to the name of the Outsourced Part.
     * @param price The double to be given to the price of the Outsourced Part.
     * @param stock The integer to be given to the stock of the Outsourced Part.
     * @param min The integer to be given to the min of the Outsourced Part.
     * @param max The integer to be given to the max of the Outsourced Part.
     * @param companyName The string to be given to the companyName of the Outsourced Part.
     */
    public Outsourced(int id, String name, double price, int stock, int min, int max, String companyName) {
        super(id, name, price, stock, min, max);
        this.companyName = companyName;
    }

    /**
     * The getCompanyName method returns the companyName.
     * @return The value in the companyName field.
     */
    public String getCompanyName() {
        return companyName;
    }

    /**
     * The setCompanyName method sets the companyName of the Outsourced Part.
     * @param companyName The string to be given to the companyName of the Outsourced Part.
     */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
}
