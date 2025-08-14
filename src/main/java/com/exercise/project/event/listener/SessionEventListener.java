package com.exercise.project.event.listener;

import org.springframework.context.event.EventListener;
import org.springframework.session.events.SessionCreatedEvent;
import org.springframework.session.events.SessionDeletedEvent;
import org.springframework.session.events.SessionDestroyedEvent;
import org.springframework.session.events.SessionExpiredEvent;
import org.springframework.stereotype.Component;

@Component
public class SessionEventListener {

    @EventListener
    public void processSessionCreatedEvent(SessionCreatedEvent event) {
        System.out.println(
            "\n##############################################\n" +
                "processSessionCreatedEvent" +
                "\n##############################################\n");
    }

    @EventListener
    public void processSessionDeletedEvent(SessionDeletedEvent event) {
        System.out.println(
            "\n##############################################\n" +
                "processSessionDeletedEvent" +
                "\n##############################################\n");
    }

    @EventListener
    public void processSessionDestroyedEvent(SessionDestroyedEvent event) {
        System.out.println(
            "\n##############################################\n" +
                "processSessionDestroyedEvent" +
                "\n##############################################\n");
    }

    @EventListener
    public void processSessionExpiredEvent(SessionExpiredEvent event) {
        System.out.println(
            "\n##############################################\n" +
                "processSessionExpiredEven" +
                "\n##############################################\n");
    }

}
