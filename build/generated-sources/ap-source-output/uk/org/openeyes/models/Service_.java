package uk.org.openeyes.models;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import uk.org.openeyes.models.ServiceSubspecialtyAssignment;
import uk.org.openeyes.models.User;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2015-09-10T11:27:52")
@StaticMetamodel(Service.class)
public class Service_ { 

    public static volatile SingularAttribute<Service, Date> createdDate;
    public static volatile SingularAttribute<Service, User> createdUserId;
    public static volatile SingularAttribute<Service, Date> lastModifiedDate;
    public static volatile SingularAttribute<Service, String> name;
    public static volatile SingularAttribute<Service, User> lastModifiedUserId;
    public static volatile CollectionAttribute<Service, ServiceSubspecialtyAssignment> serviceSubspecialtyAssignmentCollection;
    public static volatile SingularAttribute<Service, Integer> id;

}