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

    @Override
    public Set<ValidationError> validate(Object obj) {
        IntegerList iList = (IntegerList) obj;
        Set<ValidationError> errors = new LinkedHashSet<>();

        // check if curent CES status allows to enroll on interview
        checkCESStatus(errors);

        if (iList != null && !(iList.getValues().isEmpty())) {
            for (Integer integer: iList.getValues()) {
                if (!(integer instanceof Integer)) {
                    errors.add(new ValidationError("interviewer id", "Id should be an Integer. Please follow default steps to enroll..."));
                }
            }
        } else {
            errors.add(new ValidationError("interviewer id", "Should be at least one interviewer to enroll"));
        }

        return errors;
    }

    private void checkCESStatus(Set<ValidationError> errors) {
        CESService cesService = new CESServiceImpl();
        Integer status = cesService.getCurrentCES().getStatusId();

        switch (status) {
            case 4:
                errors.add(new ValidationError("ces status", "Can't enroll on interview during interviewing period"));
                break;
            case 5:
                errors.add(new ValidationError("ces status", "Can't enroll on interview during post interviewing period"));
                break;
            case 6:
                errors.add(new ValidationError("ces status", "Current enrollment session is closed. Unable to enroll"));
        }

    }

}
