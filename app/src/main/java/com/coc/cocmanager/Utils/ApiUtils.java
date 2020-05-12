package com.coc.cocmanager.Utils;

public class ApiUtils {

    private static String PROTOCOL = "http://";
        private static String SERVER_URL = "45.252.190.200";


    public static String LOGIN_URL = PROTOCOL + SERVER_URL + "/api/v1/login";
    public static String DELETE_USER = PROTOCOL + SERVER_URL +"/api/v1/user/";
    public static String CREATE_USER = PROTOCOL + SERVER_URL + "/api/v1/user";
    public static String UPDATE_USER = PROTOCOL + SERVER_URL + "/api/v1/user/";
    public static String USER_LIST = PROTOCOL + SERVER_URL +"/api/v1/user/filter";
    public static String LOCATION_LIST = PROTOCOL + SERVER_URL + "/api/v1/location/";
    public static String CLINIC_LIST = PROTOCOL + SERVER_URL + "/api/v1/clinic/filter";
    public static String ADD_TO_PIPELINE = PROTOCOL + SERVER_URL + "/api/v1/clinic/";
}
