package uk.org.openeyes.models;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import uk.org.openeyes.models.Contact;
import uk.org.openeyes.models.ImportSource;
import uk.org.openeyes.models.Site;
import uk.org.openeyes.models.User;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2015-09-10T11:27:52")
@StaticMetamodel(Institution.class)
public class Institution_ { 

    public static volatile SingularAttribute<Institution, ImportSource> sourceId;
    public static volatile SingularAttribute<Institution, User> createdUserId;
    public static volatile SingularAttribute<Institution, Date> createdDate;
    public static volatile SingularAttribute<Institution, Date> lastModifiedDate;
    public static volatile SingularAttribute<Institution, Contact> contactId;
    public static volatile CollectionAttribute<Institution, Site> siteCollection;
    public static volatile SingularAttribute<Institution, String> name;
    public static volatile SingularAttribute<Institution, User> lastModifiedUserId;
    public static volatile SingularAttribute<Institution, Boolean> active;
    public static volatile SingularAttribute<Institution, Integer> id;
    public static volatile SingularAttribute<Institution, String> shortName;
    public static volatile SingularAttribute<Institution, String> remoteId;

}