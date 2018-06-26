package de.berlin.fu.imp.sakai.direct.dto;

import java.util.Date;
import java.util.Locale;

import org.sakaiproject.calendar.api.CalendarEvent;
import org.sakaiproject.time.api.Time;
import org.sakaiproject.time.api.TimeRange;

import de.berlin.fu.imp.sakai.direct.util.ThreadLocalTimeFormat;
import de.berlin.fu.imp.sakai.direct.util.Util;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@SuppressWarnings("deprecation") // there's no alternative yet in the Sakai API for the deprecated "Time"-interface still used by TimeRange
@Data
@ApiModel(description="Contains the properties of a Sakai calendar event which we are interested in.")

public class ExportCalenderEvent implements Comparable<ExportCalenderEvent> {

	public static final ThreadLocalTimeFormat TIME_FORMAT_EVENT_STARTDATE=  new ThreadLocalTimeFormat("dd.MM.yyyy", Locale.GERMAN);
	public static final ThreadLocalTimeFormat TIME_FORMAT_EVENT_STARTTIME=  new ThreadLocalTimeFormat("HH:mm", Locale.GERMAN);

	@ApiModelProperty (notes="Sakai calendar event ID because why not")
	private String id;	
	@ApiModelProperty (notes="Title of the sakai calendar event")
	private String title;
	@ApiModelProperty (notes="Description of the sakai calendar event")
	private String description;
	@ApiModelProperty (notes="Formatted description of the sakai calendar event")  
	private String descriptionFormatted;
	@ApiModelProperty (notes="Location property of the sakai calendar event")
	private String location;
	@ApiModelProperty (notes="Start time of the sakai calendar event")
	private Date start;
	@ApiModelProperty (notes="Start date of the sakai calendar event, formatted as dd.MM.yyyy", example="15.06.2018")
	private String startDateFormatted;
	@ApiModelProperty (notes="Start time of the sakai calendar event, formatted as HH:mm", example="16:45")
	private String startTimeFormatted;
	
	
	
	
	public static final ExportCalenderEvent createFromSakaiEvent(CalendarEvent ce) {
		ExportCalenderEvent e = new ExportCalenderEvent();
		e.setId(ce.getId());
		e.setTitle(ce.getDisplayName());
		e.setDescription(ce.getDescription()); // TODO check which description I actually want
		e.setDescriptionFormatted(ce.getDescriptionFormatted());
		e.setLocation(ce.getLocation());
		TimeRange range = ce.getRange();	
		Time first = range.firstTime();
		if (first != null) {
			final Date d = new Date(first.getTime());
			e.setStart(d);
			e.setStartDateFormatted(TIME_FORMAT_EVENT_STARTDATE.get().format(d));
			e.setStartTimeFormatted(TIME_FORMAT_EVENT_STARTTIME.get().format(d));
		}
		return e;
	}



	/**
	 * So that Collections.sort puts the earliest event first
	 */
	@Override	
	public int compareTo(ExportCalenderEvent o) {
		Date ostart = o!=null?o.getStart():null;
		return Util.compare(getStart(),ostart);
	}
	
}
