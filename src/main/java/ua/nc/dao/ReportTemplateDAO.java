package ua.nc.dao;

import ua.nc.dao.exception.DAOException;
import ua.nc.entity.ReportTemplate;

import java.util.List;
import java.util.Map;

/**
 * Created by Rangar on 05.05.2016.
 */
public interface ReportTemplateDAO extends GenericDAO<ReportTemplate, Integer> {

    void delete(ReportTemplate report) throws DAOException;

    List<Map<String, Object>> execute(ReportTemplate report) throws DAOException;

    }
