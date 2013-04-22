package com.viberlabs.app.smartreminder;


import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import de.greenrobot.event.EventBus;
//import org.holoeverywhere.LayoutInflater;
//import org.holoeverywhere.app.Activity;
//import org.holoeverywhere.widget.Spinner;


/**
 * Created with IntelliJ IDEA.
 * User: victor
 * Date: 2/13/13
 * Time: 8:29 PM
 * To change this template use File | Settings | File Templates.
 */
public class AddReminderActivity extends SherlockFragmentActivity {

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

        setContentView(R.layout.activity_reminder_detail);

        //EventBus
        EventBus.getDefault().registerSticky(this);

        Bundle arguments = new Bundle();
        arguments.putString(AddReminderFragment.ARG_MODE, AddReminderFragment.MODE_SINGLE_PANE);
        AddReminderFragment fragment = new AddReminderFragment();
        fragment.setArguments(arguments);

        getSupportFragmentManager().beginTransaction()
                .add(R.id.reminder_detail_container, fragment).commit();
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


//    @Override
//    public org.holoeverywhere.LayoutInflater getLayoutInflater() {
//        return (org.holoeverywhere.LayoutInflater) getWindow().getLayoutInflater();
//    }

}