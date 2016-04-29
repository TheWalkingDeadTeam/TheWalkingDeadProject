package ua.nc.entity;

import java.io.Serializable;

/**
 * Created by Rangar on 24.04.2016.
 */
public interface Identified<PK extends Serializable> {
    public PK getID();
}
