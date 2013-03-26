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
import org.holoeverywhere.app.Fragment;
import org.holoeverywhere.widget.Spinner;

//import android.view.LayoutInflater;

/**
 * A fragment representing a single Reminder detail screen.
 * This fragment is either contained in a {@link com.viberlabs.app.smartreminder.ReminderListActivity}
 * in two-pane mode (on tablets) or a {@link com.viberlabs.app.smartreminder.ReminderDetailActivity}
 * on handsets.
 */
public class AddReminderFragment extends Fragment {
    private String TAG = AddReminderFragment.class.getName();

    private View rootView;
    private Reminder reminder;

    private EditText mName;
    private EditText mNotes;


    private String[] ReminderTypes = {"0", "1", "2", "3",
            "4", "5"};
    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    public static final String ARG_MODE = "mode";
    public static final String MODE_SINGLE_PANE = "single_pane";
    public static final String MODE_TWO_PANE = "two_pane";

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public AddReminderFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //EventBus
        EventBus.getDefault().registerSticky(this);
        Log.d(TAG, "Registrando EventBus en onCreate...");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {

        // Obtiene el argumento de entrads.
        Bundle bundle = this.getArguments();
        String mode = bundle.getString(ARG_MODE);


        if (mode != null && mode.equals(MODE_SINGLE_PANE)){
            // TODO Ver como se puede implementar "android:divider" para separar los botones
            // Inflate a "Done/Discard" custom action bar view.
            /*org.holoeverywhere.LayoutInflater*/ inflater = (/*org.holoeverywhere.*/LayoutInflater) getSupportActionBar().getThemedContext()
                    .getSystemService(getActivity().LAYOUT_INFLATER_SERVICE);
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

                            getActivity().finish(); // TODO: don't just finish()!
                        }
                    });
            customActionBarView.findViewById(R.id.actionbar_discard).setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            // "Discard"
                            getActivity().finish(); // TODO: don't just finish()!

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
        }

        rootView = inflater.inflate(R.layout.add_reminder, container, false);

        // Mapea los campos
        mName = (EditText) rootView.findViewById(R.id.name);
        mNotes = (EditText) rootView.findViewById(R.id.notes);

        // Carga los valores en pantalla.
        if (reminder != null){
            if (mName != null){
                mName.setText(reminder.getName());
            }

            if (mNotes != null){
                mNotes.setText(reminder.getNotes());
            }
        }
        //
//        Views.inject(this);

        Spinner mySpinner = (Spinner) rootView.findViewById(R.id.spinner);
        MyCustomAdapter myCustomAdapter = new MyCustomAdapter(getActivity(), R.layout.row, ReminderTypes);
        if (myCustomAdapter != null) {
            mySpinner.setAdapter(myCustomAdapter);
        }

        return rootView;
    }

    @Override
    public void onDestroy() {
        //EventBus
        EventBus.getDefault().unregister(this);
        Log.d(TAG, "UnRegistrando EventBus en onDestroy...");
        super.onDestroy();
    }

    // EventBus
    public void onEvent (Events.ReminderSelectedEvent event){
      // Crouton.makeText(getActivity(), "Reminder recibido por el BUS " + event.getReminder().getName(), Style.INFO).show();
        // Almacenando el objeto recivido del bus.
        reminder =  event.getReminder();
        // Remueve el evento del Bus
        EventBus.getDefault().removeStickyEvent(event);
    }

    @Override
    public org.holoeverywhere.LayoutInflater getLayoutInflater() {
        return (org.holoeverywhere.LayoutInflater) getActivity().getWindow().getLayoutInflater();
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


            /*org.holoeverywhere.*/LayoutInflater inflater = (LayoutInflater) getActivity().getWindow().getLayoutInflater();
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
