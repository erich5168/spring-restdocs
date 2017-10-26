/*
 * Copyright 2014-2016 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.restdocs.webflux;

import java.util.Map;

import org.junit.Rule;
import org.junit.Test;

import org.springframework.http.HttpHeaders;
import org.springframework.restdocs.JUnitRestDocumentation;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.client.ClientRequest;
import org.springframework.web.reactive.function.client.ExchangeFunction;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

/**
 * Tests for {@link WebFluxRestDocumentationConfigurer}.
 *
 * @author Andy Wilkinson
 */
public class WebFluxRestDocumentationConfigurerTests {

	@Rule
	public final JUnitRestDocumentation restDocumentation = new JUnitRestDocumentation();

	private final WebFluxRestDocumentationConfigurer configurer = new WebFluxRestDocumentationConfigurer(
			this.restDocumentation);

	@Test
	public void configurationCanBeRetrievedButOnlyOnce() {
		ClientRequest request = mock(ClientRequest.class);
		HttpHeaders headers = new HttpHeaders();
		headers.add(WebTestClient.WEBTESTCLIENT_REQUEST_ID, "1");
		given(request.headers()).willReturn(headers);
		this.configurer.filter(request, mock(ExchangeFunction.class));
		Map<String, Object> configuration = WebFluxRestDocumentationConfigurer
				.retrieveConfiguration(headers);
		assertThat(configuration, notNullValue());
		assertThat(WebFluxRestDocumentationConfigurer.retrieveConfiguration(headers),
				nullValue());
	}

}
