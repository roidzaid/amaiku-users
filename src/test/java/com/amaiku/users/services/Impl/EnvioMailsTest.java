package com.amaiku.users.services.Impl;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.jupiter.api.Assertions.*;

class EnvioMailsTest {

    @InjectMocks
    private EnvioMails envioMails;

    @Mock
    private JavaMailSender javaMailSender;

    @BeforeEach
    void setUp() {
        envioMails = new EnvioMails();
        ReflectionTestUtils.setField(envioMails, "remitente", "remitente@gmail.com");
        ReflectionTestUtils.setField(envioMails, "clave", "claveSuperSecreta123");
        ReflectionTestUtils.setField(envioMails, "javaMailSender", javaMailSender);
    }

    @Test
    void enviarMail_deberiaEjecutarseSinErrores() {
        // Arrange
        String destinatario = "test@correo.com";
        String plantilla = "<h1>Hola mundo</h1>";

        // Act & Assert
        assertDoesNotThrow(() -> envioMails.enviarMail(destinatario, plantilla));
    }
}