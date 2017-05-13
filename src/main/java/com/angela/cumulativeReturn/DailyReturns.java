package com.angela.cumulativeReturn;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by Angela on 5/12/2017.
 */
public class DailyReturns {
    private static TreeMap<Date, BigDecimal> dailyReturns = null;

    private DailyReturns(){
    }

    public static TreeMap<Date, BigDecimal> getDailyReturns(String fileName){
        if(dailyReturns == null){
            synchronized (DailyReturns.class) {
                if(dailyReturns == null){
                    dailyReturns = loadMap(fileName);
                }
            }
        }
        return dailyReturns;
    }

    private static TreeMap<Date, BigDecimal> loadMap(String fileName){
        List<String> lines = new ArrayList<>();
        TreeMap<Date, BigDecimal> result =  new TreeMap<Date, BigDecimal>(new Comparator<Date>()
        {
            public int compare(Date o1, Date o2)
            {
                return o1.compareTo(o2);
            }
        });

        try (Stream<String> stream = Files.lines(Paths.get(fileName))) {
            //input every line to a List
            lines = stream
                    .collect(Collectors.toList());

        } catch (IOException e) {
            e.printStackTrace();
        }

        for(String line:lines) {
            String[] splited = ((String)line).split("\\s+");
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

            try {
                Date date = formatter.parse(splited[0]);
                result.put(date, new BigDecimal(splited[1]));

            } catch (ParseException e) {
                e.printStackTrace();
            }

        }

        return result;
    }


}
