package ua.nc.dao.postgresql;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;
import ua.nc.dao.MailDAO;
import ua.nc.exception.DAOException;
import ua.nc.pool.ConnectionPool;
import ua.nc.entity.Mail;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alexander on 22.04.2016.
 */
@Repository
public class PostgreMailDAO extends MailDAO {

    private static final Logger LOGGER = Logger.getLogger(PostgreMailDAO.class);

    /*
    SQl Queries
    */
    private static final String SQL_FIND_MAIL_BY_ID = " SELECT * FROM public.email_template WHERE email_template_id = ?";
    private static final String SQL_CREATE_MAIL = "INSERT INTO public.email_template (body_template, head_template) VALUES (?,?)";
    private static final String SQL_UPDATE_MAIL = "UPDATE public.email_template SET body_template = ? , head_template = ? " +
            "WHERE email_template_id = ?";
    private static final String SQL_DELETE_MAIL = "DELETE FROM public.email_template WHERE email_template_id = ?";
    private static final String SQL_GET_EMAILS_BY_HEADER = "SELECT * FROM public.email_template WHERE head_template = ?";

    /**
     * Notifications for Logger
     */
    private static final String GET_NOTIFICATION = "There is no mail matching id";
    private static final String CREATE_NOTIFICATION = "Mail can't be inserted in db";
    private static final String UPDATE_NOTIFICATION = "Mail can't be updated";
    private static final String DELETE_NOTIFICATION = "Mail can't be deleted";

    private Connection connection;
    private PreparedStatement preparedStatement;
    private ResultSet rs;
    /**
     * Retrieves mail by id
     *
     * @param id Mail unique identifier
     * @return mail entity
     * @throws DAOException
     */
    @Override
    public Mail get(Integer id) throws DAOException {
        Mail mail = null;
        try {
            connection = ConnectionPool.getConnection();
            preparedStatement = connection.prepareStatement(SQL_FIND_MAIL_BY_ID);
            preparedStatement.setInt(1, id);
            rs = preparedStatement.executeQuery();
            mail = new Mail();
            while (rs.next()) {
                mail.setBodyTemplate(rs.getString(1));
                mail.setHeadTemplate(rs.getString(2));
            }
        } catch (Exception e) {
            LOGGER.error(GET_NOTIFICATION, e);
            throw new DAOException(e);
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (connection != null) {
                   connection.close();
                }
            } catch (SQLException e) {
                throw new DAOException(e);
            }
        }
        return mail;
    }

    /**
     * Create/Add mail entity to DataBase
     *
     * @param entity mail
     * @return generated key
     * @throws DAOException
     */
    @Override
    public Mail create(Mail entity) throws DAOException {
        try {
            connection = ConnectionPool.getConnection();
            preparedStatement = connection.prepareStatement(SQL_CREATE_MAIL, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, entity.getBodyTemplate());
            preparedStatement.setString(2, entity.getHeadTemplate());
            preparedStatement.executeUpdate();
            rs = preparedStatement.getGeneratedKeys();
            rs.next();
            int id = rs.getInt(1);
            entity.setId(id);
        } catch (Exception e) {
            LOGGER.error(CREATE_NOTIFICATION, e);
            throw new DAOException(e);
        } finally {
            try {
                if (rs != null) rs.close();
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                throw new DAOException(e);
            }
        }
        return entity;
    }

    /**
     * Update mail entity in DataBase with new mail entity template
     *
     * @param entity update
     * @throws DAOException
     */
    @Override
    public Mail update(Mail entity) throws DAOException {
        try {
            connection = ConnectionPool.getConnection();
            preparedStatement = connection.prepareStatement(SQL_UPDATE_MAIL);
            preparedStatement.setString(1, entity.getBodyTemplate());
            preparedStatement.setString(2, entity.getHeadTemplate());
            preparedStatement.setInt(3, entity.getId());
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            LOGGER.error(UPDATE_NOTIFICATION, e);
            throw new DAOException(e);
        } finally {
            try {
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                throw new DAOException(e);
            }
        }
        return entity;
    }


    /**
     * Delete mail by Id from DataBase
     *
     * @param mail to be deleted
     * @throws DAOException
     */
    @Override
    public void delete(Mail mail) throws DAOException {
        try {
            connection = ConnectionPool.getConnection();
            preparedStatement = connection.prepareStatement(SQL_DELETE_MAIL);
            preparedStatement.setInt(1, mail.getId());
            preparedStatement.executeUpdate();
        } catch (Exception e) {
            LOGGER.error(DELETE_NOTIFICATION, e);
            throw new DAOException(e);
        } finally {
            try {
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                throw new DAOException(e);
            }
        }

    }

    /**
     * Get all mails by header
     *
     * @param header
     * @return
     * @throws DAOException
     */
    @Override
    public List<Mail> getByHeader(String header) throws DAOException {
        List<Mail> mails = new ArrayList<>();
        try {
            connection = ConnectionPool.getConnection();
            preparedStatement = connection.prepareStatement("");
            preparedStatement.setString(1, header);
            rs = preparedStatement.executeQuery();
            Mail mail;
            while (rs.next()) {
                mail = new Mail();
                mail.setId(rs.getInt(1));
                mail.setBodyTemplate(rs.getString(2));
                mail.setHeadTemplate(rs.getString(3));
                mails.add(mail);
            }
        } catch (Exception e) {
            throw new DAOException();
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                throw new DAOException(e);
            }
        }
        return mails;
    }

}
