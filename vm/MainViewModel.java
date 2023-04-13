package com.arsh.sqldb.vm;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.arsh.sqldb.models.Attribute;

import java.util.ArrayList;

public class MainViewModel extends ViewModel{
    public MutableLiveData<String> liveData = new MutableLiveData<String>();
    private final MutableLiveData<Integer> attributeNo = new MutableLiveData<Integer>(0);
    public final LiveData<Integer> liveAttributeNo = attributeNo;
    public final LiveData<String> stringLiveData = liveData;
    public MutableLiveData<String> dbname = new MutableLiveData<>();
    public MutableLiveData<String> tablename = new MutableLiveData<>();
    public MutableLiveData<ArrayList<Attribute>> primaryAttributes = new MutableLiveData<>();
    public MutableLiveData<String> newTable = new MutableLiveData<>();
    public MutableLiveData<ArrayList<Attribute>> insertionAttributes = new MutableLiveData<>();
    public String listType = "";
    public void setAttributes(ArrayList<Attribute> attributes) {
        this.attributes.setValue(attributes);
    }

    public MutableLiveData<ArrayList<Attribute>> attributes = new MutableLiveData<>();
    public void passString(String string){
        liveData.setValue(string);
    }
    public String getValue(){
        return stringLiveData.getValue();
    }
    public void setAttributeNo(int i){
        attributeNo.setValue(i);
    }

    public MutableLiveData<ArrayList<Attribute>> getAttributes() {
        return attributes;
    }

    public int getAttributeNo(){
        return attributeNo.getValue();
    }


}
