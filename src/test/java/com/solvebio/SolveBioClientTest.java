package com.solvebio;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.List;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import com.solvebio.exception.APIException;
import com.solvebio.exception.InvalidRequestException;
import com.solvebio.model.Depository;
import com.solvebio.model.DepositoryVersion;
import com.solvebio.net.APIResource;

public class SolveBioClientTest {
	private SolveBioClient client;

	@Before
	public void setUpTest() {
		client = new SolveBioClient();
	}

	@Test
	public void testCamelCaseToUnderscoreSingleWord() {
		String input = "Hello";
		String expectedOutput = "hello";
		String actualOutput = APIResource.camelCaseToUnderscoreCase(input);
		Assert.assertEquals(expectedOutput, actualOutput);
	}

	@Test
	public void testCamelCaseToUnderscoreTwoWords() {
		String input = "HelloWorld";
		String expectedOutput = "hello_world";
		String actualOutput = APIResource.camelCaseToUnderscoreCase(input);
		Assert.assertEquals(expectedOutput, actualOutput);
	}

	@Test(expected = APIException.class)
	public void testAuth() throws APIException {
		client.setAPIKey("abc123invalidAPIKey");
		client.getDepositoryVersions("ClinVar");
	}

	@Test
	public void testDepositoryList() throws APIException {
		List<Depository> depos = client.getDepositories();
		assertTrue(depos.size() > 0);
	}

	@Test
	public void testDepositoryRetrieve() throws APIException {
		// ClinVar
		Depository depo = client.getDepository("clinvar");
		assertEquals(depo.getName().toLowerCase(), "clinvar");

		// ClinVarToo (doesn't exist)
		try {
			client.getDepository("clinvartoo");
			fail("InvalidRequestException not thrown.");
		} catch (InvalidRequestException e) {
			assertEquals(e.getMessage().toLowerCase(), "not found");
		}
	}

	@Test
	public void testDepositoryVersionRetrieve() throws APIException {
		// ClinVar, 2.0.0-1
		DepositoryVersion version = client.getDepositoryVersion("clinvar",
				"2.0.0-1");
		assertEquals(version.getName(), "2.0.0-1");
	}

	@Test
	public void testDepositoryVersionList() throws APIException {
		// direct
		assertNotNull(client.getDepositoryVersions("clinvar"));

		// get Depository first, then get DepositoryVerions
		Depository depo = client.getDepository("clinvar");
		// assertNotNull(client.getDepositoryVersions(depo));
	}
}
