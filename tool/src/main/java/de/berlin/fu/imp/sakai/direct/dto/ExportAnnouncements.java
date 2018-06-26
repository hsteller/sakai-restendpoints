package de.berlin.fu.imp.sakai.direct.dto;

import java.util.Date;
import java.util.Locale;

import org.sakaiproject.announcement.api.AnnouncementMessageHeader;
import org.sakaiproject.message.api.Message;
import org.sakaiproject.message.api.MessageHeader;
import org.sakaiproject.time.api.Time;

import de.berlin.fu.imp.sakai.direct.util.ThreadLocalTimeFormat;
import de.berlin.fu.imp.sakai.direct.util.Util;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

// not much we can do about org.sakaiproject.time.api.Time being deprecated, but still being used by the Sakai API 
@SuppressWarnings("deprecation")
@Data
@ApiModel(description="Contains the properties of a Sakai announcement (message) which we are interested in.")
public class ExportAnnouncements implements Comparable<ExportAnnouncements> {

	public static final ThreadLocalTimeFormat TIME_FORMAT_NEWS_DATE =  new ThreadLocalTimeFormat("yyyy-MM-dd", Locale.GERMAN);

	@ApiModelProperty (notes="Sakai announcement ID because why not")
	private String id;	
	@ApiModelProperty (notes="Date of the sakai announcement")
	private Date date;
	@ApiModelProperty (notes="Date of the sakai announcement formatted as yyyy-MM-dd", example="2018-06-15")
	private String dateFormatted;
	@ApiModelProperty (notes="Content of the sakai announcement")
	private String body;
	@ApiModelProperty (notes="Title of the sakai announcement")
	private String subject;
	
	
	
	
	
	
	public static final ExportAnnouncements createFromSakaiMessage(Message msg) {
		ExportAnnouncements a = new ExportAnnouncements();
		MessageHeader head = msg.getHeader();
		Time time = head.getDate();
		if (time != null) {
			Date sent = new Date(time.getTime());	
			a.setDate(sent);
			a.setDateFormatted(TIME_FORMAT_NEWS_DATE.get().format(sent));
		}
		a.setBody(msg.getBody());
		if (head instanceof AnnouncementMessageHeader) {
			AnnouncementMessageHeader moreInfo = (AnnouncementMessageHeader) head;							
			a.setSubject(moreInfo.getSubject());
		}
		a.setId(msg.getId());
		return a;
	}





	/**
	 * So that Collections.sort puts the most recent announcement first
	 */
	@Override
	public int compareTo(ExportAnnouncements o) {				
		Date od = o!=null?o.getDate():null;
		return Util.compare(od, getDate());
	}

}
