package uk.org.openeyes.models;

import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import uk.org.openeyes.models.EventType;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2015-09-10T11:27:52")
@StaticMetamodel(EventGroup.class)
public class EventGroup_ { 

    public static volatile CollectionAttribute<EventGroup, EventType> eventTypeCollection;
    public static volatile SingularAttribute<EventGroup, String> code;
    public static volatile SingularAttribute<EventGroup, String> name;
    public static volatile SingularAttribute<EventGroup, Integer> id;

}