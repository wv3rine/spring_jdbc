package ru.wv3rine.abspringwebapp.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.data.relational.core.sql.TrueCondition;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.wv3rine.abspringwebapp.exceptions.ResourceNotFoundException;
import ru.wv3rine.abspringwebapp.models.User;
import ru.wv3rine.abspringwebapp.other.UserIdAndLogin;

import java.util.Dictionary;
import java.util.List;
// Вопрос: по sring data jdbc инфы намного меньше, чем на jpa:
// я его зря использую? Лучше jpa?


// Если я использую взаимодействие только с интерфейсами,
// правильно же, если я комментирую только их?
/**
 * Интерфейс для взаимодействия с базой данных пользователей
 * (класс {@link User})
 */
@Repository
public interface UsersDAO extends ListCrudRepository<User, Integer> {
    @Query("SELECT id, login FROM users")
    List<UserIdAndLogin> findIdAndLogins();

    // Есть pageable, но как я понял, он не поддерживает
    // кастомные @Query, так что приходится возвращать именно
    // список. Хотя здесь наверное стоит сделать какую-то свою обертку,
    // чтобы передавать еще количество элементов (чтобы было понятно, сколько страниц),
    // но пока это не нужно
    @Query("SELECT id, login FROM users WHERE id > (:page * :pageSize) ORDER BY id ASC LIMIT :pageSize")
    List<UserIdAndLogin> findIdAndLogins(@Param("pageSize") int pageSize,
                                         @Param("page") int page);
}
