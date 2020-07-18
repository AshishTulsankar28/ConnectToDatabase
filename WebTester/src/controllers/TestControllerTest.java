package controllers;


import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import javax.net.ssl.SSLEngineResult.Status;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.client.RestTemplate;

import config.CustomConfig;
import services.TestService;

@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {CustomConfig.class})
public class TestControllerTest {
	Logger logger=LogManager.getLogger();

	public static final String BASE_URI="http://localhost:8080/webTester";

	private MockMvc mockMvc;

	@Autowired
	private RestTemplate restTemplate;

	@Mock
	private TestService testService;

	@InjectMocks
	private TestController testController;

	@Before
	public void setUp() {
		logger.debug("Before building mockMvc");
		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders
				.standaloneSetup(testController)
				.build();
	}

	@Test
	public void testAppName() {
		logger.debug("TEST :: testAppName");
		ResponseEntity<String> response=restTemplate.getForEntity(BASE_URI, String.class);
		assertThat("Reason for assertion: ", response.getStatusCode(), is(HttpStatus.OK));
		logger.debug("Response Body - "+response.getBody());

	}

	@Test
	public void testGetDevName() {
		logger.debug("TEST :: testGetDevName");
		ResponseEntity<String> resp=restTemplate.getForEntity(BASE_URI+"/getDevName/true", String.class);
		assertEquals("message ", HttpStatus.OK, resp.getStatusCode());
		logger.debug("Response Body - "+resp.getBody());
	}
	
	@After
	public void tearDown() {
		logger.debug("After running test case");	
	}

}
