package uk.org.openeyes.models;

import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import uk.org.openeyes.models.User;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2015-09-10T11:27:52")
@StaticMetamodel(DoctorGrade.class)
public class DoctorGrade_ { 

    public static volatile SingularAttribute<DoctorGrade, String> grade;
    public static volatile CollectionAttribute<DoctorGrade, User> userCollection;
    public static volatile SingularAttribute<DoctorGrade, Integer> displayOrder;
    public static volatile SingularAttribute<DoctorGrade, Integer> id;

}