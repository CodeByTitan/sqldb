package com.arsh.sqldb.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.arsh.sqldb.R;

import java.util.ArrayList;

public class OptionsAdapter extends RecyclerView.Adapter<OptionsAdapter.courseHolder> {
    private final int limit = 20;
    ArrayList<String> arrayList;
    ArrayList<String> backup;
    Context context;
    public interface OnItemClickListener {
        void onItemClick(String item);
    }
    private OnItemClickListener listener;

    @NonNull
    @Override
    public courseHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.singlerow,parent,false);
        return new courseHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull courseHolder holder, int position) {
        holder.bind(arrayList.get(position),listener);
    }

    public OptionsAdapter(ArrayList<String> arrayList, Context context, OnItemClickListener listener){
        this.arrayList = arrayList;
        this.context = context;
        this.listener = listener;
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

        TextView textView;
        public courseHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.tv);
        }

        public void bind(String s, OnItemClickListener listener) {
            textView.setText(s);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(s);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }
}
