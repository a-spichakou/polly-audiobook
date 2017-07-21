package polly;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;

public class ParagraphProducer {
	
	private static volatile ParagraphProducer producer;
	
	private BufferedReader br;
	
	private AtomicInteger paragraphSeq;
	
	private ParagraphProducer() throws FileNotFoundException{
		br = new BufferedReader(new FileReader(Config.getInstance().getBookPath()));
		paragraphSeq = new AtomicInteger(0);
	}
	
	public static ParagraphProducer getInstance() throws FileNotFoundException{
		ParagraphProducer localParagraphProducer = producer;
		if(localParagraphProducer==null){
			synchronized (ParagraphProducer.class) {
				localParagraphProducer = producer;
				if(localParagraphProducer==null){
					localParagraphProducer = new ParagraphProducer();
					
					producer = localParagraphProducer;
				}
			}
		}
		return producer;
	}
	
	public synchronized WorkPiece getWork() throws IOException{
		final String line = br.readLine();
		if(line==null){
			return null;
		}
		final WorkPiece work = new WorkPiece(paragraphSeq.getAndIncrement(), line);
		
		return work;
	}

}
