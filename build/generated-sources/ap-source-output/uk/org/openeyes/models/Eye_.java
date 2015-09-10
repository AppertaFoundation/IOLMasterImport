package uk.org.openeyes.models;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import uk.org.openeyes.models.Episode;
import uk.org.openeyes.models.EtOphinbiometryBiometrydat;
import uk.org.openeyes.models.EtOphinbiometryCalculation;
import uk.org.openeyes.models.EtOphinbiometryMeasurement;
import uk.org.openeyes.models.EtOphinbiometrySelection;
import uk.org.openeyes.models.User;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2015-09-10T11:27:52")
@StaticMetamodel(Eye.class)
public class Eye_ { 

    public static volatile SingularAttribute<Eye, User> createdUserId;
    public static volatile SingularAttribute<Eye, Date> createdDate;
    public static volatile SingularAttribute<Eye, Date> lastModifiedDate;
    public static volatile CollectionAttribute<Eye, EtOphinbiometryBiometrydat> etOphinbiometryBiometrydatCollection;
    public static volatile CollectionAttribute<Eye, Episode> episodeCollection;
    public static volatile CollectionAttribute<Eye, EtOphinbiometryMeasurement> etOphinbiometryMeasurementCollection;
    public static volatile SingularAttribute<Eye, Integer> displayOrder;
    public static volatile SingularAttribute<Eye, String> name;
    public static volatile SingularAttribute<Eye, User> lastModifiedUserId;
    public static volatile CollectionAttribute<Eye, EtOphinbiometrySelection> etOphinbiometrySelectionCollection;
    public static volatile SingularAttribute<Eye, Integer> id;
    public static volatile CollectionAttribute<Eye, EtOphinbiometryCalculation> etOphinbiometryCalculationCollection;

}