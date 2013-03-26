package com.viberlabs.app.smartreminder;

import android.app.Service;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.IBinder;
import android.util.Log;
import com.viberlabs.app.smartreminder.DaoMaster.DevOpenHelper;
import de.greenrobot.event.EventBus;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: victor
 * Date: 2/12/13
 * Time: 8:26 PM
 * To change this template use File | Settings | File Templates.
 */
public class ReminderManagerService extends Service {

    private static final String TAG = ReminderManagerService.class.getName();;

    private SQLiteDatabase db;

    private DaoMaster daoMaster;
    private DaoSession daoSession;
    private ReminderDao reminderDao;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // FIXME: Cambiar la severidad a debug.
        Log.e(TAG, "Entrando a onStartCommand");

        // Posting ServiceStartedEvent to notify processed reminder
        EventBus.getDefault().post(new Events.ServiceStartedEvent());
        Log.d(TAG, "Enviando evento Events.ServiceStartedEvent");

        return Service.START_STICKY;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        //EventBus
        EventBus.getDefault().register(this);
        Log.d(TAG, "Registrando EventBus en onCreate...");
    }

    @Override
    public void onDestroy() {
        //EventBus
        EventBus.getDefault().unregister(this);
        Log.d(TAG, "UnRegistrando EventBus en onDestroy...");
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.e(TAG, "Entrando a " + TAG + ".onBind");
        return null;
    }

    // EventBus
    public void onEvent (Events.ReminderCreateEvent event){
        Log.d(TAG, "Evento tipo: ReminderCreateEvent. Reminder recibido: " + event.getReminder().getName() + " " +  event.getReminder().getNotes());

        DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "reminder-db", null);
        db = helper.getWritableDatabase();
        daoMaster = new DaoMaster(db);
        daoSession = daoMaster.newSession();
        reminderDao = daoSession.getReminderDao();
        Reminder reminder = event.getReminder();
       // reminder.setId(Long.valueOf("1"));

        reminderDao.insert(reminder);
        Log.d(TAG, "Reminder insertado en la base de datos...");

        // Close database
        db.close();

        // Posting ReminderProcessedEvent to notify processed reminder
        EventBus.getDefault().post(new Events.ReminderProcessedEvent(reminder));
    }

    // EventBus
    public void onEvent (Events.ReminderUpdateEvent event){
        Log.d(TAG, "Evento tipo: ReminderCreateEvent. Reminder recibido: " + event.getReminder().getName() + " " +  event.getReminder().getNotes());

        DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "reminder-db", null);
        db = helper.getWritableDatabase();
        daoMaster = new DaoMaster(db);
        daoSession = daoMaster.newSession();
        reminderDao = daoSession.getReminderDao();
        Reminder reminder = event.getReminder();
        // reminder.setId(Long.valueOf("1"));

        reminderDao.update(reminder);
        Log.d(TAG, "Reminder insertado en la base de datos...");

        // Close database
        db.close();

        // Posting ReminderProcessedEvent to notify processed reminder
        EventBus.getDefault().post(new Events.ReminderProcessedEvent(reminder));
    }

    // EventBus
    public void onEvent (Events.GetRemindersEvent event){
        Log.d(TAG, "Evento tipo: Events.GetRemindersEvent.");


        DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "reminder-db", null);
        db = helper.getWritableDatabase();
        daoMaster = new DaoMaster(db);
        daoSession = daoMaster.newSession();
        reminderDao = daoSession.getReminderDao();

        List<Reminder> reminderList = reminderDao.loadAll();

        Log.d(TAG, "Cantidad de registros obtenidos: " + reminderList.size());

        // Close database
        db.close();

        // Posting ReminderProcessedEvent to notify processed reminder
        EventBus.getDefault().post(new Events.ReturnRemindersListEvent(reminderList));

    }

}
