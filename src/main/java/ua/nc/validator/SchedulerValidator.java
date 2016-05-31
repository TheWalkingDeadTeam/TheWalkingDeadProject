package ua.nc.validator;

import ua.nc.service.CESService;
import ua.nc.service.CESServiceImpl;

import java.util.*;

/**
 * Created by Alexander on 20.05.2016.
 */
public class SchedulerValidator implements Validator {
    private final static String INTERVIEW_START_TIME = "interviewDateStart";
    private final static String INTERVIEW_END_TIME = "interviewDateEnd";
    private CESService cesService = new CESServiceImpl();

    @Override
    public Set<ValidationError> validate(Object obj) {
        Set<ValidationError> errors = new LinkedHashSet<>();
        Map<String, Object> map = (Map) obj;
        Date interviewStart = (Date) map.get(INTERVIEW_START_TIME);
        List<Date> interviewDates = (List) map.get(INTERVIEW_END_TIME);
        Date endRegistrationDate = cesService.getCurrentCES().getEndRegistrationDate();

        if (interviewStart.before(endRegistrationDate)) {
            errors.add(new ValidationError("interViewStart", "interview start time can't be before endRegistration"));
        }

        if (interviewDates == null || interviewDates.size() <= 1) {
            errors.add(new ValidationError("interviewDate", "Interview date is null or not proper size"));
        } else {
            Date lastInterviewDate = interviewDates.get(interviewDates.size() - 1);
            if (lastInterviewDate.before(interviewStart)) {
                errors.add(new ValidationError("interviewLast", "Last day of interview come after interview start"));
            }
        }
        return errors;
    }
}
