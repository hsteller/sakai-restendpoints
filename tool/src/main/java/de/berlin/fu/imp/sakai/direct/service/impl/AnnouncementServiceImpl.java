package de.berlin.fu.imp.sakai.direct.service.impl;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.sakaiproject.announcement.api.AnnouncementService;
import org.sakaiproject.message.api.Message;
import org.sakaiproject.message.api.MessageChannel;
import org.sakaiproject.user.api.UserNotDefinedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.berlin.fu.imp.sakai.direct.dto.ExportAnnouncements;
import de.berlin.fu.imp.sakai.direct.service.IAnnouncementService;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class AnnouncementServiceImpl implements IAnnouncementService{
	
	@Autowired
	private AnnouncementService sakaiAnnouncementService;
	
	
	
	@Override
	public List<ExportAnnouncements> getAnnouncementsForSite (String siteid){
		List<Message> news = getAnnouncements(siteid, false);
		return news.stream().map(ExportAnnouncements::createFromSakaiMessage).collect(Collectors.toList());
	}
	
	
	

	@SuppressWarnings({ "unchecked", "unused" })	
	// unchecked: 
	// unused: needed to add some dead code to catch an Exception the Sakai API throws, but doesn't declare..(
	// ah, the fun of interfaces and code injection)
	private  List<Message> getAnnouncements(String siteID, boolean oldestFirst) {
		// implementation taken from CourseServiceImpl (where it doesn't really belong..)
		try {		
			String ref = sakaiAnnouncementService.channelReference(siteID, "main");
			@SuppressWarnings("rawtypes")
			//API doesn't tell what kind of MessageChannel it returns
			MessageChannel chan = sakaiAnnouncementService.getChannelPublic(ref);				
			if (chan != null){
				List<Message> pubMess = null;
				try {
					if (chan.allowGetMessages()) {
						log.debug("getting all messages for "+siteID);
						pubMess = chan.getMessages(null, oldestFirst);
					}
					else {
						log.debug("falling back to getting only the public messages for "+siteID);
						pubMess = chan.getMessagesPublic(null, oldestFirst);
						if (false) { // otherwise the compiler won't accept the catch block below 
									 // because the "Exception is never thrown" - except it actually is, 
									 // it's just not declared by "getMessagesPublic()"..						
							throw new UserNotDefinedException(null);
						}
					}
				}
				catch (UserNotDefinedException unde) {
					log.debug("site ID: "+siteID);
					log.debug("UserNotDefindedException: "+unde.getMessage());
				}
				
				if (pubMess != null) {
					return pubMess;
				}						
			}
		}
		catch (Exception e){
			log.error(e.getMessage(), e);
		}
		return Collections.emptyList();
	}
}
