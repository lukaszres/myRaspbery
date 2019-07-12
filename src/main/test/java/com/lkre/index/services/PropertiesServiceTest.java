package com.lkre.index.services;

import java.io.IOException;
import org.junit.Assert;
import org.junit.Test;

public class PropertiesServiceTest {

    @Test
    public void getFirstPropertyTest() throws IOException {
        //given
        PropertiesService propertiesService = new PropertiesService();
        //when
        String actual = propertiesService.getPropertyService("firstProperty");
        String expected = "first property";
        //then
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void getHostPropertyTest() throws IOException {
        //given
        PropertiesService propertiesService = new PropertiesService();
        //when
        String actual = propertiesService.getPropertyService("host-property");
        String expected = "https://192.168.0.1";
        //then
        Assert.assertEquals(expected, actual);
    }
}
