package net.aydini.modescisc.cif.util;


import java.util.Calendar;
import java.util.Date;

import com.ghasemkiani.util.icu.PersianCalendar;

/**
 * 
 * @author  <a href="mailto:hi@aydini.net">Aydin Nasrollahpour </a>
 *
 *Dec 14, 2020
 */
public class PersianDateUtils {
	
	
	private static PersianDateUtils persianDateUtils;
	
	private PersianDateUtils() 
	{
	}
	
	
	public static PersianDateUtils getInstance()
	{
		if(persianDateUtils == null)
			persianDateUtils = new PersianDateUtils();
		return persianDateUtils;
	}
	
	
	public PersianCalendar getCalandar(Date date)
	{
		PersianCalendar persianCalendar = new PersianCalendar(new Date());
		return persianCalendar;
				
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
		return stringBuilder.append(calendar.get(Calendar.YEAR)).append("-").append(calendar.get(Calendar.MONTH)+1)
		.append("-").append(calendar.get(Calendar.DAY_OF_MONTH)).toString();
		
	}
	
}
