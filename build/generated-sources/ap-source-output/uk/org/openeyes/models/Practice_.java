package uk.org.openeyes.models;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import uk.org.openeyes.models.Contact;
import uk.org.openeyes.models.Patient;
import uk.org.openeyes.models.User;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2015-10-30T15:34:55")
@StaticMetamodel(Practice.class)
public class Practice_ { 

    public static volatile SingularAttribute<Practice, String> code;
    public static volatile SingularAttribute<Practice, Date> createdDate;
    public static volatile SingularAttribute<Practice, User> createdUserId;
    public static volatile SingularAttribute<Practice, String> phone;
    public static volatile SingularAttribute<Practice, Date> lastModifiedDate;
    public static volatile SingularAttribute<Practice, Contact> contactId;
    public static volatile SingularAttribute<Practice, User> lastModifiedUserId;
    public static volatile CollectionAttribute<Practice, Patient> patientCollection;
    public static volatile SingularAttribute<Practice, Integer> id;

}