package au.com.telstra.simcardactivator.service;

import au.com.telstra.simcardactivator.dto.ActuatorRequestDto;
import au.com.telstra.simcardactivator.dto.ActuatorResponseDto;
import au.com.telstra.simcardactivator.dto.SimCardRequestDto;
import au.com.telstra.simcardactivator.dto.SimCardResponseDto;
import au.com.telstra.simcardactivator.entity.SimCard;
import au.com.telstra.simcardactivator.repository.SimCardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

@Service
public class SimCardService {

    private final SimCardRepository simCardRepository;
    private final RestTemplate restTemplate;
    private static final String ACTUATOR_URL = "http://localhost:8444/actuate";

    @Autowired
    public SimCardService(SimCardRepository simCardRepository, RestTemplate restTemplate) {
        this.simCardRepository = simCardRepository;
        this.restTemplate = restTemplate;
    }

    public SimCardResponseDto activateSimCard(SimCardRequestDto requestDto) {
        // Send request to actuator
        ActuatorRequestDto actuatorRequest = new ActuatorRequestDto(requestDto.getIccid());
        ActuatorResponseDto actuatorResponse = restTemplate.postForObject(
                ACTUATOR_URL, 
                actuatorRequest, 
                ActuatorResponseDto.class
        );

        // Save record to database
        boolean isActive = actuatorResponse != null && actuatorResponse.isActive();
        SimCard simCard = new SimCard(
                requestDto.getIccid(),
                requestDto.getCustomerEmail(),
                isActive
        );
        
        SimCard savedSimCard = simCardRepository.save(simCard);
        
        // Return response
        return new SimCardResponseDto(
                savedSimCard.getIccid(),
                savedSimCard.getCustomerEmail(),
                savedSimCard.isActive()
        );
    }

    public SimCardResponseDto getSimCardById(Long id) {
        SimCard simCard = simCardRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "SimCard with ID " + id + " not found"));
        
        return new SimCardResponseDto(
                simCard.getIccid(),
                simCard.getCustomerEmail(),
                simCard.isActive()
        );
    }
}