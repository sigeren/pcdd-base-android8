package com.df.vip.pcdd.network.bean;

/**
 * Created by hang on 2017/6/18.
 */

public class LotteryLogInfo {

    public int id;
    public int user_id;
    public String chou_jiang_option;
    public double point;
    public long create_time;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getChou_jiang_option() {
        return chou_jiang_option;
    }

    public void setChou_jiang_option(String chou_jiang_option) {
        this.chou_jiang_option = chou_jiang_option;
    }

    public double getPoint() {
        return point;
    }

    public void setPoint(double point) {
        this.point = point;
    }

    public long getCreate_time() {
        return create_time;
    }

    public void setCreate_time(long create_time) {
        this.create_time = create_time;
    }
}
