package com.ekart.goliathus;

import com.ekart.goliathus.entities.*;
import io.dropwizard.Application;
import io.dropwizard.db.PooledDataSourceFactory;
import io.dropwizard.hibernate.HibernateBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

public class OmsApplication extends Application<OmsConfiguration> {

    private final HibernateBundle<OmsConfiguration> hibernateBundle = new HibernateBundle<OmsConfiguration>(
            Product.class,
            PlacedOrder.class,
            Customer.class,
            Brand.class,
            Category.class,
            OrderProductItem.class,
            ProductVariant.class,
            Tag.class
    ) {
        @Override
        public PooledDataSourceFactory getDataSourceFactory(OmsConfiguration omsConfiguration) {
            return omsConfiguration.getDatabase();
        }
    };

    public static void main(final String[] args) throws Exception {
        new OmsApplication().run(args);
    }

    @Override
    public String getName() {
        return "Oms";
    }

    @Override
    public void initialize(final Bootstrap<OmsConfiguration> bootstrap) {
        bootstrap.addBundle(hibernateBundle);
    }

    @Override
    public void run(final OmsConfiguration configuration,
                    final Environment environment) {
        environment.jersey().register(new GroceryResource(hibernateBundle.getSessionFactory()));
    }

}
