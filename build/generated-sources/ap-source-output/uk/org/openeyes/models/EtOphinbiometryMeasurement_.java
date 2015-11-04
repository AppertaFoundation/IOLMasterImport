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
@StaticMetamodel(EtOphinbiometryMeasurement.class)
public class EtOphinbiometryMeasurement_ { 

    public static volatile SingularAttribute<EtOphinbiometryMeasurement, BigDecimal> k1Left;
    public static volatile SingularAttribute<EtOphinbiometryMeasurement, Event> eventId;
    public static volatile SingularAttribute<EtOphinbiometryMeasurement, User> createdUserId;
    public static volatile SingularAttribute<EtOphinbiometryMeasurement, Date> lastModifiedDate;
    public static volatile SingularAttribute<EtOphinbiometryMeasurement, BigDecimal> axisK1Right;
    public static volatile SingularAttribute<EtOphinbiometryMeasurement, BigDecimal> axialLengthLeft;
    public static volatile SingularAttribute<EtOphinbiometryMeasurement, Eye> eyeId;
    public static volatile SingularAttribute<EtOphinbiometryMeasurement, BigDecimal> axisK1Left;
    public static volatile SingularAttribute<EtOphinbiometryMeasurement, Integer> snrLeft;
    public static volatile SingularAttribute<EtOphinbiometryMeasurement, Date> createdDate;
    public static volatile SingularAttribute<EtOphinbiometryMeasurement, Boolean> deleted;
    public static volatile SingularAttribute<EtOphinbiometryMeasurement, BigDecimal> k2Right;
    public static volatile SingularAttribute<EtOphinbiometryMeasurement, Integer> snrRight;
    public static volatile SingularAttribute<EtOphinbiometryMeasurement, BigDecimal> k2Left;
    public static volatile SingularAttribute<EtOphinbiometryMeasurement, BigDecimal> axialLengthRight;
    public static volatile SingularAttribute<EtOphinbiometryMeasurement, User> lastModifiedUserId;
    public static volatile SingularAttribute<EtOphinbiometryMeasurement, Integer> id;
    public static volatile SingularAttribute<EtOphinbiometryMeasurement, BigDecimal> k1Right;

}