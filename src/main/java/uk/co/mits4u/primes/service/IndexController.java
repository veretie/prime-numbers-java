package uk.co.mits4u.primes.service;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;

@RestController
public class IndexController {
    @GetMapping("/")
    public RedirectView redirect(HttpServletRequest request){
        return new RedirectView(request.getRequestURL().toString()+"primes-api/index.html");
    }
}
