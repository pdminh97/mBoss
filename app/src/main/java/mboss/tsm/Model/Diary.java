package mboss.tsm.Model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "Diary")
public class Diary {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "DiaryID")
    private int DiaryID;
    @ColumnInfo(name = "Content")
    private String Content;
    @ColumnInfo(name = "DiaryTime")
    private String DiaryTime;

    public Diary(String content, String diaryTime) {
        Content = content;
        DiaryTime = diaryTime;
    }

    public Diary() {
    }

    public int getDiaryID() {
        return DiaryID;
    }

    public void setDiaryID(int diaryID) {
        DiaryID = diaryID;
    }

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }

    public String getDiaryTime() {
        return DiaryTime;
    }

    public void setDiaryTime(String diaryTime) {
        DiaryTime = diaryTime;
    }
}
