package ru.netology.data;

public enum StatusCard {
    APPROVED("APPROVED"),
    DECLINED("DECLINED");

    private String statusName;
    StatusCard(String name){
        this.statusName = name;
    }
    public String getStatusName(){
        return this.statusName;
    }
}