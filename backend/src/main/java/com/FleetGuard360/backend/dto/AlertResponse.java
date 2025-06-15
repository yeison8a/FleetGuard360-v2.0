package com.FleetGuard360.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AlertResponse {
    private String id;
    private String mensaje;
    private String prioridad;
    private String tipoAlerta;
    private String generadaPor;
    private String vehiculoId;
    private LocalDateTime fecha;
    private String responsables;
    private String conductor;
    private String placaTransporte;
    private String ubicacion;
}
