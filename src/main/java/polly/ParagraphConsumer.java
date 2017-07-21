package polly;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import com.amazonaws.services.polly.AmazonPollyClient;
import com.amazonaws.services.polly.model.OutputFormat;
import com.amazonaws.services.polly.model.SynthesizeSpeechRequest;
import com.amazonaws.services.polly.model.SynthesizeSpeechResult;
import com.amazonaws.services.polly.model.Voice;
import com.amazonaws.util.IOUtils;

public class ParagraphConsumer {

	private static final String TTS_04D_OGG = "tts%04d.ogg";

	public ParagraphConsumer() {

	}

	private InputStream synthesize(String text, OutputFormat format)
			throws IOException {
		AWSResourceProvider instance = AWSResourceProvider.getInstance();
		final Voice voice = instance.getVoice();
		final AmazonPollyClient pll = instance.getPll();
		final SynthesizeSpeechRequest synthReq = new SynthesizeSpeechRequest()
				.withText(text).withVoiceId(voice.getId())
				.withOutputFormat(format);
		final SynthesizeSpeechResult synthRes = pll.synthesizeSpeech(synthReq);

		return synthRes.getAudioStream();
	}

	public void doWork(WorkPiece work) throws FileNotFoundException, IOException {
		System.out.println(work.getParagraphSeqIdx() + ": "
				+ work.getParagraph());

		// get the audio stream
		final InputStream speechStream = synthesize(work.getParagraph(), OutputFormat.Ogg_vorbis);
		
		IOUtils.copy(speechStream, new FileOutputStream(new File(getOutputFile(work))));
	}

	public String getOutputFile(WorkPiece work) {
		final String format = String.format(Config.getInstance()
				.getOutputFolder() + File.separator + TTS_04D_OGG,
				work.getParagraphSeqIdx());
		return format;
	}
}
