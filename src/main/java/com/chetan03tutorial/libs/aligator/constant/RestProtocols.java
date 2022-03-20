package com.chetan03tutorial.libs.aligator.constant;

import com.chetan03tutorial.libs.aligator.client.rest.*;
import com.gapinc.stores.libs.aligator.client.rest.*;

public enum RestProtocols {

	GET(new Get()), POST(new Post()), PUT(new Put()), DELETE(new Delete());

	RestProtocols(HttpProtocol protocol){
		this.protocol = protocol;
	}

	public static HttpProtocol protocol(String protocol){
		return valueOf(protocol.toUpperCase()).protocol;
	}

	private final HttpProtocol protocol;
}
