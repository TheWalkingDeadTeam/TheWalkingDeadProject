package ua.nc.validator;

import ua.nc.entity.CES;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by IGOR on 16.05.2016.
 */
public class CESValidator implements Validator {
    private static final String DATE_FORMAT = "yyyy-MM-dd";
    private DateFormat dateFormatter = new SimpleDateFormat(DATE_FORMAT);

    @Override
    public Set<ValidationError> validate(Object obj) {
        Set<ValidationError> errors = new HashSet<>();
        CES ces = (CES) obj;
        Calendar cal = Calendar.getInstance();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(ces.getStartRegistrationDate());
        cal.setTime(new Date());
        if ( (ces.getYear() == null) || (ces.getYear() >= 9999) || (ces.getYear() != calendar.get(Calendar.YEAR))){
            errors.add(new ValidationError("CESYear", "Incorrect year"));
        }
        if ((ces.getStartRegistrationDate() == null) || (ces.getEndRegistrationDate() == null)){
            errors.add(new ValidationError("CESRegistrationDate", "Incorrect registration date"));
        }
        Date today = new Date();
        if ((ces.getStartRegistrationDate().before(new Date())) && ((ces.getStatusId() == null) || (ces.getStatusId() == 1))){
            errors.add(new ValidationError("CESStartRegistrationDate", "Start registration date is incorrect!"));
        }
        if ((ces.getStartRegistrationDate().compareTo(ces.getEndRegistrationDate())) >= 0){
            errors.add(new ValidationError("CESEndRegistrationDate", "Start registration date is incorrect!"));
        }
        if (ces.getStartInterviewingDate() != null){
            if ((ces.getStartInterviewingDate().compareTo(ces.getEndRegistrationDate()) != 1)) {
                errors.add(new ValidationError("CESInterviewingDate", "Incorrect start interviewing date"));
            }
            if ((ces.getStartInterviewingDate().compareTo(new Date()) == -1)) {
                errors.add(new ValidationError("CESInterviewingDate", "Incorrect start interviewing date"));
            }
        }
        if ((ces.getReminders() == null)||(ces.getReminders() < 1) ){
            errors.add(new ValidationError("CESReminders", "Incorrect remind time"));
        }
        if ((ces.getQuota() == null) || (ces.getQuota() < 1)){
            errors.add(new ValidationError("CESQuota", "Incorrect quota"));
        }
        if ((ces.getInterviewTimeForDay() == null) || (ces.getInterviewTimeForDay() < 1)){
            errors.add(new ValidationError("CESTimeForDay", "Time for day error"));
        }
        if ((ces.getInterviewTimeForPerson() == null)||(ces.getInterviewTimeForPerson() < 1)){
            errors.add(new ValidationError("CESTimeForPerson", "Time for person error"));
        }
        if ((ces.getEndInterviewingDate() != null) && (ces.getStartInterviewingDate().compareTo(ces.getEndInterviewingDate())) != -1) {
                errors.add(new ValidationError("CESEndInterviewingDate", "End interviewing date is incorrect!"));
        }
        return errors;
    }
}
