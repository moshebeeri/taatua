package org.vidad.cloud.processor;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import org.apache.commons.io.FilenameUtils;
import org.apache.camel.Exchange;
import org.apache.camel.ProducerTemplate;
import org.apache.log4j.Logger;
import org.vidad.camel.BaseProcessor;
import org.vidad.camel.Template;
import org.vidad.data.Task;
import org.vidad.data.Video;
import org.vidad.tools.conf.Configure;
import org.vidad.video.xuggle.VideoInfo;


public class VideoFeedProcessor extends BaseProcessor {

	protected VideoFeedProcessor() {
		super(Logger.getLogger(VideoFeedProcessor.class));
	}


	@Override
	public void process(Exchange exchange) throws Exception {
		Task task = null;
		try{
			String filename = exchange.getIn().getHeader(Exchange.FILE_NAME, String.class);
			
			if(FilenameUtils.getExtension(filename).equals("xml")){
				task = handleXML(exchange);
			}else{ //Assume video file received
				task = store(exchange);
			}
			processSuccess(exchange, task);
		}catch(Exception e){
			handleException(VideoFeedProcessor.class, exchange, e, task);
		}
	}
	
	private Task createVideoTask(Exchange exchange) throws FileNotFoundException {
		String filename = exchange.getIn().getHeader(Exchange.FILE_NAME, String.class);
		String storeURL = Configure.settings().getString("video.storeURI");
		String tenant = Configure.settings().getString("active.tenant");
		String extension = FilenameUtils.getExtension(filename);
		String videoURL = storeURL+File.separatorChar+tenant+File.separatorChar+filename;
		Video video = new Video(videoURL, extension);
		InputStream fis = new FileInputStream(exchange.getIn().getBody(File.class));
		VideoInfo vidInfo = new VideoInfo().fromStream(fis);
		video.setInfo(vidInfo);
		mongo.insertCollectionable(video);
		return new Task(video.getObjectId());
	}


	private Task handleXML(Exchange exchange) {
		return null;
	}


	private Task store(Exchange exchange) throws FileNotFoundException {
		ProducerTemplate storeTemplate = Template.byId("videofile-store-context");
		//throws CamelExecutionException
		storeTemplate.send("direct:video_store", exchange);
		return createVideoTask(exchange);
	}	
}
