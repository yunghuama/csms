package com.platform.util;

import java.util.UUID;
public class Uuid {

    public static String getUuid() {
        String uuid = UUID.randomUUID().toString();
        return uuid.replace("-", "");
    }
    
	public static void main(String[] args) {
		for (int i=0;i<10;i++) {
			String uuid = getUuid();
			System.out.println(uuid);			
		}
	}    
}
