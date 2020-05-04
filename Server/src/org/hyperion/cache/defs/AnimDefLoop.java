package org.hyperion.cache.defs;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;


public class AnimDefLoop {

    public static void dumpAnimDef(){
        for(ObjectDef objectDef : ObjectDef.objectDefinitions) {
        	if(objectDef == null || objectDef.animationId == -1) continue;
            addObjectAnim(objectDef.id, objectDef.animationId);
        }
    }

    public static void addObjectAnim(int objectId, int animId) {
		String filePath = "./data/objectanims.txt";
		try {
			BufferedWriter out = new BufferedWriter(new FileWriter(filePath, true));
			try {
                out.write("object id : " + objectId + " --- anim id : "+ animId);
				out.newLine();
			} finally {
				out.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
    }

    public static void main(String[] args){
        dumpAnimDef();
    }
}
