package com.arsh.sqldb.adapters;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.arsh.sqldb.Attribute;
import com.arsh.sqldb.R;

import java.util.ArrayList;
import java.util.Objects;

public class AttributesAdapter extends RecyclerView.Adapter<AttributesAdapter.courseHolder> {
    private final int limit = 20;
    ArrayList<Attribute> arrayList;
    ArrayList<Attribute> backup;
    Context context;
    public interface OnItemClickListener {
        void onItemClick(String item);
    }
    private OnItemClickListener listener;

    String[] array;
    @NonNull
    @Override
    public courseHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.attributesrow,parent,false);
        return new courseHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull courseHolder holder, int position) {
        holder.bind(context,array,arrayList,listener,position);
    }

    public AttributesAdapter(ArrayList<Attribute> arrayList, Context context, OnItemClickListener listener){
        this.arrayList = arrayList;
        this.context = context;
        this.listener = listener;
        this.array = Objects.requireNonNull(context).getResources().getStringArray(R.array.datatypes);
        backup = new ArrayList<>(arrayList);
    }

//    @Override
//    public Filter getFilter() {
//        return filter;
//    }
//
//    Filter filter = new Filter() {
//        @Override
//        protected FilterResults performFiltering(CharSequence keyword) {
//            ArrayList<String> filteredData = new ArrayList<>();
//            if(keyword.toString().isEmpty()){
//                filteredData.addAll(backup);
//            }else{
//                for (String obj: backup) {
//                    if(obj.toLowerCase().contains(keyword.toString().toLowerCase())){
//                        filteredData.add(obj);
//                    }
//                }
//            }
//            FilterResults results = new FilterResults();
//            results.values = filteredData;
//            return results;
//        }
//
//        @SuppressLint("NotifyDataSetChanged")
//        @Override
//        protected void publishResults(CharSequence constraint, FilterResults results) {
//            arrayList.clear();
//            arrayList.addAll((ArrayList<String>)results.values);
//            notifyDataSetChanged();
//        }
//    };

    public static class courseHolder extends RecyclerView.ViewHolder{

        EditText textView;
        AutoCompleteTextView autoCompleteTextView;
        ArrayAdapter<String> adapter;
        public courseHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.tv);
            autoCompleteTextView = itemView.findViewById(R.id.datatype);
        }

        public void bind(Context context, String[] array, ArrayList<Attribute> attributes, OnItemClickListener listener, int position) {
            textView.setText(attributes.get(position).getAttribute());
            adapter = new ArrayAdapter<>(context,R.layout.dropdown,array);
            textView.addTextChangedListener(new TextWatcher() {

                @Override
                public void afterTextChanged(Editable s) {}

                @Override
                public void beforeTextChanged(CharSequence s, int start,
                                              int count, int after) {
                }

                @Override
                public void onTextChanged(CharSequence s, int start,
                                          int before, int count) {
                    attributes.get(position).setAttribute(s.toString());
                }
            });
            autoCompleteTextView.setAdapter(adapter);
            autoCompleteTextView.addTextChangedListener(new TextWatcher() {

                @Override
                public void afterTextChanged(Editable s) {}

                @Override
                public void beforeTextChanged(CharSequence s, int start,
                                              int count, int after) {
                }

                @Override
                public void onTextChanged(CharSequence s, int start,
                                          int before, int count) {
                    attributes.get(position).setDatatype(s.toString());
                }
            });
//            itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    listener.onItemClick(s);
//                }
//            });
        }
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }
}
