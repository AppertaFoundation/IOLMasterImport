package uk.org.openeyes.models;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import uk.org.openeyes.models.Patient;
import uk.org.openeyes.models.User;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2015-09-10T11:27:52")
@StaticMetamodel(EthnicGroup.class)
public class EthnicGroup_ { 

    public static volatile SingularAttribute<EthnicGroup, String> code;
    public static volatile SingularAttribute<EthnicGroup, Date> createdDate;
    public static volatile SingularAttribute<EthnicGroup, User> createdUserId;
    public static volatile SingularAttribute<EthnicGroup, Date> lastModifiedDate;
    public static volatile SingularAttribute<EthnicGroup, String> name;
    public static volatile SingularAttribute<EthnicGroup, Integer> displayOrder;
    public static volatile SingularAttribute<EthnicGroup, User> lastModifiedUserId;
    public static volatile CollectionAttribute<EthnicGroup, Patient> patientCollection;
    public static volatile SingularAttribute<EthnicGroup, Integer> id;

}