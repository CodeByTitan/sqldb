package com.arsh.sqldb.fragments;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.arsh.sqldb.helper.SimpleDividerItemDecoration;
import com.arsh.sqldb.vm.MainViewModel;
import com.arsh.sqldb.R;
import com.arsh.sqldb.data.SqlConnection;
import com.arsh.sqldb.adapters.InsertionAdapter;
import com.arsh.sqldb.adapters.OptionsAdapter;
import com.arsh.sqldb.models.Attribute;
import com.google.android.material.button.MaterialButton;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link lists#newInstance} factory method to
 * create an instance of this fragment.
 */
public class lists extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public lists() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment lists.
     */
    // TODO: Rename and change types and number of parameters
    public static lists newInstance(String param1, String param2) {
        lists fragment = new lists();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    ArrayList<String> arrayList;
    OptionsAdapter optionsAdapter;
    String query = "";
    RecyclerView recyclerView;
    MainViewModel mainViewModel;
    TextView title;
    InsertionAdapter insertionAdapter;
    ArrayList<Attribute> tempList = new ArrayList<>();
    Connection con;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_lists, container, false);
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        title = view.findViewById(R.id.title);
        arrayList = new ArrayList<String>();
        con = new SqlConnection().SQLConnection();
        mainViewModel = new ViewModelProvider(requireActivity()).get(MainViewModel.class);
        try {
            if (Objects.equals(mainViewModel.liveData.getValue(), "databases")) {
                title.setText("Databases");
                ResultSet resultSet = con.getMetaData().getCatalogs();
                while (resultSet.next()) {
                    arrayList.add(resultSet.getString("TABLE_CAT"));
                    Log.d("arraysss", resultSet.getString("TABLE_CAT"));
                }
            } else if (Objects.equals(mainViewModel.liveData.getValue(), "tables")) {
                title.setText("Tables");
                Statement statement = con.createStatement();
                ResultSet resultSet = statement.executeQuery("show tables");
                while (resultSet.next()) {
                    arrayList.add(resultSet.getString(1));
                }
            } else if (Objects.equals(mainViewModel.liveData.getValue(), "operations")) {
                title.setText(mainViewModel.tablename.getValue());
                arrayList.add("Insert values into table");
                arrayList.add("Delete values from table");
                arrayList.add("Modify values in the table");
                arrayList.add("Alter table columns");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        optionsAdapter = new OptionsAdapter(arrayList, requireContext(), (OptionsAdapter.OnItemClickListener) item -> {
            Toast.makeText(requireContext(), item, Toast.LENGTH_SHORT).show();
            if (Objects.equals(mainViewModel.liveData.getValue(), "databases")) {
                mainViewModel.dbname.setValue(item);
                Fragment fragment = new choice();
                Toast.makeText(requireContext(), item, Toast.LENGTH_SHORT).show();
                requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainerView, fragment).addToBackStack("lists").commit();
            } else if (Objects.equals(mainViewModel.liveData.getValue(), "tables")) {
                mainViewModel.tablename.setValue(item);
                Fragment fragment = new lists();
                Toast.makeText(requireContext(), item, Toast.LENGTH_SHORT).show();
                requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainerView, fragment).addToBackStack("lists").commit();
                mainViewModel.liveData.setValue("operations");
            } else if (Objects.equals(mainViewModel.liveData.getValue(), "operations")) {
                if (Objects.equals(item, "Insert values into table")) {
                    showDialog("insert");
                } else if (Objects.equals(item, "Delete values from table")) {
                    showDialog("delete");
                } else if (Objects.equals(item, "Modify values in the table")) {
                    showDialog("modify");
                } else if (Objects.equals(item, "Alter table columns")) {
                    showDialog("alter");
                }
            }
        });
        recyclerView.addItemDecoration(new SimpleDividerItemDecoration(requireContext()));
        recyclerView.setAdapter(optionsAdapter);
        recyclerView.setHasFixedSize(true);
        return view;
    }

    public Boolean isInt(String str) {
        return str.matches("\\d+");
    }

    public void showDialog(String type) {
        final Dialog dialog = new Dialog(requireContext());
        switch (type) {
            case "insert": {
                fillAttributes();
//                Log.d("querys", String.valueOf(tempList.size()));
//                for(Attribute i : tempList){
//                    Log.d("querys", i.attribute);
//                    Log.d("querys", i.datatype);
//                }
//                dialog.setContentView(R.layout.insertvalues);
//                recyclerView = dialog.findViewById(R.id.tablerecyclerview);
//                recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
//                insertionAdapter = new InsertionAdapter(requireActivity(),tempList,requireContext(),(InsertionAdapter.OnItemClickListener)item->{
//
//                });
//                recyclerView.addItemDecoration(new SimpleDividerItemDecoration(requireContext()));
//                recyclerView.setAdapter(insertionAdapter);
//                recyclerView.setHasFixedSize(true);
                automateInsertionInterface(dialog);
                MaterialButton button = dialog.findViewById(R.id.submit);
                button.setOnClickListener(
                        view -> {
                            String query = "";
                            String values = "";
                            int j = 1;
                            for (Attribute i : mainViewModel.insertionAttributes.getValue()) {
                                if (j == mainViewModel.insertionAttributes.getValue().size()) {
                                    if (isInt(i.value)) {
                                        values += i.value;
                                    } else {
                                        values += "'" + i.value + "'";
                                    }
                                } else {
                                    if (isInt(i.value)) {
                                        values += i.value + ",";
                                    } else {
                                        values += "'" + i.value + "'" + ",";
                                    }
                                }
                                j++;
                            }
                            query = "INSERT INTO " + mainViewModel.tablename.getValue() + " VALUES(" + values + ")";
                            try (Statement statement = con.createStatement()) {
                                statement.execute(query);
                                dialog.dismiss();
                            } catch (SQLException e) {
                                e.printStackTrace();
                                dialog.dismiss();
                            }
                        });
                dialog.show();
            }
            case "modify": {
                fillAttributes();
                automateInsertionInterface(dialog);
                MaterialButton button = dialog.findViewById(R.id.submit);
                button.setText("Find Entry");
                button.setOnClickListener(
                        view -> {
                            findValues();

                        });
                dialog.show();
            }
            case "delete": {

            }
            case "alter": {

            }
        }
    }

    public void findValues() {
        ArrayList<Attribute> newTemp = new ArrayList<>();
        for (Attribute i : mainViewModel.insertionAttributes.getValue()) {
            if (i.value != "" && i.value != null) {
                newTemp.add(i);
            }
        }
        query = "Select * from " + mainViewModel.tablename.getValue() + " where ";
        if (!newTemp.isEmpty()) {
            int j = 0;
            for (Attribute i : newTemp) {
                j++;
                Log.d("querry", String.valueOf(j));
                Log.d("querry", String.valueOf(newTemp.size()));
                if (j == newTemp.size()) {
                    if (isInt(i.value)) {
                        query += i.attribute + "=" + i.value;
                    } else if (i.value != "") {
                        query += i.attribute + "=" + "'" + i.value + "'";
                    }
                } else {
                    if (isInt(i.value)) {
                        query += i.attribute + "=" + i.value + " and ";
                    } else if (i.value != "") {
                        query += i.attribute + "=" + "'" + i.value + "'" + " and ";
                    }
                }
                Log.d("querry", i.attribute);
            }
        }
        try (Statement statement = con.createStatement()) {
            ArrayList<Attribute> newTempList = new ArrayList<>();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                for (Attribute i : tempList) {
                    Log.d("querry", i.attribute);
                    String tempVal = resultSet.getString(i.attribute);
                    Log.d("querry", tempVal);
                    Attribute attr = new Attribute(i.attribute, tempVal);
                    newTempList.add(attr);
                }
            }
            insertionAdapter.updateList(newTempList);
            insertionAdapter.notifyDataSetChanged();
            for (Attribute i : insertionAdapter.arrayList) {
                Log.d("arr", i.attribute);
                Log.d("arr", i.value);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            Toast.makeText(requireContext(), "Entry not found", Toast.LENGTH_SHORT).show();
        }
    }

    public void automateInsertionInterface(Dialog dialog) {
        dialog.setContentView(R.layout.insertvalues);
        recyclerView = dialog.findViewById(R.id.tablerecyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        ArrayList<Attribute> list = new ArrayList<Attribute>(new LinkedHashSet<Attribute>(tempList));

        insertionAdapter = new InsertionAdapter(requireActivity(), list, requireContext(), (InsertionAdapter.OnItemClickListener) item -> {

        });
        recyclerView.addItemDecoration(new SimpleDividerItemDecoration(requireContext()));
        recyclerView.setAdapter(insertionAdapter);
        recyclerView.setHasFixedSize(true);
    }

    public void fillAttributes() {
        try {
            Statement statement = con.createStatement();
            ResultSet resultSet = statement.executeQuery("describe " + mainViewModel.tablename.getValue());
            while (resultSet.next()) {
                Attribute attribute = new Attribute(resultSet.getString("Field"), resultSet.getString("Type"), false);
                tempList.add(attribute);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
