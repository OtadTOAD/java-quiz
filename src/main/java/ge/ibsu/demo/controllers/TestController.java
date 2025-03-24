package ge.ibsu.demo.controllers;

import ge.ibsu.demo.dto.PersonInfo;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/test")
public class TestController {

    @RequestMapping(value = "/hello", method = RequestMethod.GET, produces = {"application/json"})
    public String hello() {
        return "Hello From Server";
    }

    @RequestMapping(value = "/person", method = RequestMethod.POST, produces = {"application/json"})
    public PersonInfo testPost(@RequestBody PersonInfo p) {
        p.setLastName(p.getLastName() + " from server");
        return p;
    }
}
