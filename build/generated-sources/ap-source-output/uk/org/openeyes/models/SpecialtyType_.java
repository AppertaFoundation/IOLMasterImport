package uk.org.openeyes.models;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import uk.org.openeyes.models.Specialty;
import uk.org.openeyes.models.User;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2015-09-10T11:27:52")
@StaticMetamodel(SpecialtyType.class)
public class SpecialtyType_ { 

    public static volatile SingularAttribute<SpecialtyType, Date> createdDate;
    public static volatile SingularAttribute<SpecialtyType, User> createdUserId;
    public static volatile SingularAttribute<SpecialtyType, Date> lastModifiedDate;
    public static volatile SingularAttribute<SpecialtyType, String> name;
    public static volatile SingularAttribute<SpecialtyType, Integer> displayOrder;
    public static volatile SingularAttribute<SpecialtyType, User> lastModifiedUserId;
    public static volatile SingularAttribute<SpecialtyType, Boolean> active;
    public static volatile SingularAttribute<SpecialtyType, Integer> id;
    public static volatile CollectionAttribute<SpecialtyType, Specialty> specialtyCollection;

}