package com.viberlabs.app.smartreminder;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.DaoConfig;
import de.greenrobot.dao.Property;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table REMINDER.
*/
public class ReminderDao extends AbstractDao<Reminder, Long> {

    public static final String TABLENAME = "REMINDER";

    /**
     * Properties of entity Reminder.<br/>
     * Can be used for QueryBuilder and for referencing column names.
    */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property Name = new Property(1, String.class, "name", false, "NAME");
        public final static Property Type = new Property(2, String.class, "type", false, "TYPE");
        public final static Property Notes = new Property(3, String.class, "notes", false, "NOTES");
        public final static Property DueDate = new Property(4, java.util.Date.class, "dueDate", false, "DUE_DATE");
        public final static Property Position = new Property(5, String.class, "position", false, "POSITION");
    };


    public ReminderDao(DaoConfig config) {
        super(config);
    }
    
    public ReminderDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "'REMINDER' (" + //
                "'_id' INTEGER PRIMARY KEY AUTOINCREMENT ," + // 0: id
                "'NAME' TEXT NOT NULL ," + // 1: name
                "'TYPE' TEXT," + // 2: type
                "'NOTES' TEXT," + // 3: notes
                "'DUE_DATE' INTEGER," + // 4: dueDate
                "'POSITION' TEXT);"); // 5: position
    }

    /** Drops the underlying database table. */
    public static void dropTable(SQLiteDatabase db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "'REMINDER'";
        db.execSQL(sql);
    }

    /** @inheritdoc */
    @Override
    protected void bindValues(SQLiteStatement stmt, Reminder entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
        stmt.bindString(2, entity.getName());
 
        String type = entity.getType();
        if (type != null) {
            stmt.bindString(3, type);
        }
 
        String notes = entity.getNotes();
        if (notes != null) {
            stmt.bindString(4, notes);
        }
 
        java.util.Date dueDate = entity.getDueDate();
        if (dueDate != null) {
            stmt.bindLong(5, dueDate.getTime());
        }
 
        String position = entity.getPosition();
        if (position != null) {
            stmt.bindString(6, position);
        }
    }

    /** @inheritdoc */
    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    /** @inheritdoc */
    @Override
    public Reminder readEntity(Cursor cursor, int offset) {
        Reminder entity = new Reminder( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.getString(offset + 1), // name
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // type
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // notes
            cursor.isNull(offset + 4) ? null : new java.util.Date(cursor.getLong(offset + 4)), // dueDate
            cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5) // position
        );
        return entity;
    }
     
    /** @inheritdoc */
    @Override
    public void readEntity(Cursor cursor, Reminder entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setName(cursor.getString(offset + 1));
        entity.setType(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setNotes(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setDueDate(cursor.isNull(offset + 4) ? null : new java.util.Date(cursor.getLong(offset + 4)));
        entity.setPosition(cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5));
     }
    
    /** @inheritdoc */
    @Override
    protected Long updateKeyAfterInsert(Reminder entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    /** @inheritdoc */
    @Override
    public Long getKey(Reminder entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    /** @inheritdoc */
    @Override    
    protected boolean isEntityUpdateable() {
        return true;
    }
    
}