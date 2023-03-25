package com.example.weatherforecast;

public class FeedReaderContract {
    private FeedReaderContract() {}

    public static class FeedEntry {
        public static final String TABLE_NAME = "favorites";
        public static final String COLUMN_NAME_CITY = "city";
        public static final String COLUMN_NAME_TEMP = "temp";
        public static final String COLUMN_NAME_PRESSURE = "pressure";
        public static final String COLUMN_NAME_LON = "lon";
        public static final String COLUMN_NAME_LAT = "lat";
        public static final String COLUMN_NAME_DESCRIPTION = "description";
        public static final String COLUMN_NAME_ACTUALTIME = "actualTime";

    }
    static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + FeedEntry.TABLE_NAME + " (" +
                    FeedEntry.COLUMN_NAME_CITY + " TEXT PRIMARY KEY," +
                    FeedEntry.COLUMN_NAME_TEMP + " REAL," +
                    FeedEntry.COLUMN_NAME_PRESSURE + " REAL," +
                    FeedEntry.COLUMN_NAME_LON + " REAL," +
                    FeedEntry.COLUMN_NAME_LAT + " REAL," +
                    FeedEntry.COLUMN_NAME_DESCRIPTION + " TEXT," +
                    FeedEntry.COLUMN_NAME_ACTUALTIME + " TEXT)";
    static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + FeedEntry.TABLE_NAME;
}
