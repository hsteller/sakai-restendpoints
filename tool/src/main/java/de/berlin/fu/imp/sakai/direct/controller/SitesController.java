package de.berlin.fu.imp.sakai.direct.controller;

import java.util.List;

import org.sakaiproject.site.api.SiteService;
import org.sakaiproject.site.api.SiteService.SelectionType;
import org.sakaiproject.site.api.SiteService.SortType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.Info;
import io.swagger.annotations.SwaggerDefinition;

@RestController
@RequestMapping("/sites")
@Api
@SwaggerDefinition (info=@Info(title="Experimenting with the SiteService",
		  					   version="20180626",
		                       description="Playing around with the siteservice for a bit"))
public class SitesController {

	
	@Autowired
	private SiteService siteService;
	
	@ApiOperation(value="Retrieves all public sites which contain the search parameter in their title, description or skin (whatever that means).")
	@RequestMapping(value="/search", method=RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
	@ResponseBody
	public List<String> getSiteIds(
			@RequestParam (required=true, name="query")
			@ApiParam (required=true,name="query",example="programming", value="Search Sakai for sites with this String in their title or description")
			 String title
			){
				
		List<String> ids = siteService.getSiteIds(SelectionType.PUBVIEW, null,title, null, SortType.TYPE_DESC, null);		
		return ids;
	}
	
}
