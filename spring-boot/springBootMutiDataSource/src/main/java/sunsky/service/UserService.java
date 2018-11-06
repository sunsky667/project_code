package sunsky.service;

import sunsky.entity.User;

import java.util.List;

public interface UserService {
    public User queryUserById(Integer id);
    public List<User> queryUsers();
}
