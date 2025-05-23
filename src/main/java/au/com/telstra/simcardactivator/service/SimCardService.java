package au.com.telstra.simcardactivator.service;

import au.com.telstra.simcardactivator.dto.ActuatorRequestDto;
import au.com.telstra.simcardactivator.dto.ActuatorResponseDto;
import au.com.telstra.simcardactivator.dto.SimCardRequestDto;
import au.com.telstra.simcardactivator.dto.SimCardResponseDto;
import au.com.telstra.simcardactivator.entity.SimCard;
import au.com.telstra.simcardactivator.repository.SimCardRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import java.util.Objects;

/**
 * Service for handling SIM card activation and retrieval operations.
 */
@Service
public class SimCardService {

    private static final Logger logger = LoggerFactory.getLogger(SimCardService.class);
    
    private final SimCardRepository simCardRepository;
    private final RestTemplate restTemplate;
    
    @Value("${simcard.actuator.url:http://localhost:8444/actuate}")
    private String actuatorUrl;

    /**
     * Constructs a new SimCardService with the required dependencies.
     *
     * @param simCardRepository repository for SIM card data operations
     * @param restTemplate template for making REST calls
     */
    @Autowired
    public SimCardService(SimCardRepository simCardRepository, RestTemplate restTemplate) {
        this.simCardRepository = simCardRepository;
        this.restTemplate = restTemplate;
    }

    /**
     * Activates a SIM card by sending a request to the actuator service and saving the result.
     *
     * @param requestDto the request containing ICCID and customer email
     * @return a response DTO with the activation result
     * @throws ResponseStatusException if the actuator service is unavailable
     */
    public SimCardResponseDto activateSimCard(SimCardRequestDto requestDto) {
        Objects.requireNonNull(requestDto, "Request cannot be null");
        
        if (requestDto.getIccid() == null || requestDto.getIccid().trim().isEmpty()) {
            logger.error("ICCID is null or empty");
            throw new IllegalArgumentException("ICCID cannot be null or empty");
        }
        
        if (requestDto.getCustomerEmail() == null || requestDto.getCustomerEmail().trim().isEmpty()) {
            logger.error("Customer email is null or empty");
            throw new IllegalArgumentException("Customer email cannot be null or empty");
        }
        
        logger.info("Activating SIM card with ICCID: {}", requestDto.getIccid());
        
        // Send request to actuator
        ActuatorRequestDto actuatorRequest = new ActuatorRequestDto(requestDto.getIccid());
        ActuatorResponseDto actuatorResponse;
        
        try {
            actuatorResponse = restTemplate.postForObject(
                    actuatorUrl, 
                    actuatorRequest, 
                    ActuatorResponseDto.class
            );
        } catch (RestClientException e) {
            logger.error("Failed to communicate with actuator service", e);
            throw new ResponseStatusException(
                    HttpStatus.SERVICE_UNAVAILABLE, 
                    "Failed to communicate with SIM card actuator service", 
                    e
            );
        }

        // Save record to database
        boolean isActive = false;
        if (actuatorResponse != null) {
            isActive = actuatorResponse.isActive();
        }
        logger.info("SIM card activation result: {}", isActive);
        
        SimCard simCard = new SimCard(
                requestDto.getIccid(),
                requestDto.getCustomerEmail(),
                isActive
        );
        
        SimCard savedSimCard = simCardRepository.save(simCard);
        logger.info("Saved SIM card record with ID: {}", savedSimCard.getId());
        
        // Return response
        return new SimCardResponseDto(
                savedSimCard.getIccid(),
                savedSimCard.getCustomerEmail(),
                savedSimCard.isActive()
        );
    }

    /**
     * Retrieves a SIM card record by its ID.
     *
     * @param id the ID of the SIM card record
     * @return a response DTO with the SIM card information
     * @throws ResponseStatusException if the SIM card with the given ID is not found
     */
    public SimCardResponseDto getSimCardById(Long id) {
        Objects.requireNonNull(id, "ID cannot be null");
        
        logger.info("Retrieving SIM card with ID: {}", id);
        
        SimCard simCard = simCardRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("SIM card not found with ID: {}", id);
                    return new ResponseStatusException(
                            HttpStatus.NOT_FOUND, "SimCard with ID " + id + " not found");
                });
        
        logger.info("Found SIM card with ICCID: {}", simCard.getIccid());
        
        return new SimCardResponseDto(
                simCard.getIccid(),
                simCard.getCustomerEmail(),
                simCard.isActive()
        );
    }
}