package au.com.telstra.simcardactivator.service;

import au.com.telstra.simcardactivator.model.ActuatorRequest;
import au.com.telstra.simcardactivator.model.ActuatorResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ActuatorService {

    private final RestTemplate restTemplate;
    private final String actuatorUrl;

    public ActuatorService(RestTemplate restTemplate, @Value("${actuator.url}") String actuatorUrl) {
        this.restTemplate = restTemplate;
        this.actuatorUrl = actuatorUrl;
    }

    public ActuatorResponse activateSimCard(String iccid) {
        ActuatorRequest request = new ActuatorRequest(iccid);
        return restTemplate.postForObject(actuatorUrl, request, ActuatorResponse.class);
    }
}