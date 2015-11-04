package uk.org.openeyes.models;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import uk.org.openeyes.models.Contact;
import uk.org.openeyes.models.ImportSource;
import uk.org.openeyes.models.Institution;
import uk.org.openeyes.models.User;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2015-10-30T15:34:55")
@StaticMetamodel(Site.class)
public class Site_ { 

    public static volatile SingularAttribute<Site, ImportSource> sourceId;
    public static volatile SingularAttribute<Site, User> createdUserId;
    public static volatile SingularAttribute<Site, Contact> replytoContactId;
    public static volatile SingularAttribute<Site, Institution> institutionId;
    public static volatile SingularAttribute<Site, Date> lastModifiedDate;
    public static volatile SingularAttribute<Site, Contact> contactId;
    public static volatile CollectionAttribute<Site, User> userCollection;
    public static volatile SingularAttribute<Site, Boolean> active;
    public static volatile SingularAttribute<Site, String> telephone;
    public static volatile SingularAttribute<Site, String> remoteId;
    public static volatile SingularAttribute<Site, Date> createdDate;
    public static volatile SingularAttribute<Site, String> name;
    public static volatile SingularAttribute<Site, User> lastModifiedUserId;
    public static volatile SingularAttribute<Site, String> location;
    public static volatile SingularAttribute<Site, Integer> id;
    public static volatile SingularAttribute<Site, String> shortName;
    public static volatile SingularAttribute<Site, String> fax;

}