package uk.org.openeyes.models;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import uk.org.openeyes.models.Contact;
import uk.org.openeyes.models.Episode;
import uk.org.openeyes.models.EthnicGroup;
import uk.org.openeyes.models.Gp;
import uk.org.openeyes.models.OphinbiometryImportedEvents;
import uk.org.openeyes.models.Practice;
import uk.org.openeyes.models.User;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2015-10-30T15:34:55")
@StaticMetamodel(Patient.class)
public class Patient_ { 

    public static volatile SingularAttribute<Patient, User> createdUserId;
    public static volatile SingularAttribute<Patient, String> gender;
    public static volatile SingularAttribute<Patient, Date> lastModifiedDate;
    public static volatile SingularAttribute<Patient, Date> dateOfDeath;
    public static volatile SingularAttribute<Patient, Contact> contactId;
    public static volatile SingularAttribute<Patient, Date> noAllergiesDate;
    public static volatile SingularAttribute<Patient, EthnicGroup> ethnicGroupId;
    public static volatile SingularAttribute<Patient, Practice> practiceId;
    public static volatile SingularAttribute<Patient, String> nhsNum;
    public static volatile SingularAttribute<Patient, Date> noRisksDate;
    public static volatile CollectionAttribute<Patient, OphinbiometryImportedEvents> ophinbiometryImportedEventsCollection;
    public static volatile SingularAttribute<Patient, String> hosNum;
    public static volatile SingularAttribute<Patient, Date> createdDate;
    public static volatile SingularAttribute<Patient, Date> noFamilyHistoryDate;
    public static volatile SingularAttribute<Patient, Gp> gpId;
    public static volatile CollectionAttribute<Patient, Episode> episodeCollection;
    public static volatile SingularAttribute<Patient, Integer> pasKey;
    public static volatile SingularAttribute<Patient, Date> dob;
    public static volatile SingularAttribute<Patient, User> lastModifiedUserId;
    public static volatile SingularAttribute<Patient, Integer> id;

}