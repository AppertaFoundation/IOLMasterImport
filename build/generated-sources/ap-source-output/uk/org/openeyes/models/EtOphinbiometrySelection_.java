package uk.org.openeyes.models;

import java.math.BigDecimal;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import uk.org.openeyes.models.Event;
import uk.org.openeyes.models.Eye;
import uk.org.openeyes.models.User;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2015-10-30T15:34:55")
@StaticMetamodel(EtOphinbiometrySelection.class)
public class EtOphinbiometrySelection_ { 

    public static volatile SingularAttribute<EtOphinbiometrySelection, BigDecimal> iolPowerRight;
    public static volatile SingularAttribute<EtOphinbiometrySelection, Integer> lensIdLeft;
    public static volatile SingularAttribute<EtOphinbiometrySelection, Event> eventId;
    public static volatile SingularAttribute<EtOphinbiometrySelection, User> createdUserId;
    public static volatile SingularAttribute<EtOphinbiometrySelection, BigDecimal> predictedRefractionLeft;
    public static volatile SingularAttribute<EtOphinbiometrySelection, Date> lastModifiedDate;
    public static volatile SingularAttribute<EtOphinbiometrySelection, BigDecimal> predictedRefractionRight;
    public static volatile SingularAttribute<EtOphinbiometrySelection, Integer> lensIdRight;
    public static volatile SingularAttribute<EtOphinbiometrySelection, Eye> eyeId;
    public static volatile SingularAttribute<EtOphinbiometrySelection, Date> createdDate;
    public static volatile SingularAttribute<EtOphinbiometrySelection, Boolean> deleted;
    public static volatile SingularAttribute<EtOphinbiometrySelection, BigDecimal> iolPowerLeft;
    public static volatile SingularAttribute<EtOphinbiometrySelection, User> lastModifiedUserId;
    public static volatile SingularAttribute<EtOphinbiometrySelection, Integer> id;

}