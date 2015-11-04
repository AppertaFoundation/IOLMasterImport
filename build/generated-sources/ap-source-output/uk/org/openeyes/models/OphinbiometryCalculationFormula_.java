package uk.org.openeyes.models;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import uk.org.openeyes.models.EtOphinbiometryCalculation;
import uk.org.openeyes.models.EtOphinbiometryIolRefValues;
import uk.org.openeyes.models.User;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2015-10-30T15:34:55")
@StaticMetamodel(OphinbiometryCalculationFormula.class)
public class OphinbiometryCalculationFormula_ { 

    public static volatile SingularAttribute<OphinbiometryCalculationFormula, Date> createdDate;
    public static volatile SingularAttribute<OphinbiometryCalculationFormula, Boolean> deleted;
    public static volatile SingularAttribute<OphinbiometryCalculationFormula, User> createdUserId;
    public static volatile SingularAttribute<OphinbiometryCalculationFormula, Date> lastModifiedDate;
    public static volatile SingularAttribute<OphinbiometryCalculationFormula, String> name;
    public static volatile SingularAttribute<OphinbiometryCalculationFormula, Integer> displayOrder;
    public static volatile SingularAttribute<OphinbiometryCalculationFormula, User> lastModifiedUserId;
    public static volatile SingularAttribute<OphinbiometryCalculationFormula, Integer> id;
    public static volatile CollectionAttribute<OphinbiometryCalculationFormula, EtOphinbiometryIolRefValues> etOphinbiometryIolRefValuesCollection;
    public static volatile CollectionAttribute<OphinbiometryCalculationFormula, EtOphinbiometryCalculation> etOphinbiometryCalculationCollection1;
    public static volatile CollectionAttribute<OphinbiometryCalculationFormula, EtOphinbiometryCalculation> etOphinbiometryCalculationCollection;

}