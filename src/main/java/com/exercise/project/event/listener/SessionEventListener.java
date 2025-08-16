package com.exercise.project.event.listener;

import org.springframework.context.event.EventListener;
import org.springframework.data.rest.core.event.ExceptionEvent;
import org.springframework.session.events.SessionCreatedEvent;
import org.springframework.session.events.SessionDeletedEvent;
import org.springframework.session.events.SessionDestroyedEvent;
import org.springframework.session.events.SessionExpiredEvent;
import org.springframework.stereotype.Component;

@Component
public class SessionEventListener {

    @EventListener
    void processSessionCreatedEvent(SessionCreatedEvent event) {
        System.out.println(
            "\n##############################################\n" +
                "processSessionCreatedEvent" +
                "\n##############################################\n");
    }

    @EventListener
    void processSessionDeletedEvent(SessionDeletedEvent event) {
        System.out.println(
            "\n##############################################\n" +
                "processSessionDeletedEvent" +
                "\n##############################################\n");
    }

    @EventListener
    void processSessionDestroyedEvent(SessionDestroyedEvent event) {
        System.out.println(
            "\n##############################################\n" +
                "processSessionDestroyedEvent" +
                "\n##############################################\n");
    }

    @EventListener
    void processSessionExpiredEvent(SessionExpiredEvent event) {
        System.out.println(
            "\n##############################################\n" +
                "processSessionExpiredEven" +
                "\n##############################################\n");
    }

    @EventListener
    void processExceptionEvent(ExceptionEvent event) {
        System.out.println(
            "\n##############################################\n" +
                "processExceptionEvent" +
                "\n##############################################\n");
    }

}
