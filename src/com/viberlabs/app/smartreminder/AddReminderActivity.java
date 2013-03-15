package com.viberlabs.app.smartreminder;


import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import com.actionbarsherlock.app.ActionBar;
import de.greenrobot.event.EventBus;
import org.holoeverywhere.LayoutInflater;
import org.holoeverywhere.app.Activity;
import org.holoeverywhere.widget.Spinner;


/**
 * Created with IntelliJ IDEA.
 * User: victor
 * Date: 2/13/13
 * Time: 8:29 PM
 * To change this template use File | Settings | File Templates.
 */
public class AddReminderActivity extends Activity {

    private String TAG = AddReminderActivity.class.getName();

    private EditText mName;
    private EditText mNotes;

//    @InjectView(R.id.name) TextView mName;
//    @InjectView(R.id.notes) TextView mNotes;

    private Reminder reminder;

    String[] ReminderTypes = {"0", "1", "2", "3",
            "4", "5"};

    /**
     *
     * @param savedInstanceState
     */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //EventBus
        EventBus.getDefault().registerSticky(this);

        //
        Log.d(TAG, "Antes de inflar el Done/Discard custom action bar view");


        // TODO Ver como se puede implementar "android:divider" para separar los botones
        // Inflate a "Done/Discard" custom action bar view.
        LayoutInflater inflater = (LayoutInflater) getSupportActionBar().getThemedContext()
                .getSystemService(LAYOUT_INFLATER_SERVICE);
        Log.d(TAG, "Antes de inflar el customActionBarView");
        final View customActionBarView = inflater.inflate(
                R.layout.actionbar_custom_view_done_discard, null);       // .actionbar_custom_view_done_discard
        Log.d(TAG, "Antes del setOnClickListener");
        customActionBarView.findViewById(R.id.actionbar_done).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // "Done"
                        // Posting ReminderSelectedEvent to EventBus for add_reminder fragment
                        boolean newReminder = false;
                        if (reminder == null){
                            reminder = new Reminder();
                            newReminder = true;
                        }
                        //
                        reminder.setName(mName.getText().toString());
                        reminder.setNotes(mNotes.getText().toString());
                        //
                        if (newReminder) {
                            // Creacion de nuevo reminder
                            EventBus.getDefault().post(new Events.ReminderCreateEvent(reminder));
                        } else{
                            // Actualizacion de reminder
                            EventBus.getDefault().post(new Events.ReminderUpdateEvent(reminder));
                        }

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

        Log.d(TAG, "Antes de inflar el layout con los campos de entrada");
        // Carga el layout de los campos de entrada.
        setContentView(R.layout.add_reminder);
        // Mapea los campos
        mName = (EditText) findViewById(R.id.name);
        mNotes = (EditText) findViewById(R.id.notes);

        // Carga los valores en pantalla.
        if (reminder != null){
            mName.setText(reminder.getName());
            mNotes.setText(reminder.getNotes());
        }
        //
//        Views.inject(this);

        Spinner mySpinner = (Spinner) findViewById(R.id.spinner);
        MyCustomAdapter myCustomAdapter = new MyCustomAdapter(this, R.layout.row, ReminderTypes);
        if (myCustomAdapter != null) {
            mySpinner.setAdapter(myCustomAdapter);
        }
    }

    @Override
    protected void onDestroy() {
        //EventBus
        EventBus.getDefault().unregister(this);

        super.onDestroy();    //To change body of overridden methods use File | Settings | File Templates.
    }

    // EventBus
    public void onEvent (Events.ReminderSelectedEvent event){
        Log.d(TAG, "Reminder recibido por el BUS " + event.getReminder().getName());
        // Almacenando el objeto recivido del bus.
        reminder =  event.getReminder();
        // Remueve el evento del Bus
        EventBus.getDefault().removeStickyEvent(event);
    }


    @Override
    public org.holoeverywhere.LayoutInflater getLayoutInflater() {
        return (org.holoeverywhere.LayoutInflater) getWindow().getLayoutInflater();
    }


    // SPINNER ADAPTER


    public class MyCustomAdapter extends ArrayAdapter<String> {

        public MyCustomAdapter(Context context, int textViewResourceId,
                               String[] objects) {
            super(context, textViewResourceId, objects);
        }

        @Override
        public View getDropDownView(int position, View convertView,
                                    ViewGroup parent) {
            return getCustomView(position, convertView, parent);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            return getCustomView(position, convertView, parent);
        }

        public View getCustomView(int position, View convertView, ViewGroup parent) {


            LayoutInflater inflater = getLayoutInflater();
            View row = inflater.inflate(R.layout.spinner_row, parent, false);


            ImageView icon = (ImageView) row.findViewById(R.id.icon);
            TextView type = (TextView) row.findViewById(R.id.type);

            if (ReminderTypes[position] == "0") {
                icon.setImageResource(R.drawable.ic_launcher);
                type.setText("Generic reminder");
            } else if (ReminderTypes[position] == "1") {
                icon.setImageResource(R.drawable.birthday);
                type.setText("Birthday");
            } else if (ReminderTypes[position] == "2") {
                icon.setImageResource(R.drawable.phone_call);
                type.setText("Phone Call");
            } else if (ReminderTypes[position] == "3") {
                icon.setImageResource(R.drawable.mail);
                type.setText("Send Mail");
            } else if (ReminderTypes[position] == "4") {
                icon.setImageResource(R.drawable.medicine);
                type.setText("Take Medicine");
            } else if (ReminderTypes[position] == "5") {
                icon.setImageResource(R.drawable.medicine);
                type.setText("Medical Appointment");
            }

            return row;
        }
    }
}