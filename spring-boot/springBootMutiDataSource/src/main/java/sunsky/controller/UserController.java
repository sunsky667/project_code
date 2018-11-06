package sunsky.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import sunsky.entity.User;
import sunsky.service.UserService;

@Controller
@Configuration
@PropertySource(value = "classpath:application.yml")
public class UserController {

    @Value("{server.port}")
    private String port;

    @Autowired
    private UserService userService;

    @RequestMapping("findUserById")
    @ResponseBody
    public User findUserById(Integer id){
        System.out.println("========port========="+port);
        return userService.queryUserById(id);
    }
}
