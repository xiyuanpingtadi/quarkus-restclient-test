package com.example.service;

import io.smallrye.mutiny.Uni;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Path("/test")
@RegisterRestClient(baseUri = "http://127.0.0.1:8080/")
public interface RestClientService {

    @GET
    @Path("/test/{int}")
    @Produces(MediaType.TEXT_PLAIN)
    Uni<String> test(@PathParam("int") Integer s);
}
