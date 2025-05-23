package au.com.telstra.simcardactivator.dto;

/**
 * Data Transfer Object for SIM card activation responses.
 * Contains the ICCID of the SIM card, the customer's email address,
 * and a flag indicating whether the SIM card was activated successfully.
 */
public class SimCardResponseDto {
    private String iccid;
    private String customerEmail;
    private boolean active;

    /**
     * Default constructor.
     */
    public SimCardResponseDto() {
        // Default constructor for JSON serialization
    }

    /**
     * Constructor with all fields.
     *
     * @param iccid the ICCID of the SIM card
     * @param customerEmail the email address of the customer
     * @param active whether the SIM card was activated successfully
     */
    public SimCardResponseDto(String iccid, String customerEmail, boolean active) {
        this.iccid = iccid;
        this.customerEmail = customerEmail;
        this.active = active;
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

    /**
     * Checks if the SIM card was activated successfully.
     *
     * @return true if the SIM card is active, false otherwise
     */
    public boolean isActive() {
        return active;
    }

    /**
     * Sets the activation status of the SIM card.
     *
     * @param active the activation status to set
     */
    public void setActive(boolean active) {
        this.active = active;
    }
}