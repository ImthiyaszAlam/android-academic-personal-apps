package com.example.landsurvey;

import androidx.room.TypeConverter;

import com.google.android.gms.maps.model.LatLng;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class LatLngListConverter {

        @TypeConverter
        public static String fromPolygonPoints(List<LatLng> points) {
            if (points == null) return null;
            Gson gson = new Gson();
            return gson.toJson(points);
        }

        @TypeConverter
        public static List<LatLng> toPolygonPoints(String pointsString) {
            if (pointsString == null) return null;
            Gson gson = new Gson();
            Type listType = new TypeToken<ArrayList<LatLng>>() {}.getType();
            return gson.fromJson(pointsString, listType);
        }


}
