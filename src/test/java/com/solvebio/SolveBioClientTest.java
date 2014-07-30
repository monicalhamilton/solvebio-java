package com.solvebio;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.solvebio.exception.APIException;
import com.solvebio.exception.AuthenticationException;
import com.solvebio.exception.InvalidRequestException;
import com.solvebio.model.Depository;
import com.solvebio.model.DepositoryVersion;

public class SolveBioClientTest {
	private SolveBioClient client;

	@Before
	public void setUpTest() {
		client = new SolveBioClient();
	}

	// TODO: add a test for com.solvebio.net.APIResource.camelCaseToUnderscoreCase

	@Test
	public void testAuth() throws APIException {
		client.setAPIKey("abc123invalidAPIKey");
		try {
			client.getDepositoryVersions("ClinVar");
			fail("AuthenticationException not thrown.");
		} catch (AuthenticationException e) {
		}
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
		DepositoryVersion version = client.getDepositoryVersion("clinvar", "2.0.0-1");
		// TODO: uncomment when implemented
		// assertEquals(version.getName(), "2.0.0-1");
		fail();
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
