package net.aydini.modescisc.cif.util;

import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang.StringUtils;

import net.aydini.modescisc.cif.exception.ServiceException;
import net.aydini.modescisc.cif.util.JalaliCalendar.YearMonthDate;

/**
 * 
 * @author <a href="mailto:hi@aydini.net">Aydin Nasrollahpour </a>
 *
 *         Dec 14, 2020
 */
public class PersianDateUtils
{

    private static PersianDateUtils persianDateUtils;

    private final static int STANDARD_PERSIAN_DATE_LENTH = 8;

    private PersianDateUtils()
    {
    }

    public static PersianDateUtils getInstance()
    {
        if (persianDateUtils == null) persianDateUtils = new PersianDateUtils();
        return persianDateUtils;
    }

    public JalaliCalendar getCalandar(Date date)
    {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        YearMonthDate yearMonthDate = new YearMonthDate(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),calendar.get(Calendar.DATE));
        yearMonthDate = JalaliCalendar.gregorianToJalali(yearMonthDate);
        return getCalandar(yearMonthDate.getYear(),yearMonthDate.getMonth(),yearMonthDate.getDate());

    }

    public JalaliCalendar getCalandar(int year, int month, int day)
    {
        return new JalaliCalendar(year, month, day);

    }

    public Date convertPersianDate(String jalaliDate, String delimiter)
    {
        if (jalaliDate == null) return null;
        if (delimiter != null) jalaliDate = jalaliDate.replaceAll(delimiter, "");
        if (jalaliDate.length() != STANDARD_PERSIAN_DATE_LENTH) throw new ServiceException("invalid date", jalaliDate);
        if (!StringUtils.isNumeric(jalaliDate)) throw new ServiceException("invalid date", jalaliDate);

        int year = Integer.parseInt(jalaliDate.substring(0, 4));
        int month = Integer.parseInt(jalaliDate.substring(4, 6))-1;
        int day = Integer.parseInt(jalaliDate.substring(6, 8));

        return getCalandar(year, month, day).getTime();

    }


    public String getPersianDate(Date date)
    {

        return toString(date);

    }

    private String toString(Date date)
    {
        JalaliCalendar calendar = getCalandar(date);
        StringBuilder stringBuilder = new StringBuilder();
        String month = StringUtils.leftPad(String.valueOf(calendar.get(Calendar.MONTH) +1), 2,"0");
        String day = StringUtils.leftPad(String.valueOf(calendar.get(Calendar.DAY_OF_MONTH)), 2,"0");
        return stringBuilder.append(calendar.get(Calendar.YEAR)).append("-").append(month).append("-")
                .append(day).toString();
    }
    
    

}
