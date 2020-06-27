package nhom7.thh.meomeonote.dbhelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import com.prolificinteractive.materialcalendarview.CalendarDay;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

import nhom7.thh.meomeonote.entity.Attachment;
import nhom7.thh.meomeonote.entity.Checklist;
import nhom7.thh.meomeonote.entity.Note;
import nhom7.thh.meomeonote.entity.User;
import nhom7.thh.meomeonote.util.BaseUtil;

import static java.lang.Integer.parseInt;

public class DbHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "my_note";
    private static final String USER_TABLE_NAME = "user";
    private static final String USER_ID = "id";
    private static final String USER_PHONE_NUMBER = "phone_number";
    private static final String USER_PASSWORD = "password";
    private static final String USER_STATUS = "status";

    private static final String NOTE_TABLE_NAME = "note";
    private static final String NOTE_ID = "id";
    private static final String NOTE_PASSWORD = "password";
    private static final String NOTE_TITLE = "title";
    private static final String NOTE_CONTENT = "content";
    private static final String NOTE_CREATED = "created";
    private static final String NOTE_LAST_MODIFIED = "last_modified";
    private static final String NOTE_TIMER = "timer";
    private static final String NOTE_STATUS = "status";
    private static final String NOTE_USER_ID = "user_id";
    private static final String NOTE_CAT_NAME = "cat_name";


    private static final String CHECKLIST_TABLE_NAME = "checklist";
    private static final String CHECKLIST_ID = "id";
    private static final String CHECKLIST_PASSWORD = "password";
    private static final String CHECKLIST_CONTENT = "content";
    private static final String CHECKLIST_CREATED = "created";
    private static final String CHECKLIST_LAST_MODIFIED = "last_modified";
    private static final String CHECKLIST_STATUS = "status";
    private static final String CHECKLIST_USER_ID = "user_id";
    private static final String CHECKLIST_DATE_REMOVE = "date_remove";

    private static final String ATTACHMENT_TABLE_NAME = "attachment";
    private static final String ATTACHMENT_ID = "id";
    private static final String ATTACHMENT_LINK = "link";
    private static final String ATTACHMENT_TYPE = "type";
    private static final String ATTACHMENT_CREATED = "created";
    private static final String ATTACHMENT_LAST_MODIFIED = "last_modified";
    private static final String ATTACHMENT_STATUS = "status";
    private static final String ATTACHMENT_NOTE_ID = "note_id";
    private Context context;

    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
        Log.d("DB Manager", "DB Manager");
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sqlQueryUser = "CREATE TABLE " + USER_TABLE_NAME + " (" +
                USER_ID + " integer primary key AUTOINCREMENT, " +
                USER_PASSWORD + " TEXT," +
                USER_STATUS + " TEXT," +
                USER_PHONE_NUMBER + " TEXT)";
        db.execSQL(sqlQueryUser);

        String sqlQueryNote = "CREATE TABLE " + NOTE_TABLE_NAME + " (" +
                NOTE_ID + " integer primary key AUTOINCREMENT , " +
                NOTE_PASSWORD + " TEXT," +
                NOTE_CONTENT + " TEXT," +
                NOTE_TITLE + " TEXT," +
                NOTE_TIMER + " TEXT," +
                NOTE_CREATED + " TEXT," +
                NOTE_LAST_MODIFIED + " TEXT," +
                NOTE_CAT_NAME + " TEXT," +
                NOTE_STATUS + " integer," +
                NOTE_USER_ID + " integer)";
        db.execSQL(sqlQueryNote);

        String sqlQueryChecklist = "CREATE TABLE " + CHECKLIST_TABLE_NAME + " (" +
                CHECKLIST_ID + " integer primary key AUTOINCREMENT , " +
                CHECKLIST_CONTENT + " TEXT," +
                CHECKLIST_CREATED + " TEXT," +
                CHECKLIST_LAST_MODIFIED + " TEXT," +
                CHECKLIST_PASSWORD + " TEXT," +
                CHECKLIST_DATE_REMOVE + " TEXT," +
                CHECKLIST_STATUS + " integer," +
                CHECKLIST_USER_ID + " integer)";
        db.execSQL(sqlQueryChecklist);

        String sqlQueryAtt = "CREATE TABLE " + ATTACHMENT_TABLE_NAME + " (" +
                ATTACHMENT_ID + " integer primary key AUTOINCREMENT, " +
                ATTACHMENT_LINK + " TEXT," +
                ATTACHMENT_STATUS + " integer," +
                ATTACHMENT_CREATED + " TEXT," +
                ATTACHMENT_LAST_MODIFIED + " TEXT," +
                ATTACHMENT_TYPE + " TEXT," +
                ATTACHMENT_NOTE_ID + " integer)";
        db.execSQL(sqlQueryAtt);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + USER_TABLE_NAME);
        onCreate(db);
        Toast.makeText(context, "Drop successfully", Toast.LENGTH_SHORT).show();

    }

    public void addUser(User user) {
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(USER_PHONE_NUMBER, user.getPhoneNumber());
            values.put(USER_PASSWORD, user.getPassword());
            values.put(USER_STATUS, 1);
            if (getUserByPhoneNumber(user.getPhoneNumber()) != null) {
                db.update(USER_TABLE_NAME, values, USER_ID + "=?", new String[]{String.valueOf(user.getId())});
            } else
                db.insert(USER_TABLE_NAME, null, values);
            db.close();
        } catch (Exception e) {
            Log.v("error", e.toString());
        }
    }

    public User getUserById(int id) {
        try {
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cursor = db.query(USER_TABLE_NAME, new String[]{USER_ID
                            , USER_PHONE_NUMBER, USER_PASSWORD}, USER_ID + "=?",
                    new String[]{String.valueOf(id)}, null, null, null, null);
            if (cursor != null)
                cursor.moveToFirst();
            else
                return null;
            User User = new User(cursor.getInt(0), cursor.getString(1), cursor.getString(2), parseInt(cursor.getString(3)));
            cursor.close();
            db.close();
            return User;
        } catch (Exception e) {
            Log.v("error", e.toString());
            return null;
        }
    }

    public int updateUser(User user) {
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(USER_ID, user.getId());
            values.put(USER_PHONE_NUMBER, user.getPhoneNumber());
            values.put(USER_PASSWORD, user.getPassword());
            return db.update(USER_TABLE_NAME, values, USER_ID + "=?", new String[]{String.valueOf(user.getId())});
        } catch (Exception e) {
            Log.v("error", e.toString());
            return 0;
        }
    }

    public int deleteUser(User user) {
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(USER_ID, user.getId());
            values.put(USER_STATUS, 0);
            return db.update(USER_TABLE_NAME, values, USER_ID + "=?", new String[]{String.valueOf(user.getId())});
        } catch (Exception e) {
            Log.v("error", e.toString());
            return 0;
        }
    }

    public List<User> getAllUser() {
        try {
            List<User> listUser = new ArrayList<>();
            String selectQuery = "SELECT  * FROM " + USER_TABLE_NAME;
            SQLiteDatabase db = this.getWritableDatabase();
            Cursor cursor = db.rawQuery(selectQuery, null);
            if (cursor.moveToFirst()) {
                do {
                    User user = new User();
                    user.setId(cursor.getInt(0));
                    user.setPhoneNumber(cursor.getString(1));
                    user.setPassword(cursor.getString(2));
                    listUser.add(user);
                } while (cursor.moveToNext());
            }
            cursor.close();
            db.close();
            return listUser;
        } catch (Exception e) {
            Log.v("error", e.toString());
            return null;
        }
    }

    public User getUserByPhoneNumber(String phoneNumber) {
        try {
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cursor = db.query(USER_TABLE_NAME, new String[]{USER_ID,
                            USER_ID, USER_PHONE_NUMBER, USER_PASSWORD}, USER_PHONE_NUMBER + "=?",
                    new String[]{String.valueOf(phoneNumber)}, null, null, null, null);
            if (cursor != null)
                cursor.moveToFirst();
            else
                return null;
            User User = new User(cursor.getInt(0), cursor.getString(1),
                    cursor.getString(2), parseInt(cursor.getString(3)));
            cursor.close();
            db.close();
            return User;
        } catch (Exception e) {
            Log.v("error", e.toString());
            return null;
        }
    }


    public long addNote(Note note) {
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(NOTE_PASSWORD, note.getPassword());
            values.put(NOTE_CONTENT, note.getContent());
            values.put(NOTE_CREATED, note.getCreated());
            values.put(NOTE_LAST_MODIFIED, note.getLast_modified());
            values.put(NOTE_STATUS, note.getStatus());
            values.put(NOTE_TIMER, note.getTimer());
            values.put(NOTE_TITLE, note.getTitle());
            values.put(NOTE_USER_ID, note.getUser_id());
            values.put(NOTE_CAT_NAME, note.getCatName());
            long id = db.insert(NOTE_TABLE_NAME, null, values);
            db.close();
            return id;
        } catch (Exception e) {
            Log.v("error", e.toString());
            return 0;
        }
    }

    public Note getNoteById(int id) {
        try {
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cursor = db.query(NOTE_TABLE_NAME, new String[]{NOTE_ID,
                            NOTE_TITLE, NOTE_CREATED, NOTE_CONTENT
                            , NOTE_STATUS, NOTE_USER_ID, NOTE_LAST_MODIFIED
                            , NOTE_PASSWORD, NOTE_TIMER, NOTE_CAT_NAME}, NOTE_ID + "=?",
                    new String[]{String.valueOf(id)}, null, null, null, null);
            if (cursor != null)
                cursor.moveToFirst();
            else
                return null;
            Note note = new Note();
            note.setId(cursor.getInt(0));
            note.setPassword(cursor.getString(1));
            note.setContent(cursor.getString(2));
            note.setCreated(cursor.getString(3));
            note.setLast_modified(cursor.getString(4));
            note.setStatus(cursor.getInt(5));
            note.setTimer(cursor.getString(6));
            note.setTitle(cursor.getString(7));
            note.setUser_id(cursor.getInt(8));
            note.setCatName(cursor.getString(9));
            cursor.close();
            db.close();
            return note;
        } catch (Exception e) {
            Log.v("error", e.toString());
            return null;
        }

    }

    public int updateNote(Note note) {
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(NOTE_ID, note.getId());
            values.put(NOTE_PASSWORD, note.getPassword());
            values.put(NOTE_CONTENT, note.getContent());
            values.put(NOTE_CREATED, note.getCreated());
            values.put(NOTE_LAST_MODIFIED, note.getLast_modified());
            values.put(NOTE_STATUS, note.getStatus());
            values.put(NOTE_TIMER, note.getTimer());
            values.put(NOTE_TITLE, note.getTitle());
            values.put(NOTE_USER_ID, note.getUser_id());
            values.put(NOTE_CAT_NAME, note.getCatName());
            return db.update(NOTE_TABLE_NAME, values, NOTE_ID + "=?", new String[]{String.valueOf(note.getId())});
        } catch (Exception e) {
            Log.v("error", e.toString());
            return 0;
        }
    }

    public int deleteNote(Note note) {
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(NOTE_ID, note.getId());
            values.put(NOTE_STATUS, 0);
            return db.update(NOTE_TABLE_NAME, values, NOTE_ID + "=?", new String[]{String.valueOf(note.getId())});
        } catch (Exception e) {
            Log.v("error", e.toString());
            return 0;
        }
    }

    public List<Note> getNodeByUserIdAndDate(int userId, String date) {
        List<Note> list = getNodeByUserId(userId);
        List<Note> listReturn = new ArrayList<>();
        for (Note note : list) {
            if (BaseUtil.compareDate(date, note.getTimer())) {
                listReturn.add(note);
            }
        }
        return listReturn;
    }

    public HashSet<CalendarDay> getNodeByUserIdAndMonth(int userId, String date) {
        HashSet<CalendarDay> set = new HashSet<>();
        List<Note> list = getNodeByUserId(userId);
        List<Note> listReturn = new ArrayList<>();
        for (Note note : list) {
            if (BaseUtil.compareDate(date, note.getTimer())) {
                listReturn.add(note);
            }
        }
        for (Note note : listReturn) {
            String d = note.getTimer();
            Date date1 = null;
            try {
                date1 = new SimpleDateFormat("dd/MM/yyyy").parse(d.split("\\s+")[1]);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            CalendarDay calDay = CalendarDay.from(date1);
            set.add(calDay);
        }
        return set;
    }

    public List<Note> getNodeByUserId(int userId) {
        try {
            SQLiteDatabase db = this.getReadableDatabase();
            List<Note> nodes = new ArrayList<>();
            Cursor cursor = db.query(NOTE_TABLE_NAME, new String[]{NOTE_ID,
                            NOTE_PASSWORD, NOTE_TITLE, NOTE_CONTENT, NOTE_CREATED,
                            NOTE_LAST_MODIFIED, NOTE_TIMER, NOTE_STATUS, NOTE_USER_ID, NOTE_CAT_NAME},
                    NOTE_USER_ID + "=?" + " AND " + NOTE_STATUS + "= 1 ",
                    new String[]{String.valueOf(userId)}, null, null, null, null);
            if (cursor != null)
                cursor.moveToFirst();
            else
                return null;
            do {
                Note note = new Note();
                note.setId(cursor.getInt(0));
                note.setPassword(cursor.getString(1));
                note.setTitle(cursor.getString(2));
                note.setContent(cursor.getString(3));
                note.setCreated(cursor.getString(4));
                note.setLast_modified(cursor.getString(5));
                note.setTimer(cursor.getString(6));
                note.setStatus(cursor.getInt(7));
                note.setUser_id(cursor.getInt(8));
                note.setCatName(cursor.getString(9));
                nodes.add(note);
            } while (cursor.moveToNext());
            cursor.close();
            db.close();
            return nodes;
        } catch (Exception e) {
            Log.v("error", e.toString());
            return null;
        }
    }

    public long addChecklist(Checklist checklist) {
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(CHECKLIST_PASSWORD, checklist.getPassword());
            values.put(CHECKLIST_CONTENT, checklist.getContent());
            values.put(CHECKLIST_CREATED, checklist.getCreated());
            values.put(CHECKLIST_LAST_MODIFIED, checklist.getLast_modified());
            values.put(CHECKLIST_STATUS, checklist.getStatus());
            values.put(CHECKLIST_USER_ID, checklist.getUser_id());
            values.put(CHECKLIST_DATE_REMOVE, checklist.getDateRemove());
            long id = db.insert(CHECKLIST_TABLE_NAME, null, values);
            db.close();
            return id;
        } catch (Exception e) {
            Log.v("error", e.toString());
            return 0;
        }
    }

    public Checklist getChecklistById(int id) {
        try {
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cursor = db.query(CHECKLIST_TABLE_NAME, new String[]{CHECKLIST_ID,
                            CHECKLIST_CONTENT, CHECKLIST_PASSWORD, CHECKLIST_LAST_MODIFIED,
                            CHECKLIST_CREATED, CHECKLIST_STATUS, CHECKLIST_USER_ID, CHECKLIST_DATE_REMOVE},
                    CHECKLIST_ID + "=?",
                    new String[]{String.valueOf(id)}, null, null, null, null);
            if (cursor != null)
                cursor.moveToFirst();
            else
                return null;
            Checklist checklist = new Checklist();
            checklist.setId(cursor.getInt(0));
            checklist.setPassword(cursor.getString(2));
            checklist.setContent(cursor.getString(1));
            checklist.setCreated(cursor.getString(4));
            checklist.setLast_modified(cursor.getString(3));
            checklist.setStatus(cursor.getInt(5));
            checklist.setUser_id(cursor.getInt(6));
            checklist.setDateRemove(cursor.getString(7));
            cursor.close();
            db.close();
            return checklist;
        } catch (Exception e) {
            Log.v("error", e.toString());
            return null;
        }

    }

    public int updateChecklist(Checklist checklist) {
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(CHECKLIST_USER_ID, checklist.getId());
            values.put(CHECKLIST_PASSWORD, checklist.getPassword());
            values.put(CHECKLIST_CONTENT, checklist.getContent());
            values.put(CHECKLIST_CREATED, checklist.getCreated());
            values.put(CHECKLIST_LAST_MODIFIED, checklist.getLast_modified());
            values.put(CHECKLIST_STATUS, checklist.getStatus());
            values.put(CHECKLIST_USER_ID, checklist.getUser_id());
            values.put(CHECKLIST_DATE_REMOVE, checklist.getDateRemove());
            return db.update(CHECKLIST_TABLE_NAME, values, CHECKLIST_ID + "=?",
                    new String[]{String.valueOf(checklist.getId())});
        } catch (Exception e) {
            Log.v("error", e.toString());
            return 0;
        }
    }

    public int deleteChecklist(Checklist checklist) {
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(CHECKLIST_ID, checklist.getId());
            values.put(CHECKLIST_DATE_REMOVE, checklist.getDateRemove());
            values.put(CHECKLIST_STATUS, 0);
            return db.update(CHECKLIST_TABLE_NAME, values, CHECKLIST_ID + "=?",
                    new String[]{String.valueOf(checklist.getId())});
        } catch (Exception e) {
            Log.v("error", e.toString());
            return 0;
        }
    }

    public List<Checklist> getChecklistByUserId(int userId) {
        try {
            SQLiteDatabase db = this.getReadableDatabase();
            List<Checklist> checklists = new ArrayList<>();
            Cursor cursor = db.query(CHECKLIST_TABLE_NAME, new String[]{CHECKLIST_ID,
                            CHECKLIST_PASSWORD, CHECKLIST_CONTENT, CHECKLIST_CREATED,
                            CHECKLIST_LAST_MODIFIED, CHECKLIST_STATUS, CHECKLIST_USER_ID, CHECKLIST_DATE_REMOVE},
                    CHECKLIST_USER_ID + "=?" + " AND " + CHECKLIST_STATUS + "= 1 ",
                    new String[]{String.valueOf(userId)}, null, null, null, null);
            if (cursor != null)
                cursor.moveToFirst();
            else
                return null;
            do {
                Checklist checklist = new Checklist();
                checklist.setId(cursor.getInt(0));
                checklist.setPassword(cursor.getString(1));
                checklist.setContent(cursor.getString(2));
                checklist.setCreated(cursor.getString(3));
                checklist.setLast_modified(cursor.getString(4));
                checklist.setStatus(cursor.getInt(5));
                checklist.setUser_id(cursor.getInt(6));
                checklist.setDateRemove(cursor.getString(7));
                checklists.add(checklist);
            } while (cursor.moveToNext());
            cursor.close();
            db.close();
            return checklists;
        } catch (Exception e) {
            Log.v("error", e.toString());
            return null;
        }
    }


    public List<Checklist> getNodeByUserIdOrderByCreaded(int userId) {
        List<Checklist> checklists = getChecklistByUserId(userId);
        Collections.sort(checklists, new Comparator<Checklist>() {
            @Override
            public int compare(Checklist o1, Checklist o2) {
                int time1 = parseInt(o1.getCreated());
                int time2 = parseInt(o2.getCreated());
                return time2 - time1;
            }
        });
        return checklists;
    }

    public void addAttachment(Attachment attachment) {
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(ATTACHMENT_CREATED, attachment.getCreated());
            values.put(ATTACHMENT_LAST_MODIFIED, attachment.getLast_modified());
            values.put(ATTACHMENT_LINK, attachment.getLink());
            values.put(ATTACHMENT_STATUS, 1);
            values.put(ATTACHMENT_TYPE, attachment.getType());
            values.put(ATTACHMENT_NOTE_ID, attachment.getNote_id());
            db.insert(ATTACHMENT_TABLE_NAME, null, values);
            db.close();
        } catch (Exception e) {
            Log.v("error", e.toString());
        }
    }

    public Attachment getAttachmentById(int id) {
        try {
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cursor = db.query(ATTACHMENT_TABLE_NAME, new String[]{ATTACHMENT_ID,
                            ATTACHMENT_CREATED, ATTACHMENT_LAST_MODIFIED, ATTACHMENT_LINK
                            , ATTACHMENT_STATUS, ATTACHMENT_TYPE, ATTACHMENT_NOTE_ID}, ATTACHMENT_ID + "=?",
                    new String[]{String.valueOf(id)}, null, null, null, null);
            if (cursor != null)
                cursor.moveToFirst();
            else
                return null;
            Attachment attachment = new Attachment();
            attachment.setId(cursor.getInt(0));
            attachment.setCreated(cursor.getString(1));
            attachment.setLast_modified(cursor.getString(2));
            attachment.setLink(cursor.getString(3));
            attachment.setStatus(cursor.getInt(4));
            attachment.setType(cursor.getString(5));
            attachment.setNote_id(cursor.getInt(6));
            cursor.close();
            db.close();
            return attachment;
        } catch (Exception e) {
            Log.v("error", e.toString());
            return null;
        }
    }

    public int updateAttachment(Attachment attachment) {
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(ATTACHMENT_ID, attachment.getId());
            values.put(ATTACHMENT_CREATED, attachment.getCreated());
            values.put(ATTACHMENT_LAST_MODIFIED, attachment.getLast_modified());
            values.put(ATTACHMENT_LINK, attachment.getLink());
            values.put(ATTACHMENT_STATUS, attachment.getStatus());
            values.put(ATTACHMENT_TYPE, attachment.getType());
            values.put(ATTACHMENT_NOTE_ID, attachment.getNote_id());
            return db.update(ATTACHMENT_TABLE_NAME, values, ATTACHMENT_ID + "=?", new String[]{String.valueOf(attachment.getId())});
        } catch (Exception e) {
            Log.v("error", e.toString());
            return 0;
        }
    }

    public int deleteAttachment(Attachment attachment) {
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(ATTACHMENT_ID, attachment.getId());
            values.put(ATTACHMENT_STATUS, 0);
            return db.update(ATTACHMENT_TABLE_NAME, values, ATTACHMENT_ID + "=?", new String[]{String.valueOf(attachment.getId())});
        } catch (Exception e) {
            Log.v("error", e.toString());
            return 0;
        }
    }


    public List<Attachment> getAttachmentByNoteId(int noteId) {
        try {
            SQLiteDatabase db = this.getReadableDatabase();
            List<Attachment> attachments = new ArrayList<>();
            Cursor cursor = db.query(ATTACHMENT_TABLE_NAME, new String[]{ATTACHMENT_ID,
                            ATTACHMENT_CREATED, ATTACHMENT_LAST_MODIFIED, ATTACHMENT_NOTE_ID,
                            ATTACHMENT_TYPE, ATTACHMENT_STATUS, ATTACHMENT_LINK}, ATTACHMENT_NOTE_ID + "=?" + " AND " + ATTACHMENT_STATUS + "= 1 ",
                    new String[]{String.valueOf(noteId)}, null, null, null, null);
            if (cursor != null)
                cursor.moveToFirst();
            else
                return null;
            do {
                Attachment attachment = new Attachment();
                attachment.setId(cursor.getInt(0));
                attachment.setCreated(cursor.getString(1));
                attachment.setLast_modified(cursor.getString(2));
                attachment.setNote_id(cursor.getInt(3));
                attachment.setType(cursor.getString(4));
                attachment.setStatus(cursor.getInt(5));
                attachment.setLink(cursor.getString(6));
                attachments.add(attachment);
            } while (cursor.moveToNext());
            cursor.close();
            db.close();
            return attachments;
        } catch (Exception e) {
            Log.v("error", e.toString());
            return null;
        }
    }
}
