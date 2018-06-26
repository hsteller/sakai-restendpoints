package de.berlin.fu.imp.sakai.direct.service;

import java.util.List;

import de.berlin.fu.imp.sakai.direct.dto.ExportAnnouncements;

public interface IAnnouncementService {
	
	public List<ExportAnnouncements> getAnnouncementsForSite (String siteid);

}
