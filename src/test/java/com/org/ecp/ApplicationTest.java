package com.org.demo;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

class ApplicationTest {
@InjectMocks
public Application application;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void customOpenAPI(){
        Assert.assertNotNull(application.customOpenAPI());
    }

    @Test
    void forwardedHeaderFilter(){
    	Assert.assertNotNull(application.forwardedHeaderFilter());
    }

    @Test
    void corsConfigurationSource(){
    	Assert.assertNotNull(application.corsConfigurationSource());
    }
}
