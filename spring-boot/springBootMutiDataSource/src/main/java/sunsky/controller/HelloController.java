package sunsky.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * only for first test
 */
@Controller
public class HelloController {

    @Value("${server.port}")
    private String port;

    @RequestMapping("/hello")
    @ResponseBody
    public String helloWord(){
        System.out.println("==========port=========="+port);
        return "hello word";
    }

}
