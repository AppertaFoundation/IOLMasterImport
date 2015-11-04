package uk.org.openeyes.models;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import uk.org.openeyes.models.Event;
import uk.org.openeyes.models.EventGroup;
import uk.org.openeyes.models.EventType;
import uk.org.openeyes.models.User;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2015-10-30T15:34:55")
@StaticMetamodel(EventType.class)
public class EventType_ { 

    public static volatile SingularAttribute<EventType, EventGroup> eventGroupId;
    public static volatile CollectionAttribute<EventType, EventType> eventTypeCollection;
    public static volatile SingularAttribute<EventType, User> createdUserId;
    public static volatile CollectionAttribute<EventType, Event> eventCollection;
    public static volatile SingularAttribute<EventType, Date> createdDate;
    public static volatile SingularAttribute<EventType, Date> lastModifiedDate;
    public static volatile SingularAttribute<EventType, Boolean> supportServices;
    public static volatile SingularAttribute<EventType, String> name;
    public static volatile SingularAttribute<EventType, User> lastModifiedUserId;
    public static volatile SingularAttribute<EventType, String> className;
    public static volatile SingularAttribute<EventType, Integer> id;
    public static volatile SingularAttribute<EventType, EventType> parentEventTypeId;

}