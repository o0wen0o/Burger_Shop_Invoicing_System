package service;

import java.util.List;

/**
 * @author o0wen0o
 * @create 2023-03-25 10:27 AM
 */
public interface Service<E> {
    List<E> getList();
    void showList();
    void saveFile();
}
