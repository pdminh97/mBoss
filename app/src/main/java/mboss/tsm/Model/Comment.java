package mboss.tsm.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class Comment {
    @SerializedName("CommentID")
    @Expose
    private int CommentID ;
    @SerializedName("AccountID")
    @Expose
    private int AccountID;
    @SerializedName("ClinicID")
    @Expose
    private int ClinicID;
    @SerializedName("AccountName")
    @Expose
    private String AccountName;
    @SerializedName("CommentTime")
    @Expose
    private Date CommentTime;
    @SerializedName("Content")
    @Expose
    private String Content;
    @SerializedName("Rating")
    @Expose
    private double Rating;

    public int getAccountID() {
        return AccountID;
    }

    public void setAccountID(int accountID) {
        AccountID = accountID;
    }

    public int getClinicID() {
        return ClinicID;
    }

    public void setClinicID(int clinicID) {
        ClinicID = clinicID;
    }

    public int getCommentID() {
        return CommentID;
    }

    public void setCommentID(int commentID) {
        CommentID = commentID;
    }

    public String getAccountName() {
        return AccountName;
    }

    public void setAccountName(String accountName) {
        AccountName = accountName;
    }

    public Date getCommentTime() {
        return CommentTime;
    }

    public void setCommentTime(Date commentTime) {
        CommentTime = commentTime;
    }

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }

    public double getRating() {
        return Rating;
    }

    public void setRating(double rating) {
        Rating = rating;
    }
}
