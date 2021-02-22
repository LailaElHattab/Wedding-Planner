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
public enum Venue {
     KEMPINSKI("Kempinski"),
     FAIRMONT("Fairmont"),
     SOFITEL("Sofitel"),
     STEIGENBERGER("Steigenberger") ,
     NOVOTEL("Novotel"),
     NILE_RITZ_CARLTON("Nile Ritz Carlton"),
     HILTON("Hilton"),
     InterContinental("InterContinental"),
     Marriott("Marriott") ,
     ALMASA("AlMasa") ,
     FOUR_SEASONS("Four Seasons");

     private String displayName;

     Venue(String displayName) {
          this.displayName = displayName;
     }

     @Override
     public String toString() {
          return displayName;
     }
}
