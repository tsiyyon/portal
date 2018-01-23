package me.tzion.portal;

import me.tzion.portal.core.Request;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.Test;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;


public class FetureTest extends JerseyTest {

    @Path("/")
    public static class TestResource {

        @POST
        @Path("maps")
        @Consumes(MediaType.APPLICATION_JSON)
        @Produces(MediaType.APPLICATION_JSON)
        public Response maps(Request<Map<String, Object>> request) {
            return Response.ok().entity(request.get()).build();
        }

        @POST
        @Path("arrays")
        @Consumes(MediaType.APPLICATION_JSON)
        @Produces(MediaType.APPLICATION_JSON)
        public Response arrays(Request<List<Map<String, Object>>> request) {
            return Response.ok().entity(request.get()).build();
        }
    }

    @Override
    protected Application configure() {
        ResourceConfig resourceConfig = new ResourceConfig();
        resourceConfig.register(TestResource.class);
        resourceConfig.register(PortalFeature.class);
        return resourceConfig;
    }

    @Test
    public void should_able_to_use_portal_feature_to_read_json_map() {
        Map<String, Object> entity = new HashMap<>();
        entity.put("test", "test");
        Response response = target("/maps").request().post(Entity.json(entity));
        assertThat(response.getStatus(), is(200));

        Map<String, Object> body = response.readEntity(Map.class);
        assertThat(body.get("test"), is("test"));
    }

    @Test
    public void should_able_to_use_portal_feature_to_read_json_array() {
        List<Map<String, Object>> array = new ArrayList<>();
        array.add(new HashMap<String, Object>() {{
            put("test", "test");
        }});

        Response response = target("/arrays").request().post(Entity.json(array));
        assertThat(response.getStatus(), is(200));
        List<Map<String, Object>> list = response.readEntity(List.class);
        assertThat(list.get(0).get("test"), is("test"));
    }
}
