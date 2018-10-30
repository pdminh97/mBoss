package mboss.tsm.Utility;

import android.arch.persistence.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

import mboss.tsm.Model.Category;

public class ConvertCategoryType {
    @TypeConverter // note this annotation
    public String fromCategoryList(Category category) {
        if (category == null) {
            return (null);
        }
        Gson gson = new Gson();
        Type type = new TypeToken<Category>() {
        }.getType();
        String json = gson.toJson(category, type);
        return json;
    }

    @TypeConverter // note this annotation
    public Category toExtraList(String categoryString) {
        if (categoryString == null) {
            return (null);
        }
        Gson gson = new Gson();
        Type type = new TypeToken<Category>() {
        }.getType();
        Category cardType = gson.fromJson(categoryString, type);
        return cardType;
    }
}
