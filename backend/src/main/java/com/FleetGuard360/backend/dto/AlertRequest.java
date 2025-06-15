package com.FleetGuard360.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AlertRequest {
    private String mensaje;
    private String prioridad;
    private Integer tipoAlerta;
    private UUID generadaPor;
    private UUID vehiculoId;
    private LocalDateTime fecha;
    private String responsables;
    private String conductor;
    private String placaTransporte;
    private String ubicacion;
}
