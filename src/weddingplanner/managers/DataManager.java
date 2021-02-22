package weddingplanner.managers;

import java.util.List;

/**
 * @author laila-elhattab
 */

public interface DataManager<T> {

    //Read from a file
    public List<T> loadAll();

    //add an object whether its an event or a user
    public void add(T obj) throws Exception;

    //edits an object whether its an event or a user
    public void edit(T obj) throws Exception;

}
