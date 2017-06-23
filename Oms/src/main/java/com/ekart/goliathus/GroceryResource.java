package com.ekart.goliathus;

import com.ekart.goliathus.entities.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by sandesh.kumar on 22/06/17.
 */
@Path("/hack")
public class GroceryResource {

    private SessionFactory factory;

    public GroceryResource(SessionFactory factory) {
        this.factory = factory;
    }

    @Path("/store")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public void storeData(String object) {

        JSONObject jsonObject = new JSONObject(object);
        JSONArray jsonArray = jsonObject.getJSONObject("result").getJSONArray("products");
        for (Object node : jsonArray) {
            JSONObject jsonNode = (JSONObject) node;
//            jsonNode.getJSONObject()
        }
        Session session = factory.getCurrentSession();

    }


    @GET
    @Path("/discounts")
    @Produces(MediaType.APPLICATION_JSON)
    public List<SlotDiscount> getDiscount(@QueryParam("locality") String locality) {
        Session session = factory.openSession();
        Transaction txn = session.beginTransaction();

        List<PlacedOrder> orders = session.getNamedQuery("com.ekart.goliathus.entities.PlacedOrder.findByLocality")
                                             .setParameter("locality", locality).list();

        Map<Slot, Double> slotValueMap = orders.stream().collect(Collectors.groupingBy(PlacedOrder::getSlot, Collectors.summingDouble(PlacedOrder::getOrderValue)));

        List<SlotDiscount> slotDiscounts = new ArrayList<>();
        slotValueMap.forEach((slot, orderValue) -> {

            List<Discount> discounts = session.getNamedQuery("com.ekart.goliathus.entities.Discount.findByOrderValue")
                            .setParameter("orderValue", orderValue.intValue()).list();

            int discount = discounts.stream().mapToInt(Discount::getDiscount).max().orElse(0);
            if(discount > 0) {
                slotDiscounts.add(new SlotDiscount(slot, discount));
            }
        });
        txn.commit();
        session.close();
        return slotDiscounts;
    }
}
