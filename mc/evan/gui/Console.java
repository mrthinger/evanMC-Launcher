package mc.evan.gui;

import java.io.IOException;
import java.io.OutputStream;

import javafx.application.Platform;
import javafx.scene.control.TextArea;

public class Console extends OutputStream {
	private TextArea o;

	public Console(TextArea ta) {
		this.o = ta;
	}

	@Override
	public void write(final int i) throws IOException {
		   Platform.runLater(new Runnable() {
		        public void run() {
		            o.appendText(String.valueOf((char) i));
		        }
		    });
		}
	}


