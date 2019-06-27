import org.openhealthtools.mdht.uml.cda.*;
import org.openhealthtools.mdht.uml.cda.ccd.*;
import org.openhealthtools.mdht.uml.cda.util.CDAUtil;
import org.openhealthtools.mdht.uml.hl7.datatypes.*;
import org.openhealthtools.mdht.uml.hl7.vocab.*;

import java.io.File;
import java.io.PrintStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * Created by Error404 Team on 5/20/16.
 * This class can be modified for custom record view.
 */
public class CCDGenerator {
    private ResultSet resultSet;
    private static String pID,fname,lname,DOB,street,postal_code,city,state;
    private static String fileName, timeStampString;


    enum COLUMNS{id, title, language, financial, fname, lname, mname, DOB, street, postal_code, city, state, country_code, drivers_license, ss, occupation,
    phone_home, phone_cell, pharmacy_id, date, sex, referrer, referrerID, email, race, ethnicity, religion, monthly_income,
    homeless, financial_review, pricelevel, regdate, mothersname, guardiansname, allow_imm_reg_use, allow_imm_info_share,
        allow_health_info_ex,allow_patient_portal,deceased_date, deceased_reason, soap_import_status, cmsportal_login, care_team}

    public CCDGenerator(ResultSet resultSet) throws SQLException {
        this.resultSet = resultSet;
        pID = resultSet.getString(COLUMNS.id.toString());
        street = resultSet.getString(COLUMNS.street.toString());
        city = resultSet.getString(COLUMNS.city.toString());
        state = resultSet.getString(COLUMNS.state.toString());
        postal_code = resultSet.getString(COLUMNS.postal_code.toString());
        fname = resultSet.getString(COLUMNS.fname.toString());
        lname = resultSet.getString(COLUMNS.lname.toString());
        DOB = resultSet.getString(COLUMNS.DOB.toString());
        generate();
        allFunctions.setRecordName(fileName);
        allFunctions.setTimeStampString(timeStampString);
    }
    public CCDGenerator(String pID, String fname, String lname, String DOB, String street, String postal_code, String city, String state){
        this.pID = pID;
        this.street = street;
        this.city = city;
        this.state = state;
        this.postal_code = postal_code;
        this.fname = fname;
        this.lname = lname;
        this.DOB = DOB;
        generate();
    }

    private static void generate() {
        // create and initialize an instance of the ContinuityOfCareDocument class
        ContinuityOfCareDocument ccdDocument = CCDFactory.eINSTANCE.createContinuityOfCareDocument().init();

// create a patient role object and add it to the document
        PatientRole patientRole = CDAFactory.eINSTANCE.createPatientRole();
        ccdDocument.addPatientRole(patientRole);
        II id = DatatypesFactory.eINSTANCE.createII();
        patientRole.getIds().add(id);
        id.setRoot(pID);
        id.setExtension("2.16.840.1.113883.19.5");

// create an address object and add it to patient role
        AD addr = DatatypesFactory.eINSTANCE.createAD();
        patientRole.getAddrs().add(addr);
        addr.getUses().add(PostalAddressUse.H);
        addr.addStreetAddressLine(street);
        addr.addCity(city);
        addr.addState(state);
        addr.addPostalCode(postal_code);

// create a patient object and add it to patient role
        Patient patient = CDAFactory.eINSTANCE.createPatient();
        patientRole.setPatient(patient);
        PN name = DatatypesFactory.eINSTANCE.createPN();
        patient.getNames().add(name);
        name.addGiven(fname);
        name.addFamily(lname);

        CE administrativeGenderCode = DatatypesFactory.eINSTANCE.createCE();
        patient.setAdministrativeGenderCode(administrativeGenderCode);
        administrativeGenderCode.setCode("M");
        administrativeGenderCode.setCodeSystem("2.16.840.1.113883.5.1");

        TS birthTime = DatatypesFactory.eINSTANCE.createTS();
        patient.setBirthTime(birthTime);
        birthTime.setValue(DOB);

        // create and initialize the CCD alerts section
        AlertsSection alertsSection = CCDFactory.eINSTANCE.createAlertsSection().init();
        ccdDocument.addSection(alertsSection);

// set up the narrative (human-readable) text portion of the alerts section
        StringBuffer buffer = new StringBuffer();
        buffer.append("<table border=\"1\" width=\"100%\">");
        buffer.append("<thead>");
        buffer.append("<tr>");
        buffer.append("<th>Substance</th>");
        buffer.append("<th>Reaction</th>");
        buffer.append("<th>Status</th>");
        buffer.append("</tr>");
        buffer.append("</thead>");
        buffer.append("<tbody>");
        buffer.append("<tr>");
        buffer.append("<td>Penicillin</td>");
        buffer.append("<td>Hives</td>");
        buffer.append("<td>Active</td>");
        buffer.append("</tr>");
        buffer.append("</tbody>");
        buffer.append("</table>");
        alertsSection.createStrucDocText(buffer.toString());

        // create and initialize a CCD problem act
        ProblemAct problemAct = CCDFactory.eINSTANCE.createProblemAct().init();
        alertsSection.addAct(problemAct);

        id = DatatypesFactory.eINSTANCE.createII();
        problemAct.getIds().add(id);
        id.setRoot(UUID.randomUUID().toString());

        // create and initialize an alert observation within the problem act
        AlertObservation alertObservation = CCDFactory.eINSTANCE.createAlertObservation().init();
        problemAct.addObservation(alertObservation);
        ((EntryRelationship) alertObservation.eContainer()).setTypeCode(x_ActRelationshipEntryRelationship.SUBJ);

        id = DatatypesFactory.eINSTANCE.createII();
        alertObservation.getIds().add(id);
        id.setRoot(UUID.randomUUID().toString());

        CD code = DatatypesFactory.eINSTANCE.createCD();
        alertObservation.setCode(code);
        code.setCode("ASSERTION");
        code.setCodeSystem("2.16.840.1.113883.5.4");

        CS statusCode = DatatypesFactory.eINSTANCE.createCS();
        alertObservation.setStatusCode(statusCode);
        statusCode.setCode("completed");

        CD value = DatatypesFactory.eINSTANCE.createCD();
        alertObservation.getValues().add(value);
        value.setCode("282100009");
        value.setCodeSystem("2.16.840.1.113883.6.96");
        value.setDisplayName("Adverse reaction to substance");

        // playing entity contains coded information on the substance
        Participant2 participant = CDAFactory.eINSTANCE.createParticipant2();
        alertObservation.getParticipants().add(participant);
        participant.setTypeCode(ParticipationType.CSM);

        ParticipantRole participantRole = CDAFactory.eINSTANCE.createParticipantRole();
        participant.setParticipantRole(participantRole);
        participantRole.setClassCode(RoleClassRoot.MANU);

        PlayingEntity playingEntity = CDAFactory.eINSTANCE.createPlayingEntity();
        participantRole.setPlayingEntity(playingEntity);
        playingEntity.setClassCode(EntityClassRoot.MMAT);

        CE playingEntityCode = DatatypesFactory.eINSTANCE.createCE();
        playingEntity.setCode(playingEntityCode);
        playingEntityCode.setCode("70618");
        playingEntityCode.setCodeSystem("2.16.840.1.113883.6.88");
        playingEntityCode.setDisplayName("Penicillin");

        // reaction observation contains coded information on the adverse reaction
        ReactionObservation reactionObservation = CCDFactory.eINSTANCE.createReactionObservation().init();
        alertObservation.addObservation(reactionObservation);
        ((EntryRelationship) reactionObservation.eContainer()).setTypeCode(x_ActRelationshipEntryRelationship.MFST);
        ((EntryRelationship) reactionObservation.eContainer()).setInversionInd(Boolean.TRUE);

        code = DatatypesFactory.eINSTANCE.createCD();
        reactionObservation.setCode(code);
        code.setCode("ASSERTION");
        code.setCodeSystem("2.16.840.1.113883.5.4");

        statusCode = DatatypesFactory.eINSTANCE.createCS();
        reactionObservation.setStatusCode(statusCode);
        statusCode.setCode("completed");

        value = DatatypesFactory.eINSTANCE.createCD();
        reactionObservation.getValues().add(value);
        value.setCode("247472004");
        value.setCodeSystem("2.16.840.1.113883.6.96");
        value.setDisplayName("Hives");

        // alert status contains information about whether allergy is currently active
        AlertStatusObservation alertStatusObservation = CCDFactory.eINSTANCE.createAlertStatusObservation().init();
        alertObservation.addObservation(alertStatusObservation);
        ((EntryRelationship) alertStatusObservation.eContainer()).setTypeCode(x_ActRelationshipEntryRelationship.REFR);

        CE alertStatusObservationValue = DatatypesFactory.eINSTANCE.createCE();
        alertStatusObservation.getValues().add(alertStatusObservationValue);
        alertStatusObservationValue.setCode("55561003");
        alertStatusObservationValue.setCodeSystem("2.16.840.1.113883.6.96");
        alertStatusObservationValue.setDisplayName("Active");


        try {
            long timeStamp = System.currentTimeMillis();
            SimpleDateFormat dateFormat = new SimpleDateFormat("MMddyyyyHHmm");
            timeStampString = dateFormat.format(new Date(timeStamp));
            fileName =timeStampString+".xml";
            CDAUtil.save(ccdDocument, new PrintStream(new File(fileName)));

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static String getFileName() {
        return fileName;
    }

    public static String getTimeStampString() {
        return timeStampString;
    }
}