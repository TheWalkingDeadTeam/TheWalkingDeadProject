package ua.nc.validator;

import ua.nc.entity.Feedback;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Hlib on 10.05.2016.
 */
public class FeedbackValidator implements Validator{
    @Override
    public Set<ValidationError> validate(Object obj) {
        Set<ValidationError> errors = new HashSet<>();
        Feedback feedback = (Feedback) obj;
        if(feedback.getScore() < 1 || feedback.getScore()>100){
            ValidationError error = new ValidationError("feedback","Score must be 1-100");
            errors.add(error);
        }
        if (feedback.getComment() == null || feedback.getComment().isEmpty()){
            ValidationError error = new ValidationError("feedback","Comment is empty");
            errors.add(error);
        }
        return errors;
    }
}
