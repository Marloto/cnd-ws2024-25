package de.thi.informatik.cnd;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/example")
public class ExampleController {

    private SomeService myRef;

    public ExampleController(SomeService myRef) {
        this.myRef = myRef;
    }

    @GetMapping("/hello")
    public String hello() {
        return myRef.doSomething();
    }
}
