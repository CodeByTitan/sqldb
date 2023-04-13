package com.arsh.sqldb.adapters;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.recyclerview.widget.RecyclerView;
import com.arsh.sqldb.models.Attribute;
import com.arsh.sqldb.vm.MainViewModel;
import com.arsh.sqldb.R;

import java.util.ArrayList;
import java.util.Objects;

public class InsertionAdapter extends RecyclerView.Adapter<InsertionAdapter.courseHolder> {
    private final int limit = 20;
    public ArrayList<Attribute> arrayList;
    ArrayList<Attribute> backup;
    Context context;
    ViewModelStoreOwner owner;
    public interface OnItemClickListener {
        void onItemClick(String item);
    }
    private OnItemClickListener listener;

    String[] array;
    @NonNull
    @Override
    public courseHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.insertvaluerow,parent,false);
        return new courseHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull courseHolder holder, int position) {
        holder.bind(owner, arrayList,listener,position);
    }

    public void updateList(ArrayList<Attribute> arrayList){
        this.arrayList = arrayList;
    }

    public InsertionAdapter(ViewModelStoreOwner owner,ArrayList<Attribute> arrayList, Context context, OnItemClickListener listener){
        this.arrayList = arrayList;
        this.context = context;
        this.listener = listener;
        this.owner= owner;
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

        TextView textView;
        EditText editText;
        ArrayList<Attribute> arrayList = new ArrayList<>();;
        MainViewModel mainViewModel;
        public courseHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.attribute);
            editText = itemView.findViewById(R.id.value);
        }

        public void bind(ViewModelStoreOwner owner, ArrayList<Attribute> attributes, OnItemClickListener listener, int position) {
            mainViewModel = new ViewModelProvider(owner).get(MainViewModel.class);
            textView.setText(attributes.get(position).getAttribute());
            editText.setText(attributes.get(position).value);
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
                    attributes.get(position).value = s.toString();
                    mainViewModel.insertionAttributes.postValue(attributes);
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
