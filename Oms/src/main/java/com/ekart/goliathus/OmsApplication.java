package com.ekart.goliathus;

import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

public class OmsApplication extends Application<OmsConfiguration> {

    public static void main(final String[] args) throws Exception {
        new OmsApplication().run(args);
    }

    @Override
    public String getName() {
        return "Oms";
    }

    @Override
    public void initialize(final Bootstrap<OmsConfiguration> bootstrap) {
        // TODO: application initialization
    }

    @Override
    public void run(final OmsConfiguration configuration,
                    final Environment environment) {
        // TODO: implement application
    }

}
