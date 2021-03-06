package com.NaTicket.n.flights.pojo;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;

import com.NaTicket.n.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.StringTokenizer;

/**
 * Created by Ankit on 07-01-2018.
 */

public class Flight_Utils {


    public static String getAge(String date){
        String[] items1 = date.split("-");
        String d1=items1[0];
        String m1=items1[1];
        String y1=items1[2];
        int Day = Integer.parseInt(d1);
        int Month = Integer.parseInt(m1)-1;
        int Year = Integer.parseInt(y1);
        Calendar dob = Calendar.getInstance();
        Calendar today = Calendar.getInstance();
        dob.set(Year, Month, Day);
        int age = today.get(Calendar.YEAR) - dob.get(Calendar.YEAR);
        if (today.get(Calendar.DAY_OF_YEAR) < dob.get(Calendar.DAY_OF_YEAR)) {
            age--;
        }
        Integer ageInt = new Integer(age);
        String ageS = ageInt.toString();
        System.out.println("Selected Age :" + ageS);
        return ageS;
    }


    public static String getTime(String date){
        String formattedTime = null;
        SimpleDateFormat dateFormatd = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.ENGLISH);
        SimpleDateFormat outputdate = new SimpleDateFormat("HH:mm", Locale.ENGLISH);
        Date DepartureDateTime=null;
        try {
            DepartureDateTime = dateFormatd.parse(date);
            formattedTime = outputdate.format(DepartureDateTime);
        } catch (ParseException ignored) {
        }
        return formattedTime;
    }


    public static Date coverttime24Hours(String _24HourTime){
        Date _24HourDt = null;
        try {
            SimpleDateFormat _24HourSDF = new SimpleDateFormat("HH:mm");
            SimpleDateFormat _12HourSDF = new SimpleDateFormat("hh:mm a");
            _24HourDt = _12HourSDF.parse(_24HourTime);
            _24HourTime=_12HourSDF.format(_24HourDt);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return _24HourDt;
    }


    public static String getDate(String date){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.ENGLISH);
        SimpleDateFormat output = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
        Date DepartureDateTimePARSE =null;
        try {
            DepartureDateTimePARSE = dateFormat.parse(date);

        } catch (ParseException e) {
            e.printStackTrace();
        }

        //SimpleDateFormat timeFormat = new SimpleDateFormat("dd MMM,yyyy",Locale.ENGLISH);
        String ReturnDepartureDate = output.format(DepartureDateTimePARSE);

        return  ReturnDepartureDate;
    }

    public static Date getDurationforfilters(String date){
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm hrs", Locale.ENGLISH);
        SimpleDateFormat output = new SimpleDateFormat("HH:mm", Locale.ENGLISH);
        Date DepartureDateTimePARSE =null;
        try {
            DepartureDateTimePARSE = dateFormat.parse(date);
            DepartureDateTimePARSE=output.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        //SimpleDateFormat timeFormat = new SimpleDateFormat("dd MMM,yyyy",Locale.ENGLISH);
        //String ReturnDepartureDate = output.format(DepartureDateTimePARSE);

        return  DepartureDateTimePARSE;
    }

    public static String getLength(int Size){
        String Numberofstops;
        if (Size == 1) {
            Numberofstops = "" + "Non";
        } else {
            Numberofstops = String.valueOf(Size - 1);
        }
        return Numberofstops;

    }

    public static int getLengthforfilters(int Size){
        int Numberofstops;
        if (Size == 1) {
            Numberofstops =0;
        } else {
            Numberofstops = Size - 1;
        }
        return Numberofstops;

    }

    public static Date getTimeforFilters(String date){
        String formattedTime = null;
        SimpleDateFormat dateFormatd = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.ENGLISH);
        SimpleDateFormat outputdate = new SimpleDateFormat("HH:mm", Locale.ENGLISH);
        Date DepartureDateTime=null;
        try {
            DepartureDateTime = dateFormatd.parse(date);
            formattedTime = outputdate.format(DepartureDateTime);
        } catch (ParseException ignored) {
        }
        return DepartureDateTime;
    }

    /*public static Bitmap getImage(Context contex, String ImagePath) {

        ArrayList<String> flightImageCodeArray = new ArrayList<>(Arrays.asList("goair", "JetAirways", "aircosta", "LB", "2J", "2M", "3D", "3U", "3V", "4M", "4Z", "5C", "5L", "5X", "5Y", "6E", "6H", "7B", "7D", "8Q", "8U", "9B", "9F", "9U", "9W", "ft", "A3", "AA", "AB", "AC", "AF", "AG", "AH", "AI", "AJ", "AM", "AP", "AR", "AS", "AT", "AV", "AY", "AZ", "B2", "B3", "B6", "BA", "BD", "BE", "BG", "BI", "BN", "BP", "BR", "BT", "BU", "BV", "BW", "C9", "CA", "CI", "CK", "CL", "CM", "CO", "CS", "CU", "CV", "CX", "CY", "CZ", "D3", "D6", "D9", "DJ", "DL", "DO", "DT", "DU", "EI", "EK", "EL", "EN", "EP", "ES", "ET", "EW", "EY", "F9", "FA", "FB", "FI", "FJ", "FL", "flyLAL_LOGO_L", "FM", "FV", "FX", "G0", "G3", "G8", "G9", "GA", "GE", "GF", "GY", "H9", "HF", "HJ", "HM", "HR", "HU", "HX", "HY", "IB", "IC", "IG", "IN", "IR", "IT", "IX", "IY", "IZ", "J2", "J9", "JA", "JJ", "JK", "JL", "JM", "JO", "JP", "JQ", "JS", "JU", "JZ", "KA", "KC", "KE", "KF", "KH", "KK", "KL", "KM", "KQ", "KU", "KW", "KX", "KZ", "LA", "LG", "LH", "LI", "LN", "LO", "LR", "LT", "lufthansa-cargo", "LX", "LY", "MA", "MD", "ME", "MF", "MH", "MI", "MK", "MP", "MS", "MU", "MX", "NF", "NG", "NH", "NI", "NT", "NW", "NX", "NZ", "OA", "OK", "OM", "OS", "OU", "OV", "OZ", "PG", "PK", "PR", "PS", "PU", "PW", "PX", "PY", "PZ", "QF", "QI", "QM", "QR", "QV", "QW", "QY", "R5", "RA", "RB", "RJ", "RO", "RV", "S2", "S7", "SA", "SB", "SC", "SD", "SG", "sia-cargo", "SK", "SN", "SP", "SQ", "SS", "SU", "SV", "SW", "T3", "TA", "TC", "TE", "TF", "TG", "TK", "TL", "TM", "TN", "TO", "TP", "TR", "TS", "TU", "TX", "U8", "UA", "UC", "UI", "UL", "UM", "UN", "UO", "UP", "US", "UT", "UU", "UX", "V3", "V7", "VA", "VF", "VG", "VI", "VK", "VN", "VR", "VS", "VT", "VU", "VV", "W5", "W8", "WB", "WF", "WX", "WY", "XF", "XK", "XL", "Y4", "Y9", "YM", "YW", "Z5", "ZH", "ZI", "AK", "UK"));
        ArrayList<String> flightImagePositions = new ArrayList<>(Arrays.asList("30,22,515,253", "30,22,308,50", "30,22,555,20", "30,22,493,616", "30,22,4,12", "30,22,44,12", "30,22,86,12", "30,22,142,12", "30,22,222,14", "30,22,262,13", "30,22,312,14", "30,22,362,11", "30,22,413,14", "30,22,458,14", "30,22,510,14", "46,16,556,22", "30,22,622,20", "30,22,4,56", "30,22,45,55", "30,22,90,56", "30,22,132,56", "23,22,180,52", "27,23,220,52", "30,22,266,52", "30,22,312,50", "30,22,733,617", "30,22,360,50", "30,22,416,52", "30,22,460,52", "30,22,506,50", "30,22,557,48", "30,22,614,49", "30,22,4,96", "30,22,45,96", "27,23,90,94", "30,22,134,94", "30,22,172,95", "30,22,217,98", "30,22,266,96", "30,22,309,92", "30,22,360,90", "30,22,412,92", "30,22,456,92", "30,22,506,89", "27,23,552,84", "27,23,606,84", "30,22,2,142", "30,22,44,140", "30,22,87,140", "30,22,130,140", "30,22,172,134", "30,22,218,134", "30,22,266,134", "30,22,310,132", "27,23,360,128", "27,23,410,130", "30,22,458,128", "30,22,508,128", "30,22,549,124", "30,22,596,122", "30,22,2,174", "30,22,40,174", "30,22,82,172", "30,22,122,172", "27,23,164,172", "27,23,208,172", "30,22,252,174", "30,22,296,174", "30,22,340,172", "30,22,398,172", "30,22,440,170", "30,22,482,170", "30,22,526,170", "30,22,562,170", "27,23,604,170", "27,23,2,220", "30,22,44,220", "30,22,90,220", "30,22,125,220", "30,22,166,220", "30,22,206,220", "30,22,248,218", "30,22,296,216", "30,22,346,210", "27,23,392,210", "27,23,440,206", "30,22,482,206", "30,22,530,206", "30,22,580,206", "30,22,0,266", "30,22,44,264", "30,22,100,266", "30,22,142,264", "30,22,184,264", "27,23,234,264", "27,23,282,260", "30,22,326,258", "30,22,368,258", "30,22,419,256", "30,22,468,252", "30,22,514,252", "30,22,562,252", "30,22,4,306", "30,22,50,306", "27,23,100,308", "27,23,142,306", "30,22,184,306", "30,22,240,306", "30,22,285,306", "30,22,330,304", "30,22,378,304", "30,22,426,300", "30,22,462,298", "30,22,504,294", "27,23,550,288", "27,23,590,288", "30,22,2,352", "30,22,44,346", "30,22,92,348", "30,22,138,342", "59,20,184,346", "30,22,253,344", "30,22,296,342", "30,22,340,342", "51,54,382,342", "27,23,448,340", "30,22,490,340", "30,22,530,338", "30,22,584,340", "30,22,618,336", "30,22,0,418", "30,22,50,420", "30,22,100,414", "30,22,148,414", "30,22,198,415", "27,23,248,408", "30,22,288,406", "30,22,326,406", "30,22,370,408", "30,22,414,408", "30,22,460,402", "30,22,496,402", "30,22,540,396", "30,22,584,392", "30,22,620,394", "27,23,0,458", "30,22,50,454", "30,22,96,454", "30,22,132,454", "30,22,186,454", "30,22,232,452", "30,22,280,452", "30,22,323,448", "30,22,364,452", "30,22,410,452", "27,23,456,448", "30,22,506,444", "30,22,538,442", "30,22,578,440", "30,22,620,440", "30,22,698,28", "30,22,742,26", "30,22,784,28", "30,22,826,28", "30,22,868,28", "27,23,910,28", "30,22,694,64", "30,22,742,64", "30,22,786,64", "30,22,832,64", "30,22,868,64", "30,22,910,64", "30,22,696,102", "30,22,742,102", "30,22,784,102", "27,23,832,102", "30,22,880,102", "30,22,922,96", "30,22,700,142", "30,22,742,142", "30,22,788,142", "30,22,830,138", "30,22,882,138", "30,22,924,142", "27,23,698,194", "30,22,748,188", "30,22,792,188", "30,22,840,184", "30,22,882,184", "30,22,928,184", "30,22,700,232", "30,22,753,232", "30,22,792,232", "30,22,838,232", "30,22,880,230", "30,22,922,232", "30,22,700,276", "30,22,746,278", "30,22,796,276", "27,23,840,274", "30,22,882,276", "30,22,930,276", "30,22,702,316", "30,22,750,316", "30,22,796,316", "30,22,838,312", "30,22,882,314", "30,22,924,314", "30,23,709,352", "30,22,754,348", "30,22,802,348", "30,22,850,352", "30,22,892,354", "30,22,934,354", "30,22,708,380", "30,22,754,380", "30,22,808,380", "30,22,856,386", "30,22,900,384", "27,23,934,386", "30,22,710,416", "30,22,756,416", "30,22,810,416", "30,22,852,416", "30,22,896,414", "30,22,936,422", "27,23,712,452", "30,22,754,454", "30,22,810,446", "30,22,846,446", "27,23,886,446", "30,22,920,448", "30,22,954,450", "30,22,10,524", "30,22,58,524", "30,22,106,524", "27,23,154,524", "30,22,200,522", "30,22,248,522", "30,22,300,524", "30,22,346,520", "30,22,386,518", "30,22,428,518", "27,23,466,514", "30,22,508,514", "30,22,540,514", "30,22,600,510", "27,23,640,506", "30,22,700,508", "30,22,746,506", "30,22,810,506", "30,22,872,504", "27,23,918,500", "30,22,954,504", "30,22,12,576", "30,22,62,577", "30,22,102,576", "30,22,150,580", "27,23,196,576", "30,22,246,578", "30,22,302,576", "30,22,350,570", "30,22,402,568", "30,22,450,574", "30,22,504,574", "27,23,554,570", "30,22,602,570", "30,22,663,564", "30,22,726,562", "27,23,784,566", "30,22,834,565", "30,22,884,562", "30,22,930,562", "30,22,18,622", "30,22,58,624", "27,23,101,622", "30,22,164,622", "30,22,232,622", "30,22,286,622", "46,35,318,616", "30,22,390,618", "30,22,450,618", "30,22,636,618", "30,22,681,618"));
        Bitmap resizedBitmap = null;
        try {
            if (ImagePath.equalsIgnoreCase("null")) {
                if (ImagePath.contains("(")) {
                    StringTokenizer tokens = new StringTokenizer(ImagePath, "(");
                    ImagePath = (tokens.nextToken());
                }
            }
            int FlightPositionIndex = flightImageCodeArray.indexOf(ImagePath);
            String PositionString = flightImagePositions.get(FlightPositionIndex);

            StringTokenizer tokens = new StringTokenizer(PositionString, ",");
            int W = Integer.parseInt(tokens.nextToken());// this will contain "Fruit"
            int h = Integer.parseInt(tokens.nextToken());
            int X = Integer.parseInt(tokens.nextToken());
            int Y = Integer.parseInt(tokens.nextToken());

            Paint paint = new Paint();
            paint.setFilterBitmap(true);
            Bitmap bitmapOrg = BitmapFactory.decodeResource(contex.getResources(), R.drawable.sprite);
            int targetWidth = 1000;
            int targetHeight = 800;

            Bitmap targetBitmap = Bitmap.createBitmap(targetWidth, targetHeight, Bitmap.Config.ARGB_8888);
            RectF rectf = new RectF(0, 0, 1000, 1200);

            Canvas canvas = new Canvas(targetBitmap);
            Path path = new Path();

            path.addRect(rectf, Path.Direction.CW);
            canvas.clipPath(path);

            canvas.drawBitmap(bitmapOrg, new Rect(0, 0, bitmapOrg.getWidth(), bitmapOrg.getHeight()),
                    new Rect(0, 0, targetWidth, targetHeight), paint);

            Matrix matrix = new Matrix();
            matrix.postScale(1f, 1f);
            resizedBitmap = Bitmap.createBitmap(targetBitmap, X, Y, W, h, matrix, true);


        } catch (Exception e) {
            e.printStackTrace();
        }

        return resizedBitmap;
    }*/


    public static String getDuration(String Date1, String Dtae2) {

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-M-dd HH:mm:ss");

        String dur="";
        try {
            Date date1 = simpleDateFormat.parse(Date1);
            Date date2 = simpleDateFormat.parse(Dtae2);

            long mills = date2.getTime() - date1.getTime();
            int hours = (int) (mills/(1000 * 60 * 60));
            int mins = (int) (mills/(1000*60)) % 60;

            dur= String.valueOf(String.format("%02d",hours)+":"+String.format("%02d",mins)+" hrs");

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return dur;
    }


}
