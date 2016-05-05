package ua.nc.dao.postgresql;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;
import ua.nc.dao.AbstractPostgreDAO;
import ua.nc.dao.MailDAO;
import ua.nc.dao.exception.DAOException;
import ua.nc.entity.Mail;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alexander on 22.04.2016.
 */
@Repository
public class PostgreMailDAO extends AbstractPostgreDAO<Mail, Integer> implements MailDAO {

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
    private static final String SQL_GET_ALL = "SELECT * FROM public.email_template";

    /**
     * Notifications for Logger
     */
    private static final String GET_NOTIFICATION = "There is no mail matching id";
    private static final String CREATE_NOTIFICATION = "Mail can't be inserted in db";
    private static final String UPDATE_NOTIFICATION = "Mail can't be updated";
    private static final String DELETE_NOTIFICATION = "Mail can't be deleted";

    private PreparedStatement preparedStatement;
    private ResultSet rs;


    public PostgreMailDAO(Connection connection) {
        super(connection);
    }

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
            preparedStatement = connection.prepareStatement(SQL_FIND_MAIL_BY_ID);
            preparedStatement.setInt(1, id);
            rs = preparedStatement.executeQuery();
            mail = new Mail();
            while (rs.next()) {
                mail.setId(id);
                mail.setBodyTemplate(rs.getString(2));
                mail.setHeadTemplate(rs.getString(3));
            }
        } catch (SQLException e) {
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
            preparedStatement = connection.prepareStatement(SQL_CREATE_MAIL, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, entity.getBodyTemplate());
            preparedStatement.setString(2, entity.getHeadTemplate());
            preparedStatement.executeUpdate();
            rs = preparedStatement.getGeneratedKeys();
            rs.next();
            int id = rs.getInt(1);
            entity.setId(id);
        } catch (SQLException e) {
            LOGGER.error(CREATE_NOTIFICATION, e);
            throw new DAOException(e);
        } finally {
            try {
                if (rs != null) rs.close();
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
            } catch (SQLException e) {
                throw new DAOException(e);
            }
        }
        return entity;
    }

    @Override
    public String getSelectQuery() {
        return null;
    }

    @Override
    public String getCreateQuery() {
        return null;
    }

    @Override
    public String getUpdateQuery() {
        return null;
    }

    @Override
    public String getAllQuery() {
        return null;
    }

    @Override
    protected List<Mail> parseResultSet(ResultSet rs) throws DAOException {
        return null;
    }

    @Override
    protected void prepareStatementForInsert(PreparedStatement statement, Mail object) throws DAOException {

    }

    @Override
    protected void prepareStatementForUpdate(PreparedStatement statement, Mail object) throws DAOException {

    }



    @Override
    public Mail persist(Mail object) throws DAOException {
        return null;
    }

    @Override
    public Mail read(Integer key) throws DAOException {
        return null;
    }

    /**
     * Update mail entity in DataBase with new mail entity template
     *
     * @param entity update
     * @throws DAOException
     */
    @Override
    public void update(Mail entity) throws DAOException {
        try {
            preparedStatement = connection.prepareStatement(SQL_UPDATE_MAIL);
            preparedStatement.setString(1, entity.getBodyTemplate());
            preparedStatement.setString(2, entity.getHeadTemplate());
            preparedStatement.setInt(3, entity.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error(UPDATE_NOTIFICATION, e);
            throw new DAOException(e);
        } finally {
            try {
                if (preparedStatement != null) {
                    preparedStatement.close();
                }

            } catch (SQLException e) {
                throw new DAOException(e);
            }
        }
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
            preparedStatement = connection.prepareStatement(SQL_DELETE_MAIL);
            preparedStatement.setInt(1, mail.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error(DELETE_NOTIFICATION, e);
            throw new DAOException(e);
        } finally {
            try {
                if (preparedStatement != null) {
                    preparedStatement.close();
                }

            } catch (SQLException e) {
                throw new DAOException(e);
            }
        }

    }

    /**
     * Get All mails from db
     *
     * @return
     * @throws DAOException
     */
    @Override
    public List<Mail> getAll() throws DAOException {
        List<Mail> mails = new ArrayList<>();
        try {
            preparedStatement = connection.prepareStatement(SQL_GET_ALL);
            ResultSet rs = preparedStatement.executeQuery();
            Mail mail;
            while (rs.next()) {
                mail = new Mail();
                mail.setId(rs.getInt(1));
                mail.setBodyTemplate(rs.getString(2));
                mail.setHeadTemplate(rs.getString(3));
                mails.add(mail);
            }
        } catch (SQLException e) {
            throw new DAOException();
        } finally {
            try {
                if (rs != null) rs.close();
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
            } catch (SQLException e) {
                throw new DAOException(e);
            }
        }
        return mails;
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
        } catch (SQLException e) {
            throw new DAOException();
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
            } catch (SQLException e) {
                throw new DAOException(e);
            }
        }
        return mails;
    }

}
