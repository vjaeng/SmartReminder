package com.viberlabs.app.smartreminder;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import de.greenrobot.event.EventBus;
import de.keyboardsurfer.android.widget.crouton.Crouton;
import de.keyboardsurfer.android.widget.crouton.Style;


/**
 * An activity representing a list of Reminders. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link ReminderDetailActivity} representing
 * item add_reminder. On tablets, the activity presents the list of items and
 * item add_reminder side-by-side using two vertical panes.
 * <p>
 * The activity makes heavy use of fragments. The list of items is a
 * {@link ReminderListFragment} and the item add_reminder
 * (if present) is a {@link ReminderDetailFragment}.
 * <p>
 * This activity also implements the required
 * {@link ReminderListFragment.Callbacks} interface
 * to listen for item selections.
 */
public class ReminderListActivity extends SherlockFragmentActivity
        implements ReminderListFragment.Callbacks {

    private String TAG = ReminderListActivity.class.getName();

    public static int THEME = R.style.Theme_Sherlock_Light ;

    Intent service;

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private boolean mTwoPane;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminder_list);

        //setTheme(THEME);

        if (findViewById(R.id.reminder_detail_container) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-large and
            // res/values-sw600dp). If this view is present, then the
            // activity should be in two-pane mode.
            mTwoPane = true;

            // In two-pane mode, list items should be given the
            // 'activated' state when touched.
            ((ReminderListFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.reminder_list))
                    .setActivateOnItemClick(true);
        }

        // disable the home button and the up affordance:
        getSupportActionBar().setHomeButtonEnabled(false);

        // TODO: If exposing deep links into your app, handle intents here.


        //EventBus
        EventBus.getDefault().register(this);


        // Inicia el servicio
        service = new Intent(this, ReminderManagerService.class);
        this.startService(service);
        Log.d(TAG, "Iniciando servicio ReminderManagerService");


    }

    // EventBus
    public void onEvent (Events.ServiceStartedEvent event){
        Log.d(TAG, "Evento tipo: Events.ServiceStartedEvent.");

        // Posting GetRemindersEvent to notify processed reminder
        Reminder reminder = new Reminder();
        EventBus.getDefault().post(new Events.GetRemindersEvent(reminder));
        Log.d(TAG, "Enviando evento Events.GetRemindersEvent");
    }

    /**
     * Callback method from {@link ReminderListFragment.Callbacks}
     * indicating that the item with the given ID was selected.
     */
    @Override
    public void onItemSelected(String id) {
        if (mTwoPane) {
            // In two-pane mode, show the detail view in this activity by
            // adding or replacing the detail fragment using a
            // fragment transaction.
           // Bundle arguments = new Bundle();
           // arguments.putString(ReminderDetailFragment.ARG_ITEM_ID, id);
            ReminderDetailFragment fragment = new ReminderDetailFragment();
           // fragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.reminder_detail_container, fragment)
                    .commit();

        } else {
            // In single-pane mode, simply start the detail activity
            // for the selected item ID.
            Intent detailIntent = new Intent(this, ReminderDetailActivity.class);
           // detailIntent.putExtra(ReminderDetailFragment.ARG_ITEM_ID, id);
            startActivity(detailIntent);
        }
    }

/*    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getSupportMenuInflater();
        inflater.inflate(R.menu.activity_item_detail, menu);
        return true;
    }*/

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //Used to put dark icons on light action bar
        boolean isLight = THEME == R.style.Theme_Sherlock_Light;


        menu.add(0, 0, 1,"Add")
                .setIcon(isLight ? R.drawable.ic_action_add_holo_light : R.drawable.ic_action_add_holo_dark)
                .setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM | MenuItem.SHOW_AS_ACTION_WITH_TEXT);

        menu.add(0, 1, 2,"Search")
                .setIcon(isLight ? R.drawable.ic_action_search_holo_light : R.drawable.ic_action_search_holo_dark)
                .setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM | MenuItem.SHOW_AS_ACTION_WITH_TEXT);

        menu.add(0, 2, 3,"About")
               .setShowAsAction(MenuItem.SHOW_AS_ACTION_NEVER);


        return true;
    }

    @Override
    protected void onDestroy() {
        Crouton.cancelAllCroutons();
        // Detiene el servicio.
        this.stopService(service);

        //EventBus
        EventBus.getDefault().unregister(this);

        super.onDestroy();    //To change body of overridden methods use File | Settings | File Templates.
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case 0:

                //Crouton.makeText(this, "Add action pressed", Style.ALERT).show();
                Intent intent = new Intent(getApplicationContext(),
                        AddReminderActivity.class);
                startActivityForResult(intent, 1);
                break;

            case 1:
                Crouton.makeText(this, "Search action pressed", Style.CONFIRM).show();
                break;
            case 2:
                Crouton.makeText(this, "About action pressed", Style.INFO).show();
                Intent aboutIntent = new Intent(getApplicationContext(),
                        LicensesActivity.class);
                startActivity(aboutIntent);
                break;

        }

        return true;
    }

}
