package polly;

import java.util.List;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.polly.AmazonPollyClient;
import com.amazonaws.services.polly.model.DescribeVoicesRequest;
import com.amazonaws.services.polly.model.DescribeVoicesResult;
import com.amazonaws.services.polly.model.Voice;

public class AWSResourceProvider {
	
	private static volatile AWSResourceProvider instance;
	
	private final AmazonPollyClient pll;
	private final Voice voice;
	private final Voice voiceDirectSpeech;
	
	private AWSResourceProvider(){
		// create an Amazon Polly client in a specific region
		final DefaultAWSCredentialsProviderChain awsCredentialsProvider = new DefaultAWSCredentialsProviderChain();
		pll = new AmazonPollyClient(awsCredentialsProvider,
				new ClientConfiguration());
		pll.setRegion(com.amazonaws.regions.Region.getRegion(Regions.US_EAST_1));
		// Create describe voices request.
		final DescribeVoicesRequest describeVoicesRequest = new DescribeVoicesRequest();

		// Synchronously ask Amazon Polly to describe available TTS voices.
		final DescribeVoicesResult describeVoicesResult = pll
				.describeVoices(describeVoicesRequest);
		final List<Voice> voices = describeVoicesResult.getVoices();
		voice = voices.get(4);
		voiceDirectSpeech = voices.get(5);
	}

	public static AWSResourceProvider getInstance(){
		AWSResourceProvider localAWSResourceProvider = instance;
		if(localAWSResourceProvider==null){
			synchronized (AWSResourceProvider.class) {
				localAWSResourceProvider = instance;
				if(localAWSResourceProvider==null){
					localAWSResourceProvider = new AWSResourceProvider();
					
					instance = localAWSResourceProvider;
				}
			}
		}
		return instance;
	}

	public Voice getVoice() {
		return voice;
	}

	public Voice getVoiceDirectSpeech() {
		return voiceDirectSpeech;
	}

	public AmazonPollyClient getPll() {
		return pll;
	}
	
}
