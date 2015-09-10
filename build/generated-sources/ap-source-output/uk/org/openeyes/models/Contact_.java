package uk.org.openeyes.models;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import uk.org.openeyes.models.ContactLabel;
import uk.org.openeyes.models.Gp;
import uk.org.openeyes.models.Institution;
import uk.org.openeyes.models.Patient;
import uk.org.openeyes.models.Practice;
import uk.org.openeyes.models.Site;
import uk.org.openeyes.models.User;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2015-09-10T11:27:52")
@StaticMetamodel(Contact.class)
public class Contact_ { 

    public static volatile SingularAttribute<Contact, String> lastName;
    public static volatile SingularAttribute<Contact, User> createdUserId;
    public static volatile SingularAttribute<Contact, Date> lastModifiedDate;
    public static volatile CollectionAttribute<Contact, Site> siteCollection;
    public static volatile SingularAttribute<Contact, String> nickName;
    public static volatile CollectionAttribute<Contact, User> userCollection;
    public static volatile CollectionAttribute<Contact, Gp> gpCollection;
    public static volatile CollectionAttribute<Contact, Patient> patientCollection;
    public static volatile CollectionAttribute<Contact, Site> siteCollection1;
    public static volatile SingularAttribute<Contact, String> title;
    public static volatile SingularAttribute<Contact, String> primaryPhone;
    public static volatile SingularAttribute<Contact, String> firstName;
    public static volatile SingularAttribute<Contact, String> qualifications;
    public static volatile CollectionAttribute<Contact, Practice> practiceCollection;
    public static volatile CollectionAttribute<Contact, Institution> institutionCollection;
    public static volatile SingularAttribute<Contact, Date> createdDate;
    public static volatile SingularAttribute<Contact, User> lastModifiedUserId;
    public static volatile SingularAttribute<Contact, Integer> id;
    public static volatile SingularAttribute<Contact, ContactLabel> contactLabelId;

}