package uk.org.openeyes.models;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import uk.org.openeyes.models.Firm;
import uk.org.openeyes.models.Service;
import uk.org.openeyes.models.Subspecialty;
import uk.org.openeyes.models.User;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2015-09-10T11:27:52")
@StaticMetamodel(ServiceSubspecialtyAssignment.class)
public class ServiceSubspecialtyAssignment_ { 

    public static volatile SingularAttribute<ServiceSubspecialtyAssignment, Date> createdDate;
    public static volatile SingularAttribute<ServiceSubspecialtyAssignment, User> createdUserId;
    public static volatile CollectionAttribute<ServiceSubspecialtyAssignment, Firm> firmCollection;
    public static volatile SingularAttribute<ServiceSubspecialtyAssignment, Date> lastModifiedDate;
    public static volatile SingularAttribute<ServiceSubspecialtyAssignment, User> lastModifiedUserId;
    public static volatile SingularAttribute<ServiceSubspecialtyAssignment, Subspecialty> subspecialtyId;
    public static volatile SingularAttribute<ServiceSubspecialtyAssignment, Integer> id;
    public static volatile SingularAttribute<ServiceSubspecialtyAssignment, Service> serviceId;

}