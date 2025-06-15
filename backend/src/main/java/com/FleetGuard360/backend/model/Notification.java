package com.FleetGuard360.backend.model;


import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "notificacion")
public class Notification {
    @Id
    private UUID id;

    @Column(name = "alerta_id", nullable = false)
    private UUID alertaId;

    @Column(name = "destinatario_id", nullable = false)
    private UUID destinatarioId;

    @Column(name = "metodo_envio", length = 20)
    private String metodoEnvio;

    private boolean leido;

    @Column(name = "fecha_envio")
    private LocalDateTime fechaEnvio;
}
