package com.ekart.goliathus;

import io.dropwizard.Configuration;
import io.dropwizard.db.DataSourceFactory;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.*;

@Data
public class OmsConfiguration extends Configuration {
    @NotNull
    @Valid
    private final DataSourceFactory database = new DataSourceFactory();
}
