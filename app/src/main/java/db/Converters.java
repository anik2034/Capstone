package db;



import androidx.room.TypeConverter;

import com.anik.capstone.model.borrowing.BorrowingModel;
import com.anik.capstone.model.rating.RatingModel;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class Converters {
    private static Gson gson = new Gson();

    @TypeConverter
    public static List<String> fromString(String value) {
        if (value == null) {
            return new ArrayList<>();
        }
        Type listType = new TypeToken<List<String>>() {}.getType();
        return gson.fromJson(value, listType);
    }

    @TypeConverter
    public static String fromList(List<String> list) {
        return gson.toJson(list);
    }

    @TypeConverter
    public static BorrowingModel fromBorrowingModelString(String value) {
        Type borrowingModelType = new TypeToken<BorrowingModel>() {}.getType();
        return gson.fromJson(value, borrowingModelType);
    }

    @TypeConverter
    public static String fromBorrowingModel(BorrowingModel borrowingModel) {
        return gson.toJson(borrowingModel);
    }

    @TypeConverter
    public static RatingModel fromRatingModelString(String value) {
        Type ratingModelType = new TypeToken<RatingModel>() {}.getType();
        return gson.fromJson(value, ratingModelType);
    }

    @TypeConverter
    public static String fromRatingModel(RatingModel ratingModel) {
        return gson.toJson(ratingModel);
    }
}
