package uk.org.openeyes.models;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import uk.org.openeyes.models.ServiceSubspecialtyAssignment;
import uk.org.openeyes.models.Specialty;
import uk.org.openeyes.models.User;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2015-10-30T15:34:55")
@StaticMetamodel(Subspecialty.class)
public class Subspecialty_ { 

    public static volatile SingularAttribute<Subspecialty, Date> createdDate;
    public static volatile SingularAttribute<Subspecialty, User> createdUserId;
    public static volatile SingularAttribute<Subspecialty, Date> lastModifiedDate;
    public static volatile SingularAttribute<Subspecialty, Specialty> specialtyId;
    public static volatile SingularAttribute<Subspecialty, String> name;
    public static volatile SingularAttribute<Subspecialty, User> lastModifiedUserId;
    public static volatile CollectionAttribute<Subspecialty, ServiceSubspecialtyAssignment> serviceSubspecialtyAssignmentCollection;
    public static volatile SingularAttribute<Subspecialty, String> refSpec;
    public static volatile SingularAttribute<Subspecialty, Integer> id;

}