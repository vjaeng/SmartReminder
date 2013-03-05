package com.viberlabs.app.smartreminder;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
//import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.actionbarsherlock.app.SherlockListFragment;
import de.greenrobot.event.EventBus;
import de.keyboardsurfer.android.widget.crouton.Crouton;
import de.keyboardsurfer.android.widget.crouton.Style;

import java.util.List;

/**
 * A list fragment representing a list of Reminders. This fragment
 * also supports tablet devices by allowing list items to be given an
 * 'activated' state upon selection. This helps indicate which item is
 * currently being viewed in a {@link ReminderDetailFragment}.
 * <p>
 * Activities containing this fragment MUST implement the {@link Callbacks}
 * interface.
 */
public class ReminderListFragment extends SherlockListFragment {
    private String TAG = ReminderListFragment.class.getName();

    ReminderArrayAdapter arrayAdapter;
    List<Reminder> reminderList;

    /**
     * The serialization (saved instance state) Bundle key representing the
     * activated item position. Only used on tablets.
     */
    private static final String STATE_ACTIVATED_POSITION = "activated_position";

    /**
     * The fragment's current callback object, which is notified of list item
     * clicks.
     */
    private Callbacks mCallbacks = sDummyCallbacks;

    /**
     * The current activated item position. Only used on tablets.
     */
    private int mActivatedPosition = ListView.INVALID_POSITION;

    /**
     * A callback interface that all activities containing this fragment must
     * implement. This mechanism allows activities to be notified of item
     * selections.
     */
    public interface Callbacks {
        /**
         * Callback for when an item has been selected.
         */
        public void onItemSelected(String id);
    }

    /**
     * A dummy implementation of the {@link Callbacks} interface that does
     * nothing. Used only when this fragment is not attached to an activity.
     */
    private static Callbacks sDummyCallbacks = new Callbacks() {
        @Override
        public void onItemSelected(String id) {
        }
    };

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ReminderListFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        //EventBus
//        EventBus.getDefault().register(this);
//        Log.d(TAG, "Registrando EventBus en onCreate...");

        //EventBus
        EventBus.getDefault().register(this);

        
        // TODO VJaen
//        int layout = android.R.layout.simple_list_item_1;
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR1){//.ECLAIR_MR1)
//        	Log.e(this.getClass().getName().toString(), "Entrando a [Build.VERSION.SDK_INT >= Build.VERSION_CODES.ECLAIR_MR1]" + Build.VERSION.SDK_INT + ">=" + Build.VERSION_CODES.ECLAIR_MR1+"?");
//           layout = android.R.layout.simple_list_item_activated_1;
//        }
//        Log.e(this.getClass().getName().toString(), "Seteando en ListAdapter...");
//        setListAdapter(new ArrayAdapter<DummyContent.DummyItem>(
//              getActivity(),
//              layout,
//              android.R.id.text1,
//              DummyContent.ITEMS));
//        Log.d(this.getClass().getName().toString(), "ListAdapter seteado...");
        
        // TODO: replace with a real list adapter.
//        setListAdapter(new ArrayAdapter<DummyContent.DummyItem>(
//                getActivity(),
//                android.R.layout.simple_list_item_activated_1,
//                android.R.id.text1,
//                DummyContent.ITEMS));


    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Restore the previously serialized activated item position.
        if (savedInstanceState != null
                && savedInstanceState.containsKey(STATE_ACTIVATED_POSITION)) {
            setActivatedPosition(savedInstanceState.getInt(STATE_ACTIVATED_POSITION));
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        // Activities containing this fragment must implement its callbacks.
        if (!(activity instanceof Callbacks)) {
            throw new IllegalStateException("Activity must implement fragment's callbacks.");
        }

        mCallbacks = (Callbacks) activity;
    }

    @Override
    public void onDetach() {
        super.onDetach();

        // Reset the active callbacks interface to the dummy implementation.
        mCallbacks = sDummyCallbacks;
    }

    @Override
    public void onListItemClick(ListView listView, View view, int position, long id) {
        super.onListItemClick(listView, view, position, id);

        // Notify the active callbacks interface (the activity, if the
        // fragment is attached to one) that an item has been selected.
        mCallbacks.onItemSelected("BlaBlaBla");
        //DummyContent.ITEMS.get(position).id

        // Posting ReminderSelectedEvent to EventBus for add_reminder fragment

        Reminder reminder = (Reminder) reminderList.get(position);
     //   reminder.setPosition(DummyContent.ITEMS.get(position).id);
        EventBus.getDefault().postSticky(new Events.ReminderSelectedEvent(reminder));
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (mActivatedPosition != ListView.INVALID_POSITION) {
            // Serialize and persist the activated item position.
            outState.putInt(STATE_ACTIVATED_POSITION, mActivatedPosition);
        }
    }

    /**
     * Turns on activate-on-click mode. When this mode is on, list items will be
     * given the 'activated' state when touched.
     */
    public void setActivateOnItemClick(boolean activateOnItemClick) {
        // When setting CHOICE_MODE_SINGLE, ListView will automatically
        // give items the 'activated' state when touched.
        getListView().setChoiceMode(activateOnItemClick
                ? ListView.CHOICE_MODE_SINGLE
                : ListView.CHOICE_MODE_NONE);
    }

    private void setActivatedPosition(int position) {
        if (position == ListView.INVALID_POSITION) {
            getListView().setItemChecked(mActivatedPosition, false);
        } else {
            getListView().setItemChecked(position, true);
        }

        mActivatedPosition = position;
    }


    // EventBus
    public void onEvent (Events.ReminderProcessedEvent event){
        Crouton.makeText(getActivity(), "Reminder procesado " + event.getReminder().getName() + " - " + event.getReminder().getId(), Style.INFO).show();
        // Posting GetRemindersEvent to notify processed reminder
        Reminder reminder = new Reminder();
        EventBus.getDefault().post(new Events.GetRemindersEvent(reminder));
        Log.d(TAG, "Enviando evento Events.GetRemindersEvent");
    }


    public void onEvent (Events.ReturnRemindersListEvent event){
        Log.d(TAG, "Evento tipo: Events.ReturnRemindersListEvent.  Cantidad de registros recibidos: " + event.getRemindersList().size());

        // Carga los registro recibidos en la lista
        reminderList = event.getRemindersList();
        arrayAdapter = new ReminderArrayAdapter(
        getActivity(), R.layout.row, reminderList);
        setListAdapter(arrayAdapter);

//        Log.d(TAG, "EL reminderList tiene " + reminderList.size());
//
//        // Google Card
//        googleCardsAdapter = new GoogleCardsAdapter(getActivity(),  R.layout.activity_googlecards_card,
//        reminderList);
//
//        googleCardsAdapter.
//        getListView().setAdapter(googleCardsAdapter);
//        Log.d(TAG, "Seteado el ListAdapter a googleCardsAdapter");
    }

//    // **** SERVICE INVOCATION ****
//    // Service used to get reminders list
//    private IReminderService reminderService;
//
//    /****/
//    // Is the service bound currently?
//    private boolean bound = false;
//
//    // Connection to the stock service, handles lifecycle events
//    private ServiceConnection connection = new ServiceConnection() {
//
//        public void onServiceConnected(ComponentName className, IBinder service) {
//            reminderService = IReminderService.Stub.asInterface(service);
//            Log.d(TAG, "Connected to service");
//
//
//
//            try {
//                // Invoca este metodo solo para iniciar el servicio.
//                Log.d(TAG, "Invoca este metodo solo para iniciar el servicio");
//                reminderService.startService();
//            } catch (RemoteException e) {
//                // TODO Auto-generated catch block
//                e.printStackTrace();
//            }
//            //
//
//        }
//
//        public void onServiceDisconnected(ComponentName className) {
//            reminderService = null;
//            Log.d(TAG, "Disconnected from service");
//        }
//    };
//
//
//    @Override
//    public void onStart() {
//        super.onStart();
//        Log.d(TAG, "onStart - Inicio");
//        // create initial list
//        if (!bound) {
//            bound = getActivity().bindService(new Intent(getActivity(),
//                    ReminderManagerService.class), connection,
//                    Context.BIND_AUTO_CREATE);
//            Log.d(TAG, "Bound to service: " + bound);
//
//        }
//        if (!bound) {
//            Log.e(TAG, "Failed to bind to service");
//            throw new RuntimeException("Failed to bind to service");
//        }
//
//        Log.d(TAG, "onStart - Fin");
//    }
//
//    @Override
//    public void onPause() {
//        Log.d(TAG, "onPause - Inicio");
//        super.onPause();
//        if (bound) {
//            bound = false;
//            getActivity().unbindService(connection);
//            Log.d(TAG, "onPause - UnbindService to service");
//        }
//        Log.d(TAG, "onPause - Fin");
//    }

    @Override
    public void onDestroy() {
        Log.d(TAG, "onDestroy - Inicio");
        super.onDestroy();
        // disconnect from the stock service
//        if (bound) {
//            bound = false;
//            getActivity().unbindService(connection);
//            Log.d(TAG, "onDestroy - UnbindService to service");
//        }

        //EventBus
        EventBus.getDefault().unregister(this);

        Log.d(TAG, "onDestroy - Fin");
    }


    // GOOGLE CARD

//    GoogleCardsAdapter googleCardsAdapter;// = new GoogleCardsAdapter(getActivity());
//
//    private class GoogleCardsAdapter extends ArrayAdapter<Reminder> {
//        //private String TAG = ReminderArrayAdapter.class.getName();
//
//        private List<Reminder> reminderList;
//        private LayoutInflater mInflater;
//
//        private Context mContext;
//
//        public GoogleCardsAdapter(Context context, List<Integer> indices, List<Reminder> reminderList) {
//            //,	int textViewResourceId,
//            super(indices);
//            //super(context, android.R.layout.simple_list_item_1, reminderList);
//            //resource, textViewResourceId,
//            // TODO Auto-generated constructor stub
//            this.reminderList = reminderList;
//
//           // mInflater = LayoutInflater.from(context);
//            Log.d(TAG, "googleCardsAdapter.Constructor.  Reminders recibidos: " + reminderList.size());
//        }
//
//
//
////        public GoogleCardsAdapter(Context context) {
////            mContext = context;
////        }
//
//        @Override
//        public View getView(int position, View convertView, ViewGroup parent) {
//// TODO Auto-generated method stub
//            return getCustomView(position, convertView, parent);
//        }
//
//        //        public View getCustomView(int position, View convertView, ViewGroup parent) {
//
//
//        public View getCustomView(int position, View convertView, ViewGroup parent) {
//            Log.d(TAG, "Dentro de GoogleCardsAdapter.getView para posicion " + position);
//            View view = convertView;
//            if (view == null) {
//                view = LayoutInflater.from(mContext).inflate(R.layout.activity_googlecards_card, parent, false);
//
//            }
//
//            TextView textView = (TextView) view.findViewById(R.id.activity_googlecards_card_textview);
//            textView.setText("This is card is for " + (getItem(position).getName()));
//
//            ImageView imageView = (ImageView) view.findViewById(R.id.activity_googlecards_card_imageview);
//            int imageResId;
//
//            switch (position % 5) {
//                case 0:
//                    imageResId = R.drawable.img_nature1;
//                    break;
//                case 1:
//                    imageResId = R.drawable.img_nature2;
//                    break;
//                case 2:
//                    imageResId = R.drawable.img_nature3;
//                    break;
//                case 3:
//                    imageResId = R.drawable.img_nature4;
//                    break;
//                default:
//                    imageResId = R.drawable.img_nature5;
//            }
//            imageView.setImageResource(imageResId);
//
//            return view;
//        }
//    }
}
