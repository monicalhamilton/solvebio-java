package com.solvebio.model;

import java.util.List;

import com.solvebio.exception.APIConnectionException;
import com.solvebio.exception.APIException;
import com.solvebio.exception.AuthenticationException;
import com.solvebio.exception.InvalidRequestException;
import com.solvebio.net.CollectionResource;

public class Depository extends SolveBioModel {
	// TODO:
	// ** BONUS ** add all missing fields and corresponding getters/setters
	private String name;
	private String description;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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
		throw new UnsupportedOperationException("implement me.");
	}

	public class DepositoryCollection extends CollectionResource<Depository> {
	}
}
