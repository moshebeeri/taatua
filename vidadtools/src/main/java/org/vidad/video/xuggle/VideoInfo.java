package org.vidad.video.xuggle;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.Serializable;

import org.apache.log4j.Logger;

import com.xuggle.xuggler.ICodec;
import com.xuggle.xuggler.IContainer;
import com.xuggle.xuggler.IStream;
import com.xuggle.xuggler.IStreamCoder;

public class VideoInfo implements Serializable{
	private static final long serialVersionUID = 2421066255769771580L;
	transient Logger log = Logger.getLogger(VideoInfo.class);
	private int numStreams;
	private long duration;
	private long fileSize;
	private int bitRate;
	
	public VideoInfo fromStream(InputStream in){
        //create a Xuggler container object
        IContainer container = IContainer.make();
        int result = container.open(in, null);
        if (result<0)
            throw new RuntimeException("Failed to open media file");
        numStreams = container.getNumStreams();
        duration = container.getDuration();
        fileSize = container.getFileSize();
        bitRate = container.getBitRate();
        
        log.info("Number of streams: " + numStreams);
        log.info("Duration (ms): " + duration);
        log.info("File Size (bytes): " + fileSize);
        log.info("Bit Rate: " + bitRate);
        return this;
	}
 
    public int getNumStreams() {
		return numStreams;
	}

	public void setNumStreams(int numStreams) {
		this.numStreams = numStreams;
	}

	public long getDuration() {
		return duration;
	}

	public void setDuration(long duration) {
		this.duration = duration;
	}

	public long getFileSize() {
		return fileSize;
	}

	public void setFileSize(long fileSize) {
		this.fileSize = fileSize;
	}


	public int getBitRate() {
		return bitRate;
	}

	public void setBitRate(int bitRate) {
		this.bitRate = bitRate;
	}

	public static void main(String[] args) throws FileNotFoundException {
        String filename = "/home/moshe/Videos/transmission/Game.of.Thrones.S03E10.720p.HDTV.x264-EVOLVE.mkv";

    	new VideoInfo().fromStream(new FileInputStream(filename));
    	
        
        // first we create a Xuggler container object
        IContainer container = IContainer.make();
        
        // we attempt to open up the container
        int result = container.open(filename, IContainer.Type.READ, null);
        
        // check if the operation was successful
        if (result<0)
            throw new RuntimeException("Failed to open media file");
        
        // query how many streams the call to open found
        int numStreams = container.getNumStreams();
        
        // query for the total duration
        long duration = container.getDuration();
        
        // query for the file size
        long fileSize = container.getFileSize();

        // query for the bit rate
        long bitRate = container.getBitRate();
        
        System.out.println("Number of streams: " + numStreams);
        System.out.println("Duration (ms): " + duration);
        System.out.println("File Size (bytes): " + fileSize);
        System.out.println("Bit Rate: " + bitRate);
        
        // iterate through the streams to print their meta data
        for (int i=0; i<numStreams; i++) {
            
            // find the stream object
            IStream stream = container.getStream(i);
            
            // get the pre-configured decoder that can decode this stream;
            IStreamCoder coder = stream.getStreamCoder();
            
            System.out.println("*** Start of Stream Info ***");
            System.out.println( stream.getDuration());
            System.out.printf("stream %d: ", i);
            System.out.printf("type: %s; ", coder.getCodecType());
            System.out.printf("codec: %s; ", coder.getCodecID());
            System.out.printf("duration: %s; ", stream.getDuration());
            System.out.printf("start time: %s; ", container.getStartTime());
            System.out.printf("timebase: %d/%d; ",
                 stream.getTimeBase().getNumerator(),
                 stream.getTimeBase().getDenominator());
            System.out.printf("codec tb: %d/%d; ",
                 coder.getTimeBase().getNumerator(),
                 coder.getTimeBase().getDenominator());
            System.out.println();
            
            if (coder.getCodecType() == ICodec.Type.CODEC_TYPE_AUDIO) {
                System.out.printf("sample rate: %d; ", coder.getSampleRate());
                System.out.printf("channels: %d; ", coder.getChannels());
                System.out.printf("format: %s", coder.getSampleFormat());
            } 
            else if (coder.getCodecType() == ICodec.Type.CODEC_TYPE_VIDEO) {
                System.out.printf("width: %d; ", coder.getWidth());
                System.out.printf("height: %d; ", coder.getHeight());
                System.out.printf("format: %s; ", coder.getPixelType());
                System.out.printf("frame-rate: %5.2f; ", coder.getFrameRate().getDouble());
            }
            
            System.out.println();
            System.out.println("*** End of Stream Info ***");
            
        }
        
    }
	
}
