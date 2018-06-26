package de.berlin.fu.imp.sakai.direct.util;

import org.sakaiproject.authz.api.SecurityAdvisor;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class GodMode implements SecurityAdvisor {

	
	
	
	@Override
	public SecurityAdvice isAllowed(String userId, String function, String reference) {
		if (log.isDebugEnabled()) {
			log.debug("allmighty security advisor grants permission for user: " + userId + "; f(x)=" + function
					+ "; ref=" + reference);
		}
		return SecurityAdvice.ALLOWED;
	}

}
