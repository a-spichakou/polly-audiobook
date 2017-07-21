package polly;

import java.util.ArrayList;
import java.util.List;

public class WorkersFactory {
	
	private static volatile WorkersFactory instance;
	
	private WorkersFactory(){};
	
	public static final WorkersFactory getInstance(){
		WorkersFactory localInstance = instance;
		if(localInstance==null){
			synchronized (WorkersFactory.class) {
				localInstance = instance;
				if(localInstance==null){
					localInstance = instance = new WorkersFactory();
				}
			}
		}
		return instance;
	}

	public List<Worker> produce(int count){
		final ArrayList<Worker> result = new ArrayList<Worker>();
		for(int i=0;i<count;i++){
			result.add(new Worker());
		}
		return result;
	}
}
