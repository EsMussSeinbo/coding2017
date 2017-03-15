package com.coderising.download;

import java.io.IOException;
import java.io.RandomAccessFile;

import com.coderising.download.api.Connection;
import com.coderising.download.api.DownloadListener;
import com.coderising.download.impl.ConnectionManagerImpl;

public class DownloadThread extends Thread{

	int startPos;
	int endPos;
	String url;
	String path;
	DownloadListener listener;
	private static int count=0;
	private Object lock= new Object();


	public DownloadThread(String url, String path, DownloadListener listener,
			int startpos, int endpos) {
		// TODO Auto-generated constructor stub
		this.startPos = startpos;
		this.endPos = endpos;
		this.url=url;
		this.path=path;
		this.listener=listener;
	}
	public void run(){	
		RandomAccessFile raf=null;
		try{
			Connection conn=new ConnectionManagerImpl().open(url);
			raf=new RandomAccessFile(path,"rwd");
			byte[] data=conn.read(startPos,endPos);
			raf.seek(startPos);
			raf.write(data);
		}catch(Exception e){
			throw new RuntimeException("读取错误");
		}finally{
			try{
				if(raf!=null){
					raf.close();
				}
			}catch(IOException e){
				e.printStackTrace();
			}
		}
	}
}
