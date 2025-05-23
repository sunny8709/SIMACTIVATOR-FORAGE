package au.com.telstra.simcardactivator.dto;

public class ActuatorRequestDto {
    private String iccid;

    public ActuatorRequestDto() {
    }

    public ActuatorRequestDto(String iccid) {
        this.iccid = iccid;
    }

    public String getIccid() {
        return iccid;
    }

    public void setIccid(String iccid) {
        this.iccid = iccid;
    }
}