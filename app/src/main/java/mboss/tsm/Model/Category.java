package mboss.tsm.Model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.util.Log;

import java.io.Serializable;

import mboss.tsm.mboss.R;

@Entity
public class Category  implements Serializable {
    @PrimaryKey()
    @ColumnInfo(name = "CategoryID")
    private  int categoryID;
    @ColumnInfo(name = "Image")
    private  int image;
    @ColumnInfo(name = "Name")
    private  String name;

    public Category() {
    }
    @Ignore
    public Category(int categoryID, int image, String name) {
        this.categoryID = categoryID;
        this.image = image;
        this.name = name;
    }

    public int getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(int categoryID) {
        this.categoryID = categoryID;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static Category[] initCategoryData() {
        Category category1 = new Category(1,R.mipmap.tam, "Tắm");
        Category category2 = new Category(2,R.mipmap.tia, "Tỉa Lông");
        Category category3 = new Category(3,R.mipmap.mong, "Cắt móng");
        Category category4 = new Category(4,R.mipmap.an, "Cho ăn");
        Category category5 = new Category(5,R.mipmap.tiem, "Tiêm");
        Category category6 = new Category(6,R.mipmap.thuoc, "Uống thuốc");
        Category category7 = new Category(7,R.mipmap.kham, "Khám định kỳ");
        return new Category[] {
                category1, category2, category3, category4, category5, category6, category7
        };
    }
}
