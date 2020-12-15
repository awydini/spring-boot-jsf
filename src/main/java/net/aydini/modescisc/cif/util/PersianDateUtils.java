package net.aydini.modescisc.cif.util;

import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang.StringUtils;

import com.ghasemkiani.util.icu.PersianCalendar;

import net.aydini.modescisc.cif.exception.ServiceException;

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

    public PersianCalendar getCalandar(Date date)
    {
        return new PersianCalendar(new Date());

    }

    public PersianCalendar getCalandar(int year, int month, int day)
    {
        return new PersianCalendar(year, month, day);

    }

    public Date convertPersianDate(String jalaliDate, String delimiter)
    {
        if (jalaliDate == null) return null;
        if (delimiter != null) jalaliDate = jalaliDate.replaceAll(delimiter, "");
        if (jalaliDate.length() != STANDARD_PERSIAN_DATE_LENTH) throw new ServiceException("invalid length", jalaliDate);
        if (!StringUtils.isNumeric(jalaliDate)) throw new ServiceException("invalid input", jalaliDate);

        int year = Integer.parseInt(jalaliDate.substring(0, 4));
        int month = Integer.parseInt(jalaliDate.substring(4, 6))-1;
        int day = Integer.parseInt(jalaliDate.substring(6, 8));

        return getCalandar(year, month, day).getTime();

    }

    private PersianCalendar getPersianCalendar(Date date)
    {
        return new PersianCalendar(new Date());
    }

    public String getPersianDate(Date date)
    {

        return toString(date);

    }

    private String toString(Date date)
    {
        PersianCalendar calendar = getPersianCalendar(date);
        StringBuilder stringBuilder = new StringBuilder();
        return stringBuilder.append(calendar.get(Calendar.YEAR)).append("-").append(calendar.get(Calendar.MONTH) + 1).append("-")
                .append(calendar.get(Calendar.DAY_OF_MONTH)).toString();

    }

}
