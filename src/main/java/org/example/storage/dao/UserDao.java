package org.example.storage.dao;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.model.Entity;
import org.example.model.User;
import org.example.storage.DataSource;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
public class UserDao implements Dao {

    private final DataSource dataSource;

    private static final String USER_TITLE = "user:";

    @Override
    public User save(Entity entity) {
        User user = (User) entity;
        String entityKey = USER_TITLE + user.getId();

        getStorage().put(entityKey, user);
        return user;
    }

    @Override
    public User update(Entity entity) {
        User user = getUserById(((User) entity).getId());

        if (user == null) {
            log.warn("Could not update the user with id {} because it does not exist", ((User) entity).getId());
            return null;
        }

        user.setId(user.getId());
        user.setName(user.getName());
        user.setEmail(user.getEmail());

        return save(user);
    }

    @Override
    public boolean delete(long userId) {
        User user = getUserById(userId);

        if (user == null) {
            log.warn("Could not delete the user with id {} because it does not exist", userId);
            return false;
        }

        getStorage().remove(USER_TITLE + userId);
        return true;
    }

    @Override
    public Map<String, Entity> getStorage() {
        return dataSource.getStorage();
    }

    public User getUserById(long userId) {
        String key = getStorage().keySet()
                .stream()
                .filter((USER_TITLE + userId)::equals)
                .findFirst()
                .orElse(null);

        if (key == null) {
            log.warn("User with id {} not found", userId);
            return null;
        }
        return (User) getStorage().get(key);
    }

    public User getUserByEmail(String email) {
        LinkedList<User> matchingUsers = getStorage().values()
                .stream()
                .filter(User.class::isInstance)
                .filter(user -> email.equals(((User) user).getEmail()))
                .map(User.class::cast)
                .collect(Collectors.toCollection(LinkedList::new));

        return matchingUsers.getFirst();
    }

    public List<User> getUsersByName(String name, int pageSize, int pageNum) {
        List<Entity> matchingUsers = getStorage().values()
                .stream()
                .filter(User.class::isInstance)
                .filter(user -> name.equals(((User) user).getName()))
                .collect(Collectors.toList());

        if (matchingUsers.isEmpty()) {
            log.info("No users with name {} found", name);
            return Collections.emptyList();
        }

        return getUsersPage(pageSize, pageNum, matchingUsers);
    }

    private List<User> getUsersPage(int pageSize, int pageNum, List<Entity> matchingUsers) {
        List<Entity> page = getPage(matchingUsers, pageNum, pageSize);

        return page
                .stream()
                .map(User.class::cast)
                .collect(Collectors.toList());
    }
}
