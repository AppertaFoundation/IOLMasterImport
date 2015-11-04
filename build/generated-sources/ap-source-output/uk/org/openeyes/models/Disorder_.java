package uk.org.openeyes.models;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import uk.org.openeyes.models.Episode;
import uk.org.openeyes.models.Specialty;
import uk.org.openeyes.models.User;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2015-10-30T15:34:55")
@StaticMetamodel(Disorder.class)
public class Disorder_ { 

    public static volatile SingularAttribute<Disorder, Date> createdDate;
    public static volatile SingularAttribute<Disorder, User> createdUserId;
    public static volatile SingularAttribute<Disorder, Date> lastModifiedDate;
    public static volatile CollectionAttribute<Disorder, Episode> episodeCollection;
    public static volatile SingularAttribute<Disorder, Specialty> specialtyId;
    public static volatile SingularAttribute<Disorder, User> lastModifiedUserId;
    public static volatile SingularAttribute<Disorder, Boolean> active;
    public static volatile SingularAttribute<Disorder, String> term;
    public static volatile SingularAttribute<Disorder, Integer> id;
    public static volatile SingularAttribute<Disorder, String> fullySpecifiedName;

}