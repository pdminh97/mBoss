package mboss.tsm.Model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.io.Serializable;

import mboss.tsm.mboss.R;

@Entity
public class Version implements Serializable {
    @ColumnInfo(name = "ID")
    @PrimaryKey
    private int ID;
    @ColumnInfo(name = "Number")
    private int number;

    public Version(int ID, int number) {
        this.ID = ID;
        this.number = number;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public static Version[] initVersionData() {
        Version version1 = new Version(1, -1);
        Version version2 = new Version(2, -1);
        return new Version[] {version1, version2};
    }
}
