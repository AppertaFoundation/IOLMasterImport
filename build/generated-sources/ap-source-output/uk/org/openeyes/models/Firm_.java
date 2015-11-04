package uk.org.openeyes.models;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import uk.org.openeyes.models.Episode;
import uk.org.openeyes.models.ServiceSubspecialtyAssignment;
import uk.org.openeyes.models.User;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2015-10-30T15:34:55")
@StaticMetamodel(Firm.class)
public class Firm_ { 

    public static volatile SingularAttribute<Firm, User> createdUserId;
    public static volatile SingularAttribute<Firm, Date> createdDate;
    public static volatile SingularAttribute<Firm, Date> lastModifiedDate;
    public static volatile CollectionAttribute<Firm, Episode> episodeCollection;
    public static volatile SingularAttribute<Firm, User> consultantId;
    public static volatile SingularAttribute<Firm, ServiceSubspecialtyAssignment> serviceSubspecialtyAssignmentId;
    public static volatile CollectionAttribute<Firm, User> userCollection;
    public static volatile SingularAttribute<Firm, String> name;
    public static volatile SingularAttribute<Firm, User> lastModifiedUserId;
    public static volatile SingularAttribute<Firm, Boolean> active;
    public static volatile SingularAttribute<Firm, Integer> id;
    public static volatile SingularAttribute<Firm, String> pasCode;

}