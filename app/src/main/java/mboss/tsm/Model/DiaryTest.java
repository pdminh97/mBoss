package mboss.tsm.Model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.net.Uri;

import java.io.Serializable;
import java.util.List;

//@SuppressLint("ParcelCreator")
@Entity(tableName = "DiaryTest")
public class DiaryTest implements Serializable {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "DiaryID")
    private int DiaryID;
    @ColumnInfo(name = "BossID")
    private int BossID;
    @ColumnInfo(name = "CategoryID")
    private int CategoryID;
    @ColumnInfo(name = "Content")
    private String Content;
    @ColumnInfo(name = "DiaryTime")
    private String DiaryTime;
    @ColumnInfo(name = "Status")
    private boolean Status;
    @ColumnInfo(name = "Image")
    private String Image;
    @ColumnInfo(name = "NotifyChecked")
    private boolean NotifyChecked;
    @Ignore
    private List<Uri> UriImages;
    @Ignore
    private List<Tag> tageds;

    public DiaryTest(String content, String diaryTime) {
        Content = content;
        DiaryTime = diaryTime;
    }


    public DiaryTest(String content) {
        Content = content;
    }

    public DiaryTest() {
    }




    public List<Tag> getTageds() {
        return tageds;
    }

    public void setTageds(List<Tag> tageds) {
        this.tageds = tageds;
    }

    public List<Uri> getUriImages() {
        return UriImages;
    }

    public void setUriImages(List<Uri> uriImages) {
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

    public boolean isStatus() {
        return Status;
    }

    public void setStatus(boolean status) {
        Status = status;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public boolean isNotifyChecked() {
        return NotifyChecked;
    }

    public void setNotifyChecked(boolean notifyChecked) {
        NotifyChecked = notifyChecked;
    }

    public int getBossID() {
        return BossID;
    }

    public void setBossID(int bossID) {
        BossID = bossID;
    }

    public int getCategoryID() {
        return CategoryID;
    }

    public void setCategoryID(int categoryID) {
        CategoryID = categoryID;
    }


}
