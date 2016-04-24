package ua.nc.dao.VDanchul.entities;

import java.io.Serializable;

/**
 * Created by Rangar on 24.04.2016.
 */
public interface Identified<PK extends Serializable> {
    public PK getID();
}
