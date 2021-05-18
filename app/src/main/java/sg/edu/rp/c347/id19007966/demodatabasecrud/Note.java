package sg.edu.rp.c347.id19007966.demodatabasecrud;

public class Note {
    private int id;
    private String noteContent;

    public Note(int id, String noteContent) {
        this.id = id;
        this.noteContent = noteContent;
    }

    public int getId() {
        return id;
    }

    public String getNoteContent() {
        return noteContent;
    }

    @Override
    public String toString() {
        return "ID:" + id + ", " + noteContent;
    }
}
