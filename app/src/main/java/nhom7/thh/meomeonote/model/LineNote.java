package nhom7.thh.meomeonote.model;

public class LineNote {
    private int id;
    private String title;
    private String shortContent;
    private String lastModified;
    private int catAvt;

    public LineNote(int id, String title, String shortContent, String lastModified, int catAvt) {
        this.id = id;
        this.title = title;
        this.shortContent = shortContent;
        this.lastModified = lastModified;
        this.catAvt = catAvt;
    }

    public LineNote() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getShortContent() {
        return shortContent;
    }

    public void setShortContent(String shortContent) {
        this.shortContent = shortContent;
    }

    public String getLastModified() {
        return lastModified;
    }

    public void setLastModified(String lastModified) {
        this.lastModified = lastModified;
    }

    public int getCatAvt() {
        return catAvt;
    }

    public void setCatAvt(int catAvt) {
        this.catAvt = catAvt;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "LineNote{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", shortContent='" + shortContent + '\'' +
                ", lastModified='" + lastModified + '\'' +
                ", catAvt=" + catAvt +
                '}';
    }
}
