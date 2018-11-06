package sunsky.dao.master;

import sunsky.entity.User;

import java.util.List;

public interface UserDao {
    public User queryById(Integer id);

    public List<User> queryUsers();
}
