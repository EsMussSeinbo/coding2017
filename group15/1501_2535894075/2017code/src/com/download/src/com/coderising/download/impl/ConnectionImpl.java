package com.coderising.download.impl;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;

import com.coderising.download.api.Connection;

public class ConnectionImpl implements Connection{
	private HttpURLConnection conn;
	public ConnectionImpl(HttpURLConnection conn){
		this.conn=conn;
	}

	@Override
	public byte[] read(int startPos, int endPos) throws IOException {
		byte[] buffer=new byte[endPos-startPos+1];
		ByteArrayOutputStream bos=new ByteArrayOutputStream();
		conn.connect();
		if(conn!=null){
			conn.setRequestProperty("Range", "bytes="+startPos+"-"+endPos);
		}
		int code=conn.getResponseCode();
		if(code==206){
			InputStream in=conn.getInputStream();
			int len=0;
			byte[] b = new byte[1024];
			while((len=in.read(b))!=-1){
				bos.write(b,0,len);
			}
			buffer=bos.toByteArray();
		}
		return buffer;
	}

	@Override
	public int getContentLength() {
		
		return conn.getContentLength();
	}

	@Override
	public void close() {
		try {
			InputStream input=conn.getInputStream();
			if(input!=null){
				input.close();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
