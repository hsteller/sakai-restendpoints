package de.berlin.fu.imp.sakai.direct.controller;

import java.util.Collections;
import java.util.List;

import org.sakaiproject.component.api.ServerConfigurationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import de.berlin.fu.imp.sakai.direct.dto.ExportAnnouncements;
import de.berlin.fu.imp.sakai.direct.dto.ExportCalenderEvent;
import de.berlin.fu.imp.sakai.direct.dto.PortalData;
import de.berlin.fu.imp.sakai.direct.service.IAnnouncementService;
import de.berlin.fu.imp.sakai.direct.service.ICalendarService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Info;
import io.swagger.annotations.SwaggerDefinition;

@RestController
@RequestMapping("/portal")
@Api
@SwaggerDefinition (info=@Info(title="Data needed for the new portal page",
		  					   version="20180615",
		                       description="Provides data needed by our new \"My Campus\" portal page."))
public class PortalDataController {


	// TODO: use real site id from KVV; I'm using ALP2 for now so that I've got some test data
	private static final String DEFAULT_PORTAL_PROJECT_SITE_ID="20164409-da31-430e-8bce-9f9aaa3f64dc"; 
	
	
	@Autowired
	private ServerConfigurationService serverConfig; 
	
	@Autowired
	private ICalendarService calendarService;
	
	@Autowired
	private IAnnouncementService newsService;
	
	
	private final String getDataSiteId() {
		return serverConfig.getString("fu-berlin-portaldata-site-id", DEFAULT_PORTAL_PROJECT_SITE_ID);		
	}
	
	@ApiOperation(value="Loads the calendar events of the configured portal site, but only those from the start of today until one year into the future.")
	@RequestMapping(value="/events", method=RequestMethod.GET, produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	public List<ExportCalenderEvent> getCalendarEvents(){
		final String siteId = getDataSiteId();
		List<ExportCalenderEvent> calEvents =  calendarService.getUpcomingEventsForSite(siteId);
		Collections.sort(calEvents);
		return calEvents;
	}
	
	@ApiOperation(value="Loads all public announcements made in the configured portal site.")
	@RequestMapping(value="/news", method=RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
	public List<ExportAnnouncements> getAnnouncements(){
		final String siteId = getDataSiteId();
		List<ExportAnnouncements> news = newsService.getAnnouncementsForSite(siteId);	
		Collections.sort(news);
		return news;
	}
	
	@ApiOperation(value="I want to reduce the number of requests as much as possible, so this method will return an object that contains the responses of all the other endpoints.")
	@RequestMapping(value="/all", method=RequestMethod.GET, produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public PortalData getEverything(){
		List<ExportCalenderEvent> events = getCalendarEvents();
		List<ExportAnnouncements> news = getAnnouncements();
		return new PortalData(events,news);
		
	}
	
	
}
