package com.example.familymapclient;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import Result.EventIDResult;
import Result.PersonIDResult;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

public class ClientModelTest {
    private ClientModel cm;
    private Map<String, EventIDResult> eventMap;
    private Map<String, PersonIDResult> peopleMap;
    private Map<String, List<EventIDResult>> peopleEventMap;
    private PersonIDResult user;
    private Set<PersonIDResult> paternalAncestors;
    private Set<PersonIDResult> maternalAncestors;
    private List<String> eventTypesForUser;
    private List<String> eventTypesForFemaleAncestors;
    private List<String> eventTypesForMaleAncestors;
    private Map<String, List<PersonIDResult>> childrenMap;
    private String authToken;

    private PersonIDResult[] family;
    private EventIDResult[] familyEvents;
    private List<EventIDResult> expectedEventsOfUser;
    private List<EventIDResult> expectedEventsOfFather;
    private List<EventIDResult> expectedEventsOfMother;
    private List<PersonIDResult> expectedParents;
    private List<PersonIDResult> unexpectedParents;


    @BeforeEach
    public void setUp(){
        cm = ClientModel.getInstance();
        eventMap = cm.getEventMap();
        peopleMap = cm.getPeopleMap();
        peopleEventMap = cm.getPeopleEventMap();
        user = cm.getUser();
        paternalAncestors = cm.getPaternalAncestors();
        maternalAncestors = cm.getMaternalAncestors();
        eventTypesForUser = cm.getEventTypesForUser();
        eventTypesForFemaleAncestors = cm.getEventTypesForFemaleAncestors();
        eventTypesForMaleAncestors = cm.getEventTypesForMaleAncestors();
        childrenMap = cm.getChildrenMap();
        authToken = cm.getAuthToken();
        cm.clearClientForTesting();
        cm = ClientModel.getInstance();

        family = new PersonIDResult[7];
        familyEvents = new EventIDResult[6];
        expectedEventsOfUser = new ArrayList<>();
        expectedEventsOfFather = new ArrayList<>();
        expectedEventsOfMother = new ArrayList<>();
        expectedParents = new ArrayList<>();
        unexpectedParents = new ArrayList<>();

        PersonIDResult person1 = new PersonIDResult();PersonIDResult person2 = new PersonIDResult();PersonIDResult person3 = new PersonIDResult();
        PersonIDResult person4 = new PersonIDResult();PersonIDResult person5 = new PersonIDResult();PersonIDResult person6 = new PersonIDResult();
        PersonIDResult person7 = new PersonIDResult();

        person1.setUserName("john");person2.setUserName("john");person3.setUserName("john");person4.setUserName("john");
        person5.setUserName("john");person6.setUserName("john");person7.setUserName("john");

        person1.setGender("m");person2.setGender("m");person3.setGender("f");person4.setGender("m");
        person5.setGender("f");person6.setGender("m");person7.setGender("f");

        person1.setFirstName("john");person1.setLastName("doe");person1.setPersonID("user");
        person2.setFirstName("john's father");person2.setLastName("doe");person2.setPersonID("user's father");
        person3.setFirstName("john's mother");person3.setLastName("doe");person3.setPersonID("user's mother");
        person4.setFirstName("john's paternal grandpa");person4.setLastName("doe");person4.setPersonID("user's paternal grandpa");
        person5.setFirstName("john's paternal grandma");person5.setLastName("doe");person5.setPersonID("user's paternal grandma");
        person6.setFirstName("john's maternal grandpa");person6.setLastName("jones");person6.setPersonID("user's maternal grandpa");
        person7.setFirstName("johns's maternal grandma");person7.setLastName("jones");person7.setPersonID("user's maternal grandma");

        person1.setFatherID("user's father");person1.setMotherID("user's mother");
        person2.setFatherID("user's paternal grandpa");person2.setMotherID("user's paternal grandma");
        person3.setFatherID("user's maternal grandpa");person3.setMotherID("user's maternal grandma");

        person2.setSpouseID(person3.getPersonID());person3.setSpouseID(person2.getPersonID());
        person4.setSpouseID(person5.getPersonID());person5.setSpouseID(person4.getPersonID());
        person6.setSpouseID(person7.getPersonID());person7.setSpouseID(person6.getPersonID());

        family[0] = person1;family[1] = person2;family [2] = person3;family [3] = person4;
        family [4] = person5;family [5] = person6;family [6] = person7;

        expectedParents.add(person2);expectedParents.add(person3);
        unexpectedParents.add(person4);unexpectedParents.add(person5);

        cm.setPeople(family);

        EventIDResult event1 = new EventIDResult();EventIDResult event2 = new EventIDResult();EventIDResult event3 = new EventIDResult();EventIDResult event4 = new EventIDResult();EventIDResult event5 = new EventIDResult();EventIDResult event6 = new EventIDResult();
        event1.setUserName("john");event1.setEventID("thebaptismEvent");event1.setPersonID("user");event1.setEventType("Baptism");event1.setCountry("Bulgaria");event1.setLongitude(0.01);event1.setLatitude(5.5);event1.setCity("Bulgaria city");event1.setYear(1990);
        event2.setUserName("john");event2.setEventID("puppybuyingOfJohn");event2.setPersonID("user");event2.setEventType("Bought a puppy");event2.setYear(1985);event2.setCity("salt lake city");event2.setCountry("USA");event2.setLatitude(800.0);event2.setLongitude(757.0);
        event3.setUserName("john");event3.setEventID("adventureGoingOfJohn");event3.setPersonID("user");event3.setEventType("Adventure");event3.setYear(1995);event3.setCity("Lanki");event3.setCountry("Sri Lanka");event3.setLatitude(830.0);event3.setLongitude(7357.0);
        event4.setUserName("john");event4.setEventID("deathOfJoe'sFather");event4.setPersonID("user's father");event4.setEventType("Death");event4.setYear(1991);event4.setCity("Death Valley");event4.setCountry("California");event4.setLatitude(50.0);event4.setLongitude(57.0);
        event5.setUserName("john");event5.setEventID("deathOfJoe'sMother");event5.setPersonID("user's mother");event5.setEventType("Death");
        event5.setYear(1990);event5.setCity("Las Vegas");event5.setCountry("Nevada");event5.setLatitude(51.0);event5.setLongitude(51.0);
        event6.setUserName("john");event6.setEventID("birthOfJoe'sMother");event6.setPersonID("user's mother");event6.setEventType("Birth");event6.setYear(1960);event6.setCity("Berkely");event6.setCountry("California");event6.setLatitude(100.0);event6.setLongitude(100.0);

        familyEvents[0] = event1;familyEvents[1] = event2;familyEvents[2] = event3;familyEvents[3] = event4;familyEvents[4] = event5;familyEvents[5] = event6;

        expectedEventsOfUser.add(event2);expectedEventsOfUser.add(event1);expectedEventsOfUser.add(event3);
        expectedEventsOfFather.add(event4);
        expectedEventsOfMother.add(event6);expectedEventsOfMother.add(event5);
        cm.setEvents(familyEvents);
    }

    @AfterEach
    public void tearDown(){
        cm.setEventMap(eventMap);
        cm.setPeopleMap(peopleMap);
        cm.setPeopleEventMap(peopleEventMap);
        cm.setUser(user);
        cm.setPaternalAncestors(paternalAncestors);
        cm.setMaternalAncestors(maternalAncestors);
        cm.setEventTypesForUser(eventTypesForUser);
        cm.setEventTypesForFemaleAncestors(eventTypesForFemaleAncestors);
        cm.setEventTypesForMaleAncestors(eventTypesForMaleAncestors);
        cm.setChildrenMap(childrenMap);
        cm = null;
    }

    @Test
    public void testSetPeople(){
        Map<String, PersonIDResult> peopleMap = cm.getPeopleMap();

        for (int i = 0; i < family.length; i++){
            assertEquals(family[i], peopleMap.get(family[i].getPersonID()));
        }
    }


    @Test
    public void testSetEvents(){
        Map<String, EventIDResult> events = cm.getEventMap();
        for (int i = 0; i < familyEvents.length; i++){
            assertEquals(familyEvents[i], events.get(familyEvents[i].getEventID()));
        }
    }

    @Test
    public void testCreateEventsLists(){
        assertEquals(expectedEventsOfUser, cm.getEventsOfPersonByPersonId("user"));
        assertEquals(expectedEventsOfMother, cm.getEventsOfPersonByPersonId("user's mother"));
        assertEquals(expectedEventsOfFather, cm.getEventsOfPersonByPersonId("user's father"));
    }

    @Test
    public void testFindParentsByID(){
        PersonIDResult person1 = new PersonIDResult();
        person1.setUserName("john");person1.setGender("m");person1.setFirstName("john");person1.setLastName("doe");person1.setPersonID("user");

        List<PersonIDResult> output = cm.findParentsByPersonID(person1);
        assertTrue(expectedParents.contains(output.get(0)));
        assertTrue(expectedParents.contains(output.get(1)));
    }

    @Test
    public void testFindParentsByIDShouldFail(){
        PersonIDResult person1 = family[0];
        List<PersonIDResult> output = cm.findParentsByPersonID(person1);
        assertFalse(unexpectedParents.contains(output.get(0)));
        assertFalse(unexpectedParents.contains(output.get(1)));
    }

    @Test
    public void testFindSpouseByPersonID(){
        PersonIDResult person2 = family[1];PersonIDResult person3 = family[2];
        PersonIDResult person6 = family[5];PersonIDResult person7 = family[6];

        PersonIDResult output = cm.findSpouseByPersonID(person3);
        assertEquals(person2, output);
        output = cm.findSpouseByPersonID(person6);
        assertEquals(person7, output);
    }

    @Test
    public void testFindSpouseByPersonIDShouldFail(){
        PersonIDResult person2 = family[1];PersonIDResult person3 = family[2];
        PersonIDResult person5 = family[4];PersonIDResult person6 = family[5];

        PersonIDResult output = cm.findSpouseByPersonID(person3);
        assertNotEquals(person6, output);

        output = cm.findSpouseByPersonID(person5);
        assertNotEquals(person2, output);
    }
}