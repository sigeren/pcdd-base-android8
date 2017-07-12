package com.extreme.ks.pcdd.network.bean;

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
}
