package com.example.familymapclient;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import Result.PersonIDResult;
import Result.EventIDResult;

public class ClientModel {

    private static ClientModel myObj;

    private Map<String, EventIDResult> eventMap;
    private Map<String,PersonIDResult> peopleMap;
    private Map<String, List<EventIDResult>> peopleEventMap;
    private PersonIDResult user;
    private Set<PersonIDResult> paternalAncestors;
    private Set<PersonIDResult> maternalAncestors;
    private List<String> eventTypesForUser;
    private List<String> eventTypesForFemaleAncestors;
    private List<String> eventTypesForMaleAncestors;
    private List<String> allEventTypes;
    private Map<String, List<PersonIDResult>> childrenMap;
    private String authToken;
    private boolean loggedIn = false;
    private boolean showFemaleEvents = true;
    private boolean showMaleEvents = true;

    private ClientModel() {
        peopleMap = new HashMap<>();
        peopleEventMap = new HashMap<>();
        eventMap = new HashMap<>();
        user = new PersonIDResult();
        paternalAncestors = new HashSet<>();
        maternalAncestors = new HashSet<>();
        eventTypesForUser= new ArrayList<>();
        eventTypesForFemaleAncestors = new ArrayList<>();
        eventTypesForMaleAncestors = new ArrayList<>();
        childrenMap = new HashMap<>();
        authToken = new String();
        loggedIn = false;
    }

    public static ClientModel getInstance() {
        if (myObj == null) {
            myObj = new ClientModel();
        }
        return myObj;
    }

    public void setPeople(PersonIDResult[] input) {
        user = input[0];

        for (int i = 0; i < input.length; i++){
            peopleMap.put(input[i].getPersonID(), input[i]);
        }

        createPaternalAndMaternalSets();
    }

    public void createPaternalAndMaternalSets(){
        PersonIDResult motherOfUser = peopleMap.get(user.getMotherID());
        PersonIDResult fatherOfUser = peopleMap.get(user.getFatherID());

        addParents(motherOfUser,true);
        addParents(fatherOfUser,false);

        for (Map.Entry<String, PersonIDResult> entry : peopleMap.entrySet()) {
            String possibleParentPersonID = new String(entry.getValue().getPersonID());
            List<PersonIDResult> childrenOfPossible = new ArrayList<>();

            for(Map.Entry<String, PersonIDResult> entryTwo : peopleMap.entrySet()){
                if (possibleParentPersonID.equals(entryTwo.getValue().getFatherID()) || possibleParentPersonID.equals(entryTwo.getValue().getMotherID())){
                    childrenOfPossible.add(entryTwo.getValue());
                }
            }
            childrenMap.put(possibleParentPersonID, childrenOfPossible);
        }
    }

    public void setEvents(EventIDResult[] input){

        for (int i = 0; i < input.length; i++){

            eventMap.put(input[i].getEventID(), input[i]);

            if (peopleEventMap.containsKey(input[i].getPersonID())){
                List<EventIDResult> eventListFromMap = peopleEventMap.get(input[i].getPersonID());

                if (input[i].getYear() < eventListFromMap.get(0).getYear()){

                    eventListFromMap.add(0, input[i]);
                } else if (input[i].getYear() > eventListFromMap.get(eventListFromMap.size()-1).getYear()){
                    eventListFromMap.add(input[i]);
                } else if(input[i].getYear() == eventListFromMap.get(eventListFromMap.size()-1).getYear()){
                    eventListFromMap.add(eventListFromMap.size()-1, input[i]);
                } else {
                    for (int j = 0; j < eventListFromMap.size() - 1; ++j){
                        if ((input[i].getYear() > eventListFromMap.get(j).getYear()) && (!(input[i].getYear() > eventListFromMap.get(j+1).getYear()))){
                            eventListFromMap.add(j+1, input[i]);
                        }
                    }
                }
                peopleEventMap.put(input[i].getPersonID(), eventListFromMap);
            }
            else {
                List<EventIDResult> in = new ArrayList<>();
                in.add(input[i]);
                peopleEventMap.put(input[i].getPersonID(), in);
            }
        }
        createEventLists();
    }

    public void createEventLists(){

        for (Map.Entry<String, List<EventIDResult>> entry: peopleEventMap.entrySet()){
              if(entry.getKey().equals(user.getPersonID())){
                for (int i = 0; i < entry.getValue().size(); i++){
                    eventTypesForUser.add(entry.getValue().get(i).getEventType().toLowerCase());
                }
            } else if (peopleMap.get(entry.getKey()).getGender().equals("f")) {
                for (int i = 0; i < entry.getValue().size(); i++){
                    eventTypesForFemaleAncestors.add(entry.getValue().get(i).getEventType().toLowerCase());
                }
            } else if (peopleMap.get(entry.getKey()).getGender().equals("m")){
                for (int i = 0; i < entry.getValue().size(); i++){  //initiliazes male event types
                    eventTypesForMaleAncestors.add(entry.getValue().get(i).getEventType().toLowerCase());
                }
            }
        }
    }

    private void addParents(PersonIDResult personReceived, Boolean isMaternalSide){
        if (isMaternalSide){
            maternalAncestors.add(personReceived);
        } else {
            paternalAncestors.add(personReceived);
        }

        if (personReceived.getFatherID() == null || personReceived.getFatherID().equals("")){
            return;
        } else {
            if (isMaternalSide){
                PersonIDResult motherOfPersonReceived = peopleMap.get(personReceived.getMotherID());
                PersonIDResult fatherOfPersonReceived = peopleMap.get(personReceived.getFatherID());
                addParents(motherOfPersonReceived, true);
                addParents(fatherOfPersonReceived, true);
            } else {
                PersonIDResult motherOfPersonReceived = peopleMap.get(personReceived.getMotherID());
                PersonIDResult fatherOfPersonReceived = peopleMap.get(personReceived.getFatherID());
                addParents(motherOfPersonReceived,false);
                addParents(fatherOfPersonReceived,false);
            }
        }
    }

    public List<PersonIDResult> findParentsByPersonID(PersonIDResult potentialChild){
        List<PersonIDResult> parents = new ArrayList<>();

        for (Map.Entry<String, List<PersonIDResult>> entryTwo: childrenMap.entrySet()){
            String possibleParent = entryTwo.getKey();
            List<PersonIDResult> children = entryTwo.getValue();

            for (int i = 0; i < children.size(); i++){
                if (potentialChild.getPersonID().equals(children.get(i).getPersonID())){
                    parents.add(peopleMap.get(possibleParent));
                }
            }
        }
        return parents;
    }

    public PersonIDResult findSpouseByPersonID(PersonIDResult potentialSpouse){
        PersonIDResult foundSpouse = new PersonIDResult();

        for (Map.Entry<String, PersonIDResult> entry: peopleMap.entrySet()){
            if (entry.getValue().getSpouseID().equals(potentialSpouse.getPersonID())){
                return entry.getValue();
            }
        }
        return foundSpouse;
    }


    public Map<String,PersonIDResult> getPeopleMap(){ return peopleMap; }

    public PersonIDResult getPersonById(String id){ return peopleMap.get(id); }

    public Map<String, List<EventIDResult>> getPeopleEventMap() { return peopleEventMap; }

    public Map<String, EventIDResult> getEventMap(){ return eventMap; }

    public List<EventIDResult> getEventsOfPersonByPersonId(String id){ return peopleEventMap.get(id); }

    public EventIDResult getEventById(String id){ return eventMap.get(id); }

    public PersonIDResult getUser(){ return user; }

    public List<String> getEventTypesForUser() { return eventTypesForUser; }

    public List<String> getEventTypesForFemaleAncestors() { return eventTypesForFemaleAncestors; }

    public List<String> getEventTypesForMaleAncestors() { return eventTypesForMaleAncestors; }

    public Map<String, List<PersonIDResult>> getChildrenMap(){ return childrenMap; }

    public Set<PersonIDResult> getPaternalAncestors() {return paternalAncestors;}

    public Set<PersonIDResult> getMaternalAncestors() {return maternalAncestors;}

    public void setEventMap(Map<String, EventIDResult> eventMap) { this.eventMap = eventMap; }

    public void setPeopleMap(Map<String, PersonIDResult> peopleMap) { this.peopleMap = peopleMap; }

    public void setPeopleEventMap(Map<String, List<EventIDResult>> peopleEventMap) { this.peopleEventMap = peopleEventMap; }

    public void setUser(PersonIDResult user) { this.user = user; }

    public void setPaternalAncestors(Set<PersonIDResult> paternalAncestors) { this.paternalAncestors = paternalAncestors; }

    public void setMaternalAncestors(Set<PersonIDResult> maternalAncestors) { this.maternalAncestors = maternalAncestors; }

    public void setEventTypesForUser(List<String> eventTypesForUser) { this.eventTypesForUser = eventTypesForUser; }

    public void setEventTypesForFemaleAncestors(List<String> eventTypesForFemaleAncestors) { this.eventTypesForFemaleAncestors = eventTypesForFemaleAncestors; }

    public void setEventTypesForMaleAncestors(List<String> eventTypesForMaleAncestors) { this.eventTypesForMaleAncestors = eventTypesForMaleAncestors; }

    public void setChildrenMap(Map<String, List<PersonIDResult>> childrenMap) { this.childrenMap = childrenMap; }

    public String getAuthToken() { return authToken; }

    public void setAuthToken(String authToken) { this.authToken = authToken; }

    public boolean isLoggedIn() { return loggedIn; }

    public void setLoggedIn(boolean loggedIn) { this.loggedIn = loggedIn; }

    public List<String> getAllEventTypes() { return allEventTypes; }

    public void setAllEventTypes(List<String> allEventTypes) { this.allEventTypes = allEventTypes; }

    public boolean isShowMaleEvents() { return showMaleEvents; }

    public void setShowMaleEvents(boolean showMaleEvents) { this.showMaleEvents = showMaleEvents; }

    public boolean isShowFemaleEvents() { return showFemaleEvents; }

    public void setShowFemaleEvents(boolean showFemaleEvents) { this.showFemaleEvents = showFemaleEvents; }

    public void clearClientForTesting() {
        eventMap.clear();
        eventMap = null;
        peopleMap.clear();
        peopleMap = null;
        peopleEventMap.clear();
        peopleEventMap = null;
        user = null;
        paternalAncestors.clear();
        paternalAncestors = null;
        maternalAncestors.clear();
        maternalAncestors = null;
        eventTypesForUser.clear();
        eventTypesForUser = null;
        eventTypesForFemaleAncestors.clear();
        eventTypesForFemaleAncestors = null;
        eventTypesForMaleAncestors.clear();
        eventTypesForMaleAncestors = null;
        childrenMap.clear();
        childrenMap = null;
        authToken = null;
        myObj = null;
    }

}