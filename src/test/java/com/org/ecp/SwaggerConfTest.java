package com.org.demo;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockServletContext;
import org.springframework.web.context.support.GenericWebApplicationContext;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;

class SwaggerConfTest {
    @InjectMocks
    public SwaggerConf swaggerConf;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void addResourceHandlers(){
        ResourceHandlerRegistry registry = new ResourceHandlerRegistry(new GenericWebApplicationContext(), new MockServletContext());
        swaggerConf.addResourceHandlers(registry);
        Assert.assertNotNull(swaggerConf);
    }
}
