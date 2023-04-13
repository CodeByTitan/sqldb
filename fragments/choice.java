package com.arsh.sqldb.fragments;

import android.app.Dialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;

import com.arsh.sqldb.vm.MainViewModel;
import com.arsh.sqldb.R;
import com.arsh.sqldb.data.SqlConnection;
import com.arsh.sqldb.adapters.AttributeSelectAdapter;
import com.arsh.sqldb.adapters.AttributesAdapter;
import com.arsh.sqldb.models.Attribute;
import com.google.android.material.button.MaterialButton;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link choice#newInstance} factory method to
 * create an instance of this fragment.
 */
public class choice extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment databases.
     */
    // TODO: Rename and change types and number of parameters
    public static choice newInstance(String param1, String param2) {
        choice fragment = new choice();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public choice() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    TextView textView;
    TextView choice1;
    TextView choice2;
    MainViewModel mainViewModel;
    RecyclerView recyclerView;
    AttributesAdapter attributesAdapter;
    ArrayList<Attribute> arrayList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_choice, container, false);
        textView = view.findViewById(R.id.dbname);
        choice2 = view.findViewById(R.id.usedatabase);
        choice1 = view.findViewById(R.id.createdatabase);
        mainViewModel = new ViewModelProvider(requireActivity()).get(MainViewModel.class);
        textView.setText(mainViewModel.dbname.getValue());
        choice1.setOnClickListener(table -> {
            showDialog("attributeno");
        });
        choice2.setOnClickListener(table -> {
            Fragment fragment = new lists();
            requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainerView, fragment).addToBackStack("main").commit();
            mainViewModel.liveData.setValue("tables");
        });
        return view;
    }

    private void showDialog(String type) {
        final Dialog dialog = new Dialog(requireContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        if (Objects.equals(type, "attributeno")) {
            dialog.setContentView(R.layout.attributes);
            EditText editText = dialog.findViewById(R.id.attributeno);
            EditText tablename = dialog.findViewById(R.id.tablename);
            tablename.addTextChangedListener(new TextWatcher() {

                @Override
                public void afterTextChanged(Editable s) {
                }

                @Override
                public void beforeTextChanged(CharSequence s, int start,
                                              int count, int after) {
                }

                @Override
                public void onTextChanged(CharSequence s, int start,
                                          int before, int count) {
                    if (s.length() != 0) {
                        mainViewModel.newTable.setValue(s.toString());
                    }
                }
            });

            editText.addTextChangedListener(new TextWatcher() {

                @Override
                public void afterTextChanged(Editable s) {
                }

                @Override
                public void beforeTextChanged(CharSequence s, int start,
                                              int count, int after) {
                }

                @Override
                public void onTextChanged(CharSequence s, int start,
                                          int before, int count) {
                    if (s.length() != 0) {
                        mainViewModel.setAttributeNo(Integer.parseInt(s.toString()));
                    }
                }
            });
            MaterialButton button = dialog.findViewById(R.id.submit);
            button.setOnClickListener(
                    view -> {
                        if (mainViewModel.liveAttributeNo.getValue() != 0) {
                            dialog.dismiss();
                            showDialog("attributes");
                        }
                    });
        } else if (Objects.equals(type, "attributes")) {
            dialog.setContentView(R.layout.createtabledialog);
            recyclerView = dialog.findViewById(R.id.tablerecyclerview);
            recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
            int i = 0;
            int attributeno = mainViewModel.getAttributeNo();
            arrayList = new ArrayList<>();
            while (i < attributeno) {
                int j = i + 1;
                Attribute attr = new Attribute("attr_" + j, "", false);
                arrayList.add(attr);
                i++;
            }
            attributesAdapter = new AttributesAdapter(arrayList, requireContext(), (AttributesAdapter.OnItemClickListener) item -> {
            });
            recyclerView.addItemDecoration(new SimpleDividerItemDecoration(requireContext()));
            recyclerView.setAdapter(attributesAdapter);
            recyclerView.setHasFixedSize(true);
            MaterialButton button = dialog.findViewById(R.id.submit);
            button.setOnClickListener(
                    view -> {
                        mainViewModel.setAttributes(arrayList);
                        for (Attribute j : arrayList) {
                            Log.d("array", j.attribute);
                            Log.d("array", j.datatype);
                        }
                        dialog.dismiss();
                        showDialog("primary");
                    });
        } else if (Objects.equals(type, "primary")) {
            dialog.setContentView(R.layout.createtabledialog);
            TextView textView = dialog.findViewById(R.id.tabletext);
            textView.setText("Select attribute(s) for primary key :");
            RecyclerView rcv = dialog.findViewById(R.id.tablerecyclerview);
            rcv.setLayoutManager(new LinearLayoutManager(requireContext()));
            AttributeSelectAdapter adapter = new AttributeSelectAdapter(arrayList, requireContext(), (AttributeSelectAdapter.OnItemClickListener) item -> {
            });
            rcv.addItemDecoration(new SimpleDividerItemDecoration(requireContext()));
            rcv.setAdapter(adapter);
            rcv.setHasFixedSize(true);
            MaterialButton button = dialog.findViewById(R.id.submit);
            button.setText("create table");
            button.setOnClickListener(
                    view -> {
                        ArrayList<Attribute> tempList = new ArrayList<>();
                        for (Attribute j : arrayList) {
                            if (j.checked) {
                                tempList.add(j);
                            }
                        }
                        mainViewModel.primaryAttributes.setValue(tempList);
                        dialog.dismiss();
                        for (Attribute i : mainViewModel.primaryAttributes.getValue()) {
                            Log.d("primaryattributes", i.attribute);
                        }
                        for (Attribute i : mainViewModel.attributes.getValue()) {
                            Log.d("attributes", i.attribute);
                        }
                        Connection con = new SqlConnection().SQLConnection();
                        try {
                            Statement statement = con.createStatement();
                            String query = "";
                            query = "create table " + mainViewModel.newTable.getValue() + "(";
                            int j = 0;
                            StringBuilder addnewString = new StringBuilder();
                            int items = mainViewModel.attributes.getValue().size();
                            for (Attribute i : mainViewModel.attributes.getValue()) {
//
                                addnewString = new StringBuilder((i.attribute + " " + i.datatype + ","));
                                query = query + addnewString;
                                j++;
                            }
                            addnewString = new StringBuilder(("PRIMARY KEY("));
                            int m = 0;
                            int primarySize = mainViewModel.primaryAttributes.getValue().size();
                            for (Attribute k : mainViewModel.primaryAttributes.getValue()) {
                                if (primarySize > 1) {
                                    addnewString.append(k.attribute).append(",");
                                    primarySize--;
                                } else if (primarySize == 1) {
                                    addnewString.append(k.attribute).append(")");
                                }
                            }
                            query = query + addnewString;
                            query = query + ")";
                            query = query.replace("varchar","varchar(50)");
                            Log.d("SqlQuery", query);
                            statement.execute(query);
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                        dialog.dismiss();
                    });
        }
        dialog.show();
    }

}