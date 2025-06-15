package com.FleetGuard360.backend.model;

import jakarta.persistence.*;
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
@Entity
@Table(name = "alerta")
public class Alert {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    @Column(nullable = false)
    private String mensaje;
    @Column(nullable = false)
    private String prioridad;
    @ManyToOne
    @JoinColumn(name = "tipo_alerta_id", nullable = false)
    private TipeAlert tipeAlert;
    @ManyToOne
    @JoinColumn(name = "generada_por", nullable = false)
    private User generadaPor;
    @Column(nullable = true)
    private UUID vehiculoId;
    private LocalDateTime fecha;
    private String responsables;
    private String conductor;
    private String placaTransporte;
    private String ubicacion;

}
