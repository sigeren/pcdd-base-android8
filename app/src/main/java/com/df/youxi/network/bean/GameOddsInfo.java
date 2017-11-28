package com.df.youxi.network.bean;

import java.io.Serializable;

/**
 * Created by hang on 2017/2/21.
 */

public class GameOddsInfo implements Serializable {
    public int id;
    public int game_type;
    public int bili_type;
    public String bili_name;
    public double bili;
    public String result = "";
    public String result_desc;
    public double min_point;    //最小投注金额
    public double max_point;

    @Override
    public String toString() {
        return "GameOddsInfo{" +
                "id=" + id +
                ", game_type=" + game_type +
                ", bili_type=" + bili_type +
                ", bili_name='" + bili_name + '\'' +
                ", bili=" + bili +
                ", result='" + result + '\'' +
                ", result_desc='" + result_desc + '\'' +
                ", min_point=" + min_point +
                ", max_point=" + max_point +
                '}';
    }
}
