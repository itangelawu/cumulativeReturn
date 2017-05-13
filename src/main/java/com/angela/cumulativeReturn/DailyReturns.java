package com.angela.cumulativeReturn;

import java.util.*;

/**
 * Created by Angela on 5/12/2017.
 */
public class DailyReturns {
    private TreeMap<Date, Double> dailyReturnsMap;
    private static DailyReturns instance;

    private DailyReturns(Map<Date, Double> dailyReturnsInput){
        dailyReturnsMap = loadMap(dailyReturnsInput);
    }

    public static DailyReturns getDailyReturns(Map<Date, Double> dailyReturnsInput){
        if(instance == null){
            synchronized (DailyReturns.class) {
                if(instance == null){
                    instance = new DailyReturns(dailyReturnsInput);
                }
            }
        }
        return instance;
    }

    private TreeMap<Date, Double> loadMap(Map<Date, Double> dailyReturnsInput){
        List<String> lines = new ArrayList<>();
        TreeMap<Date, Double> result =  new TreeMap<Date, Double>(new Comparator<Date>()
        {
            public int compare(Date o1, Date o2)
            {
                return o1.compareTo(o2);
            }
        });

        result.putAll(dailyReturnsInput);

        return result;
    }

    public TreeMap<Date, Double> getDailyReturnsMap(){
        return dailyReturnsMap;
    }


}
