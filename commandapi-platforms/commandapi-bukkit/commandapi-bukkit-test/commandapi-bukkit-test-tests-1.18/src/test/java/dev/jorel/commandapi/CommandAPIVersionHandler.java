package dev.jorel.commandapi;

import dev.jorel.commandapi.nms.NMS_1_16_R3;
import dev.jorel.commandapi.nms.NMS_1_17;
import dev.jorel.commandapi.nms.NMS_1_18_R1;
import dev.jorel.commandapi.nms.NMS_1_19_1_R1;
import dev.jorel.commandapi.nms.NMS_1_19_4_R3;
import dev.jorel.commandapi.test.MockNMS;

/**
 * This file handles loading the correct platform implementation. The CommandAPIVersionHandler
 * file within the commandapi-core module is NOT used at run time. Instead, the platform modules
 * replace this class with their own version that handles loads the correct class for their version
 */
public interface CommandAPIVersionHandler {
	
	public static final String profileId = getProfileId();
	public static final boolean IS_MOJANG_MAPPED = isMojangMapped();
	
	private static String getProfileId() {
		String profileIdProperty = System.getProperty("profileId");
		if(profileIdProperty != null) {
			if ( profileIdProperty.endsWith("_Mojang")) {
				return profileIdProperty.substring(0, profileIdProperty.length() - "_Mojang".length());
			} else {
				return profileIdProperty;
			}
		} else {
			return null;
		}
	}
	
	private static boolean isMojangMapped() {
		String profileIdProperty = System.getProperty("profileId");
		if(profileIdProperty != null) {
			return profileIdProperty.endsWith("_Mojang");
		} else {
			return false;
		}
	}
	
	static CommandAPIPlatform<?, ?, ?> getPlatform() {
		if(profileId == null) {
			System.out.println("Using default version 1.19.4");
			return new MockNMS(new NMS_1_19_4_R3());
		} else {
			return new MockNMS(switch(profileId) {
				case "Minecraft_1_19_4" -> new NMS_1_19_4_R3();
				case "Minecraft_1_19_2" -> new NMS_1_19_1_R1();
				case "Minecraft_1_18" -> new NMS_1_18_R1();
				case "Minecraft_1_17" -> new NMS_1_17();
				case "Minecraft_1_16_5" -> new NMS_1_16_R3();
				default -> throw new IllegalArgumentException("Unexpected value: " + System.getProperty("profileId"));
			});
		}
	}
	
	public static MCVersion getVersion() {
		if(profileId == null) {
			System.out.println("Using default version 1.19.4");
			return MCVersion.V1_19_4;
		} else {
			return switch(profileId) {
				case "Minecraft_1_19_4" -> MCVersion.V1_19_4;
				case "Minecraft_1_19_2" -> MCVersion.V1_19_2;
				case "Minecraft_1_18" -> MCVersion.V1_18;
				case "Minecraft_1_17" -> MCVersion.V1_17;
				case "Minecraft_1_16_5" -> MCVersion.V1_16_5;
				default -> throw new IllegalArgumentException("Unexpected value: " + System.getProperty("profileId"));
			};
		}
	}
}
