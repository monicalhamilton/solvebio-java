package com.solvebio.model;

import java.util.List;

import com.solvebio.exception.APIConnectionException;
import com.solvebio.exception.APIException;
import com.solvebio.exception.AuthenticationException;
import com.solvebio.exception.InvalidRequestException;
import com.solvebio.net.CollectionResource;

public class Dataset extends SolveBioModel {
	// TODO: add missing field and getters/setters: name
	// ** BONUS ** add all missing fields and corresponding getters/setters

	/**
	 * Retrieve the list of Datasets available within a DatasetVersion.
	 * 
	 * @param apiKey
	 * @param versionId
	 * @return
	 * @throws AuthenticationException
	 * @throws InvalidRequestException
	 * @throws APIConnectionException
	 * @throws APIException
	 */
	public static List<Dataset> listForDepositoryVersion(String apiKey, String versionId)
			throws AuthenticationException, InvalidRequestException, APIConnectionException, APIException {
		throw new UnsupportedOperationException("I'm not implemented...yet...");
	}

	// TODO
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
	public static DepositoryVersion retrieve(String apiKey, String datasetId) throws AuthenticationException,
			InvalidRequestException, APIConnectionException, APIException {
		throw new UnsupportedOperationException("implement me.");
	}

	// TODO
	/**
	 * Returns a Dataset by full path.
	 * <p>
	 * GET <i>https://SOLVEBIO_API_HOST/datasets/:depository_name/:version_name/:dataset_name</i>
	 * 
	 * @param apiKey
	 * @param depositoryName
	 *            The parent Depository name.
	 * @param versionName
	 *            The parent DepositoryVersion semantic-version name (this is not globally unique).
	 * @param datasetName
	 *            The Dataset name (this may not be globally unique).
	 * @return Dataset
	 * @throws AuthenticationException
	 * @throws InvalidRequestException
	 * @throws APIConnectionException
	 * @throws APIException
	 */
	public static Dataset retrieve(String apiKey, String depositoryName, String versionName, String datasetName)
			throws AuthenticationException, InvalidRequestException, APIConnectionException, APIException {
		throw new UnsupportedOperationException("implement me.");
	}

	public class DatasetVersionCollection extends CollectionResource<Dataset> {
	}
}
