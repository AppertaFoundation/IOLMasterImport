package uk.org.openeyes.models;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import uk.org.openeyes.models.Episode;
import uk.org.openeyes.models.User;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2015-09-10T11:27:52")
@StaticMetamodel(EpisodeStatus.class)
public class EpisodeStatus_ { 

    public static volatile SingularAttribute<EpisodeStatus, Date> createdDate;
    public static volatile SingularAttribute<EpisodeStatus, User> createdUserId;
    public static volatile SingularAttribute<EpisodeStatus, Date> lastModifiedDate;
    public static volatile CollectionAttribute<EpisodeStatus, Episode> episodeCollection;
    public static volatile SingularAttribute<EpisodeStatus, String> name;
    public static volatile SingularAttribute<EpisodeStatus, User> lastModifiedUserId;
    public static volatile SingularAttribute<EpisodeStatus, Integer> id;
    public static volatile SingularAttribute<EpisodeStatus, Integer> order;

}