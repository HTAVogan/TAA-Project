/**
 * JBoss, Home of Professional Open Source
 * Copyright Red Hat, Inc., and individual contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package endpoints;

import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import jpa.DAOEvents;
import jpa.DAOLocation;
import jpa.DAOMusic;
import jpa.DAOUser;
import jpa.EntityManagerHelper;
import jpa.Entites.*;

@Path("/status")
public class StatusEndpoint {

    private static final Logger logger = Logger.getLogger(StatusEndpoint.class.getName());
    private static DAOEvents eventsDao = new DAOEvents();
    private static DAOLocation locationDao = new DAOLocation();
    private static DAOMusic musicDao = new DAOMusic();
    private static DAOUser userDao = new DAOUser();
    

	//public void constructShit(En)
	
    @GET
    public Response getStatus() {

        return Response.status(Response.Status.OK).entity("JO").build();
    }
    
    /*
    @GET
    @Path("/test")
    public String helloWorld() {

        return "hello";
    }

    @GET
    @Path("/person")
    @Produces(MediaType.APPLICATION_JSON)
    public Person getPerson() {
    	Person p = new Person();
    	p.setName("test");
    	p.setFirstName("t");
        return p;
    }

    @POST
    @Path("/person")
    @Consumes(MediaType.APPLICATION_JSON)
    public void addPerson(Person p) {
    	System.err.println(p.getName());
    }
    */
    
    @POST
    @Path("/events/create")
    @Consumes(MediaType.APPLICATION_JSON)
    public void createEvents(Events e) {
    	eventsDao.createEvent(e.getTitle(), e.getLocations(), e.getCreator(), e.getUrl(), e.getDate_start(), e.getDate_end());
    }
    
    @GET
    @Path("/events/{id}")
    //@Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public String getEventById(@PathParam("id")String  id) {
    	Events e = eventsDao.getEventById(Long.parseLong(id));
    	String ret = "Events received : id : " + e.getId() + " | title : " + e.getTitle() + " | url : " + e.getUrl();
    	return ret;
    	
    	/*
    	String querystring = "SELECT e FROM Events e WHERE e.ID = :id";
		Query query = manager.createQuery(querystring);
		query.setParameter("id", id);
		if(query.getResultList().isEmpty()) {
			System.err.println("No Events found with id " + id);
			return null;
		}
		else {
			return (Events)query.getResultList().get(0);
		}
		*/
    	
    }
    
    @GET
    @Path("/events/{title}/{url}")
    @Consumes(MediaType.TEXT_PLAIN)
    public Events createSimpleEvent(@PathParam("title") String title, @PathParam("url") String url) {
    	System.out.println("Crezation of simple Events before");
    	return eventsDao.createEvent(title, url);
    }

}

