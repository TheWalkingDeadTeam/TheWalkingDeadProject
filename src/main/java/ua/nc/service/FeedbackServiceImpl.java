package ua.nc.service;

import org.apache.log4j.Logger;
import org.springframework.security.core.context.SecurityContextHolder;
import ua.nc.dao.*;
import ua.nc.dao.enums.DataBaseType;
import ua.nc.dao.exception.DAOException;
import ua.nc.dao.factory.DAOFactory;
import ua.nc.dao.postgresql.PostgreIntervieweeTableDAO;
import ua.nc.entity.*;
import ua.nc.service.user.UserService;
import ua.nc.service.user.UserServiceImpl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Set;


/**
 * Created by Hlib on 09.05.2016.
 */
public class FeedbackServiceImpl implements FeedbackService {

    private final static Logger LOGGER = Logger.getLogger(FeedbackServiceImpl.class);
    private final static DAOFactory daoFactory  = DAOFactory.getDAOFactory(DataBaseType.POSTGRESQL);
    private final static UserService userService = new UserServiceImpl();
    private final static String ROLE_DEV = "ROLE_DEV";
    private final static String ROLE_BA = "ROLE_BA";
    private final static String ROLE_HR = "ROLE_HR";


    @Override
    public boolean saveFeedback(FeedbackAndSpecialMark feedbackAndSpecialMark, Application application) {
        Connection connection = daoFactory.getConnection();
        FeedbackDAO feedbackDAO = daoFactory.getFeedbackDAO(connection);
        IntervieweeDAO intervieweeDAO = daoFactory.getIntervieweeDAO(connection);
        CESDAO cesDAO = daoFactory.getCESDAO(connection);
        PostgreIntervieweeTableDAO intervieweeTableDAO = new PostgreIntervieweeTableDAO(connection);
        Connection connection1 = daoFactory.getConnection();
        RoleDAO roleDAO = daoFactory.getRoleDAO(connection1);
        Feedback feedback = feedbackAndSpecialMark.getFeedback();
        try {
            connection.setAutoCommit(false);
            Interviewee interviewee = intervieweeDAO.read(application.getId());
            if (interviewee == null) {
                return false;
            }
            Integer feedbackId = null;
            Feedback oldFeedback = null;
            User user = userService.getUser(((UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication()
                    .getPrincipal()).getUsername());
            Set<Role> roles = user.getRoles();
            if (roles.contains(roleDAO.findByName(ROLE_DEV))){
                feedbackId = interviewee.getDevFeedbackID();
            } else if (roles.contains(roleDAO.findByName(ROLE_BA))||roles.contains(roleDAO.findByName(ROLE_HR))) {
                feedbackId = interviewee.getHrFeedbackID();
            }

            if (feedbackId == null) {
                feedback = feedbackDAO.create(feedback);

                if (roles.contains(roleDAO.findByName(ROLE_DEV))) {
                    interviewee.setDevFeedbackID(feedback.getId());
                } else if (roles.contains(roleDAO.findByName(ROLE_BA)) || roles.contains(roleDAO.findByName(ROLE_HR))) {
                    interviewee.setHrFeedbackID(feedback.getId());
                }
                interviewee.setSpecialMark(feedbackAndSpecialMark.getSpecialMark());
                intervieweeDAO.update(interviewee);
            } else {
                oldFeedback = feedbackDAO.read(feedbackId);
                oldFeedback.setScore(feedback.getScore());
                oldFeedback.setComment(feedback.getComment());
                feedbackDAO.update(oldFeedback);
                interviewee.setSpecialMark(feedbackAndSpecialMark.getSpecialMark());
                intervieweeDAO.update(interviewee);
            }
            // vdanchul
            if ((feedbackAndSpecialMark.getSpecialMark() != null) ||
                    ((interviewee.getHrFeedbackID() != null) && (interviewee.getHrFeedbackID() != null)) ){
                CES ces = cesDAO.getCurrentCES();
                intervieweeTableDAO.updateIntervieweeTable(ces.getId(), ces.getQuota());
            }
            connection.commit();
            return true;
        } catch (SQLException ex){
            try {
                LOGGER.warn(ex.getMessage());
                connection.rollback();
            } catch (SQLException ex1){
                LOGGER.warn(ex1.getMessage());
            }
            return false;
        } catch (DAOException ex) {
            LOGGER.warn(ex.getMessage());
            return false;
        } finally {
            daoFactory.putConnection(connection);
            daoFactory.putConnection(connection1);
        }

    }

    @Override
    public Feedback getFeedback(int id) {
        Connection connection = daoFactory.getConnection();
        FeedbackDAO feedbackDAO = daoFactory.getFeedbackDAO(connection);
        RoleDAO roleDAO = daoFactory.getRoleDAO(connection);
        Feedback feedback = null;
        try{
            feedback = feedbackDAO.read(id);
        } catch (DAOException ex){
            LOGGER.warn(ex.getMessage());
        } finally {
            daoFactory.putConnection(connection);
        }
        return feedback;
    }
}
