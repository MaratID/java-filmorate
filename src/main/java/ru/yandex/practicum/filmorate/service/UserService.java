package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exeptions.NotFoundExeption;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {
    UserStorage userStorage;

    @Autowired
    public UserService(UserStorage userStorage) {
        this.userStorage = userStorage;
    }

    //добавление в друзья
    public void addFriend(int userId, int friendId) {
        User user = userStorage.findById(userId);
        User friend = userStorage.findById(friendId);
        if (user == null) {
            throw new NotFoundExeption("User not found.");
        }
        if (friend == null) {
            throw new NotFoundExeption("Friend not found.");
        }
        //добавление id друга в список друзей
        user.getFriends().add(friend.getId());
        friend.getFriends().add(user.getId());
    }

    //удаление из друзей
    public void removeFriend(int userId, int friendId) {
        User user = userStorage.findById(userId);
        User friend = userStorage.findById(friendId);
        if (user == null) {
            throw new NotFoundExeption("User not found.");
        }
        if (friend == null) {
            throw new NotFoundExeption("Friend not found.");
        }
        user.getFriends().remove(friend.getId());
        friend.getFriends().remove(user.getId());
    }

    public List<User> getCommonFriends(int userId, int otherId) {
        User user = userStorage.findById(userId);
        User other = userStorage.findById(otherId);
        if (user == null) {
            throw new NotFoundExeption("User not found.");
        }
        if (other == null) {
            throw new NotFoundExeption("Other user not found.");
        }
        List<Integer> commonFriendsIds = new ArrayList<>(user.getFriends().stream().toList());
        commonFriendsIds.retainAll(other.getFriends().stream().toList());

        List<User> commonFriends = commonFriendsIds.stream()
                .map(userStorage::findById)
                .collect(Collectors.toList());
        return commonFriends;
    }

    public List<User> getFriends(int id) {
        User user = userStorage.findById(id);
        if (user == null) {
            throw new NotFoundExeption("User not found.");
        }
        return user.getFriends().stream()
                .map(userStorage::findById)
                .collect(Collectors.toList());
    }
}