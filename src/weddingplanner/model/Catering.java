/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package weddingplanner.model;

/**
 *
 * @author laila-elhattab
 */
public enum Catering {
     A_PLATED_SIT_DOWN_DINNER("A plated sit down dinner"),
     FAMILY_STYLE_DINNER("Family Style Dinner"),
     BUFFET_STYLE("Buffet Style"),
     FOOD_STATIONS("Food Stations"),
     COCKTAIL_RECEPTIONS("Cocktail Receptions");

     private String displayName;

     Catering(String displayName) {
          this.displayName = displayName;
     }

     @Override
     public String toString() {
          return displayName;
     }
}
