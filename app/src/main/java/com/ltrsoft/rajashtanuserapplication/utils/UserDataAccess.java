package com.ltrsoft.rajashtanuserapplication.utils;

import static android.content.Context.MODE_PRIVATE;

import android.app.Activity;
import android.content.SharedPreferences;

public class UserDataAccess {
    String userId,complaintID;

    public String getUserId(Activity activity) {
        SharedPreferences pref = activity.getSharedPreferences("UserData", MODE_PRIVATE);
        return pref.getString("userID",null);
    }

    public void setUserId(String userId, Activity activity) {
        SharedPreferences pref = activity.getSharedPreferences("UserData", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("userID", userId)
                .apply();
    }

    public String getComplaintID(Activity activity) {
        SharedPreferences pref = activity.getSharedPreferences("UserData", MODE_PRIVATE);
        return pref.getString("ComplaintId",null);
    }
    public void setComplaintID(String complaintID,Activity activity) {
        SharedPreferences pref = activity.getSharedPreferences("UserData", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("ComplaintId", complaintID)
                .apply();
    }
}
