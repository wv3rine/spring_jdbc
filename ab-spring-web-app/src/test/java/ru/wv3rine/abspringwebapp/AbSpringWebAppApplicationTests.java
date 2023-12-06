package ru.wv3rine.abspringwebapp;

import org.junit.After;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.wv3rine.abspringwebapp.dao.UsersDAO;
import ru.wv3rine.abspringwebapp.models.User;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

// https://sysout.ru/testirovanie-spring-boot-prilozheniya-s-testresttemplate/
// частично отсюда

// тестовое покрытие неполное, но и приложение
// простое
@ComponentScan("ru.wv3rine.abspringwebapp")
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = AbSpringWebAppApplicationTests.class)
class AbSpringWebAppApplicationTests {
    private final TestRestTemplate testRestTemplate;

    @Mock
    private final UsersDAO usersDAO;

    @Autowired
    AbSpringWebAppApplicationTests(TestRestTemplate testRestTemplate, UsersDAO usersDAO) {
        this.testRestTemplate = testRestTemplate;
        this.usersDAO = usersDAO;
    }

    // Какой есть хороший способ очищать БД? Я пока плохо понимаю,
    // какие из них лучше, потому что в интернете просто как будто пишут
    // "этот лучше" без объяснений))
    @After
    public void resetDb() {
        Mockito.reset(usersDAO);
    }

    @Test
    public void whenCreateUser_thenHttpStatusOk() {
        User user = addTestUser("Peer Gynt", "12345");
        ResponseEntity<User> response = testRestTemplate.postForEntity("/users", user, User.class);
        assertThat(response.getStatusCode(), is(HttpStatus.OK));
    }

    @Test
    public void whenGetBadUser_thenHttpStatusNotFound() {
        addTestUser("Peer Gynt", "12345");
        ResponseEntity<User> response = testRestTemplate.exchange("/users/{id}", HttpMethod.GET, null, User.class, 100);
        assertThat(response.getStatusCode(), is(HttpStatus.NOT_FOUND));
    }

    @Test
    public void whenAddUser_thenHttpStatusOk() {
        User user = new User(null, "Peer Gynt", "peergynt", "12345", "urlol");
        ResponseEntity<User> response = testRestTemplate.postForEntity("/users", user, User.class);
        assertThat(response.getStatusCode(), is(HttpStatus.OK));
    }

    @Test
    public void whenBadUserAdd_thenHttpStatusBadRequest() {
        User user = new User(null, "Peer Gynt", "peergynt", null, "urlol");
        ResponseEntity<User> response = testRestTemplate.postForEntity("/users", user, User.class);
        assertThat(response.getStatusCode(), is(HttpStatus.BAD_REQUEST));
    }


    private User addTestUser(String name, String password) {
        User user = new User(null, name, "", password, "");
        usersDAO.save(user);
        return user;
    }

}
