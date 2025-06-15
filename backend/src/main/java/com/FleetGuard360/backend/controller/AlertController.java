package com.FleetGuard360.backend.controller;

import com.FleetGuard360.backend.dto.AlertRequest;
import com.FleetGuard360.backend.dto.AlertResponse;
import com.FleetGuard360.backend.model.Alert;
import com.FleetGuard360.backend.service.AlertService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@CrossOrigin(origins = "*")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class AlertController {

    private final AlertService alertService;

    @GetMapping("/alerts/{id}")
    public ResponseEntity<AlertResponse> getAlertById(@PathVariable UUID id) {
        Alert alert = alertService.getAlertById(id);

        AlertResponse response = new AlertResponse();
        response.setId(alert.getId().toString());
        response.setMensaje(alert.getMensaje());
        response.setPrioridad(alert.getPrioridad());
        response.setTipoAlerta(alert.getTipeAlert().getNombre());
        response.setGeneradaPor(alert.getGeneradaPor() != null ? alert.getGeneradaPor().getNombre() : null);
        response.setVehiculoId(alert.getVehiculoId() != null ? alert.getVehiculoId().toString() : null);
        response.setFecha(alert.getFecha());
        response.setResponsables(alert.getResponsables());
        response.setConductor(alert.getConductor());
        response.setPlacaTransporte(alert.getPlacaTransporte());
        response.setUbicacion(alert.getUbicacion());

        return ResponseEntity.ok(response);
    }

    @GetMapping("/alerts")
    public List<AlertResponse> getAllAlerts() {
        return alertService.getAllAlerts();
    }

    @PostMapping("/alerts")
    public ResponseEntity<AlertResponse> createAlert(@RequestBody AlertRequest request) {
        Alert alert = alertService.createAlert(request);

        AlertResponse response = new AlertResponse();
        response.setId(alert.getId().toString());
        response.setMensaje(alert.getMensaje());
        response.setPrioridad(alert.getPrioridad());
        response.setTipoAlerta(alert.getTipeAlert().getNombre());
        response.setGeneradaPor(alert.getGeneradaPor() != null ? alert.getGeneradaPor().getNombre() : null);
        response.setVehiculoId(request.getVehiculoId() != null ? request.getVehiculoId().toString() : null);
        response.setFecha(request.getFecha());
        response.setResponsables(request.getResponsables());
        response.setConductor(request.getConductor());
        response.setPlacaTransporte(request.getPlacaTransporte());
        response.setUbicacion(request.getUbicacion());

        return ResponseEntity.ok(response);

    }

    @DeleteMapping("/alerts/{id}")
    public ResponseEntity<Map<String, Object>> deleteAlert(@PathVariable UUID id) {
        alertService.deleteAlert(id);
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Alerta eliminada exitosamente");
        response.put("timestamp", LocalDateTime.now());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/alerts/{id}")
    public ResponseEntity<AlertResponse> updateAlert(@PathVariable UUID id, @RequestBody AlertRequest request) {
        Alert updateAlert = alertService.updateAlert(id, request);

        AlertResponse response = new AlertResponse();
        response.setId(updateAlert.getId().toString());
        response.setMensaje(updateAlert.getMensaje());
        response.setPrioridad(updateAlert.getPrioridad());
        response.setTipoAlerta(updateAlert.getTipeAlert().getNombre());
        response.setGeneradaPor(updateAlert.getGeneradaPor() != null ? updateAlert.getGeneradaPor().getNombre() : null);
        response.setVehiculoId(updateAlert.getVehiculoId() != null ? updateAlert.getVehiculoId().toString() : null);
        response.setFecha(updateAlert.getFecha());
        response.setResponsables(updateAlert.getResponsables());
        response.setConductor(updateAlert.getConductor());
        response.setPlacaTransporte(updateAlert.getPlacaTransporte());
        response.setUbicacion(updateAlert.getUbicacion());

        return ResponseEntity.ok(response);
    }
}
