package nl.paardenvriendjes.pvapi;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

import java.io.FileReader;
import java.text.SimpleDateFormat;
import java.util.List;

import org.hamcrest.core.Is;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.junit.Before;
import org.junit.After;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.transaction.annotation.Transactional;

import nl.paardenvriendjes.pvapi.daoimpl.memberDaoImpl;
import nl.paardenvriendjes.pvapi.domain.Member;

@ContextConfiguration("classpath:application-context.xml")
public class TestMemberCreation extends AbstractTransactionalJUnit4SpringContextTests {

	@Autowired
	private memberDaoImpl memberService;

	@Before
	public void initialize() {

	}
	
	@After
	public void after() throws Throwable {

	}
	
	@Test
	public void testDataMemberCreationCorrectDBTable() throws Exception {
		int rowsInTable = this.countRowsInTable("Member");
		assertThat(8, is(rowsInTable));
	}
	
	@Test
	public void testDataMemberCreationIsCorrect() throws Exception {
		setMembers();
		List <Member> memberList = memberService.listMembers();
		Member x = memberList.get(0);
		assertThat(x.getPassword(),Is.is("Superdaddy79"));
		assertThat(x.getAchternaam(),Is.is("van Battum"));
		assertThat(x.getVoornaam(),Is.is("Rik"));
		assertThat(x.getGeboortedatum().toString(),Is.is("1979-01-12 00:06:00.0"));
		assertThat(x.getCreatedon().toString(), Is.is("2015-01-22 00:01:00.0"));
		assertThat(x.getPlaatsnaam(), Is.is("Hilversum"));
		assertThat(x.getProfileimage(), Is.is("www.image.com/rik"));
		assertThat(x.getOvermij(), Is.is("ik ben een paardenliefhebber"));
		assertThat(x.getUsername(), Is.is("rikbattum"));
		int rowsInTable = this.countRowsInTable("Member");
		assertThat(8, is(rowsInTable));
	}
	

	public void setMembers() {

		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-mm-yyyy");
		Object obj;

		try {
			JSONParser parser = new JSONParser();
			obj = parser.parse(new FileReader("src/test/resources/members.json"));
			JSONArray members = (JSONArray) obj;
			System.out.println("Succes getting member file!");
			for (Object createmember : members) {
				JSONObject memberX = (JSONObject) createmember;
				System.out.println(memberX);
				Member m = new Member();
				m.setAchternaam((String) memberX.get("achternaam"));
				m.setVoornaam((String) memberX.get("voornaam"));
				m.setUsername((String) memberX.get("username"));
				m.setGeboortedatum(dateFormat.parse((String) memberX.get("geboortedatum")));
				m.setCreatedon(dateFormat.parse((String) memberX.get("createdon")));
				m.setEmail((String) memberX.get("email"));
				m.setOvermij((String) memberX.get("overmij"));
				m.setPassword((String) memberX.get("password"));
				m.setPlaatsnaam((String) memberX.get("plaatsnaam"));
				m.setProfileimage((String) memberX.get("profileimage"));
				memberService.saveMember(m);
			}
		} catch (Exception e) {
			e.printStackTrace();
			e.getMessage();
			System.out.println("could not create members");
		}
	}

}
