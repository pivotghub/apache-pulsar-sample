/**
 * 
 */
package com.honda.adpater.door.consumer;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.annotation.PostConstruct;

import org.apache.pulsar.client.api.Authentication;
import org.apache.pulsar.client.api.AuthenticationFactory;
import org.apache.pulsar.client.api.Consumer;
import org.apache.pulsar.client.api.PulsarClient;
import org.apache.pulsar.client.api.PulsarClientException;
import org.apache.pulsar.client.api.SubscriptionType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

/**
 * @author 002BHV744
 *
 */
@Component
public class PulsarTLSConsumer {

	private static final String TENANT = "public";
    private static final String NAMESPACE = "default";
    
    private static final String SUBSCRIPTION_NAME = "Test-SUB1";
    
	private static final String TOPIC_NAME = String.format("persistent://public/default/healthcheck", TENANT, NAMESPACE);
	
	@Autowired
	private ResourceLoader resourceLoader;
	
	
	@PostConstruct
	public void init() throws Exception {
		
		
		PulsarClient client = PulsarClient.builder()
				.serviceUrl("pulsar+ssl://eaimxtprxy-dv.dev.gm.com:16651")
				.tlsTrustCertsFilePath("/honda/cert/ca.cert.pem")
				.authentication(AuthenticationFactory.TLS("/honda/cert/T_EXT_176252-EDL_HON.cert.pem"
						, "/honda/cert/T_EXT_176252-EDL_HON.key-pk8.pem"))
				.build();
		
		 Consumer<byte[]> consumer = client.newConsumer()
                 .topic(TOPIC_NAME)
                 .subscriptionType(SubscriptionType.Shared)
                 .subscriptionName(SUBSCRIPTION_NAME)
                 .receiverQueueSize(1000000).subscribe();
		
		
	}
	
}
