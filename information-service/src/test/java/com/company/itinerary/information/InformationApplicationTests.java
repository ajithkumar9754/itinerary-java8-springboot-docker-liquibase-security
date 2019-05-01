package com.company.itinerary.information;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = InformationApplication.class)
@TestPropertySource(locations={"classpath:messages-test.properties","classpath:test-application.properties"})
@PropertySource(value ="messages.properties",ignoreResourceNotFound = true )
public class InformationApplicationTests {

	@Test
	public void contextLoads() {
	}

}
