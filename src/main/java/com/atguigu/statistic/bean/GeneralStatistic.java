package com.atguigu.statistic.bean;

public class GeneralStatistic {
    private String name;
    private String value;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "GeneralStatistic{" +
                "name='" + name + '\'' +
                ", value='" + value + '\'' +
                '}';
    }
}
