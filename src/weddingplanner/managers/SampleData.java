/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package weddingplanner.managers;

import weddingplanner.model.*;

import java.io.File;


public class SampleData {

    private static Planner planner1, planner2,planner3,planner4,planner5;

    private static Client client1, client2, client3,client4, client5,client6;

    public static void main(String[] args) throws Exception {
        /*If you want to delete the files and rerun this sample,
        uncomment the method deleteFiles() and comment the the next 2 lines*/
        //deleteFiles();
        addUsers();
        addEvents();
    }

    private static void deleteFiles() {
        new File("data/users").delete();
        new File("data/events").delete();
    }

    private static void addUsers() throws Exception {

        Admin admin = new Admin("Administrator", "admin@gmail.com", "123".toCharArray());
        UsersManager.getInstance().add(admin);

        planner1 = new Planner("Mark Elliot", "Mark@gmail.com", "456".toCharArray());
        planner1.setExYears(10);
        planner1.setSalary(8000);
        planner1.setPhotoFile("photos/marcos.jpg");
        UsersManager.getInstance().add(planner1);

        planner2 = new Planner("Laila Hani", "Liko.hani@gmail.com", "789".toCharArray());
        planner2.setExYears(5);
        planner2.setSalary(5000);
        planner2.setPhotoFile("photos/laila.jpg");
        UsersManager.getInstance().add(planner2);

        planner3 = new Planner("Farah Khalifa", "Farah.khaled@gmail.com", "789".toCharArray());
        planner3.setExYears(9);
        planner3.setSalary(10000);
        planner3.setPhotoFile("photos/farah.jpeg");
        UsersManager.getInstance().add(planner3);

        planner4 = new Planner("Sarah", "sara.khaled@gmail.com", "1239".toCharArray());
        planner4.setExYears(1);
        planner4.setSalary(1000);
        planner4.setPhotoFile("photos/sarah.jpeg");
        UsersManager.getInstance().add(planner4);

        planner5 = new Planner("Ghada", "Ghada.medhat@gmail.com", "4569".toCharArray());
        planner5.setExYears(10);
        planner5.setSalary(10000);
        planner5.setPhotoFile("photos/ghada.jpeg");
        UsersManager.getInstance().add(planner5);


        client1 = new Client("Yosr", "yosr@gmail.com", "123".toCharArray());
        client1.setActive(true);
        client1.setAge(35);
        client1.setRegistrationDate(DateUtils.toTimestamp("8/1/2021"));
        UsersManager.getInstance().add(client1);

        client2 = new Client("Youmna", "Youmna@gmail.com", "789".toCharArray());
        client2.setActive(true);
        client2.setAge(20);
        client2.setRegistrationDate(DateUtils.toTimestamp("1/2/2021"));
        UsersManager.getInstance().add(client2);

        client3 = new Client("Yola", "yola@gmail.com", "567".toCharArray());
        client3.setActive(false);
        client3.setAge(21);
        client3.setRegistrationDate(DateUtils.toTimestamp("20/3/2021"));
        UsersManager.getInstance().add(client3);

        client4 = new Client("Amiina", "Amiina@gmail.com", "567".toCharArray());
        client4.setActive(false);
        client4.setAge(32);
        client4.setRegistrationDate(DateUtils.toTimestamp("10/6/2021"));
        UsersManager.getInstance().add(client4);

        client5 = new Client("Mai", "Mai@gmail.com", "567".toCharArray());
        client5.setActive(true);
        client5.setAge(30);
        client5.setRegistrationDate(DateUtils.toTimestamp("3/1/2021"));
        UsersManager.getInstance().add(client5);

        client6 = new Client("Maha", "Maha@gmail.com", "567".toCharArray());
        client6.setActive(false);
        client6.setAge(30);
        client6.setRegistrationDate(DateUtils.toTimestamp("7/8/2021"));
        UsersManager.getInstance().add(client6);
    }

    private static void addEvents() throws Exception {

        addEvent(client1, EventType.BABY_SHOWER, "1/3/2021",
                10000, Catering.BUFFET_STYLE,
                150, Venue.Marriott, Theme.Modern,
                planner1, "I like the catering style with the baby shower but what" +
                        " about changing the theme from modern to classic.", EventStatus.Accepted);

        addEvent(client2, EventType.ENGAGEMENT, "15/3/2021",
                20000, Catering.FAMILY_STYLE_DINNER,
                250, Venue.ALMASA, Theme.Classic,
                planner2, "This is going to be so cool!! I like the venue it suits" +
                        " the the engagement but what about changing the theme." , EventStatus.Assigned);

        addEvent(client5, EventType.WEDDING, "5/3/2021",
                10000, Catering.COCKTAIL_RECEPTIONS,
                150, Venue.Marriott, Theme.Modern,
                planner1, "Perfect!", EventStatus.New);

        addEvent(client6, EventType.GENDER_REVEL, "2/1/2021",
                10000, Catering.BUFFET_STYLE,
                150, Venue.FOUR_SEASONS, Theme.Modern,
                planner1, "I'm so in love with your event theme!", EventStatus.New);

    }

    private static void addEvent(Client client, EventType type, String eventDate,
                                 int budget, Catering food, int attendees, Venue place, Theme theme,
                                 Planner planner, String plannerResponse, EventStatus status) throws Exception {
        Event event = new Event(client.getEmail(), type, DateUtils.toTimestamp(eventDate));
        event.setBudget(budget);
        event.setFood(food);
        event.setAttendeesNumber(attendees);
        event.setPlace(place);
        event.setTheme(theme);
        if (planner != null) {
            event.setPlannerEmail(planner.getEmail());
        }
        event.setPlannerResponse(plannerResponse);
        event.setStatus(status);
        EventManager.getInstance().add(event);
    }


}
