package au.com.telstra.simcardactivator.dto;

public class ActuatorResponseDto {
    private boolean active;

    public ActuatorResponseDto() {
    }

    public ActuatorResponseDto(boolean active) {
        this.active = active;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}