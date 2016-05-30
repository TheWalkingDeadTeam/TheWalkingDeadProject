package ua.nc.validator;

import ua.nc.entity.IntegerList;
import ua.nc.service.CESService;
import ua.nc.service.CESServiceImpl;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Created by Neltarion on 30.05.2016.
 */
public class InterviewEnrollValidator implements Validator {

    private static final int INTERVIEWING_ONGOING_ID = 4;
    private static final int POST_INTERVIEWING_ID = 5;
    private static final int CLOSED_ID = 6;

    @Override
    public Set<ValidationError> validate(Object obj) {
        IntegerList iList = (IntegerList) obj;
        Set<ValidationError> errors = new LinkedHashSet<>();

        // check if current CES status allows to enroll on interview
        checkCESStatus(errors);

        if (iList != null && !(iList.getValues().isEmpty())) {
            for (Integer integer: iList.getValues()) {
                if (integer == null) {
                    errors.add(new ValidationError("interviewer id", "Id should be an Integer. Please follow default steps to enroll..."));
                }
            }
        } else {
            errors.add(new ValidationError("interviewer id", "Should be at least one interviewer to enroll"));
        }

        return errors;
    }

    private static void checkCESStatus(Set<ValidationError> errors) {
        CESService cesService = new CESServiceImpl();
        Integer status = cesService.getCurrentCES().getStatusId();

        switch (status) {
            case INTERVIEWING_ONGOING_ID:
                errors.add(new ValidationError("ces status", "Can't enroll on interview during interviewing period"));
                break;
            case POST_INTERVIEWING_ID:
                errors.add(new ValidationError("ces status", "Can't enroll on interview during post interviewing period"));
                break;
            case CLOSED_ID:
                errors.add(new ValidationError("ces status", "Current enrollment session is closed. Unable to enroll"));
                break;
            default:
                break;
        }

    }

}
