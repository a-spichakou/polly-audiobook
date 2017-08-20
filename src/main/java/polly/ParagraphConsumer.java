package polly;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.attribute.PosixFilePermission;
import java.util.HashSet;

import com.amazonaws.services.polly.AmazonPollyClient;
import com.amazonaws.services.polly.model.OutputFormat;
import com.amazonaws.services.polly.model.SynthesizeSpeechRequest;
import com.amazonaws.services.polly.model.SynthesizeSpeechResult;
import com.amazonaws.services.polly.model.TextType;
import com.amazonaws.services.polly.model.Voice;
import com.amazonaws.util.IOUtils;

public class ParagraphConsumer {

	private static final String TTS_04D_OGG = "tts%04d.ogg";
	private static final String TTS_04D_OGG_IDX = "complex_tts%04d_%d.ogg";
	
	private static final String CONCAT_DIRECTSPEECH = "ffmpeg -f concat -safe 0 -i <(for f in complex_tts%04d_*.ogg; do echo \"file '$PWD/$f'\"; done) -c copy tts%04d.ogg";

	public ParagraphConsumer() {

	}

	private InputStream synthesize(boolean directSpeech, String text, OutputFormat format)
			throws IOException {
		AWSResourceProvider instance = AWSResourceProvider.getInstance();
		Voice voice = directSpeech ? instance.getVoiceDirectSpeech(): instance.getVoice();
		final AmazonPollyClient pll = instance.getPll();
		final SynthesizeSpeechRequest synthReq = new SynthesizeSpeechRequest()
				.withTextType(TextType.Ssml)
				.withText(text).withVoiceId(voice.getId())
				.withOutputFormat(format);
		final SynthesizeSpeechResult synthRes = pll.synthesizeSpeech(synthReq);

		return synthRes.getAudioStream();
	}

	public void doWork(IWorkPiece work) throws FileNotFoundException, IOException {
		final SpeechSentence[] format = work.format();
		// get the audio stream
		final String outputFile = getOutputFile(work);
		if(format.length>1){
			for(int i=0;i<format.length;i++){

				System.out.println(work.getParagraphSeqIdx() + ": Direct speech: " + format[i].isDirectSpeech() + ": " + format[i].getFormattedSentence());
				final InputStream speechStream = synthesize(format[i].isDirectSpeech(), format[i].getFormattedSentence(), OutputFormat.Ogg_vorbis);
				
				final String outputFile2 = getOutputFile(work, i);
				IOUtils.copy(speechStream, new FileOutputStream(new File(outputFile2)));
				
			}
			final String directSpeechExec = getDirectSpeechExec(work);
			System.out.println(directSpeechExec);
			//final String executeCommand = executeCommand(directSpeechExec);
			//System.out.println(executeCommand);
			
		} else{
			System.out.println(work.getParagraphSeqIdx() + ": " + format[0].getFormattedSentence());
			final InputStream speechStream = synthesize(format[0].isDirectSpeech(), format[0].getFormattedSentence(), OutputFormat.Ogg_vorbis);
			IOUtils.copy(speechStream, new FileOutputStream(new File(outputFile)));
		}
		
	}

	public String getOutputFile(IWorkPiece work) {
		final String format = String.format(Config.getInstance()
				.getOutputFolder() + File.separator + TTS_04D_OGG,
				work.getParagraphSeqIdx());
		return format;
	}
	
	public String getOutputFile(IWorkPiece work, int idx) {
		final String format = String.format(Config.getInstance()
				.getOutputFolder() + File.separator + TTS_04D_OGG_IDX,
				work.getParagraphSeqIdx(), idx);
		return format;
	}
	
	protected String getDirectSpeechExec(IWorkPiece work)
			throws IOException {
		final String format = String.format(CONCAT_DIRECTSPEECH,
				work.getParagraphSeqIdx(), work.getParagraphSeqIdx());

		final String shell = Config.getInstance()
				.getOutputFolder() + File.separator + work.getParagraphSeqIdx() + ".sh";
		final PrintWriter out = new PrintWriter(shell);
		out.println(format);
		out.close();

		final File file = new File(shell);
		final HashSet<PosixFilePermission> permissions = new HashSet<PosixFilePermission>();
		permissions.add(PosixFilePermission.OWNER_EXECUTE);
		permissions.add(PosixFilePermission.OWNER_READ);
		permissions.add(PosixFilePermission.OWNER_WRITE);
		permissions.add(PosixFilePermission.OTHERS_READ);
		permissions.add(PosixFilePermission.OTHERS_WRITE);
		permissions.add(PosixFilePermission.OTHERS_EXECUTE);
		
		Files.setPosixFilePermissions(file.toPath(), permissions);
		return file.getCanonicalPath();
	}
	
	public String executeCommand(String command) {

		StringBuffer output = new StringBuffer();

		Process p;
		try {
			p = Runtime.getRuntime().exec(command, null, new File(Config.getInstance().getOutputFolder()));
			p.waitFor();
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					p.getInputStream()));

			String line = "";
			while ((line = reader.readLine()) != null) {
				output.append(line + "\n");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return output.toString();

	}

}
