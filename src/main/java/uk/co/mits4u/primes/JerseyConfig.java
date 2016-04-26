package uk.co.mits4u.primes;

import io.swagger.jaxrs.config.BeanConfig;
import io.swagger.jaxrs.listing.ApiListingResource;
import io.swagger.jaxrs.listing.SwaggerSerializers;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.wadl.internal.WadlResource;
import org.springframework.stereotype.Component;
import uk.co.mits4u.primes.service.PrimesService;

import javax.ws.rs.ApplicationPath;

@Component
@ApplicationPath("/numbers")
public class JerseyConfig extends ResourceConfig {

    public JerseyConfig() {
        registerEndpoints();
        configureSwagger();
    }

    private void configureSwagger() {
        BeanConfig beanConfig = new BeanConfig();
        beanConfig.setVersion("1.0.0");
        beanConfig.setTitle("Primes API");
        beanConfig.setBasePath("/numbers");
        beanConfig.setResourcePackage("uk.co.mits4u.primes");
        beanConfig.setPrettyPrint(true);
        beanConfig.setScan(true);
    }

    private void registerEndpoints() {
        register(ApiListingResource.class);
        register(SwaggerSerializers.class);
        register(WadlResource.class);
        register(PrimesService.class);
    }

}