package uk.org.openeyes.models;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import uk.org.openeyes.models.Institution;
import uk.org.openeyes.models.Site;
import uk.org.openeyes.models.User;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2015-09-10T11:27:52")
@StaticMetamodel(ImportSource.class)
public class ImportSource_ { 

    public static volatile CollectionAttribute<ImportSource, Institution> institutionCollection;
    public static volatile SingularAttribute<ImportSource, Date> createdDate;
    public static volatile SingularAttribute<ImportSource, User> createdUserId;
    public static volatile SingularAttribute<ImportSource, Date> lastModifiedDate;
    public static volatile CollectionAttribute<ImportSource, Site> siteCollection;
    public static volatile SingularAttribute<ImportSource, String> name;
    public static volatile SingularAttribute<ImportSource, User> lastModifiedUserId;
    public static volatile SingularAttribute<ImportSource, Integer> id;

}