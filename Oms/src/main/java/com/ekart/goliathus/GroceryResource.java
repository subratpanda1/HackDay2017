package com.ekart.goliathus;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;

/**
 * Created by sandesh.kumar on 22/06/17.
 */
@Path("/hack")
public class GroceryResource {

    SessionFactory factory;

    public GroceryResource(SessionFactory factory) {
        this.factory = factory;
    }

    @Path("/store")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public void storeData(String object) {

        JSONObject jsonObject = new JSONObject(object);
        JSONArray jsonArray = jsonObject.getJSONObject("result").getJSONArray("products");
        for(Object node : jsonArray) {
            JSONObject jsonNode = (JSONObject) node;
//            jsonNode.getJSONObject()
        }
        Session session = factory.getCurrentSession();

    }
}
