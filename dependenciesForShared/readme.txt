Sakai deploys some stuff like Spring and Hibernate into Tomcat's shared
classloader (Sakai 10.x: tomcat/shared/lib, Sakai 11.x: tomcat/lib).
Which means a lot of Spring's "infrastructure" isn't able to see classes
or resources which are deployed as tools/webapps.

This submodule is there to "sakai:deploy" the stuff we want to use and 
which needs to be in "shared" because the rest of Spring is there.



