package ua.nc.service;

import org.apache.log4j.Logger;
import ua.nc.dao.CESDAO;
import ua.nc.dao.FieldDAO;
import ua.nc.dao.ListValueDAO;
import ua.nc.dao.exception.DAOException;
import ua.nc.dao.factory.DAOFactory;
import ua.nc.dao.postgresql.PostgreDAOFactory;
import ua.nc.entity.profile.Field;
import ua.nc.entity.profile.ListValue;

import java.sql.Connection;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Neltarion on 12.05.2016.
 */
public class EditFormServiceImpl implements EditFormService {

    private final static Logger LOGGER = Logger.getLogger(EditFormServiceImpl.class);
    //    private final static DAOFactory daoFactory  = DAOFactory.getDAOFactory(DataBaseType.POSTGRESQL);
    private final DAOFactory daoFactory = new PostgreDAOFactory();

    private final Integer CES_ID = getCES_ID();

    @Override
    public List<Field> getAllFields(Integer ces_id) {
        Connection connection = daoFactory.getConnection();
        FieldDAO fieldDAO = daoFactory.getFieldDAO(connection);
        List<Field> fields = new LinkedList<>();
        try {
            fields.addAll(fieldDAO.getFieldsForCES(ces_id));
        } catch (DAOException e) {
            LOGGER.error(e);
        } finally {
            daoFactory.putConnection(connection);
        }
        return fields;
    }

    @Override
    public List<ListValue> getListValues(Integer listId) {
        Connection connection = daoFactory.getConnection();
        ListValueDAO listValueDAO = daoFactory.getListValueDAO(connection);
        List<ListValue> listValues = new LinkedList<>();
        try {
            listValues.addAll(listValueDAO.getAllListListValue(listId));
        } catch (DAOException e) {
            LOGGER.error(e);
        } finally {
            daoFactory.putConnection(connection);
        }
        return listValues;
    }

    @Override
    public void addNewQuestion(Field field) {
        Connection connection = daoFactory.getConnection();
        Connection connection1 = daoFactory.getConnection();
        FieldDAO fieldDAO = daoFactory.getFieldDAO(connection);
        CESDAO cesDAO = daoFactory.getCESDAO(connection1);
        try {
            Field _field = fieldDAO.create(field);
            cesDAO.addCESField(CES_ID, _field.getId());
        } catch (DAOException e) {
            LOGGER.error(e);
        } finally {
            daoFactory.putConnection(connection);
            daoFactory.putConnection(connection1);
        }
    }

    @Override
    public void deleteQuestionFromCES(Integer ces_id, Integer field_id) {
        Connection connection = daoFactory.getConnection();
        CESDAO cesDAO = daoFactory.getCESDAO(connection);
        try {
            cesDAO.removeCESField(ces_id, field_id);
        } catch (DAOException e) {
            LOGGER.error(e);
        } finally {
            daoFactory.putConnection(connection);
        }
    }

    @Override
    public void updatePosition(Field field) {
        Connection connection = daoFactory.getConnection();
        FieldDAO fieldDAO = daoFactory.getFieldDAO(connection);
        try {
            fieldDAO.update(field);
        } catch (DAOException e) {
            LOGGER.error(e);
        } finally {
            daoFactory.putConnection(connection);
        }
    }

    @Override
    public Integer getCES_ID() {
        Connection connection = daoFactory.getConnection();
        CESDAO cesDAO = daoFactory.getCESDAO(connection);
        try {
            return cesDAO.getCurrentCES().getId();
        } catch (DAOException e) {
            LOGGER.error(e);
        }
        return 1;
    }

}
