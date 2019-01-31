package nl.paardenvriendjes.pvapi.abstracttest;

import org.junit.Ignore;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import nl.paardenvriendjes.configuration.Application;


@RunWith(SpringRunner.class)
@SpringBootTest(classes=Application.class)
@Ignore
public class AbstractTest {

	
	protected Logger logger = LoggerFactory.getLogger(this.getClass());
	
}
