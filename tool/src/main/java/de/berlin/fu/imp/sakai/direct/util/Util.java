package de.berlin.fu.imp.sakai.direct.util;

import java.text.Collator;

import lombok.extern.slf4j.Slf4j;


@Slf4j
public class Util {

	public static final <T extends Comparable<T>> int compare(T o1,T o2) {
		if (o1==o2) { // catches both objects being null - among other things
			return 0;
		}
		else if (o1 != null) {
			if (o2 != null) { // java.util.Date throws a NullPointer when doing "date.compareTo(null) :-(
				if (o1 instanceof String && o2 instanceof String){
					Collator coll = Collator.getInstance();					
					return coll.compare((String)o1, (String)o2);				
					//return String.CASE_INSENSITIVE_ORDER.compare((String)o1, (String) o2);						
				}
				return o1.compareTo(o2);
			}
			else {
				return 1; // o1!=null && o2==null
			}
		}
		else if (o2 != null) { // o1==null && o2!=null
			return -1;
		}
		else { // can't happen (famous last words)
			log.debug("how interesting; two null values which haven't been equal...");
			return 0;
		}
	}
	
}
