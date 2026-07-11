package org.example.t1academyhome08.controller;

import org.example.t1academyhome08.dto.ApiResponseDTO;
import org.example.t1academyhome08.dto.OperationActionRequestDTO;
import org.example.t1academyhome08.dto.ReserveLimitRequestDTO;
import org.example.t1academyhome08.dto.UserLimitResponseDTO;
import org.example.t1academyhome08.service.LimitService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/limits")
@RequiredArgsConstructor
public class LimitController {

    private final LimitService limitService;

    @GetMapping("/user/{userId}")
    public ResponseEntity<UserLimitResponseDTO> getUserLimit(@PathVariable Long userId) {
        UserLimitResponseDTO response = limitService.getUserLimit(userId);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/reserve")
    public ResponseEntity<ApiResponseDTO> reserve(@Valid @RequestBody ReserveLimitRequestDTO request) {

        limitService.reserveLimit(request.userId(), request.operationId(), request.amount());
        return ResponseEntity.ok(new ApiResponseDTO("Limit reserved successfully", true));
    }

    @PostMapping("/confirm")
    public ResponseEntity<ApiResponseDTO> confirm(@Valid @RequestBody OperationActionRequestDTO request) {

        limitService.confirmOperation(request.operationId());
        return ResponseEntity.ok(new ApiResponseDTO("Operation confirmed, limit deducted permanently", true));
    }

    @PostMapping("/cancel")
    public ResponseEntity<ApiResponseDTO> cancel(@Valid @RequestBody OperationActionRequestDTO request) {
        limitService.cancelOperation(request.operationId());
        return ResponseEntity.ok(new ApiResponseDTO("Operation cancelled, limit restored", true));
    }
}
