package mboss.tsm.Utility;

import android.arch.persistence.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;

import mboss.tsm.Model.Category;

public class ConvertCategoryType {
//    @TypeConverter // note this annotation
//    public String fromCategoryList(List<Category> category) {
//        if (category == null) {
//            return (null);
//        }
//        Gson gson = new Gson();
//        Type type = new TypeToken<List<Category>>() {
//        }.getType();
//        String json = gson.toJson(category, type);
//        return json;
//    }
//
//    @TypeConverter // note this annotation
//    public List<Category> toExtraList(String categoryString) {
//        if (categoryString == null) {
//            return (null);
//        }
//        Gson gson = new Gson();
//        Type type = new TypeToken<List<Category>>() {
//        }.getType();
//        List<Category> cardType = gson.fromJson(categoryString, type);
//        return cardType;
//    }

    @TypeConverter
    public static List<Category> stringToSomeObjectList(String data) {
        if (data == null) {
            return Collections.emptyList();
        }
        Gson gson = new Gson();
        Type listType = new TypeToken<List<Category>>() {}.getType();

        return gson.fromJson(data, listType);
    }

    @TypeConverter
    public static String someObjectListToString(List<Category> someObjects) {
        Gson gson = new Gson();
        return gson.toJson(someObjects);
    }
}
