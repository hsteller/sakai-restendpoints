package de.berlin.fu.imp.sakai.direct.service.impl;

import java.util.Calendar;
import java.util.Date;

import org.sakaiproject.time.api.TimeRange;
import org.sakaiproject.time.api.TimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.berlin.fu.imp.sakai.direct.service.ITimeService;

@Service
public class TimeServiceImpl implements ITimeService {

	private static final long MILLISECONDS_IN_A_YEAR=1000*60*60*24*365;

	@Autowired
	private TimeService sakaiTimeService;
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public long currentTimeInMillis() {
		return System.currentTimeMillis();
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Date now() {	
		return new Date(currentTimeInMillis());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Date startOfToday() {
		return startOfDay(now());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Date startOfDay(Date timeOfSomeDay) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(timeOfSomeDay);
		cal.set(Calendar.HOUR_OF_DAY,0);
		cal.set(Calendar.MINUTE,0);
		cal.set(Calendar.SECOND,0);
		cal.set(Calendar.MILLISECOND, 0);
		return cal.getTime();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public TimeRange oneYearfromToday() {
		final Date today = startOfToday();
		final long todayMillis = today.getTime();
		final long nextYear = todayMillis+MILLISECONDS_IN_A_YEAR;
		return sakaiTimeService.newTimeRange(todayMillis, nextYear);		
	}

}
