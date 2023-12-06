package ru.wv3rine.abspringwebapp.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.wv3rine.abspringwebapp.dao.UsersDAO;
import ru.wv3rine.abspringwebapp.exceptions.NotEnoughArgumentsException;
import ru.wv3rine.abspringwebapp.exceptions.ResourceNotFoundException;
import ru.wv3rine.abspringwebapp.models.User;
import ru.wv3rine.abspringwebapp.other.UserIdAndLogin;

import java.util.Dictionary;
import java.util.List;

/**
 * Класс (с аннотацией {@link Service}) для работы с
 * базой данных для класса {@link User}
 */
@Service
public class UsersService {
    private final UsersDAO usersDAO;

    @Autowired
    public UsersService(UsersDAO usersDAO) {
        this.usersDAO = usersDAO;
    }

    /**
     * Добавление пользователя в базу данных. Пользователь обязательно
     * должен иметь имя и пароль
     * @param user добавляеиый пользователь
     */
    public User addUser(User user) {
        return usersDAO.save(user);
    }

    /**
     * Нахождение пользователя по его id
     * @param id id пользователя
     * @return пользователь с указанным id, если таковой существует
     * (иначе исключение)
     */
    public User getUserById(Integer id) {
        return usersDAO.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found!"));
    }

    /**
     * Обновление пользователя user. Если пользователя с таким id
     * не существует, выбрасывает исключение. Иначе изменяет поля
     * в исходной базе данных у пользователя с таким же id, как у переданного,
     * на новые значения полей
     * @param user пользователь, поля которого нужно передать исходному
     */
    public User updateUserById(User user) {
        if (!usersDAO.existsById(user.id())) {
            throw new ResourceNotFoundException("User not found!");
        }
        return usersDAO.save(user);
    }

    // Здесь везде я заменил name на login, потому что это
    // кажется логичнее

    /**
     * Получение списка пользователей в формате класса с методами
     * getId() и getLogin() ({@link UserIdAndLogin}
     * @return список пользователей {@link UserIdAndLogin}
     */
    public List<UserIdAndLogin> getIdAndLogins() {
        return usersDAO.findIdAndLogins();
    }

    /**
     * Получение {@link Slice} пользователей в формате класса
     * {@link UserIdAndLogin} (где хранятся id и login) на указанной странице
     * @param pageSize размер страницы в списке (максимальное число элементов в одной ячейке {@link Slice})
     * @param page номер страницы
     * @return {@link Slice} пользователей {@link UserIdAndLogin}
     */
    public List<UserIdAndLogin> findIdAndLogins(int pageSize, int page) {
        return usersDAO.findIdAndLogins(pageSize, page);
    }
}
