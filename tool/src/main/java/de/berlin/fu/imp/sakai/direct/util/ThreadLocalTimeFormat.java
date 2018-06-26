package de.berlin.fu.imp.sakai.direct.util;

import java.text.SimpleDateFormat;
import java.util.Locale;

import lombok.AllArgsConstructor;
import lombok.ToString;


@AllArgsConstructor
@ToString
public class ThreadLocalTimeFormat extends ThreadLocal<SimpleDateFormat>{

	private final String pattern;
	private final Locale locale;
	
	@Override
	 protected SimpleDateFormat initialValue() {
	        return new SimpleDateFormat(pattern,locale);
	  }
	
}
