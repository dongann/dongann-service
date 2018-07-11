package com.dongann.app.utils;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

public class SerialUtil {
	private static Log log = LogFactory.getLog(SerialUtil.class);
	private static final String DEFAULT_APP = ".serial";
	private static final Hashtable serials = new Hashtable();
	private static final Hashtable fchannels = new Hashtable();
	private static int SEQUENCE_SEGMENT_SIZE = 100;

	private static Map<String,SeqSeg> seqMap = new HashMap<String,SeqSeg>();

	/**
	 * 获取下一个序列号
	 *
	 * @return 下一个序列号, 如果出现错误, 则返回0
	 */
	public static long next() {
		return next("");
	}

	public synchronized  static long next(String app) {
		String appKey = app + DEFAULT_APP;
		FileChannel fc = (FileChannel) fchannels.get(appKey);
		MappedByteBuffer serial = (MappedByteBuffer) serials.get(appKey);
		try {
			if (serial == null) {
				//获得一个可读写的随机存取文件对象
				RandomAccessFile RAFile = new RandomAccessFile(appKey, "rw");
				if (RAFile.length() < 8) {
					RAFile.writeLong(0);
				}

				//获得相应的文件通道
				fc = RAFile.getChannel();

				//取得文件的实际大小，以便映像到共享内存
				int size = (int) fc.size();

				//获得共享内存缓冲区，该共享内存可读写
				serial = fc.map(FileChannel.MapMode.READ_WRITE, 0, size);

				fchannels.put(appKey, fc);
				serials.put(appKey, serial);
			}

			FileLock flock = fc.lock();
			serial.rewind();
			long serno = serial.getLong();
			serno++;
			serial.flip();
			serial.putLong(serno);
			flock.release();

			return serno;
		} catch (IOException e) {
			log.warn(e.getMessage(), e);
			return 0l;
		}
	}

	public static SeqSeg nextSeg(String key, int segSize) {
		String appKey = key + DEFAULT_APP;
		FileChannel fc = (FileChannel) fchannels.get(appKey);
		MappedByteBuffer serial = (MappedByteBuffer) serials.get(appKey);
		try {
			if (serial == null) {
				//获得一个可读写的随机存取文件对象
				RandomAccessFile RAFile = new RandomAccessFile(appKey, "rw");
				if (RAFile.length() < 8) {
					RAFile.writeLong(0);
				}

				//获得相应的文件通道
				fc = RAFile.getChannel();

				//取得文件的实际大小，以便映像到共享内存
				int size = (int) fc.size();

				//获得共享内存缓冲区，该共享内存可读写
				serial = fc.map(FileChannel.MapMode.READ_WRITE, 0, size);

				fchannels.put(appKey, fc);
				serials.put(appKey, serial);
			}

			FileLock flock = fc.lock();
			serial.rewind();
			long seq = serial.getLong();
			serial.flip();
			serial.putLong(seq+segSize);
			flock.release();

			return new SeqSeg(seq,seq+segSize);
		} catch (IOException e) {
			log.warn(e.getMessage(), e);
			return new SeqSeg(0,0);
		}
	}

	public synchronized static String nextSeq(String key, int width){
		SeqSeg zone = seqMap.get(key);
		if(seqMap.get(key) == null || zone.isUsedUp()){
			zone = nextSeg(key,SEQUENCE_SEGMENT_SIZE);
			seqMap.put(key, zone);
		}

		return formatSequence(zone.next(),width);
	}

	/**
	 *
	 * description: 将传入流水长度限制为一定值
	 * @param seq
	 * @param width
	 * @return
	 */
	private static String formatSequence(long seq, int width) {
		String res = String.format("%0" + width + "d", seq);
		if (res.length() > width) {
			res = res.substring(res.length()-width, res.length());
		}

		return res;
	}

	private static class SeqSeg{
		private long low;
		private long high;
		public SeqSeg(long low, long high){
			this.low = low;
			this.high = high;
		}
		public long next() {
			return low++;
		}
		public boolean isUsedUp(){
			return low >= high;
		}
	}

	public static void main(String[] args) throws IOException {
		int c = 201;
		while (c-- > 0) {
			System.out.println(SerialUtil.nextSeq("test",8));
		}
	}
}