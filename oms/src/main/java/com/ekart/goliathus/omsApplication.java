package com.ekart.goliathus;

import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

public class omsApplication extends Application<omsConfiguration> {

    public static void main(final String[] args) throws Exception {
        new omsApplication().run(args);
    }

    @Override
    public String getName() {
        return "oms";
    }

    @Override
    public void initialize(final Bootstrap<omsConfiguration> bootstrap) {
        // TODO: application initialization
    }

    @Override
    public void run(final omsConfiguration configuration,
                    final Environment environment) {
        // TODO: implement application
    }

}
