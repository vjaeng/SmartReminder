package com.viberlabs.app.smartreminder;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.actionbarsherlock.app.SherlockFragment;
import com.viberlabs.app.smartreminder.dummy.DummyContent;
import de.greenrobot.event.EventBus;

/**
 * A fragment representing a single Reminder detail screen.
 * This fragment is either contained in a {@link ReminderListActivity}
 * in two-pane mode (on tablets) or a {@link ReminderDetailActivity}
 * on handsets.
 */
public class ReminderDetailFragment extends SherlockFragment {
    private String TAG = ReminderDetailFragment.class.getName();

    private View rootView;
    private Reminder reminder;
    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    public static final String ARG_ITEM_ID = "item_id";

    /**
     * The dummy content this fragment is presenting.
     */
    private DummyContent.DummyItem mItem;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ReminderDetailFragment() {
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
        rootView = inflater.inflate(R.layout.view_reminder, container, false);//.fragment_reminder_detail, container, false);
        TextView reminderName =  (TextView) rootView.findViewById(R.id.name);
        if (reminderName != null){
            reminderName.setText(reminder.getName());
        }
        // ((TextView) rootView.findViewById(R.id.name)).setText(event.getReminder().getName());

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
    }
}
