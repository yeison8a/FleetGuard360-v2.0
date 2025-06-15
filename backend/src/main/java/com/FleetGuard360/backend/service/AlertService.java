package com.FleetGuard360.backend.service;

import com.FleetGuard360.backend.dto.AlertRequest;
import com.FleetGuard360.backend.dto.AlertResponse;
import com.FleetGuard360.backend.model.Alert;
import com.FleetGuard360.backend.model.TipeAlert;
import com.FleetGuard360.backend.model.User;
import com.FleetGuard360.backend.repository.AlertRepository;
import com.FleetGuard360.backend.repository.TipeAlertRepository;
import com.FleetGuard360.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class AlertService {

    @Autowired
    private AlertRepository alertRepository;

    @Autowired
    private TipeAlertRepository tipeAlertRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private NotificationService notificationService;

    public Alert getAlertById(UUID id) {
        return alertRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Alerta no encontrada con ID: " + id));
    }

    public List<AlertResponse> getAllAlerts() {
        List<Alert> alerts = alertRepository.findAll();
        return alerts.stream().map(alert -> {
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
            return response;

        }).collect(Collectors.toList());
    }

    public Alert createAlert(AlertRequest request) {
        Alert alert = new Alert();
        alert.setMensaje(request.getMensaje());
        alert.setPrioridad(request.getPrioridad());
        alert.setFecha(LocalDateTime.now());
        alert.setResponsables(request.getResponsables());
        alert.setConductor(request.getConductor());
        alert.setPlacaTransporte(request.getPlacaTransporte());
        alert.setUbicacion(request.getUbicacion());

        if (request.getTipoAlerta() != null) {
            TipeAlert tipeAlert = tipeAlertRepository.findById(request.getTipoAlerta())
                    .orElseThrow(() -> new TipeAlertNotFoundException("Tipo de alerta no encontrado"));
            alert.setTipeAlert(tipeAlert);
        }

        if (request.getGeneradaPor() != null) {
            User user = userRepository.findById(request.getGeneradaPor())
                    .orElseThrow(() -> new UserNotFoundException("Usuario no encontrado"));
            alert.setGeneradaPor(user);
        }

        if (request.getVehiculoId() != null) {
            alert.setVehiculoId(request.getVehiculoId());
        }

       Alert savedAlert = alertRepository.save(alert);

       if (savedAlert.getGeneradaPor() != null) {
           notificationService.enviarNotificacionPorCorreo(savedAlert, savedAlert.getGeneradaPor());
       }

       return savedAlert;
    }

    public void deleteAlert(UUID id) {
        if(!alertRepository.existsById(id)) {
            throw new AlertNotFoundException("Alerta no encontrada");
        }
        alertRepository.deleteById(id);
    }

    public Alert updateAlert(UUID id, AlertRequest request) {
        Alert alert = alertRepository.findById(id)
                .orElseThrow(() -> new AlertNotFoundException("Alerta no encontrada"));

        alert.setMensaje(request.getMensaje());
        alert.setPrioridad(request.getPrioridad());
        alert.setFecha(LocalDateTime.now());
        alert.setResponsables(request.getResponsables());
        alert.setConductor(request.getConductor());
        alert.setPlacaTransporte(request.getPlacaTransporte());
        alert.setUbicacion(request.getUbicacion());

        if (request.getTipoAlerta() != null) {
            TipeAlert tipeAlert = tipeAlertRepository.findById(request.getTipoAlerta())
                    .orElseThrow(() -> new TipeAlertNotFoundException("Tipo de alerta no encontrada"));
            alert.setTipeAlert(tipeAlert);
        }

        if (request.getGeneradaPor() != null) {
            User user = userRepository.findById(request.getGeneradaPor())
                    .orElseThrow(() -> new UserNotFoundException("Usuario no encontrado"));
            alert.setGeneradaPor(user);
        }

        if (request.getVehiculoId() != null) {
            alert.setVehiculoId(request.getVehiculoId());
        }

        return alertRepository.save(alert);
    }

    public static class AlertNotFoundException extends RuntimeException {
        public AlertNotFoundException(String message) {
            super(message);
        }
    }

    public static class TipeAlertNotFoundException extends RuntimeException {
        public TipeAlertNotFoundException(String message) {
            super(message);
        }
    }

    public static class UserNotFoundException extends RuntimeException {
        public UserNotFoundException(String message) {
            super(message);
        }
    }
}