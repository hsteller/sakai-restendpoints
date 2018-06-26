package de.berlin.fu.imp.sakai.direct.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.sakaiproject.calendar.api.Calendar;
import org.sakaiproject.calendar.api.CalendarEvent;
import org.sakaiproject.exception.IdUnusedException;
import org.sakaiproject.exception.PermissionException;
import org.sakaiproject.site.api.Site;
import org.sakaiproject.site.api.SiteService;
import org.sakaiproject.site.api.ToolConfiguration;
import org.sakaiproject.time.api.TimeRange;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.berlin.fu.imp.sakai.direct.dto.ExportCalenderEvent;
import de.berlin.fu.imp.sakai.direct.service.ICalendarService;
import de.berlin.fu.imp.sakai.direct.service.ITimeService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class CalendarServiceImpl implements ICalendarService{
	// TODO add simple caching + cache clearing (copy from proinf project)
	
	
	@Autowired
	private org.sakaiproject.calendar.api.CalendarService sakaiCalService;
	
	
	@Autowired
	private ITimeService timeService;
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<ExportCalenderEvent> getEventsForSite(String siteId) {
		Calendar cal = getCalendarForSite(siteId);
		if (cal != null) {
			List<CalendarEvent> events = getCalendarEvents(cal);
			if (events != null) {
				return events.stream().map(ExportCalenderEvent::createFromSakaiEvent).collect(Collectors.toList());
			}
		} 
		else {
			log.warn("calendar was null for siteId " + siteId);
		}
		return Collections.emptyList();
	}

	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<ExportCalenderEvent> getUpcomingEventsForSite(String siteId) {
		Calendar cal = getCalendarForSite(siteId);
		if (cal != null) {
			List<CalendarEvent> events = getNextYearsCalendarEvents(cal);
			if (events != null) {
				return events.stream().map(ExportCalenderEvent::createFromSakaiEvent).collect(Collectors.toList());
			}
		} 
		else {
			log.warn("calendar was null for siteId " + siteId);
		}
		return Collections.emptyList();
	}
	
	
	
	@SuppressWarnings("unchecked") // can't do much about the ancient Sakai calendar API not using generics...
	private final List<CalendarEvent> getNextYearsCalendarEvents(Calendar cal) {
		try {
			TimeRange oneYear = timeService.oneYearfromToday();
			return (List<CalendarEvent>) cal.getEvents(oneYear, null);
		}
		catch (Exception e) {
			log.error(e.getMessage(), e);
			return new ArrayList<CalendarEvent>(0);
		}
	}
	
	@SuppressWarnings("unchecked") // can't do much about the ancient Sakai calendar API not using generics...
	private final List<CalendarEvent> getCalendarEvents(Calendar cal) {
		try {
			return (List<CalendarEvent>) cal.getEvents(null, null);
		}
		catch (Exception e) {
			log.error(e.getMessage(), e);
			return new ArrayList<CalendarEvent>(0);
		}
	}
	
	private final Calendar getCalendarForSite(String siteID) {
		String calRef = sakaiCalService.calendarReference(siteID, SiteService.MAIN_CONTAINER);
		try {			
			return sakaiCalService.getCalendar(calRef);

		}
		catch (IdUnusedException ie) {
			log.debug("calendar id unused; message:" + ie.getMessage() + "; siteID=" + siteID);			
		}
		catch (PermissionException e) {
			log.error("permission exception: " + e.getMessage()+ "; siteID=" + siteID, e);
		}			
		return null;
	}
	
	

	
}
