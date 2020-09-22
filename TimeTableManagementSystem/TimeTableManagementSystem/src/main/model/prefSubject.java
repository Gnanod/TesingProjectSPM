package main.model;

public class prefSubject {

    int id;
    int tagId;
    String subjectId;
    int roomId;

    public prefSubject() { }

    public prefSubject(int id, int tagId, String subjectId, int roomId) {
        this.id = id;
        this.tagId = tagId;
        this.subjectId = subjectId;
        this.roomId = roomId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTagId() {
        return tagId;
    }

    public void setTagId(int tagId) {
        this.tagId = tagId;
    }

    public String getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(String subjectId) {
        this.subjectId = subjectId;
    }

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }
}
