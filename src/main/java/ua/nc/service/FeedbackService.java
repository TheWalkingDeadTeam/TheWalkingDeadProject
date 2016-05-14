package ua.nc.service;

import ua.nc.entity.Application;
import ua.nc.entity.Feedback;
import ua.nc.entity.FeedbackAndSpecialMark;

/**
 * Created by Hlib on 09.05.2016.
 */
public interface FeedbackService {
    boolean saveFeedback(FeedbackAndSpecialMark feedbackAndSpecialMark, Application application);
    Feedback getFeedback(int id);
}
