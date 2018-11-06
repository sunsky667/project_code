package sunsky.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import sunsky.entity.Sd;
import sunsky.service.SdService;

import java.util.List;

@Controller
public class SdController {
    @Autowired
    private SdService sdService;

    @RequestMapping("findSds")
    @ResponseBody
    public List<Sd> find(){
        return sdService.findSds();
    }
}
