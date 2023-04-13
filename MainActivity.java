package com.arsh.sqldb;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.arsh.sqldb.adapters.OptionsAdapter;

import java.sql.Connection;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    TextView records;
    TextView error;
    Button showRecords;
    RecyclerView recyclerView;
    OptionsAdapter optionsAdapter;
    Connection con;
    ArrayList<String> arrayList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        Bitmap resized = Bitmap.createScaledBitmap(yourBitmap,250, 500, true);
//        recyclerView = findViewById(R.id.recyclerView);
//        arrayList.add("Create new database");
//        arrayList.add("Use existing database");
//        arrayList.add("");
//        arrayList.add("Create New Database");
//        arrayList.add("Create New Database");
//        records = findViewById(R.id.records);
//        error = findViewById(R.id.error);
//        showRecords = findViewById(R.id.showREcords);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//        optionsAdapter= new OptionsAdapter(arrayList, getApplicationContext(), (OptionsAdapter.OnItemClickListener) item -> {
//
//        });
//        showRecords.setOnClickListener(view -> {
//            con = SQLConnection();
//            if(con!=null){
//                try{
//                    String records = "";
//                    Statement statement = con.createStatement();
//                    ResultSet resultSet = statement.executeQuery("Select * from instructor");
//                    while (resultSet.next()) {
//                        records = resultSet.getString(1) + " " + resultSet.getString(2) + "\n";
//                    }
//                    MainActivity.this.records.setText(records);
//                    Toast.makeText(this, records, Toast.LENGTH_SHORT).show();
//                } catch (Exception e) {
//                    Log.d("error",e.toString());
//                    Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
//                }
////            }
//        });
    }
}