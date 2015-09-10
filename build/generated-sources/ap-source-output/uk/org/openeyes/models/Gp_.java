package uk.org.openeyes.models;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import uk.org.openeyes.models.Contact;
import uk.org.openeyes.models.Patient;
import uk.org.openeyes.models.User;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2015-09-10T11:27:52")
@StaticMetamodel(Gp.class)
public class Gp_ { 

    public static volatile SingularAttribute<Gp, Date> createdDate;
    public static volatile SingularAttribute<Gp, User> createdUserId;
    public static volatile SingularAttribute<Gp, Date> lastModifiedDate;
    public static volatile SingularAttribute<Gp, Contact> contactId;
    public static volatile SingularAttribute<Gp, String> natId;
    public static volatile SingularAttribute<Gp, User> lastModifiedUserId;
    public static volatile SingularAttribute<Gp, String> objProf;
    public static volatile CollectionAttribute<Gp, Patient> patientCollection;
    public static volatile SingularAttribute<Gp, Integer> id;

}