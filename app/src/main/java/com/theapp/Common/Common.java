package com.theapp.afshatquiz.Common;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.theapp.afshatquiz.Model.Questions;
import com.theapp.afshatquiz.Model.User;

import java.util.ArrayList;
import java.util.List;

public class Common {
    public static User CurrentUser;
    public static String CategoryId;
    public static List<Questions> questionsList = new ArrayList <>();
    public static List<Questions> Challenges = new ArrayList <>();


    public static boolean isConnectingToNet(Context context)
    {
        ConnectivityManager connectivityManager = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager != null)
        {
            NetworkInfo[] info = connectivityManager.getAllNetworkInfo();
            if (info != null)
            {
                for (int i=0;i<info.length;i++)
                {
                    if (info[i].getState() == NetworkInfo.State.CONNECTED)
                        return true;
                }
            }
        }
        return false;
    }

}
