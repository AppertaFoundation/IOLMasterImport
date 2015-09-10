package uk.org.openeyes.models;

import java.math.BigDecimal;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import uk.org.openeyes.models.Event;
import uk.org.openeyes.models.Eye;
import uk.org.openeyes.models.OphinbiometryCalculationFormula;
import uk.org.openeyes.models.User;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2015-09-10T11:27:52")
@StaticMetamodel(EtOphinbiometryCalculation.class)
public class EtOphinbiometryCalculation_ { 

    public static volatile SingularAttribute<EtOphinbiometryCalculation, OphinbiometryCalculationFormula> formulaIdRight;
    public static volatile SingularAttribute<EtOphinbiometryCalculation, Event> eventId;
    public static volatile SingularAttribute<EtOphinbiometryCalculation, User> createdUserId;
    public static volatile SingularAttribute<EtOphinbiometryCalculation, Date> createdDate;
    public static volatile SingularAttribute<EtOphinbiometryCalculation, Boolean> deleted;
    public static volatile SingularAttribute<EtOphinbiometryCalculation, BigDecimal> targetRefractionRight;
    public static volatile SingularAttribute<EtOphinbiometryCalculation, Date> lastModifiedDate;
    public static volatile SingularAttribute<EtOphinbiometryCalculation, BigDecimal> targetRefractionLeft;
    public static volatile SingularAttribute<EtOphinbiometryCalculation, User> lastModifiedUserId;
    public static volatile SingularAttribute<EtOphinbiometryCalculation, OphinbiometryCalculationFormula> formulaIdLeft;
    public static volatile SingularAttribute<EtOphinbiometryCalculation, Eye> eyeId;
    public static volatile SingularAttribute<EtOphinbiometryCalculation, Integer> id;

}