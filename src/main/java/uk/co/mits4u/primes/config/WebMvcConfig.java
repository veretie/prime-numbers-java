package uk.co.mits4u.primes.config;

import org.springframework.beans.factory.config.ServiceLocatorFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import uk.co.mits4u.primes.service.PrimeStrategyFactory;

@Configuration
public class WebMvcConfig {

    @Bean
    public ServiceLocatorFactoryBean primeStrategyFactory() {
        ServiceLocatorFactoryBean locatorFactoryBean = new ServiceLocatorFactoryBean();
        locatorFactoryBean.setServiceLocatorInterface(PrimeStrategyFactory.class);
        return locatorFactoryBean;
    }

}


