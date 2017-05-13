package com.angela.cumulativeReturn;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Angela on 5/12/2017.
 */
public class CumRetCalculator {
    //Storage for daily returns for various days from B
    private TreeMap<Date, BigDecimal> dailyReturns;
    private Map<DateRange, Double> dailyReturnCache;

    public CumRetCalculator(String fileName) {
        this.dailyReturns = DailyReturns.getDailyReturns(fileName);
        dailyReturnCache = new ConcurrentHashMap<DateRange, Double>();
    }

    double findCumReturn(Date asOf, Date base){
        if(asOf.before(base))
            return Double.NaN;
        DateRange range = new DateRange(asOf, base);
        dailyReturnCache.putIfAbsent(range, computeCumReturn(range));

        return dailyReturnCache.get(range);

    }

    double computeCumReturn(DateRange range) {
        Map<Date, BigDecimal> rangeReturn = this.dailyReturns.subMap(range.getBase(), false, range.getAsOf(), true);
        BigDecimal result = new BigDecimal(1);
        for (BigDecimal cumReturn: rangeReturn.values()){
            result = result.multiply(cumReturn.add(new BigDecimal(1)));
        }

        return (result.subtract(new BigDecimal(1))).doubleValue();
    }

    class DateRange{
        private Date asOf;
        private Date base;
        public DateRange(Date asOf, Date base) {
            this.asOf = asOf;
            this.base = base;
        }

        public Date getAsOf() {
            return asOf;
        }

        public void setAsOf(Date asOf) {
            this.asOf = asOf;
        }

        public Date getBase() {
            return base;
        }

        public void setBase(Date base) {
            this.base = base;
        }

        @Override
        public int hashCode()
        {
            return new HashCodeBuilder()
                    .append(this.asOf)
                    .append(this.base)
                    .toHashCode();
        }

        @Override
        public boolean equals(Object obj) {
            if (obj instanceof DateRange == false) {
                return false;
            }
            if (this == obj) {
                return true;
            }

            return new EqualsBuilder()
                    .append(this.asOf, ((DateRange)obj).getAsOf())
                    .append(this.base, ((DateRange)obj).getBase())
                    .isEquals();
        }
    }
}
