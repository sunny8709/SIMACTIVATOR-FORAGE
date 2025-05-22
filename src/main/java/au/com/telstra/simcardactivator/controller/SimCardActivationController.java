package au.com.telstra.simcardactivator.controller;

import au.com.telstra.simcardactivator.model.ActuatorResponse;
import au.com.telstra.simcardactivator.model.SimCardActivationRequest;
import au.com.telstra.simcardactivator.service.ActuatorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/sim")
public class SimCardActivationController {

    private final ActuatorService actuatorService;

    public SimCardActivationController(ActuatorService actuatorService) {
        this.actuatorService = actuatorService;
    }

    @PostMapping("/activate")
    public ResponseEntity<ActuatorResponse> activateSimCard(@RequestBody SimCardActivationRequest request) {
        ActuatorResponse response = actuatorService.activateSimCard(request.getIccid());
        
        // Print the activation result as required
        System.out.println("SIM card activation for ICCID: " + request.getIccid() + 
                           ", Customer Email: " + request.getCustomerEmail() + 
                           " - Result: " + (response.isSuccess() ? "SUCCESS" : "FAILURE"));
        
        return ResponseEntity.ok(response);
    }
}