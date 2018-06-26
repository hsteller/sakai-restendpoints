package de.berlin.fu.imp.sakai.direct.dto;

import java.util.List;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor

@ApiModel(description="Aggregator of all the data the portal is interested in; to make it possible to load all the data needed by the portal in a single request.")
public class PortalData {

	private List<ExportCalenderEvent> calendarEvents;
	private List<ExportAnnouncements> announcements;
	
}
