package sunsky.service.impl;

import sunsky.dao.master.UserDao;
import sunsky.entity.User;
import sunsky.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserDao userDao;

    public User queryUserById(Integer id){
        User user = userDao.queryById(id);
        return user;
    }

    public List<User> queryUsers() {
        List<User> users = userDao.queryUsers();
        return users;
    }
}
