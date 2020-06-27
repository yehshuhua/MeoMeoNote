package nhom7.thh.meomeonote.entity;

public class Checklist {
    private int id;
    private String password;
    private String created;
    private String last_modified;
    private int status;
    private int user_id;
    private String date_remove;

    public Checklist(int id, String password, String created, String last_modified, int status, int user_id, String date_remove) {
        this.id = id;
        this.password = password;
        this.created = created;
        this.last_modified = last_modified;
        this.status = status;
        this.user_id = user_id;
        this.date_remove = date_remove;
    }

    public Checklist() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getLast_modified() {
        return last_modified;
    }

    public void setLast_modified(String last_modified) {
        this.last_modified = last_modified;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getDate_remove() {
        return date_remove;
    }

    public void setDate_remove(String date_remove) {
        this.date_remove = date_remove;
    }
}
