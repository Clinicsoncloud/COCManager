package com.coc.cocmanager.Utils;

public class ApiUtils {

    private static String PROTOCOL = "http://";
        private static String SERVER_URL = "45.252.190.200";


    public static String CREATE_USER = PROTOCOL + SERVER_URL + "/api/v1/user";
    public static String LOGIN_URL = PROTOCOL + SERVER_URL + "/api/v1/login";
    public static String USER_LIST = PROTOCOL + SERVER_URL +"/api/v1/user/filter";
    public static String DELETE_USER = PROTOCOL + SERVER_URL +"/api/v1/user";
    public static String PRINT_POST_URL = PROTOCOL + SERVER_URL + "/api/v1/parameter";
    public static String VERIFY_OTP_URL = PROTOCOL + SERVER_URL + "/api/v1/login/verify";
    public static String SYNC_OFFLINE_DATA_URL = PROTOCOL + SERVER_URL + "/api/v1/offline/sync";
}
