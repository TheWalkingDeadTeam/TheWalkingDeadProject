package ua.nc.service;

import ua.nc.entity.Application;
import ua.nc.entity.Feedback;

/**
 * Created by Hlib on 09.05.2016.
 */
public interface FeedbackService {
    boolean saveFeedback(Feedback feedback, Application application);
    Feedback getFeedback(int id);
}
