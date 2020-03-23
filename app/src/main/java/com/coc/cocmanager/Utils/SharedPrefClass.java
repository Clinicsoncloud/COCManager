package com.coc.cocmanager.Utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

/***
 * class to save and retrieve data in the preferences
 *
 * @author Azhar
 */
public class SharedPrefClass {

    /* private members */
    @SuppressWarnings("unused")
    private Context context;

    private static final String PREF_NAME = "Garage2ghar";
    private static final String FACEBOOK_LOGIN = "FacebookLogin";
    private static final String IS_LOGIN = "islogin";


    public static final String LOGIN_STATUS = "LoginStatus";
    public static final String CONTACTID = "ContactId";
    public static final String REFERRAL_CODE = "referel_code";
    public static final String EMAIL = "Email";
    public static final String FIRSTNAME = "FirstName";
    public static final String MOBILENUMBER = "MobileNumber";
    public static final String REGMOBILENUMBER = "RMobileNumber";
    public static final String ADDRESS = "Address";
    public static final String USER_R = "user_random";
    public static final String WORK_START = "work_start";
    public static final String WORK_STARTCID = "work_start_cid";
    private static final String USER_DETAILS = "UserDetails";


    private static SharedPreferences preferences = null;
    public static SharedPrefClass sharedPreferenceDataManager = null;
    //private String key;
//	private String value;
    /***
     * this is constructor for saving and retrieving the values from shared_preferences.
     *
     * @param context
     */
    public SharedPrefClass(Context context) {
        this.context = context;
        if (preferences == null) {
//            preferences = PreferenceManager.getDefaultSharedPreferences(context);
            preferences = context.getSharedPreferences(PREF_NAME, 0);
        }
    }
    /***
     * this is static function to create singleton object.
     *
     * @param context
     * @return
     */
    public static SharedPrefClass getInstance(Context context) {
        if (sharedPreferenceDataManager == null) {
            sharedPreferenceDataManager = new SharedPrefClass(context);
        }
        return sharedPreferenceDataManager;
    }
    /***
     * this is function to clear preferences.
     *
     * @return
     */
    public void clearSharedPrefs() {
        Editor editor = preferences.edit();
        editor.clear();
        editor.apply();
    }

    /***
     * this will return saved preference having value as string
     *
     * @param key
     * @return
     */
    public String getSavedStringPreference(String key) {
        String value = preferences.getString(key, null);
        return value;
    }

    /***
     * this will return saved preference having value as boolean
     *
     * @param key
     * @return
     */
    public boolean getSavedBooleanPreference(String key) {
        boolean value = preferences.getBoolean(key, false);
        return value;
    }

    /***
     * this will return saved preference having value as boolean
     *
     * @param key
     * @return
     */
    public long getSavedLongPreference(String key) {
        long value = preferences.getLong(key, 0);
        return value;
    }

    public float getSavedFloatPreference(String key) {
        float value = preferences.getFloat(key, 0f);
        return value;
    }

    /***
     * @param key
     * @param value
     */
    public void savePreference(String key, String value) {
        Editor editor = preferences.edit();
        editor.putString(key, value);
        editor.apply();
    }


    /***
     * @param key
     * @param value
     */
    public void savePreference(String key, long value) {
        Editor editor = preferences.edit();
        editor.putLong(key, value);
        editor.apply();
    }

    /***
     * @param key
     * @param value
     */
    public void savePreference(String key, float value) {
        Editor editor = preferences.edit();
        editor.putFloat(key, value);
        editor.apply();
    }

    /***
     * @param key
     * @param value
     */
    public void savePreference(String key, boolean value) {
        Editor editor = preferences.edit();
        editor.putBoolean(key, value);
        editor.apply();
    }

    /***
     * this will return saved preference having value as boolean
     *
     * @param key
     * @return
     */
    public boolean getSavedBooleanPreferenceWithDefaultValue(String key, boolean retuenDefaultValue) {
        boolean value = preferences.getBoolean(key, retuenDefaultValue);
        return value;
    }

    /**
     * @param key
     * @param array
     * @return
     */
    public void savePreference(String key, boolean[] array) {
        Editor editor = preferences.edit();
        editor.putInt(key + "_size", array.length);
        for (int i = 0; i < array.length; i++)
            editor.putBoolean(key + "_" + i, array[i]);
        editor.apply();
    }

    public boolean[] getSavedBooleanArray(String key) {
        int size = preferences.getInt(key + "_size", 0);
        boolean array[] = new boolean[size];
        for (int i = 0; i < size; i++)
            array[i] = preferences.getBoolean(key + "_" + i, false);
        return array;
    }

    /* check login or not */
    public boolean isUserLogin() {
        return preferences.getBoolean(LOGIN_STATUS, false);
    }

    public void setUserLogin(boolean loginStatus) {
        Editor editor = preferences.edit();
        editor.putBoolean(LOGIN_STATUS, loginStatus);
        editor.apply();
    }

        /* save and get User id */

    public String getContactid() {
        return preferences.getString(CONTACTID, "0");
    }
    public void setContactid(String  contactid) {
        Editor editor = preferences.edit();
        editor.putString(CONTACTID, contactid);
        editor.apply();
    }

    public void setFirstname(String firstname) {
        Editor editor = preferences.edit();
        editor.putString(FIRSTNAME, firstname);
        editor.apply();
    }

    public String getFirstname() {
        return preferences.getString(FIRSTNAME, "");
    }

    public void setEmail(String email) {
        Editor editor = preferences.edit();
        editor.putString(EMAIL, email);
        editor.apply();
    }

    public String getEmail() {
        return preferences.getString(EMAIL, "0");
    }


    public String getUserRandom() {
        return preferences.getString(USER_R, "0");
    }

    public void setUserRandom(String strUserR) {
        Editor editor = preferences.edit();
        editor.putString(USER_R, strUserR);
        editor.apply();
    }
    public String getMobilenumber() {
        return preferences.getString(MOBILENUMBER, "0");
    }

    public void setMobilenumber(String strUserRole) {
        Editor editor = preferences.edit();
        editor.putString(MOBILENUMBER, strUserRole);
        editor.apply();
    }
    public String getRegMobilenumber() {
        return preferences.getString(REGMOBILENUMBER, "0");
    }

    public void setRegMobilenumber(String strUserRole) {
        Editor editor = preferences.edit();
        editor.putString(REGMOBILENUMBER, strUserRole);
        editor.apply();
    }
    public String getAddress() {
        return preferences.getString(ADDRESS, "0");
    }

    public void setAddress(String strUserRole) {
        Editor editor = preferences.edit();
        editor.putString(ADDRESS, strUserRole);
        editor.apply();
    }

//    public String getDealerId() {
//        return preferences.getString(USER_DEALERID, "0");
//    }
//
//    public void setDealerID(String strDealerId) {
//        Editor editor = preferences.edit();
//        editor.putString(USER_DEALERID, strDealerId);
//        editor.apply();
//    }

    public String getWorkStart() {
        return preferences.getString(WORK_START, "");
    }

    public void setWorkStart(String strworkstart) {
        Editor editor = preferences.edit();
        editor.putString(WORK_START, strworkstart);
        editor.apply();
    }

    public String getWorkStartCID() {
        return preferences.getString(WORK_STARTCID, "");
    }

    public void setWorkStartCID(String strworkstartcid) {
        Editor editor = preferences.edit();
        editor.putString(WORK_STARTCID, strworkstartcid);
        editor.apply();
    }


    public String getUserDetails() {
        return preferences.getString(USER_DETAILS, "");
    }

    public void setUserDetails(String strUserDetails) {
        Editor editor = preferences.edit();
        editor.putString(USER_DETAILS, strUserDetails);
        editor.apply();
    }

    // Religion
    public boolean isLogin() {
        return preferences.getBoolean(IS_LOGIN, false);
    }

    public void setISLogin(boolean login) {
        Editor editor = preferences.edit();
        editor.putBoolean(IS_LOGIN, login);
        editor.apply();
    }

    public String getReferralCode() {
        return preferences.getString(REFERRAL_CODE, "0");
    }

    public void setReferralCode(String  contactid) {
        Editor editor = preferences.edit();
        editor.putString(REFERRAL_CODE, contactid);
        editor.apply();
    }

}
