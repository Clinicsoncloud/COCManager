package com.coc.cocmanager.services;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import android.os.Build;
import android.util.Log;
import java.util.Calendar;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

import android.annotation.SuppressLint;

import androidx.annotation.RequiresApi;

public class DateService {

    public static final String DATE_FORMAT = "dd-MMM-yyyy hh:mm:ss";
    public static final String YYYY_MM_DD_HMS = "yyyy-MM-dd hh:mm:ss";
    public static final String MM_DD_YYY_HH_MM = "MM-dd-yyy hh:mm:ss";

    /**
     * @param dateOfBirth
     * @return
     */
    public static int getAgeFromStringDate(String dateOfBirth) {
        Date date = null;
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");
        try {
            date = sdf.parse(dateOfBirth);
        } catch (ParseException e) {
            return 0;
        }

        Calendar calDateOfBirth = Calendar.getInstance();
        Calendar today = Calendar.getInstance();

        calDateOfBirth.setTime(date);

        int year = calDateOfBirth.get(Calendar.YEAR);
        int month = calDateOfBirth.get(Calendar.MONTH);
        int day = calDateOfBirth.get(Calendar.DAY_OF_MONTH);

        calDateOfBirth.set(year, month + 1, day);

        int age = today.get(Calendar.YEAR) - calDateOfBirth.get(Calendar.YEAR);

        if (today.get(Calendar.DAY_OF_YEAR) < calDateOfBirth.get(Calendar.DAY_OF_YEAR))
            age--;

        return age;
    }

    public static String formatDateFromString(String date) {
        String formattedDate;

        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat inputDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");

        SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy");
        try {
            Date parsedDate = inputDateFormat.parse(date);
            formattedDate = formatter.format(parsedDate);
        } catch (ParseException e) {
            return null;
        }

        return formattedDate;
    }

    public static String getCurrentDateTime(String format) {

        DateFormat dateFormatter = new SimpleDateFormat(format);
        dateFormatter.setLenient(false);
        Date today = new Date();
        String s = dateFormatter.format(today);
        Log.e("today_Date", "" + s);
        return s;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static String getFormattedDate(String dob){
        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.ENGLISH);
        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy", Locale.ENGLISH);
        LocalDate date = LocalDate.parse(dob, inputFormatter);
        String formattedDate = outputFormatter.format(date);
        System.out.println(formattedDate); // prints 10-04-2018

        return formattedDate;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static String getMonthDayYearFormat(String dob){
        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.ENGLISH);
        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("dd-MMM-yyyy", Locale.ENGLISH);
        LocalDate date = LocalDate.parse(dob, inputFormatter);
        String formattedDate = outputFormatter.format(date);
        System.out.println(formattedDate); // prints 10-04-2018


        return formattedDate;
    }

}
