package com.coc.cocmanager.Utils;

public class ApiUtils {

    private static String PROTOCOL = "http://";
        private static String SERVER_URL = "45.252.190.29";
//    private static String SERVER_URL = "192.168.0.115:7777";

    public static String KIOSK = PROTOCOL + SERVER_URL + "/api/v1/kiosk";
    public static String LOGIN_URL = PROTOCOL + SERVER_URL + "/api/v1/login";
    public static String PROFILE_URL = PROTOCOL + SERVER_URL + "/api/v1/profile";
    public static String PRINT_POST_URL = PROTOCOL + SERVER_URL + "/api/v1/parameter";
    public static String VERIFY_OTP_URL = PROTOCOL + SERVER_URL + "/api/v1/login/verify";
    public static String SYNC_OFFLINE_DATA_URL = PROTOCOL + SERVER_URL + "/api/v1/offline/sync";
}
