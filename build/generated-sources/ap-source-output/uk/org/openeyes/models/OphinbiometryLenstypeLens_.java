package uk.org.openeyes.models;

import java.math.BigDecimal;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import uk.org.openeyes.models.EtOphinbiometryIolRefValues;
import uk.org.openeyes.models.User;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2015-10-30T15:34:55")
@StaticMetamodel(OphinbiometryLenstypeLens.class)
public class OphinbiometryLenstypeLens_ { 

    public static volatile SingularAttribute<OphinbiometryLenstypeLens, Float> pACD;
    public static volatile SingularAttribute<OphinbiometryLenstypeLens, String> comments;
    public static volatile SingularAttribute<OphinbiometryLenstypeLens, User> createdUserId;
    public static volatile SingularAttribute<OphinbiometryLenstypeLens, Date> lastModifiedDate;
    public static volatile SingularAttribute<OphinbiometryLenstypeLens, Integer> displayOrder;
    public static volatile SingularAttribute<OphinbiometryLenstypeLens, String> description;
    public static volatile SingularAttribute<OphinbiometryLenstypeLens, Boolean> active;
    public static volatile SingularAttribute<OphinbiometryLenstypeLens, BigDecimal> acon;
    public static volatile SingularAttribute<OphinbiometryLenstypeLens, Float> a0;
    public static volatile SingularAttribute<OphinbiometryLenstypeLens, Float> a1;
    public static volatile SingularAttribute<OphinbiometryLenstypeLens, Float> a2;
    public static volatile SingularAttribute<OphinbiometryLenstypeLens, Date> createdDate;
    public static volatile SingularAttribute<OphinbiometryLenstypeLens, Boolean> deleted;
    public static volatile SingularAttribute<OphinbiometryLenstypeLens, Float> sf;
    public static volatile SingularAttribute<OphinbiometryLenstypeLens, Integer> positionId;
    public static volatile SingularAttribute<OphinbiometryLenstypeLens, String> name;
    public static volatile SingularAttribute<OphinbiometryLenstypeLens, User> lastModifiedUserId;
    public static volatile SingularAttribute<OphinbiometryLenstypeLens, Integer> id;
    public static volatile CollectionAttribute<OphinbiometryLenstypeLens, EtOphinbiometryIolRefValues> etOphinbiometryIolRefValuesCollection;

}