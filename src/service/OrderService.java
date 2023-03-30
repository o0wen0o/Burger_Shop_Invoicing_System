package service;

import java.util.Map;

/**
 * @author o0wen0o
 * @create 2023-03-07 8:36 AM
 */
public interface OrderService<E> extends Service<E> {
    void create(Map<String, Object> data);
    void update(Map<String, Object> data);
    void delete();
}
