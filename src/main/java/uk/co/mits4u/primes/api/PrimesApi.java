package uk.co.mits4u.primes.api;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.Collection;

@Api(value = "prime-numbers", description = "Prime numbers service", produces = MediaType.APPLICATION_JSON)
@Path("/")
public interface PrimesApi {

    @ApiOperation(value = "Calculate primes in specified [floor, ceiling] range")
    @GET
    @Path("/primes")
    @Produces(MediaType.APPLICATION_JSON)
    Collection<Integer> getPrimesInRange(@ApiParam(value = "more or equal [default 0]", required = false)
                                         @QueryParam("floor") int floor,
                                         @ApiParam(value = "less or equal", required = true)
                                         @QueryParam("ceiling") int ceiling,
                                         @ApiParam(value = "algorithm", required = false)
                                         @QueryParam("algorithm") @DefaultValue("ONE") AlgorithmName algorithmName);

    @ApiOperation(value = "Check if provided number is prime")
    @GET
    @Path("/{number}/isPrime")
    @Produces(MediaType.APPLICATION_JSON)
    boolean isPrime(@ApiParam(value = "less then 2^31", required = true)
                    @PathParam("number") int numberToCheck,
                    @ApiParam(value = "algorithm", required = false)
                    @QueryParam("algorithm") @DefaultValue("ONE") AlgorithmName algorithmName);


}
