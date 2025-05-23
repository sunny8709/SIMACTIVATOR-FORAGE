package au.com.telstra.simcardactivator.dto;

/**
 * Data Transfer Object for requests to the SIM card actuator service.
 * Contains the ICCID of the SIM card to be activated.
 */
public class ActuatorRequestDto {
    private String iccid;

    /**
     * Default constructor.
     */
    public ActuatorRequestDto() {
        // Default constructor for JSON serialization
    }

    /**
     * Constructor with ICCID.
     *
     * @param iccid the ICCID of the SIM card
     */
    public ActuatorRequestDto(String iccid) {
        this.iccid = iccid;
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
}