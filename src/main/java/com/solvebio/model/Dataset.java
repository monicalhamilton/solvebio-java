package com.solvebio.model;

import java.util.List;

import com.google.gson.annotations.SerializedName;
import com.solvebio.exception.APIConnectionException;
import com.solvebio.exception.APIException;
import com.solvebio.exception.AuthenticationException;
import com.solvebio.exception.InvalidRequestException;
import com.solvebio.net.CollectionResource;

public class Dataset extends SolveBioModel {

	@SerializedName("depository")
	private String depository;

	@SerializedName("depository_id")
	private int depositoryId;

	@SerializedName("depository_version")
	private String depositoryVersion;

	@SerializedName("depository_version_id")
	private int depositoryVersionId;

	// The solvebio api docs incorrectly mark this as int
	@SerializedName("fields_url")
	private String fieldsUrl;

	@SerializedName("data_url")
	private String dataUrl;

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

	public String getDepositoryVersion() {
		return depositoryVersion;
	}

	public void setDepositoryVersion(String depositoryVersion) {
		this.depositoryVersion = depositoryVersion;
	}

	public int getDepositoryVersionId() {
		return depositoryVersionId;
	}

	public void setDepositoryVersionId(int depositoryVersionId) {
		this.depositoryVersionId = depositoryVersionId;
	}

	public String getFieldsUrl() {
		return fieldsUrl;
	}

	public void setFieldsUrl(String fieldsUrl) {
		this.fieldsUrl = fieldsUrl;
	}

	public String getDataUrl() {
		return dataUrl;
	}

	public void setDataUrl(String dataUrl) {
		this.dataUrl = dataUrl;
	}

	/**
	 * Retrieve the list of Datasets available within a DepositoryVersion.
	 * 
	 * @param apiKey
	 * @param versionId
	 * @return
	 * @throws AuthenticationException
	 * @throws InvalidRequestException
	 * @throws APIConnectionException
	 * @throws APIException
	 */
	public static List<Dataset> listForDepositoryVersion(String apiKey,
			String versionId) throws AuthenticationException,
			InvalidRequestException, APIConnectionException, APIException {
		return request(RequestMethod.GET,
				nestedURL(DepositoryVersion.class, versionId, Dataset.class),
				null, DatasetCollection.class, apiKey).getData();
	}

	/**
	 * Retrieve a Dataset with a given id.
	 * <p>
	 * GET <i>https://SOLVEBIO_API_HOST/datasets/:dataset_id</i>
	 * 
	 * @param apiKey
	 * @param datasetId
	 *            The unique Dataset id (primary key).
	 * @return Dataset
	 * @throws AuthenticationException
	 * @throws InvalidRequestException
	 * @throws APIConnectionException
	 * @throws APIException
	 */
	// I think this is meant to be Dataset... not DepositoryVersion
	public static Dataset retrieve(String apiKey, String datasetId)
			throws AuthenticationException, InvalidRequestException,
			APIConnectionException, APIException {
		return request(RequestMethod.GET,
				instanceURL(Depository.class, datasetId), null, Dataset.class,
				apiKey);
	}

	// TODO
	/**
	 * Returns a Dataset by full path.
	 * <p>
	 * GET
	 * <i>https://SOLVEBIO_API_HOST/datasets/:depository_name/:version_name/:
	 * dataset_name</i>
	 * 
	 * @param apiKey
	 * @param depositoryName
	 *            The parent Depository name.
	 * @param versionName
	 *            The parent DepositoryVersion semantic-version name (this is
	 *            not globally unique).
	 * @param datasetName
	 *            The Dataset name (this may not be globally unique).
	 * @return Dataset
	 * @throws AuthenticationException
	 * @throws InvalidRequestException
	 * @throws APIConnectionException
	 * @throws APIException
	 */
	public static Dataset retrieve(String apiKey, String depositoryName,
			String versionName, String datasetName)
			throws AuthenticationException, InvalidRequestException,
			APIConnectionException, APIException {
		return request(
				RequestMethod.GET,
				instanceURL(Dataset.class,
						String.format("%s/%s", depositoryName, versionName)),
				null, Dataset.class, apiKey);
	}

	public class DatasetCollection extends CollectionResource<Dataset> {
	}
}
