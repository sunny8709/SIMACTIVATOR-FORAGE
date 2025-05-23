package au.com.telstra.simcardactivator.controller;

import au.com.telstra.simcardactivator.dto.SimCardRequestDto;
import au.com.telstra.simcardactivator.dto.SimCardResponseDto;
import au.com.telstra.simcardactivator.service.SimCardService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.Objects;

/**
 * REST controller for SIM card activation operations.
 */
@RestController
@RequestMapping("/api/simcards")
public class SimCardController {

    private static final Logger logger = LoggerFactory.getLogger(SimCardController.class);
    
    private final SimCardService simCardService;

    /**
     * Constructs a new SimCardController with the required dependencies.
     *
     * @param simCardService service for SIM card operations
     */
    @Autowired
    public SimCardController(SimCardService simCardService) {
        this.simCardService = simCardService;
    }

    /**
     * Activates a SIM card with the provided information.
     *
     * @param requestDto the request containing ICCID and customer email
     * @return a response entity with the activation result
     */
    @PostMapping
    public ResponseEntity<SimCardResponseDto> activateSimCard(@RequestBody SimCardRequestDto requestDto) {
        logger.info("Received activation request");
        
        try {
            Objects.requireNonNull(requestDto, "Request body cannot be null");
        } catch (NullPointerException e) {
            logger.error("Request body is null");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }
        
        try {
            SimCardResponseDto responseDto = simCardService.activateSimCard(requestDto);
            logger.info("SIM card activation processed successfully");
            return ResponseEntity.ok(responseDto);
        } catch (IllegalArgumentException e) {
            logger.error("Invalid request data", e);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }
    }

    /**
     * Retrieves a SIM card record by its ID.
     *
     * @param simCardId the ID of the SIM card record
     * @return a response entity with the SIM card information
     */
    @GetMapping("/{simCardId}")
    public ResponseEntity<SimCardResponseDto> getSimCard(@PathVariable Long simCardId) {
        logger.info("Received request to get SIM card with ID: {}", simCardId);
        
        try {
            Objects.requireNonNull(simCardId, "SIM card ID cannot be null");
        } catch (NullPointerException e) {
            logger.error("SIM card ID is null");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }
        
        try {
            SimCardResponseDto responseDto = simCardService.getSimCardById(simCardId);
            logger.info("SIM card retrieved successfully");
            return ResponseEntity.ok(responseDto);
        } catch (IllegalArgumentException e) {
            logger.error("Invalid request parameter", e);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }
    }
}