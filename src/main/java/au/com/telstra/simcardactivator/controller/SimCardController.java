package au.com.telstra.simcardactivator.controller;

import au.com.telstra.simcardactivator.dto.SimCardRequestDto;
import au.com.telstra.simcardactivator.dto.SimCardResponseDto;
import au.com.telstra.simcardactivator.service.SimCardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/simcards")
public class SimCardController {

    private final SimCardService simCardService;

    @Autowired
    public SimCardController(SimCardService simCardService) {
        this.simCardService = simCardService;
    }

    @PostMapping
    public ResponseEntity<SimCardResponseDto> activateSimCard(@RequestBody SimCardRequestDto requestDto) {
        SimCardResponseDto responseDto = simCardService.activateSimCard(requestDto);
        return ResponseEntity.ok(responseDto);
    }

    @GetMapping
    public ResponseEntity<SimCardResponseDto> getSimCard(@RequestParam Long simCardId) {
        SimCardResponseDto responseDto = simCardService.getSimCardById(simCardId);
        return ResponseEntity.ok(responseDto);
    }
}