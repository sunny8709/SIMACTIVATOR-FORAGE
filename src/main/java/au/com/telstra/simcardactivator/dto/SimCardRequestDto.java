package au.com.telstra.simcardactivator.dto;

/**
 * Data Transfer Object for SIM card activation requests.
 * Contains the ICCID of the SIM card and the customer's email address.
 */
public class SimCardRequestDto {
    private String iccid;
    private String customerEmail;

    /**
     * Default constructor.
     */
    public SimCardRequestDto() {
        // Default constructor for JSON deserialization
    }

    /**
     * Constructor with all fields.
     *
     * @param iccid the ICCID of the SIM card
     * @param customerEmail the email address of the customer
     */
    public SimCardRequestDto(String iccid, String customerEmail) {
        this.iccid = iccid;
        this.customerEmail = customerEmail;
    }

    /**
     * Gets the ICCID of the SIM card.
     *
     * @return the ICCID
     */
    public String getIccid() {
        return iccid;
    }

    /**
     * Sets the ICCID of the SIM card.
     *
     * @param iccid the ICCID to set
     */
    public void setIccid(String iccid) {
        this.iccid = iccid;
    }

    /**
     * Gets the email address of the customer.
     *
     * @return the customer's email address
     */
    public String getCustomerEmail() {
        return customerEmail;
    }

    /**
     * Sets the email address of the customer.
     *
     * @param customerEmail the customer's email address to set
     */
    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }
}