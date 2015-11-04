package uk.org.openeyes.models;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import uk.org.openeyes.models.Disorder;
import uk.org.openeyes.models.SpecialtyType;
import uk.org.openeyes.models.Subspecialty;
import uk.org.openeyes.models.User;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2015-10-30T15:34:55")
@StaticMetamodel(Specialty.class)
public class Specialty_ { 

    public static volatile SingularAttribute<Specialty, Integer> code;
    public static volatile SingularAttribute<Specialty, User> createdUserId;
    public static volatile SingularAttribute<Specialty, Date> lastModifiedDate;
    public static volatile CollectionAttribute<Specialty, Disorder> disorderCollection;
    public static volatile SingularAttribute<Specialty, String> defaultTitle;
    public static volatile SingularAttribute<Specialty, String> abbreviation;
    public static volatile SingularAttribute<Specialty, Boolean> defaultIsSurgeon;
    public static volatile SingularAttribute<Specialty, String> adjective;
    public static volatile SingularAttribute<Specialty, Date> createdDate;
    public static volatile SingularAttribute<Specialty, String> name;
    public static volatile SingularAttribute<Specialty, User> lastModifiedUserId;
    public static volatile SingularAttribute<Specialty, Integer> id;
    public static volatile CollectionAttribute<Specialty, Subspecialty> subspecialtyCollection;
    public static volatile SingularAttribute<Specialty, SpecialtyType> specialtyTypeId;

}