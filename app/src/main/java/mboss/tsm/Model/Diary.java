package mboss.tsm.Model;

import android.annotation.SuppressLint;
import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.List;

//@SuppressLint("ParcelCreator")
@Entity(tableName = "Diary")
public class Diary implements Parcelable {
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

    public Diary(String content, String diaryTime) {
        Content = content;
        DiaryTime = diaryTime;
    }


    public Diary(String content) {
        Content = content;
    }

    public Diary() {
    }

    protected Diary(Parcel in) {
        DiaryID = in.readInt();
        BossID = in.readInt();
        CategoryID = in.readInt();
        Content = in.readString();
        DiaryTime = in.readString();
        Status = in.readByte() != 0;
        Image = in.readString();
        NotifyChecked = in.readByte() != 0;
        UriImages = in.createTypedArrayList(Uri.CREATOR);
    }

    public static final Creator<Diary> CREATOR = new Creator<Diary>() {
        @Override
        public Diary createFromParcel(Parcel in) {
            return new Diary(in);
        }

        @Override
        public Diary[] newArray(int size) {
            return new Diary[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(DiaryID);
        dest.writeInt(BossID);
        dest.writeInt(CategoryID);
        dest.writeString(Content);
        dest.writeString(DiaryTime);
        dest.writeByte((byte) (Status ? 1 : 0));
        dest.writeString(Image);
        dest.writeByte((byte) (NotifyChecked ? 1 : 0));
        dest.writeTypedList(UriImages);
    }
}
