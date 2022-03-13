package com.perfect.islamyat.models;

public class myPrayerModel {
    public String code;
    public DataModel data;


    public static class DataModel {
        public TimingsModel timings;
        public TodayDateModel date;
        public MetaModel meta;

        public static class TimingsModel {
            public String Fajr;
            public String Sunrise;
            public String Dhuhr;
            public String Asr;
            public String Sunset;
            public String Maghrib;
            public String Isha;
            public String Imsak;
            public String Midnight;
        }

        public static class TodayDateModel {
            public GregorianModel gregorian;
            public Hijri hijri;

            public static class GregorianModel {
                public String date;
                public String day;
            }

            public static class Hijri {
                public String date;
                public String day;
            }
        }

        public static class MetaModel {
            public String latitude;
            public String longitude;
            public String timezone;
            public MethodModel method;


            public static class MethodModel {
                public String id;
                public String name;
            }
        }
    }
}
