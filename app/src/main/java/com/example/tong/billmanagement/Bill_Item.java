package com.example.tong.billmanagement;

import java.io.Serializable;

/**
 * Created by 15639 on 2018/5/24.
 */

public class Bill_Item implements Serializable
{
    private String id;
    private String date;
    private String year;
    private String month;
    private String day;
    private String coast;
    private String type;
    private String content;

    public Bill_Item(String id, String year, String month, String day, String coast, String type, String content)
    {
        this.id = id;
        this.date = year + "-" + month + "-" + day;
        this.year = year;
        this.month = month;
        this.day = day;
        this.coast = coast;
        this.type = type;
        this.content = content;
    }

    public String getId()
    {
        return id;
    }

    public String getDate()
    {
        return date;
    }

    public String getYear()
    {
        return year;
    }

    public String getMonth()
    {
        return month;
    }

    public String getDay()
    {
        return day;
    }

    public String getCoast()
    {
        return coast;
    }

    public String getType()
    {
        return type;
    }

    public String getContent()
    {
        return content;
    }
}
