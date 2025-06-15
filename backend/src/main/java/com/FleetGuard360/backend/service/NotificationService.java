package com.FleetGuard360.backend.service;

import com.FleetGuard360.backend.model.Alert;
import com.FleetGuard360.backend.model.Notification;
import com.FleetGuard360.backend.model.User;
import com.FleetGuard360.backend.repository.NotificationRepository;
import com.sendgrid.*;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final NotificationRepository notificationRepository;
    private final SendGrid sendGrid;

    @Value("${sendgrid.from-email}")
    private String fromEmail;

    public void enviarNotificacionPorCorreo(Alert alert, User destinatario) {
        if (destinatario == null || destinatario.getCorreo() == null) return;

        String toEmail = destinatario.getCorreo();
        String subject = "Nueva alerta generada";
        String contentText = "Se ha generado una nueva alerta:\n\n" +
                "Mensaje: " + alert.getMensaje() + "\n" +
                "Prioridad: " + alert.getPrioridad() + "\n" +
                "Fecha: " + alert.getFecha();

        try {
            Email from = new Email(fromEmail);
            Email to = new Email(toEmail);
            Content content = new Content("text/plain", contentText);
            Mail mail = new Mail(from, subject, to, content);

            Request request = new Request();
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());

            sendGrid.api(request);

        } catch (IOException e) {
            System.err.println("Error al enviar correo: " + e.getMessage());
        }

        Notification notificacion = Notification.builder()
                .id(UUID.randomUUID())
                .alertaId(alert.getId())
                .destinatarioId(destinatario.getId())
                .metodoEnvio("EMAIL")
                .leido(false)
                .fechaEnvio(LocalDateTime.now())
                .build();

        notificationRepository.save(notificacion);
    }
}