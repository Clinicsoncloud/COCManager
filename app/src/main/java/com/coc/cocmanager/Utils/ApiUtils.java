package com.coc.cocmanager.Utils;

public class ApiUtils {

    private static String PROTOCOL = "http://";
        private static String STAGING_URL = "45.252.190.200";
        private static String SERVER_URL = "45.252.190.200";


    public static String STOCK_OUT = PROTOCOL + SERVER_URL + "/api/v1/stock";
    public static String DELETE_USER = PROTOCOL + SERVER_URL +"/api/v1/user/";
    public static String CREATE_USER = PROTOCOL + SERVER_URL + "/api/v1/user";
    public static String UPDATE_USER = PROTOCOL + SERVER_URL + "/api/v1/user/";
    public static String USER_LIST = PROTOCOL + SERVER_URL +"/api/v1/user/filter";
    public static String LOGIN_URL = PROTOCOL + SERVER_URL + "/api/v1/login/user";
    public static String ITEM_LIST = PROTOCOL + SERVER_URL + "/api/v1/item/filter";
    public static String STOCK_IN_DETAILS = PROTOCOL + SERVER_URL + "/api/v1/stock";
    public static String ADD_TO_PIPELINE = PROTOCOL + SERVER_URL + "/api/v1/clinic/";
    public static String LOCATION_LIST = PROTOCOL + SERVER_URL + "/api/v1/location/";
    public static String STOCK_LIST = PROTOCOL + SERVER_URL + "/api/v1/stock/filter";
    public static String CLINIC_LIST = PROTOCOL + SERVER_URL + "/api/v1/clinic/filter";
    public static String CLIENT_LIST = PROTOCOL + SERVER_URL + "/api/v1/customer/filter";
    public static String SUMMARY = PROTOCOL + SERVER_URL + "/api/v1/clinic/transport-statistics";
    public static String ITEM_CATEGORY_LIST = PROTOCOL + SERVER_URL + "/api/v1/item-category/filter";
}
