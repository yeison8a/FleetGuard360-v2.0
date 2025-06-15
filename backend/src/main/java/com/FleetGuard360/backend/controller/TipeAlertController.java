package com.FleetGuard360.backend.controller;

import com.FleetGuard360.backend.dto.TipeAlertRequest;
import com.FleetGuard360.backend.model.TipeAlert;
import com.FleetGuard360.backend.repository.TipeAlertRepository;
import com.FleetGuard360.backend.service.TipeAlertService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class TipeAlertController {

    private final TipeAlertService tipeAlertService;

    @GetMapping("/tipeAlerts")
    public ResponseEntity<List<TipeAlert>> getAllTipeAlerts() {
        return ResponseEntity.ok(tipeAlertService.getAllTipeAlerts());
    }

    @PostMapping("/tipeAlerts")
    public ResponseEntity<TipeAlert> createTipeAlert(@RequestBody TipeAlertRequest request) {
        TipeAlert created = tipeAlertService.createTipeAlert(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @DeleteMapping("/tipeAlerts/{id}")
    public ResponseEntity<String> deleteTipeAlert(@PathVariable Integer id) {
        tipeAlertService.deleteTipeAlert(id);
        return ResponseEntity.ok("Eliminado con exito");
    }

}
