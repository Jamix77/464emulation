package org.hyperion.cache;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class LoadWizard {
	
	private MapHashes[] hashes = new MapHashes[2000];

	public void loadJimmy() {
		try {
			BufferedReader file = new BufferedReader(new FileReader("./data/idx5NameHash.txt"));
			String line;
			int counter = 0;
			try {
				while((line = file.readLine()) != null) {
					String[] lol = line.split(":");
					hashes[counter] = new MapHashes(Integer.parseInt(lol[1]), Integer.parseInt(lol[0]));
					counter++;
				}
			} finally {
				file.close();
			}
		} catch(IOException e) {
			System.out.println("ops wrong spell");
		}
	}
	
	public int forID(int hash) {
		try {
		for(int i = 0; i < hashes.length; i++) {
			if(hashes[i].hash == hash) {
				return hashes[i].id;
			}
		}
		} catch(Exception e) {
			return -1;
		}
		System.out.println("reading -1...");
		return -1;

	}
	
	public MapHashes getHashes(int id) {
		return hashes[id];
	}
	
}
