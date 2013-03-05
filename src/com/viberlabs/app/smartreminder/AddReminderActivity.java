package com.viberlabs.app.smartreminder;


import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockActivity;

import de.greenrobot.event.EventBus;

/**
 * Created with IntelliJ IDEA.
 * User: victor
 * Date: 2/13/13
 * Time: 8:29 PM
 * To change this template use File | Settings | File Templates.
 */
public class AddReminderActivity extends SherlockActivity {

    private String TAG = AddReminderActivity.class.getName();

    private EditText mName;
    private EditText mNotes;


    String[] ReminderTypes = {"1", "2", "3",
            "4", "5"};

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //


        // Inflate a "Done/Discard" custom action bar view.
        LayoutInflater inflater = (LayoutInflater) getSupportActionBar().getThemedContext()
                .getSystemService(LAYOUT_INFLATER_SERVICE);

        final View customActionBarView = inflater.inflate(
                R.layout.actionbar_custom_view_done_discard, null);       // .actionbar_custom_view_done_discard
        customActionBarView.findViewById(R.id.actionbar_done).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // "Done"
                        // Posting ReminderSelectedEvent to EventBus for add_reminder fragment
                        Reminder reminder = new Reminder();
                        reminder.setPosition("99");
                        reminder.setName(mName.getText().toString());
                        reminder.setNotes(mNotes.getText().toString());
                        EventBus.getDefault().post(new Events.ReminderNewEvent(reminder));

                        finish(); // TODO: don't just finish()!
                    }
                });
        customActionBarView.findViewById(R.id.actionbar_discard).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // "Discard"
                        finish(); // TODO: don't just finish()!
                    }
                });


        // Show the custom action bar view and hide the normal Home icon and title.
        final ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayOptions(
                ActionBar.DISPLAY_SHOW_CUSTOM,
                ActionBar.DISPLAY_SHOW_CUSTOM | ActionBar.DISPLAY_SHOW_HOME
                        | ActionBar.DISPLAY_SHOW_TITLE);
        actionBar.setCustomView(customActionBarView, new ActionBar.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));

        // Carga el layout de los campos de entrada.
        setContentView(R.layout.add_reminder);
        // Mapea los campos
        mName = (EditText) findViewById(R.id.name);
        mNotes = (EditText) findViewById(R.id.notes);

        Spinner mySpinner = (Spinner)findViewById(R.id.spinner);
        MyCustomAdapter myCustomAdapter = new MyCustomAdapter(this, R.layout.row, ReminderTypes);
        if (myCustomAdapter != null){
            mySpinner.setAdapter(myCustomAdapter);
        }


    }


    // SPINNER


    public class MyCustomAdapter extends ArrayAdapter<String> {

        public MyCustomAdapter(Context context, int textViewResourceId,
                               String[] objects) {
            super(context, textViewResourceId, objects);
// TODO Auto-generated constructor stub
        }

        @Override
        public View getDropDownView(int position, View convertView,
                                    ViewGroup parent) {
// TODO Auto-generated method stub
            return getCustomView(position, convertView, parent);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
// TODO Auto-generated method stub
            return getCustomView(position, convertView, parent);
        }

        public View getCustomView(int position, View convertView, ViewGroup parent) {
// TODO Auto-generated method stub


            LayoutInflater inflater=getLayoutInflater();
            View row=inflater.inflate(R.layout.spinner_row, parent, false);


            ImageView icon=(ImageView)row.findViewById(R.id.icon);
            TextView type = (TextView) row.findViewById(R.id.type) ;

            if (ReminderTypes[position]=="1"){
                icon.setImageResource(R.drawable.birthday);
                type.setText("Birthday");
            }
            else if (ReminderTypes[position]=="2"){
                icon.setImageResource(R.drawable.phone_call);
                type.setText("Phone Call");
            }
            else if (ReminderTypes[position]=="3"){
                icon.setImageResource(R.drawable.medicine);
                type.setText("Take Medicine");
            }
            else if (ReminderTypes[position]=="4"){
                icon.setImageResource(R.drawable.mail);
                type.setText("Send Mail");
            }
            else {
                icon.setImageResource(R.drawable.ic_launcher);
                type.setText("Generic");
            }

            return row;
        }
    }


}