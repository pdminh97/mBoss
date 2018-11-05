package mboss.tsm.Model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.net.Uri;

import java.io.Serializable;

@Entity(tableName = "Diary")
public class Diary implements Serializable {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "DiaryID")
    private int DiaryID;
    @ColumnInfo(name = "Content")
    private String Content;
    @ColumnInfo(name = "DiaryTime")
    private String DiaryTime;
    private Uri[] UriImages;

    public Diary(String content, String diaryTime) {
        Content = content;
        DiaryTime = diaryTime;
    }

    public Diary(String content) {
        Content = content;
    }

    public Diary() {
    }

    public Uri[] getUriImages() {
        return UriImages;
    }

    public void setUriImages(Uri[] uriImages) {
        UriImages = uriImages;
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
