package com.sunsky.schedual;

import org.springframework.stereotype.Component;

@Component
public class SchedualServiceHiImpl implements SchedualServiceHi {
    public String sayHiFromClientOne(String name) {
        return "say Hi ERROR";
    }

    public String findUserById(Integer id) {
        return "query user id ERROR";
    }
}
