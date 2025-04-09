package com.amaiku.users.services;

public interface MailsService<T>{

    void enviarMail(String destinatario, String plantilla);

}
