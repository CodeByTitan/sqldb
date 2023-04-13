package com.arsh.sqldb.data;

import android.content.res.Resources;
import android.os.StrictMode;
import android.util.Log;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;

public class SqlConnection {
    public Connection SQLConnection() {
        Connection con = null;
        StrictMode.ThreadPolicy tp = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(tp);
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://10.100.65.107:3306/university?useSSL=false&allowPublicKeyRetrieval=true&enabledTLSProtocols=TLSv1.2", "root", "DesolatedT1tan");
        } catch (Exception e) {
            Log.d("error",e.toString());

        }
        return con;
    }
}
