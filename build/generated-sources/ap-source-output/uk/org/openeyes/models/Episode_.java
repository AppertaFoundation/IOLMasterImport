package uk.org.openeyes.models;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import uk.org.openeyes.models.Disorder;
import uk.org.openeyes.models.EpisodeStatus;
import uk.org.openeyes.models.Event;
import uk.org.openeyes.models.Eye;
import uk.org.openeyes.models.Firm;
import uk.org.openeyes.models.Patient;
import uk.org.openeyes.models.User;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2015-10-30T15:34:55")
@StaticMetamodel(Episode.class)
public class Episode_ { 

    public static volatile SingularAttribute<Episode, Boolean> legacy;
    public static volatile SingularAttribute<Episode, User> createdUserId;
    public static volatile CollectionAttribute<Episode, Event> eventCollection;
    public static volatile SingularAttribute<Episode, Date> endDate;
    public static volatile SingularAttribute<Episode, Date> lastModifiedDate;
    public static volatile SingularAttribute<Episode, Boolean> supportServices;
    public static volatile SingularAttribute<Episode, Patient> patientId;
    public static volatile SingularAttribute<Episode, Eye> eyeId;
    public static volatile SingularAttribute<Episode, Disorder> disorderId;
    public static volatile SingularAttribute<Episode, Firm> firmId;
    public static volatile SingularAttribute<Episode, Date> createdDate;
    public static volatile SingularAttribute<Episode, Integer> deleted;
    public static volatile SingularAttribute<Episode, User> lastModifiedUserId;
    public static volatile SingularAttribute<Episode, EpisodeStatus> episodeStatusId;
    public static volatile SingularAttribute<Episode, Integer> id;
    public static volatile SingularAttribute<Episode, Date> startDate;

}