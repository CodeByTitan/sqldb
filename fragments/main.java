package com.arsh.sqldb.fragments;

import android.app.Dialog;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

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
import com.google.android.material.button.MaterialButton;

import java.sql.Connection;
import java.sql.Statement;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link main#newInstance} factory method to
 * create an instance of this fragment.
 *
 */
public class main extends Fragment {

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
     * @return A new instance of fragment main.
     */
    // TODO: Rename and change types and number of parameters
    public static main newInstance(String param1, String param2) {
        main fragment = new main();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }
    String dbname = "";
    private void showDialog() {
        final Dialog dialog = new Dialog(requireContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.tagremovedialog);
        EditText editText = dialog.findViewById(R.id.dbname);
        editText.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {}

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                if(s.length() != 0){
                    dbname = s.toString();
                }
            }
        });
        MaterialButton button = dialog.findViewById(R.id.submit);
        button.setOnClickListener(
                view->{
                    if(!dbname.isEmpty()){
                        Connection con = new SqlConnection().SQLConnection();
                        if(con!=null){
                            try{
                                Statement statement = con.createStatement();
                                statement.execute("Create database "+dbname);
                                dialog.dismiss();
                            } catch (Exception e) {
                                Log.d("error",e.toString());
                                dialog.dismiss();
                            }
                        }
                    }

        });

//        editText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//            public void onFocusChange(View v, boolean hasFocus) {
//                if (hasFocus)
//                    editText.setHint("");
//                else
//                    editText.setHint("Your hint");
//            }
//        });
        dialog.show();
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    }
    public main() {
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

    MainViewModel mainViewModel;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        TextView createdb = view.findViewById(R.id.createdatabase);
        TextView usedb = view.findViewById(R.id.usedatabase);
        createdb.setOnClickListener(db->{
            showDialog();
        });
        mainViewModel = new ViewModelProvider(requireActivity()).get(MainViewModel.class);
        usedb.setOnClickListener(use->{
            Fragment fragment = new lists();
            requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainerView,fragment).addToBackStack("main").commit();
            mainViewModel.liveData.setValue("databases");
        });
        return view;
    }
}