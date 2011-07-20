package upnpdevicetest;

import java.io.IOException;
import java.io.InputStream;

import org.osgi.service.upnp.UPnPIcon;

public class IconImpl implements UPnPIcon {
	
	private int height;
	private int width;
	private int depth;
	private int size;
	private String type;
	private String name;
	
	//creates an icon
	IconImpl(int height, int width, int depth, int size,
			String type, String name) {
		this.height = height;
		this.width = width;
	    this.depth = depth;
	    this.size = size;
	    this.type = type;
	    this.name = name;
	}

	public String getMimeType() {
		return type;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public int getSize() {
		return size;
	}

	public int getDepth() {
		return depth;
	}

	public InputStream getInputStream() throws IOException {
		return this.getClass().getResourceAsStream(name);
	}

}
