package au.com.telstra.simcardactivator.dto;

/**
 * Data Transfer Object for responses from the SIM card actuator service.
 * Contains a flag indicating whether the SIM card was activated successfully.
 */
public class ActuatorResponseDto {
    private boolean active;

    /**
     * Default constructor.
     */
    public ActuatorResponseDto() {
        // Default constructor for JSON deserialization
    }

    /**
     * Constructor with activation status.
     *
     * @param active whether the SIM card was activated successfully
     */
    public ActuatorResponseDto(boolean active) {
        this.active = active;
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