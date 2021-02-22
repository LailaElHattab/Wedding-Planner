/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package weddingplanner.model;

/**
 * @author laila-elhattab
 */
public enum EventType {
    ENGAGEMENT("Engagement"),
    WEDDING("Wedding"),
    BACHELOR_PARTY("Bachelor Party"),
    GENDER_REVEL("General Revel"),
    BABY_SHOWER("Baby Shower");

    private String displayName;

    EventType(String displayName) {
        this.displayName = displayName;
    }

    @Override
    public String toString() {
        return displayName;
    }
}
