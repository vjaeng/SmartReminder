package com.viberlabs.app.smartreminder;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: victor
 * Date: 2/20/13
 * Time: 8:38 PM
 * To change this template use File | Settings | File Templates.
 */
public class Events {
    /**
     * Clase de evento para pasar un nuevo event para creación
     */
    public static class ReminderCreateEvent {

        private final Reminder reminder;

        public ReminderCreateEvent(Reminder reminder) {
            this.reminder = reminder;
        }

        public Reminder getReminder() {
            return reminder;
        }
    }
    /**
     * Clase de evento para pasar un evento para modificacion
     */
    public static class ReminderUpdateEvent {

        private final Reminder reminder;

        public ReminderUpdateEvent(Reminder reminder) {
            this.reminder = reminder;
        }

        public Reminder getReminder() {
            return reminder;
        }
    }
    /**
     * Clase de evento para pasar el recordatorio procesado por una operación de creación/actualización
     */
    public static class ReminderProcessedEvent {

        private final Reminder reminder;

        public ReminderProcessedEvent(Reminder reminder) {
            this.reminder = reminder;
        }

        public Reminder getReminder() {
            return reminder;
        }
    }
    /**
     * Clase evento para pasar el recordatorio selecconado de la lista
     */
    public static class ReminderSelectedEvent {

        private final Reminder reminder;

        public ReminderSelectedEvent(Reminder reminder) {
            this.reminder = reminder;
        }

        public Reminder getReminder() {
            return reminder;
        }
    }

    /**
     * Clase de evento para pasar un nuevo event para creación
     */
    public static class GetRemindersEvent {

        private final Reminder reminder;

        public GetRemindersEvent(Reminder reminder) {
            this.reminder = reminder;
        }

        public Reminder getReminder() {
            return reminder;
        }
    }

    /**
     * Clase de evento para pasar un nuevo event para creación
     */
    public static class ServiceStartedEvent {

        public ServiceStartedEvent() {

        }
    }

    /**
     * Clase evento para obtener la lista de recordatorios
     */
    public static class ReturnRemindersListEvent {

        private List<Reminder> remindersList = null;

        public ReturnRemindersListEvent(List<Reminder> remindersList) {
            this.remindersList = remindersList;
        }

        public List<Reminder> getRemindersList() {
            return this.remindersList;
        }
    }
}
