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
import com.solvebio.model.Dataset;
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
	public void testGetDepositories() throws APIException {
		List<Depository> depos = client.getDepositories();
		assertTrue(depos.size() > 0);
	}

	@Test
	public void testGetDepository() throws APIException {
		// ClinVar
		Depository depo = client.getDepository("clinvar");
		assertEquals(depo.getName().toLowerCase(), "clinvar");

		// ClinVarToo (doesn't exist)
		try {
			client.getDepository("clinvartoo");
			fail("InvalidRequestException not thrown.");
		} catch (InvalidRequestException e) {
			assertTrue(e.getMessage().toLowerCase().endsWith("not found"));
		}
	}

	@Test
	public void testGetDepositoryVersion() throws APIException {
		// ClinVar, 2.0.0-1
		DepositoryVersion version = client.getDepositoryVersion("clinvar",
				"2.0.0-1");
		assertEquals(version.getName(), "2.0.0-1");
	}

	@Test
	public void testGetDepositoryVersionWithId() throws APIException {
		// ClinVar, 2.0.0-1
		DepositoryVersion version = client.getDepositoryVersion("15");
		assertEquals(version.getName(), "2.0.0-1");
	}

	@Test
	public void testGetDepositoryVersions() throws APIException {
		// direct
		assertNotNull(client.getDepositoryVersions("clinvar"));

		// get Depository first, then get DepositoryVerions
		Depository depo = client.getDepository("clinvar");
		assertNotNull(client.getDepositoryVersions(depo));
	}

	@Test
	public void testGetDatasets() throws APIException {

		List<Dataset> datasets = client.getDatasets("ClinVar/2.0.0-1");
		Assert.assertTrue(datasets.size() > 0);
	}


	@Test
	public void testGetDataset() throws APIException {

		Dataset dataset = client.getDataset("ClinVar", "2.0.0-1", "Variants");
		Assert.assertEquals(dataset.getName(), "Variants");
	}

	@Test
	public void testGetDatasetWithId() throws APIException {

		Dataset dataset = client.getDataset("25");
		Assert.assertEquals(dataset.getName(), "Variants");
	}

	@Test
	public void testGetDatasetWithName() throws APIException {

		Dataset dataset = client.getDataset("ClinVar/2.0.0-1/Variants");
		Assert.assertEquals(dataset.getName(), "Variants");
	}
}
