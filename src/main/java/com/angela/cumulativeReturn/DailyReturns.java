package com.angela.cumulativeReturn;

import java.util.*;

/**
 * Created by Angela on 5/12/2017.
 * This is a singleton class to load data for daily returns. Since there can be data in the range of high-thousands items,
 * so set it as an one-time operation. That's is only one instance across whole application and data loaded only once
 * when application start
 */
public class DailyReturns {
    private TreeMap<Date, Double> dailyReturnsMap;
    private static DailyReturns instance;

    /**
     * private constructor which intialize daily returns map
     * @param dailyReturnsInput inputMap
     */
    private DailyReturns(Map<Date, Double> dailyReturnsInput){
        dailyReturnsMap = loadMap(dailyReturnsInput);
    }

    /**
     * method to create instance only when not exist
     * @param dailyReturnsInput inputMap
     */
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

    /**
     * method to load user input map to a tree map, improve the performance when searching a range of daily returns
     * @param dailyReturnsInput inputMap
     * @return TreeMap<Date, Double> sorted map according to the date
     */
    private TreeMap<Date, Double> loadMap(Map<Date, Double> dailyReturnsInput){
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

    /**
     * method to return sorted daily returns map
     * @return TreeMap<Date, Double> sorted map according to the date
     */
    public TreeMap<Date, Double> getDailyReturnsMap(){
        return dailyReturnsMap;
    }


}
