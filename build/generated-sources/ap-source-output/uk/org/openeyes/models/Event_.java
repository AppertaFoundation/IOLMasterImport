package uk.org.openeyes.models;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import uk.org.openeyes.models.Episode;
import uk.org.openeyes.models.EtOphinbiometryCalculation;
import uk.org.openeyes.models.EtOphinbiometryIolRefValues;
import uk.org.openeyes.models.EtOphinbiometryMeasurement;
import uk.org.openeyes.models.EtOphinbiometrySelection;
import uk.org.openeyes.models.EventType;
import uk.org.openeyes.models.OphinbiometryImportedEvents;
import uk.org.openeyes.models.User;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2015-10-30T15:34:55")
@StaticMetamodel(Event.class)
public class Event_ { 

    public static volatile SingularAttribute<Event, User> createdUserId;
    public static volatile SingularAttribute<Event, Date> lastModifiedDate;
    public static volatile CollectionAttribute<Event, EtOphinbiometryMeasurement> etOphinbiometryMeasurementCollection;
    public static volatile CollectionAttribute<Event, EtOphinbiometrySelection> etOphinbiometrySelectionCollection;
    public static volatile SingularAttribute<Event, Episode> episodeId;
    public static volatile SingularAttribute<Event, String> deleteReason;
    public static volatile SingularAttribute<Event, EventType> eventTypeId;
    public static volatile CollectionAttribute<Event, OphinbiometryImportedEvents> ophinbiometryImportedEventsCollection;
    public static volatile SingularAttribute<Event, Date> createdDate;
    public static volatile SingularAttribute<Event, Boolean> deleted;
    public static volatile SingularAttribute<Event, Boolean> deletePending;
    public static volatile SingularAttribute<Event, User> lastModifiedUserId;
    public static volatile SingularAttribute<Event, Integer> id;
    public static volatile CollectionAttribute<Event, EtOphinbiometryIolRefValues> etOphinbiometryIolRefValuesCollection;
    public static volatile CollectionAttribute<Event, EtOphinbiometryCalculation> etOphinbiometryCalculationCollection;
    public static volatile SingularAttribute<Event, Date> eventDate;
    public static volatile SingularAttribute<Event, String> info;

}