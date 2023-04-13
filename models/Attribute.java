package com.arsh.sqldb.models;

public class Attribute{

    public String attribute, datatype, value;
    public Boolean checked;

    public Boolean getChecked() {
        return checked;
    }

    public void setChecked(Boolean checked) {
        this.checked = checked;
    }

    public Attribute(String attribute, String datatype, Boolean checked, String value) {
        this.attribute =attribute;
        this.datatype = datatype;
        this.checked = checked;
        this.value = value;
    }
    public Attribute(String attribute, String datatype, Boolean checked) {
        this.attribute =attribute;
        this.datatype = datatype;
        this.checked = checked;
    }
    public Attribute(String attribute, String value) {
        this.attribute =attribute;
        this.value = value;
    }

    public Attribute(String attribute, Boolean checked) {
        this.attribute =attribute;
        this.checked = checked;
    }


    public String getDatatype() {
        return datatype;
    }

    public void setDatatype(String datatype) {
        this.datatype = datatype;
    }

    public String getAttribute() {
        return attribute;
    }

    public void setAttribute(String attribute) {
        this.attribute = attribute;
    }
}
