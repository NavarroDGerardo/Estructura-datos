package com.company;
public class hashValues {
    private int hashCode;
    private int index;
    private Auto value;

    public hashValues(int hashCode, int index, Auto value) {
        this.hashCode = hashCode;
        this.index = index;
        this.value = value;
    }

    public int getHashCode() {
        return hashCode;
    }

    public int getIndex() {
        return index;
    }

    public Auto getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "hashValues{" + "hashCode=" + hashCode + ", index=" + index + ", value=" + value + '}';
    }
    
}
