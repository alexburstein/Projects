package sql_crud;

import java.io.Serializable;

public interface CRUD<K extends Serializable, V extends Serializable> extends AutoCloseable{
    K create(V data);
    V read(K key);
    V update(K key, V newData);
    V delete(K key);
}
