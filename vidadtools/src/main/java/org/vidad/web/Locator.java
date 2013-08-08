package org.vidad.web;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

public class Locator {
	String geoplugin = "http://www.geoplugin.net/xml.gp?ip=";

	public String getInfoFromGeoPlugin(String ip) {
		String url = geoplugin + ip;
		try {
			InputStream is = new URL(url).openStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			StringBuilder sb = new StringBuilder();
			String line;
			while ((line = br.readLine()) != null) {
				sb.append(line);
			}
			br.close();
			return sb.toString();

		} catch (IOException e) {
			e.printStackTrace();
		}
		return "";
	}

}
