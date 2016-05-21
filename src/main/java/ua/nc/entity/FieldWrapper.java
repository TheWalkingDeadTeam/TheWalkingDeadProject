package ua.nc.entity;

import ua.nc.entity.profile.Field;

import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * Created by Neltarion on 16.05.2016.
 */

/**
 * Iterable wrapper class for a list of Field objects.
 */
public class FieldWrapper implements Iterable<Field>{

    /**
     * List of Field objects
     */
    List<Field> fields;

    public List<Field> getFields() {
        return fields;
    }

    public void setFields(List<Field> fields) {
        this.fields = fields;
    }

    @Override
    public Iterator<Field> iterator() {
        return new FieldsIterator();
    }

    private final class FieldsIterator implements Iterator<Field> {

        private int pos;

        public FieldsIterator() {
            if (!fields.isEmpty() && fields != null) {
                pos = 0;
            }
        }

        @Override
        public boolean hasNext() {
            return !(fields.size() == pos);
        }

        @Override
        public Field next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            return fields.get(pos++);
        }

        @Override
        public void remove() {
        }
    }

}
