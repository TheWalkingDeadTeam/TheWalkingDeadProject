package ua.nc.service;

import org.apache.log4j.Logger;
import org.springframework.security.core.context.SecurityContextHolder;
import ua.nc.dao.*;
import ua.nc.dao.enums.DataBaseType;
import ua.nc.dao.enums.UserRoles;
import ua.nc.dao.exception.DAOException;
import ua.nc.dao.factory.DAOFactory;
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

    @Override
    public void saveFeedback(Feedback feedback, Application application) {
        Connection connection = daoFactory.getConnection();
        FeedbackDAO feedbackDAO = daoFactory.getFeedbackDAO(connection);
        IntervieweeDAO intervieweeDAO = daoFactory.getIntervieweeDAO(connection);
        Connection connection1 = daoFactory.getConnection();
        RoleDAO roleDAO = daoFactory.getRoleDAO(connection1);
        try {
            connection.setAutoCommit(false);
            Interviewee interviewee = intervieweeDAO.getById(application.getId());

            feedback = feedbackDAO.create(feedback);
            User user = userService.getUser(((UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication()
                    .getPrincipal()).getUsername());
            Set<Role> roles = user.getRoles();
            if (roles.contains(roleDAO.findByName("ROLE_DEV"))){
                interviewee.setDevFeedbackID(feedback.getId());
            } else if (roles.contains(roleDAO.findByName("ROLE_BA"))||roles.contains(roleDAO.findByName("ROLE_HR"))) {
                interviewee.setHrFeedbackID(feedback.getId());
            }
            intervieweeDAO.update(interviewee);
            connection.commit();
        } catch (SQLException ex){
            try {
                LOGGER.warn(ex.getMessage());
                connection.rollback();
            } catch (SQLException ex1){
                LOGGER.warn(ex1.getMessage());
            }
        } catch (DAOException ex) {
            LOGGER.warn(ex.getMessage());

        } finally {
            daoFactory.putConnection(connection);
            daoFactory.putConnection(connection1);
        }

    }
}
