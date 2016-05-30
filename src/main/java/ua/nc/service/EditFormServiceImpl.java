package ua.nc.service;

import org.apache.log4j.Logger;
import ua.nc.dao.CESDAO;
import ua.nc.dao.FieldDAO;
import ua.nc.dao.ListTypeDAO;
import ua.nc.dao.ListValueDAO;
import ua.nc.dao.exception.DAOException;
import ua.nc.dao.factory.DAOFactory;
import ua.nc.dao.postgresql.PostgreDAOFactory;
import ua.nc.entity.FullFieldWrapper;
import ua.nc.entity.OptionWrapper;
import ua.nc.entity.profile.Field;
import ua.nc.entity.profile.ListType;
import ua.nc.entity.profile.ListValue;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Neltarion on 12.05.2016.
 */
public class EditFormServiceImpl implements EditFormService {

    private final static Logger LOGGER = Logger.getLogger(EditFormServiceImpl.class);
    private final DAOFactory DAO_FACTORY = new PostgreDAOFactory();

    @Override
    public List<Field> getAllFields(Integer ces_id) {
        Connection connection = DAO_FACTORY.getConnection();
        FieldDAO fieldDAO = DAO_FACTORY.getFieldDAO(connection);
        List<Field> fields = new LinkedList<>();
        try {
            fields.addAll(fieldDAO.getFieldsForCES(ces_id));
        } catch (DAOException e) {
            LOGGER.error(e);
        } finally {
            DAO_FACTORY.putConnection(connection);
        }
        return fields;
    }

    @Override
    public List<ListValue> getListValues(Integer listId) {
        Connection connection = DAO_FACTORY.getConnection();
        ListValueDAO listValueDAO = DAO_FACTORY.getListValueDAO(connection);
        List<ListValue> listValues = new LinkedList<>();
        try {
            listValues.addAll(listValueDAO.getAllListListValue(listId));
        } catch (DAOException e) {
            LOGGER.error(e);
        } finally {
            DAO_FACTORY.putConnection(connection);
        }
        return listValues;
    }

    @Override
    public void addNewQuestion(FullFieldWrapper field) {
        Connection connection = DAO_FACTORY.getConnection();
        try {
            connection.setAutoCommit(false);
            FieldDAO fieldDAO = DAO_FACTORY.getFieldDAO(connection);
            CESDAO cesDAO = DAO_FACTORY.getCESDAO(connection);
            ListTypeDAO listTypeDAO = DAO_FACTORY.getListTypeDAO(connection);
            ListValueDAO listValueDAO = DAO_FACTORY.getListValueDAO(connection);
            if (field.getListTypeName().isEmpty() || field.getListTypeName() == null) {
                Field newField = fieldDAO.create(new Field(field.getName(), field.getFieldTypeID(), field.isMultipleChoice(), field.getOrderNum(), null));
                cesDAO.addCESField(getCES_ID(), newField.getId());
            } else {
                ListType newListType = listTypeDAO.create(new ListType(field.getListTypeName()));
                for (OptionWrapper ow : field.getInputOptionsFields()) {
                    ListValue newListValue = listValueDAO.create(new ListValue(newListType.getId(), ow.getValue()));
                }
                Field _field = fieldDAO.create(new Field(field.getName(), field.getFieldTypeID(), field.isMultipleChoice(), field.getOrderNum(), newListType.getId()));
                cesDAO.addCESField(getCES_ID(), _field.getId());
            }
            connection.commit();
        } catch (Exception e) {
            try {
                connection.rollback();
            } catch (SQLException exp) {
                LOGGER.error(exp);
            }
            LOGGER.error(e);
        } finally {
            DAO_FACTORY.putConnection(connection);
        }
    }

    @Override
    public void deleteQuestionFromCES(Integer ces_id, Integer field_id) {
        Connection connection = DAO_FACTORY.getConnection();
        CESDAO cesDAO = DAO_FACTORY.getCESDAO(connection);
        try {
            cesDAO.removeCESField(ces_id, field_id);
        } catch (DAOException e) {
            LOGGER.error(e);
        } finally {
            DAO_FACTORY.putConnection(connection);
        }
    }

    @Override
    public void updatePosition(Field field) {
        Connection connection = DAO_FACTORY.getConnection();
        FieldDAO fieldDAO = DAO_FACTORY.getFieldDAO(connection);
        try {
            fieldDAO.update(field);
        } catch (DAOException e) {
            LOGGER.error(e);
        } finally {
            DAO_FACTORY.putConnection(connection);
        }
    }

    @Override
    public Integer getCES_ID() {
        Connection connection = DAO_FACTORY.getConnection();
        CESDAO cesDAO = DAO_FACTORY.getCESDAO(connection);
        try {
            return cesDAO.getCurrentCES().getId();
        } catch (DAOException e) {
            LOGGER.error(e);
        } finally {
            DAO_FACTORY.putConnection(connection);
        }
        return 1;
    }

    public void deleteOption() {
        Connection connection = DAO_FACTORY.getConnection();
        ListValueDAO listValueDAO = DAO_FACTORY.getListValueDAO(connection);
        DAO_FACTORY.putConnection(connection);
    }

    @Override
    public Integer newPositionNumber() {
        List<Field> allFields = getAllFields(getCES_ID());

        Collections.sort(allFields, new Comparator<Field>() {
            @Override
            public int compare(Field o1, Field o2) {
                return o1.getOrderNum() - o2.getOrderNum();
            }
        });

        Integer lastNumber = allFields.get(allFields.size() - 1).getOrderNum();

        return lastNumber + 1;
    }

    @Override
    public Field getField(Integer id) {
        Connection connection = DAO_FACTORY.getConnection();
        FieldDAO fieldDAO = DAO_FACTORY.getFieldDAO(connection);
        Field field = new Field();
        try {
           field = fieldDAO.read(id);
        } catch (DAOException e) {
            LOGGER.error(e);
        } finally {
            DAO_FACTORY.putConnection(connection);
        }
        return field;
    }

}
