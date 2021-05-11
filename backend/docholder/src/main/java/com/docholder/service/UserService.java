package com.docholder.service;

import com.docholder.model.User;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

public interface UserService {
    /**
     * Создает нового пользователя
     *
     * @param user - пользователь для создания
     */
    void create(User user);

    /**
     * Возвращает список всех имеющихся пользователей
     *
     * @return список клиентов
     */
    List<User> readAll();

    /**
     * Возвращает клиента по его UID
     *
     * @param uid - UID пользователя
     * @return - объект пользователь с заданным UID
     */
    User read(UUID uid);

    User readByEmail(String email);

    List<User> readByCompany(UUID id);

    User authorization(String email, String password);

    /**
     * Обновляет пользователя с заданным UID,
     * в соответствии с переданным пользователем
     *
     * @param user - пользователь в соответсвии с которым нужно обновить данные
     * @param uid   - uid пользователя которого нужно обновить
     * @return - true если данные были обновлены, иначе false
     */
    boolean update(User user, UUID uid);

    /**
     * Удаляет пользователя с заданным UID
     *
     * @param uid - uid пользователя, которого нужно удалить
     * @return - true если пользователь был удален, иначе false
     */
    boolean delete(UUID uid);

    boolean updateAvatar(UUID id, MultipartFile avatar);

}
