package com.coderising.download.impl;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import com.coderising.download.api.Connection;
import com.coderising.download.api.ConnectionException;
import com.coderising.download.api.ConnectionManager;
import com.sun.org.apache.xerces.internal.util.URI.MalformedURIException;

public class ConnectionManagerImpl implements ConnectionManager {

	@Override
	public Connection open(String url) throws ConnectionException {
		try{
			URL u=new URL(url);
			HttpURLConnection conn=(HttpURLConnection)u.openConnection();
			conn.setConnectTimeout(5000);
			conn.setRequestMethod("GET");
			int code=conn.getResponseCode();
			if(code==200){
				return new ConnectionImpl(conn);
			}else{
				throw new RuntimeException("链接错误");
			}
		}catch(IOException e){
			throw new RuntimeException("IO异常");
		}
	}

}
