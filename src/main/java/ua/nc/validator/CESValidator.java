package ua.nc.validator;

import ua.nc.entity.CES;
import ua.nc.service.CESService;
import ua.nc.service.CESServiceImpl;

import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;


public class CESValidator implements Validator {
    CESService cesService = new CESServiceImpl();

    /**
     * Check, if all fields of CES setting is valid (compares dates, checks int values)
     *
     * @param obj CES, that will be validate
     * @return errors, that was found in CES
     */
    @Override
    public Set<ValidationError> validate(Object obj) {
        Set<ValidationError> errors = new HashSet<>();
        CES ces = (CES) obj;
        Calendar cal = Calendar.getInstance();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(ces.getStartRegistrationDate());
        cal.setTime(new Date());
        if ((ces.getYear() == null) || (ces.getYear() >= 9999) || (ces.getYear() != calendar.get(Calendar.YEAR))) {
            errors.add(new ValidationError("CESYear", "Incorrect year"));
        }
        if ((ces.getStartRegistrationDate() == null) || (ces.getEndRegistrationDate() == null)) {
            errors.add(new ValidationError("CESRegistrationDate", "Incorrect registration date"));
        }
        Date today = new Date();
        if ((ces.getStartRegistrationDate().before(new Date())) && ((ces.getStatusId() == null) ||
                (ces.getStatusId() == cesService.PENDING_ID))) {
            errors.add(new ValidationError("CESStartRegistrationDate", "Start registration date is incorrect!"));
        }
        if (!ces.getStartRegistrationDate().before(ces.getEndRegistrationDate())) {
            errors.add(new ValidationError("CESEndRegistrationDate", "Start registration date is incorrect!"));
        }
        if (ces.getStartInterviewingDate() != null) {
            if (!ces.getStartInterviewingDate().before(ces.getEndRegistrationDate())) {
                errors.add(new ValidationError("CESInterviewingDate", "Incorrect start interviewing date"));
            }
            if (ces.getStartInterviewingDate().before(new Date())) {
                errors.add(new ValidationError("CESInterviewingDate", "Incorrect start interviewing date"));
            }
        }
        if ((ces.getReminders() == null) || (ces.getReminders() < 1)) {
            errors.add(new ValidationError("CESReminders", "Incorrect remind time"));
        }
        if ((ces.getQuota() == null) || (ces.getQuota() < 1)) {
            errors.add(new ValidationError("CESQuota", "Incorrect quota"));
        }
        if ((ces.getInterviewTimeForDay() == null) || (ces.getInterviewTimeForDay() < 1)) {
            errors.add(new ValidationError("CESTimeForDay", "Time for day error"));
        }
        if ((ces.getInterviewTimeForPerson() == null) || (ces.getInterviewTimeForPerson() < 1)) {
            errors.add(new ValidationError("CESTimeForPerson", "Time for person error"));
        }
        if ((ces.getEndInterviewingDate() != null) && (!ces.getStartInterviewingDate().before(ces.getEndInterviewingDate()))) {
            errors.add(new ValidationError("CESEndInterviewingDate", "End interviewing date is incorrect!"));
        }
        return errors;
    }
}
