package com.abelium.inatrace.components.agstack;

import com.abelium.inatrace.api.ApiStatus;
import com.abelium.inatrace.api.errors.ApiException;
import com.abelium.inatrace.components.agstack.api.ApiRegisterFieldBoundaryErrorResponse;
import com.abelium.inatrace.components.agstack.api.ApiRegisterFieldBoundaryRequest;
import com.abelium.inatrace.components.agstack.api.ApiRegisterFieldBoundaryResponse;
import com.abelium.inatrace.db.entities.common.PlotCoordinate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
public class AgStackClientService {

	@Value("${INATrace.agstack.apiKey}")
	private String apiKey;

	@Value("${INATrace.agstack.clientSecret}")
	private String clientSecret;

	@Value("${INATrace.agstack.baseURL}")
	private String baseURL;

	public ApiRegisterFieldBoundaryResponse registerFieldBoundaryResponse(List<PlotCoordinate> plotCoordinates) {

		ApiRegisterFieldBoundaryRequest request = new ApiRegisterFieldBoundaryRequest();
		request.setS2Index("8, 13");

		StringBuilder stringBuilder = new StringBuilder();

		for (int i = 0; i < plotCoordinates.size(); i++) {
			stringBuilder
					.append(plotCoordinates.get(i).getLongitude()).append(" ")
					.append(plotCoordinates.get(i).getLatitude());
			if (i < plotCoordinates.size() - 1) {
				stringBuilder.append(", ");
			}
		}
		request.setWkt("POLYGON ((" + stringBuilder + "))");

		WebClient webClient = WebClient.create(baseURL);

		return webClient
				.post()
				.uri(uriBuilder -> uriBuilder.path("/register-field-boundary").build())
				.body(Mono.just(request), ApiRegisterFieldBoundaryRequest.class)
				.header("API-KEY", apiKey)
				.header("CLIENT-SECRET", clientSecret)
				.accept(MediaType.APPLICATION_JSON)
				.retrieve()
				.onStatus(
						HttpStatus.INTERNAL_SERVER_ERROR::equals,
						clientResponse -> clientResponse
								.bodyToMono(ApiRegisterFieldBoundaryErrorResponse.class)
								.flatMap(error -> Mono.error(new ApiException(ApiStatus.ERROR, error.getError()))))
				.onStatus(HttpStatus.BAD_REQUEST::equals,
						clientResponse -> clientResponse
								.bodyToMono(ApiRegisterFieldBoundaryErrorResponse.class)
								.flatMap(error -> Mono.error(new ApiException(ApiStatus.INVALID_REQUEST, error.getError()))))
				.bodyToMono(ApiRegisterFieldBoundaryResponse.class)
				.block();
	}

}
