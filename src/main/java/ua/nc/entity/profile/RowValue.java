package ua.nc.entity.profile;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by Rangar on 08.05.2016.
 */
public class RowValue {
    public int userId;
    public Boolean rejected;
    public String name;
    public Map<Integer, Object> fields = new LinkedHashMap<>();
}
