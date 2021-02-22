/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package weddingplanner.model;

import java.io.Serializable;
import java.util.Objects;

/**
 *
 * @author laila-elhattab
 */
public class Event implements Serializable {

    private String clientEmail;
    private EventType type;
    private long eventDate;
    private Catering food;
    private int budget;
    private Venue place;
    private Theme theme;
    private int attendeesNumber;
    private String attendeesList;
    private String plannerEmail;
    private String plannerResponse;
    private EventStatus status;


    public Event(String clientEmail, EventType type, long eventDate) {
        this.clientEmail = clientEmail;
        this.type = type;
        this.eventDate = eventDate;
        this.attendeesList = "";
        this.setStatus(EventStatus.New);
    }

    public void setType(EventType type) {
        this.type = type;
    }

    public void setEventDate(long eventDate) {
        this.eventDate = eventDate;
    }

    public String getClientEmail() {
        return clientEmail;
    }

    public EventType getType() {
        return type;
    }

    public Catering getFood() {
        return food;
    }

    public void setFood(Catering food) {
        this.food = food;
    }

    public long getEventDate() {
        return eventDate;
    }


    public int getBudget() {
        return budget;
    }

    public void setBudget(int budget) {
        this.budget = budget;
    }

    public Venue getPlace() {
        return place;
    }

    public void setPlace(Venue place) {
        this.place = place;
    }

    public Theme getTheme() {
        return theme;
    }

    public void setTheme(Theme theme) {
        this.theme = theme;
    }

    public String getAttendeesList() {
        return attendeesList;
    }

    public void setAttendeesList(String attendeesList) {
        this.attendeesList = attendeesList;
    }

    public int getAttendeesNumber() {
        return attendeesNumber;
    }

    public void setAttendeesNumber(int attendeesNumber) {
        this.attendeesNumber = attendeesNumber;
    }

    public String getPlannerEmail() {
        return plannerEmail;
    }

    public void setPlannerEmail(String plannerEmail) {
        this.plannerEmail = plannerEmail;
    }

    public String getPlannerResponse() {
        return plannerResponse;
    }

    public void setPlannerResponse(String plannerResponse) {
        this.plannerResponse = plannerResponse;
    }

    public EventStatus getStatus() {
        return status;
    }

    public void setStatus(EventStatus status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Event event = (Event) o;
        return eventDate == event.eventDate &&
                Objects.equals(clientEmail, event.clientEmail) &&
                type == event.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(clientEmail, type, eventDate);
    }
}
