package uk.org.openeyes.models;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import uk.org.openeyes.models.User;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2015-09-10T11:27:52")
@StaticMetamodel(OphinbiometryLensPosition.class)
public class OphinbiometryLensPosition_ { 

    public static volatile SingularAttribute<OphinbiometryLensPosition, Date> createdDate;
    public static volatile SingularAttribute<OphinbiometryLensPosition, User> createdUserId;
    public static volatile SingularAttribute<OphinbiometryLensPosition, Date> lastModifiedDate;
    public static volatile SingularAttribute<OphinbiometryLensPosition, String> name;
    public static volatile SingularAttribute<OphinbiometryLensPosition, Boolean> displayOrder;
    public static volatile SingularAttribute<OphinbiometryLensPosition, User> lastModifiedUserId;
    public static volatile SingularAttribute<OphinbiometryLensPosition, Integer> id;

}