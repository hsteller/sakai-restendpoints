package de.berlin.fu.imp.sakai.direct.service;

import java.util.List;

import de.berlin.fu.imp.sakai.direct.dto.ExportCalenderEvent;

public interface ICalendarService {
	
	
	/**
	 * 
	 * Loads all calendar events for the given site's main calendar from the sakai calendar API
	 * @param siteId id of the site whose events we want to load
	 * @return all calendar events in the given site's main calendar
	 */
	public List<ExportCalenderEvent> getEventsForSite(String siteId);
	
	/**
	 * 
	 * Loads calendar events between the start of today and one year in the future
	 * from the given site's main calendar.
	 * @param siteId id of the site whose future events we want to load
	 * @return future calendar events in the given site's main calendar from today until next year
	 */
	public List<ExportCalenderEvent> getUpcomingEventsForSite(String siteId);
	
}
