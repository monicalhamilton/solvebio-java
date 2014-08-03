package com.solvebio.model;

import java.util.List;

import com.google.common.collect.Lists;
import com.google.gson.annotations.SerializedName;
import com.solvebio.exception.APIConnectionException;
import com.solvebio.exception.APIException;
import com.solvebio.exception.AuthenticationException;
import com.solvebio.exception.InvalidRequestException;
import com.solvebio.net.CollectionResource;

public class Depository extends SolveBioModel {

	@SerializedName("versions_count")
	private int versionsCount;

	@SerializedName("external_url")
	private String externalUrl;

	@SerializedName("versions_url")
	private String versionsUrl;

	@SerializedName("latest_version")
	private String latestVersion;

	@SerializedName("latest_version_id")
	private String latestVersionId;
	
	public int getVersionsCount() {
		return versionsCount;
	}

	public void setVersionsCount(int versionsCount) {
		this.versionsCount = versionsCount;
	}

	public String getExternalUrl() {
		return externalUrl;
	}

	public void setExternalUrl(String externalUrl) {
		this.externalUrl = externalUrl;
	}

	public String getVersionsUrl() {
		return versionsUrl;
	}

	public void setVersionsUrl(String versionsUrl) {
		this.versionsUrl = versionsUrl;
	}

	public String getLatestVersion() {
		return latestVersion;
	}

	public void setLatestVersion(String latestVersion) {
		this.latestVersion = latestVersion;
	}

	public String getLatestVersionId() {
		return latestVersionId;
	}

	public void setLatestVersionId(String latestVersionId) {
		this.latestVersionId = latestVersionId;
	}

	/**
	 * Retrieves a DepositoryCollection containing all the accessible Depositories.
	 * <p>
	 * GET <i>http://SOLVEBIO_API_HOST/depositories</i>
	 * 
	 * @param apiKey
	 * @return List<Depository>
	 * @throws AuthenticationException
	 * @throws InvalidRequestException
	 * @throws APIConnectionException
	 * @throws APIException
	 */
	public static List<Depository> list(String apiKey) throws AuthenticationException, InvalidRequestException,
			APIConnectionException, APIException {
		return request(RequestMethod.GET, classURL(Depository.class), null, DepositoryCollection.class, apiKey)
				.getData();
	}

	/**
	 * Retrieves of Depository by name or id.
	 * <p>
	 * GET <i>http://SOLVEBIO_API_HOST/depositories/:depository_name_or_id</i>
	 * 
	 * @param apiKey
	 * @param depositoryNameOrId
	 * @return Depository
	 * @throws AuthenticationException
	 * @throws InvalidRequestException
	 * @throws APIConnectionException
	 * @throws APIException
	 */
	public static Depository retrieve(String apiKey, String depositoryNameOrId) throws AuthenticationException,
			InvalidRequestException, APIConnectionException, APIException {
		return request(RequestMethod.GET, instanceURL(Depository.class, depositoryNameOrId), null, Depository.class,
				apiKey);
	}

	/**
	 * Retrieves of list of DepositoryVersions contained by this.
	 * @param apiKey
	 * @return
	 * @throws AuthenticationException
	 * @throws InvalidRequestException
	 * @throws APIConnectionException
	 * @throws APIException
	 */
	public List<DepositoryVersion> getDepositoryVersions(String apiKey) throws AuthenticationException,
			InvalidRequestException, APIConnectionException, APIException {
		return DepositoryVersion.listForDepository(apiKey, getName());
	}

	public class DepositoryCollection extends CollectionResource<Depository> {
	}
}
