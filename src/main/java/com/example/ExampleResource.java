package com.example;

import com.example.service.RestClientService;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import java.util.function.Consumer;
import java.util.stream.IntStream;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.jboss.logging.Logger;

@Path("/test")
public class ExampleResource {
    private static final Logger LOG = Logger.getLogger(ExampleResource.class);

    @Inject
    @RestClient
    RestClientService restClientService;

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/test/{int}")
    public Uni<String> test(@PathParam("int") Integer integer) {
        return Uni.createFrom().item("end: " + integer);
    }

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/doTest")
    public Multi<String> doTest() {
        return Multi.createFrom()
             .items(IntStream.range(0,300).boxed())
             .onItem()
             .transformToUniAndMerge(
                 num -> {
                     LOG.info( "start: " + num);
                     return restClientService.test( num ).onItem().invoke(LOG::info);
                 }
             );
    }
}