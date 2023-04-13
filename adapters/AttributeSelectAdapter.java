package com.arsh.sqldb.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.recyclerview.widget.RecyclerView;


import com.arsh.sqldb.Attribute;
import com.arsh.sqldb.R;

import java.util.ArrayList;
import java.util.Objects;

public class AttributeSelectAdapter extends RecyclerView.Adapter<AttributeSelectAdapter.courseHolder> {
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
        View view = inflater.inflate(R.layout.attributeselectrow,parent,false);
        return new courseHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull courseHolder holder, int position) {
        holder.bind(arrayList,listener,position);
    }

    public AttributeSelectAdapter(ArrayList<Attribute> arrayList, Context context, OnItemClickListener listener){
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
        AppCompatCheckBox checkbox;
        public courseHolder(@NonNull View itemView) {
            super(itemView);
            checkbox = itemView.findViewById(R.id.checkbox);
        }

        public void bind(ArrayList<Attribute> attributes, OnItemClickListener listener, int position) {
           checkbox.setText(attributes.get(position).getAttribute());
           checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
               @Override
               public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                   attributes.get(position).checked = b;
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
