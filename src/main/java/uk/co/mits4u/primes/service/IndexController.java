package uk.co.mits4u.primes.service;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;

@RestController
public class IndexController {
    @GetMapping("/")
    public RedirectView redirect(HttpServletRequest request) {

        StringBuilder sb = new StringBuilder();

        if (StringUtils.isNoneEmpty(request.getHeader("X-Forwarded-Host"))) {
            sb.append(request.getHeader("X-Forwarded-Proto"));
            sb.append("://");
            sb.append(request.getHeader("X-Forwarded-Host"));
            sb.append(":");
            sb.append(request.getHeader("X-Forwarded-Port"));
            sb.append("/");
        } else {
            sb.append(request.getRequestURL().toString());
        }

        sb.append("primes-api/index.html");



        return new RedirectView(sb.toString());
    }
}
