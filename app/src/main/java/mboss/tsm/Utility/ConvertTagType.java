package mboss.tsm.Utility;

import android.arch.persistence.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

import mboss.tsm.Model.Boss;

public class ConvertTagType {
    @TypeConverter
    public String fromTagList(List<Boss> tags) {
        if(tags == null)
            return null;
        else {
            Type type = new TypeToken<List<Boss>>(){}.getType();
            return new Gson().toJson(tags, type);
        }
    }

    @TypeConverter
    public List<Boss> toTagList(String tagJson) {
        if(tagJson == null)
            return null;
        else {
            Type type = new TypeToken<List<Boss>>(){}.getType();
            return new Gson().fromJson(tagJson, type);
        }
    }
}
