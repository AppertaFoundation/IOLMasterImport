package uk.org.openeyes.models;

import java.math.BigDecimal;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import uk.org.openeyes.models.Event;
import uk.org.openeyes.models.Eye;
import uk.org.openeyes.models.User;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2015-09-10T11:27:52")
@StaticMetamodel(EtOphinbiometryBiometrydat.class)
public class EtOphinbiometryBiometrydat_ { 

    public static volatile SingularAttribute<EtOphinbiometryBiometrydat, Event> eventId;
    public static volatile SingularAttribute<EtOphinbiometryBiometrydat, User> createdUserId;
    public static volatile SingularAttribute<EtOphinbiometryBiometrydat, BigDecimal> r1Right;
    public static volatile SingularAttribute<EtOphinbiometryBiometrydat, Date> lastModifiedDate;
    public static volatile SingularAttribute<EtOphinbiometryBiometrydat, Integer> r2AxisRight;
    public static volatile SingularAttribute<EtOphinbiometryBiometrydat, Integer> r2AxisLeft;
    public static volatile SingularAttribute<EtOphinbiometryBiometrydat, BigDecimal> axialLengthLeft;
    public static volatile SingularAttribute<EtOphinbiometryBiometrydat, BigDecimal> scleralThicknessRight;
    public static volatile SingularAttribute<EtOphinbiometryBiometrydat, BigDecimal> r2Left;
    public static volatile SingularAttribute<EtOphinbiometryBiometrydat, Eye> eyeId;
    public static volatile SingularAttribute<EtOphinbiometryBiometrydat, Integer> r1AxisLeft;
    public static volatile SingularAttribute<EtOphinbiometryBiometrydat, BigDecimal> acdRight;
    public static volatile SingularAttribute<EtOphinbiometryBiometrydat, Integer> r1AxisRight;
    public static volatile SingularAttribute<EtOphinbiometryBiometrydat, BigDecimal> r2Right;
    public static volatile SingularAttribute<EtOphinbiometryBiometrydat, BigDecimal> snrLeft;
    public static volatile SingularAttribute<EtOphinbiometryBiometrydat, BigDecimal> scleralThicknessLeft;
    public static volatile SingularAttribute<EtOphinbiometryBiometrydat, Date> createdDate;
    public static volatile SingularAttribute<EtOphinbiometryBiometrydat, Boolean> deleted;
    public static volatile SingularAttribute<EtOphinbiometryBiometrydat, BigDecimal> snrRight;
    public static volatile SingularAttribute<EtOphinbiometryBiometrydat, BigDecimal> axialLengthRight;
    public static volatile SingularAttribute<EtOphinbiometryBiometrydat, User> lastModifiedUserId;
    public static volatile SingularAttribute<EtOphinbiometryBiometrydat, BigDecimal> acdLeft;
    public static volatile SingularAttribute<EtOphinbiometryBiometrydat, BigDecimal> r1Left;
    public static volatile SingularAttribute<EtOphinbiometryBiometrydat, Integer> id;

}