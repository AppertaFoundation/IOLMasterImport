package uk.org.openeyes.models;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import uk.org.openeyes.models.Contact;
import uk.org.openeyes.models.User;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2015-10-30T15:34:55")
@StaticMetamodel(ContactLabel.class)
public class ContactLabel_ { 

    public static volatile SingularAttribute<ContactLabel, Date> createdDate;
    public static volatile SingularAttribute<ContactLabel, User> createdUserId;
    public static volatile SingularAttribute<ContactLabel, Date> lastModifiedDate;
    public static volatile CollectionAttribute<ContactLabel, Contact> contactCollection;
    public static volatile SingularAttribute<ContactLabel, String> name;
    public static volatile SingularAttribute<ContactLabel, User> lastModifiedUserId;
    public static volatile SingularAttribute<ContactLabel, Boolean> active;
    public static volatile SingularAttribute<ContactLabel, Integer> id;

}