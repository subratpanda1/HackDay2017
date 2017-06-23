package com.ekart.goliathus;

import com.ekart.goliathus.entities.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.time.DayOfWeek;
import java.time.LocalDate;
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
    @Path("/slots")
    @Produces(MediaType.APPLICATION_JSON)
    public List<SlotDiscount> getDiscount(@QueryParam("locality") String locality) {
        Session session = factory.openSession();
        Transaction txn = session.beginTransaction();

        List<PlacedOrder> orders = session.getNamedQuery("com.ekart.goliathus.entities.PlacedOrder.findByLocality")
                                          .setParameter("locality", locality).list();

        Map<Slot, Double> slotValueMap = orders.stream().collect(Collectors.groupingBy(PlacedOrder::getSlot, Collectors.summingDouble(PlacedOrder::getOrderValue)));

        List<DayOfWeek> days = getNextNDays(3);
        List<Slot> allAvailableSlots = session.createCriteria(Slot.class).add(Restrictions.in("day", days)).list();
        List<SlotDiscount> slotDiscounts = new ArrayList<>();
        allAvailableSlots.forEach(slot -> {
            if (!slotValueMap.containsKey(slot)) {
                slotDiscounts.add(new SlotDiscount(slot, 0, slotValueMap.get(slot)));
            } else {
                List<Discount> discounts = session.getNamedQuery("com.ekart.goliathus.entities.Discount.findByOrderValue")
                                                  .setParameter("orderValue", slotValueMap.get(slot).intValue()).list();

                int discount = discounts.stream().mapToInt(Discount::getDiscount).max().orElse(0);
                slotDiscounts.add(new SlotDiscount(slot, discount, slotValueMap.get(slot)));
            }
        });
        txn.commit();
        session.close();
        return slotDiscounts;
    }


    @GET
    @Path("/localities")
    @Produces(MediaType.APPLICATION_JSON)
    public Set<String> getLocalities() {
        Session session = factory.openSession();
        Transaction txn = session.beginTransaction();
        List<Customer> customers = session.createCriteria(Customer.class).list();
        txn.commit();
        session.close();
        return customers.stream().map(customer -> customer.getAddress().getLocality()).collect(Collectors.toSet());
    }

    @GET
    @Path("/discounts")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Discount> getDiscounts() {
        Session session = factory.openSession();
        Transaction txn = session.beginTransaction();
        List<Discount> discounts = session.createCriteria(Discount.class).list();
        txn.commit();
        session.close();
        return discounts;
    }

    @PUT
    @Path("/order")
    @Consumes(MediaType.APPLICATION_JSON)
    public Long createOrder(OrderDto orderDto) {

        Session session = factory.openSession();
        Transaction txn = session.beginTransaction();
        PlacedOrder placedOrder = new PlacedOrder();
        Customer customer = session.get(Customer.class, orderDto.getCustomerId());
        placedOrder.setCustomer(customer);

        Slot slot = session.get(Slot.class, orderDto.getSlotId());
        placedOrder.setSlot(slot);

        placedOrder.setLocality(orderDto.getLocality());
        placedOrder.setOrderValue(orderDto.getOrderValue());
        placedOrder.setStatus("CREATED");

        session.saveOrUpdate(placedOrder);
        txn.commit();
        session.close();
        return placedOrder.getId();
    }


    private List<DayOfWeek> getNextNDays(int n) {
        DayOfWeek today = LocalDate.now().getDayOfWeek();
        int c = today.ordinal();
        List<DayOfWeek> days = new ArrayList<>();
        DayOfWeek[] daysArray = DayOfWeek.values();
        for (int i = 1; i <= n; i++) {
            days.add(daysArray[(c + i) % daysArray.length]);
        }
        return days;
    }
}
