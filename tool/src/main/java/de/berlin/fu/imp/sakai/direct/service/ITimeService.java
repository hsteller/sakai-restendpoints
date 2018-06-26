package de.berlin.fu.imp.sakai.direct.service;

import java.util.Date;

import org.sakaiproject.time.api.TimeRange;

public interface ITimeService {

	

	/**
	 * 
	 * @return milliseconds since epoch (simply forwards the call to #System{@link #currentTimeInMillis()})
	 */
	public long currentTimeInMillis();

	/**
	 * 
	 * @return the day right now (as returned by currentTimeInMillis())
	 */
	public Date now();

	/**
	 * @return Current date/time with all time fields (hours, minutes, seconds, milliseconds) set to zero.
	 */
	public Date startOfToday();

	/**
	 * @return the parameter date with all time fields (hours, minutes, seconds, milliseconds) set to zero.
	 * 
	 */
	public Date startOfDay(Date timeOfSomeDay);
	
	/**
	 * @return sakai TimeRange, 365 days from the start of today 
	 * @see #startOfToday() 
	 */
	public TimeRange oneYearfromToday();
	
}
