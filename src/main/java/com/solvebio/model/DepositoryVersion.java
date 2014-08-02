package com.solvebio.model;

import java.util.List;

import com.google.gson.annotations.SerializedName;
import com.solvebio.exception.APIConnectionException;
import com.solvebio.exception.APIException;
import com.solvebio.exception.AuthenticationException;
import com.solvebio.exception.InvalidRequestException;
import com.solvebio.net.CollectionResource;

public class DepositoryVersion extends SolveBioModel {

	@SerializedName("dataset_url")
	private String datasetsUrl;

	@SerializedName("depository")
	private String depository;

	@SerializedName("depository_id")
	private int depositoryId;
	
	public String getDatasetsUrl() {
		return datasetsUrl;
	}

	public void setDatasetsUrl(String datasetsUrl) {
		this.datasetsUrl = datasetsUrl;
	}

	public String getDepository() {
		return depository;
	}

	public void setDepository(String depository) {
		this.depository = depository;
	}

	public int getDepositoryId() {
		return depositoryId;
	}

	public void setDepositoryId(int depositoryId) {
		this.depositoryId = depositoryId;
	}

	/**
	 * Retrieve the list of DepositoryVersions available within a Depository.
	 * 
	 * @param apiKey
	 * @param depositoryNameOrId
	 * @return
	 * @throws AuthenticationException
	 * @throws InvalidRequestException
	 * @throws APIConnectionException
	 * @throws APIException
	 */
	public static List<DepositoryVersion> listForDepository(String apiKey, String depositoryNameOrId)
			throws AuthenticationException, InvalidRequestException, APIConnectionException, APIException {
		return request(RequestMethod.GET, classURL(DepositoryVersion.class), null, DepositoryVersionCollection.class,
				apiKey).getData();
	}

	/**
	 * Retrieve a DepositoryVersion with a given id.
	 * <p>
	 * GET <i>http://SOLVEBIO_API_HOST/depository_versions/:version_id</i>
	 * 
	 * @param apiKey
	 * @param versionId
	 *            The unique DepositoryVersion id (primary key).
	 * @return DepositoryVersion
	 * @throws AuthenticationException
	 * @throws InvalidRequestException
	 * @throws APIConnectionException
	 * @throws APIException
	 */
	public static DepositoryVersion retrieve(String apiKey, String versionId) throws AuthenticationException,
			InvalidRequestException, APIConnectionException, APIException {
		return request(RequestMethod.GET, instanceURL(DepositoryVersion.class, versionId), null,
				DepositoryVersion.class, apiKey);
	}

	/**
	 * Returns a DepositoryVersion by full path.
	 * <p>
	 * GET <i>http://SOLVEBIO_API_HOST/depository_versions/:depository_name/:version_name</i>
	 * 
	 * @param apiKey
	 * @param depositoryName
	 *            The parent Depository name.
	 * @param versionName
	 *            The DepositoryVersion semantic-version name (this is not globally unique).
	 * @return DepositoryVersion
	 * @throws AuthenticationException
	 * @throws InvalidRequestException
	 * @throws APIConnectionException
	 * @throws APIException
	 */
	public static DepositoryVersion retrieve(String apiKey, String depositoryName, String versionName)
			throws AuthenticationException, InvalidRequestException, APIConnectionException, APIException {
		return request(RequestMethod.GET,
				instanceURL(DepositoryVersion.class, String.format("%s/%s", depositoryName, versionName)), null,
				DepositoryVersion.class, apiKey);
	}

	public class DepositoryVersionCollection extends CollectionResource<DepositoryVersion> {
	}
}
