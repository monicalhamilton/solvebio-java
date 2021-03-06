package com.solvebio;

import java.util.List;

import com.solvebio.exception.APIConnectionException;
import com.solvebio.exception.APIException;
import com.solvebio.exception.AuthenticationException;
import com.solvebio.exception.InvalidRequestException;
import com.solvebio.model.Dataset;
import com.solvebio.model.Depository;
import com.solvebio.model.DepositoryVersion;

public class SolveBioClient {
	public static final String API_HOST = "https://api.solvebio.com";
	public static final String API_VERSION = "v1";

	private static final String API_KEY = System.getProperty("apiKey");
	
	private String apiKey;

	public SolveBioClient() {
		this(API_KEY);
	}

	public SolveBioClient(String apiKey) {
		this.apiKey = apiKey;
	}

	public String getAPIKey() {
		return apiKey;
	}

	public void setAPIKey(String apiKey) {
		if (apiKey != null) {
			this.apiKey = apiKey;
		} else {
			this.apiKey = API_KEY;
		}
	}

	// ----------------------------------------
	// Depositories
	// ----------------------------------------
	public List<Depository> getDepositories() throws AuthenticationException, InvalidRequestException,
			APIConnectionException, APIException {
		return Depository.list(apiKey);
	}

	public Depository getDepository(String depositoryName) throws AuthenticationException, InvalidRequestException,
			APIConnectionException, APIException {
		return Depository.retrieve(apiKey, depositoryName);
	}

	// ----------------------------------------
	// DepositoryVersions
	// ----------------------------------------
	public List<DepositoryVersion> getDepositoryVersions(String depositoryName) throws AuthenticationException,
			InvalidRequestException, APIConnectionException, APIException {
		return DepositoryVersion.listForDepository(apiKey, depositoryName);
	}

	public List<DepositoryVersion> getDepositoryVersions(Depository depo) throws AuthenticationException,
			InvalidRequestException, APIConnectionException, APIException {
		return depo.getDepositoryVersions(apiKey);
	}

	public DepositoryVersion getDepositoryVersion(String depositoryName, String versionId)
			throws AuthenticationException, InvalidRequestException, APIConnectionException, APIException {
		return DepositoryVersion.retrieve(apiKey, depositoryName, versionId);
	}

	public DepositoryVersion getDepositoryVersion(String depositoryId)
			throws AuthenticationException, InvalidRequestException, APIConnectionException, APIException {
		return DepositoryVersion.retrieve(apiKey, depositoryId);
	}

	// ----------------------------------------
	// Datasets
	// ----------------------------------------
	public List<Dataset> getDatasets(String versionId) throws AuthenticationException, InvalidRequestException,
			APIConnectionException, APIException {
		return Dataset.listForDepositoryVersion(apiKey, versionId);
	}

	public Dataset getDataset(String depositoryName, String versionId, String datasetName) throws AuthenticationException,
			InvalidRequestException, APIConnectionException, APIException {
		// Looks like you need to have a datasetName argument in order to find a unique dataset
		// Otherwise, you'd be returning a list of datasets
		return Dataset.retrieve(apiKey, depositoryName, versionId, datasetName);
	}

	public Dataset getDataset(String datasetId) throws AuthenticationException,
			InvalidRequestException, APIConnectionException, APIException {
		return Dataset.retrieve(apiKey, datasetId);
	}
	
}
