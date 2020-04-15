package com.example.familymapclient;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bignerdranch.expandablerecyclerview.ChildViewHolder;
import com.bignerdranch.expandablerecyclerview.ExpandableRecyclerAdapter;
import com.bignerdranch.expandablerecyclerview.ParentViewHolder;
import com.bignerdranch.expandablerecyclerview.model.Parent;

import com.joanzapata.iconify.IconDrawable;
import com.joanzapata.iconify.fonts.FontAwesomeIcons;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import Result.EventIDResult;
import Result.PersonIDResult;

public class PersonActivity extends AppCompatActivity {
    String selectedPersonID = new String();
    private RecyclerView recyclerView;
    private Adapter adapter;
    private Context here = this;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        selectedPersonID = getIntent().getExtras().getString("personID");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person);

        TextView firstNameTextView = findViewById(R.id.firstNameTitle);
        TextView lastNameTextView = findViewById(R.id.lastNameTitle);
        TextView genderTextView = findViewById(R.id.genderTitle);

        PersonIDResult personSelected;
        personSelected = ClientModel.getInstance().getPersonById(selectedPersonID);

        firstNameTextView.setText(personSelected.getFirstName());
        lastNameTextView.setText(personSelected.getLastName());
        if (personSelected.getGender().equals("m")){
            genderTextView.setText("Male");
        } else {
            genderTextView.setText("Female");
        }

        recyclerView = findViewById(R.id.expandableList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        updateUI();

    }

    void updateUI() {
        List<EventIDResult> eventsOfPerson = ClientModel.getInstance().getEventsOfPersonByPersonId(selectedPersonID);
        PersonIDResult person = ClientModel.getInstance().getPersonById(selectedPersonID);
        List<ShowRow> eventRows = new ArrayList<>();
        List<ShowRow> peopleRows = new ArrayList<>();


        List<PersonIDResult> personsChildren = new ArrayList<>();
        List<PersonIDResult> personsParents;
        PersonIDResult spouse;

        Map<String, List<PersonIDResult>> childrenMap = ClientModel.getInstance().getChildrenMap();
        if (childrenMap.containsKey(person.getPersonID())){
            personsChildren = childrenMap.get(person.getPersonID());
        }
        personsParents = ClientModel.getInstance().findParentsByPersonID(person);
        spouse = ClientModel.getInstance().findSpouseByPersonID(person);

        if (personsParents.size() != 0){
            for (int i = 0; i < personsParents.size(); i++){
                if (personsParents.get(i).getGender().equals("f")){
                    ShowRow temp = new ShowRow(personsParents.get(i).getDescription(),"Mother", "female", personsParents.get(i).getPersonID());
                    peopleRows.add(temp);
                } else {
                    ShowRow temp = new ShowRow(personsParents.get(i).getDescription(),"Father", "male", personsParents.get(i).getPersonID());
                    peopleRows.add(temp);
                }

            }
        }

        if (personsChildren.size() != 0){
            for (int i = 0; i < personsChildren.size(); i++){
                if (personsChildren.get(i).getGender().equals("f")){
                    ShowRow temp = new ShowRow(personsChildren.get(i).getDescription(), "Daughter", "female", personsChildren.get(i).getPersonID());
                    peopleRows.add(temp);
                } else {
                    ShowRow temp = new ShowRow(personsChildren.get(i).getDescription(), "Son", "male", personsChildren.get(i).getPersonID());
                    peopleRows.add(temp);
                }
            }
        }

        if (spouse.getPersonID() != null && !spouse.getPersonID().equals("")){
            if (spouse.getGender().equals("f")){
                ShowRow temp = new ShowRow(spouse.getDescription(), "Wife", "female", spouse.getPersonID());
                peopleRows.add(temp);
            } else {
                ShowRow temp = new ShowRow(spouse.getDescription(), "Husband", "Male", spouse.getPersonID());
                peopleRows.add(temp);
            }
        }

        for (int i = 0; i < eventsOfPerson.size(); i++){
            ShowRow temp = new ShowRow(eventsOfPerson.get(i).getDescription(), person.getDescription(), "event", eventsOfPerson.get(i).getEventID());
            eventRows.add(temp);
        }

        Group eventGroup = new Group("LIFE EVENTS", eventRows);
        Group peopleGroup = new Group("FAMILY", peopleRows);

        List<Group> groups = new ArrayList<>();
        groups.add(eventGroup);
        groups.add(peopleGroup);

        adapter = new Adapter(this, groups);
        recyclerView.setAdapter(adapter);

        adapter.setExpandCollapseListener(
                new ExpandableRecyclerAdapter.ExpandCollapseListener() {
                    @Override
                    public void onParentExpanded(int parentPosition) {
                        adapter.expandParent(parentPosition);
                    }
                    @Override
                    public void onParentCollapsed(int parentPosition) {
                        adapter.collapseParent(parentPosition);
                    }
                });

    }

    class Group implements Parent<ShowRow> {
        String name;
        List<ShowRow> rows;

        Group(String name, List<ShowRow> rows){
            this.name = name;
            this.rows = rows;
        }

        @Override
        public List<ShowRow> getChildList() {

            return rows;
        }
        @Override
        public boolean isInitiallyExpanded() {
            return false;
        }

    }

    class Adapter extends ExpandableRecyclerAdapter<Group, ShowRow, GroupHolder, Holder> {

        private LayoutInflater inflater;

        public Adapter(Context context, List<Group> groups) {
            super(groups);
            inflater = LayoutInflater.from(context);
        }

        @Override
        public GroupHolder onCreateParentViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View view = inflater.inflate(R.layout.group, viewGroup, false);
            return new GroupHolder(view);
        }

        @Override
        public Holder onCreateChildViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View view = inflater.inflate(R.layout.display_row, viewGroup, false);
            return new Holder(view);
        }

        @Override
        public void onBindParentViewHolder(@NonNull GroupHolder holder, int i, Group group) {
            holder.bind(group);
        }

        @Override
        public void onBindChildViewHolder(@NonNull Holder holder, int i, int j, ShowRow item) {
            holder.bind(item);
        }
    }

    class GroupHolder extends ParentViewHolder {

        private TextView textView;

        public GroupHolder(View view) {
            super(view);
            textView = view.findViewById(R.id.groupHolderId);
        }

        void bind(Group group) {
            textView.setText(group.name);
        }

    }

    class Holder extends ChildViewHolder implements View.OnClickListener {

        private TextView textView;
        private TextView textViewTwo;
        private ImageView iconForRow;
        private String rowType;
        private String Id;

        public Holder(View view) {
            super(view);
            textView = view.findViewById(R.id.topTextOfRow);
            textViewTwo = view.findViewById(R.id.bottomTextOfRow);
            iconForRow = view.findViewById(R.id.rowIcon);
            LinearLayout row = view.findViewById(R.id.rowLayout);
            row.setOnClickListener(this);
            rowType = new String();
            Id = new String();
        }

        void bind(ShowRow row) {
            rowType = row.getType();
            Id = row.getIdOfRow();

            textView.setText(row.getTopRow());
            textViewTwo.setText(row.getBottomRow());
            if (row.getType().equals("event")){
                Drawable eventIcon = new IconDrawable(here, FontAwesomeIcons.fa_map_marker).colorRes(R.color.green).sizeDp(40);
                iconForRow.setImageDrawable(eventIcon);
            } else if (row.getType().equals("female")){
                Drawable genderIcon = new IconDrawable(here, FontAwesomeIcons.fa_female).colorRes(R.color.femaleColor).sizeDp(40);
                iconForRow.setImageDrawable(genderIcon);
            } else {
                Drawable genderIcon = new IconDrawable(here, FontAwesomeIcons.fa_male).colorRes(R.color.maleColor).sizeDp(40);
                iconForRow.setImageDrawable(genderIcon);
            }
        }

        @Override
        public void onClick(View view) {
            if (rowType.equals("event")){

                Intent intent = new Intent(here, EventActivity.class);
                Bundle mBundle = new Bundle();

                mBundle.putString("personID", Id);
                intent.putExtras(mBundle);
                startActivity(intent);

            } else {
                Intent intent = new Intent(here, PersonActivity.class);
                Bundle mBundle  = new Bundle();

                mBundle.putString("personID", Id);
                intent.putExtras(mBundle);
                startActivity(intent);
            }
        }
    }
}