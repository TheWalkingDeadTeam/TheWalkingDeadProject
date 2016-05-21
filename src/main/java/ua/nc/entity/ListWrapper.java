package ua.nc.entity;

import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * Created by Neltarion on 14.05.2016.
 */

/**
 * Iterable wrapper class for List of Integers
 */
public class ListWrapper implements Iterable<Integer>{

    List<Integer> id;

    public List<Integer> getId() {
        return id;
    }

    public void setId(List<Integer> id) {
        this.id = id;
    }

    @Override
    public Iterator<Integer> iterator() {
        return new ListWrapperIterator();
    }

    private final class ListWrapperIterator implements Iterator<Integer> {

        private int pos;

        public ListWrapperIterator() {
            if (!id.isEmpty() && id != null) {
                pos = 0;
            }
        }

        @Override
        public boolean hasNext() {
            return !(id.size() == pos);
        }

        @Override
        public Integer next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            return id.get(pos++);
        }

        @Override
        public void remove() {
        }
    }
}
