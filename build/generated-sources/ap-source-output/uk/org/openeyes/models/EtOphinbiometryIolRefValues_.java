package uk.org.openeyes.models;

import java.math.BigDecimal;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import uk.org.openeyes.models.Event;
import uk.org.openeyes.models.Eye;
import uk.org.openeyes.models.OphinbiometryCalculationFormula;
import uk.org.openeyes.models.OphinbiometryLenstypeLens;
import uk.org.openeyes.models.User;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2015-10-30T15:34:55")
@StaticMetamodel(EtOphinbiometryIolRefValues.class)
public class EtOphinbiometryIolRefValues_ { 

    public static volatile SingularAttribute<EtOphinbiometryIolRefValues, Event> eventId;
    public static volatile SingularAttribute<EtOphinbiometryIolRefValues, User> createdUserId;
    public static volatile SingularAttribute<EtOphinbiometryIolRefValues, Date> lastModifiedDate;
    public static volatile SingularAttribute<EtOphinbiometryIolRefValues, BigDecimal> emmetropiaRight;
    public static volatile SingularAttribute<EtOphinbiometryIolRefValues, Eye> eyeId;
    public static volatile SingularAttribute<EtOphinbiometryIolRefValues, String> iolRefValuesLeft;
    public static volatile SingularAttribute<EtOphinbiometryIolRefValues, OphinbiometryCalculationFormula> formulaId;
    public static volatile SingularAttribute<EtOphinbiometryIolRefValues, BigDecimal> emmetropiaLeft;
    public static volatile SingularAttribute<EtOphinbiometryIolRefValues, Date> createdDate;
    public static volatile SingularAttribute<EtOphinbiometryIolRefValues, OphinbiometryLenstypeLens> lensId;
    public static volatile SingularAttribute<EtOphinbiometryIolRefValues, User> lastModifiedUserId;
    public static volatile SingularAttribute<EtOphinbiometryIolRefValues, String> iolRefValuesRight;
    public static volatile SingularAttribute<EtOphinbiometryIolRefValues, Integer> id;

}