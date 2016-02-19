package com.gp.浏览器;

import java.awt.EventQueue;
import java.awt.Frame;
import java.io.IOException;

import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.WindowConstants;

/**
 * @author 高攀
 * @下午7:45:34
 */
public class SimpleWebBrowser {
	public static void main(String[] args) {
		String initialPage = "http://bbs.miercn.com/bd/201510/thread_568529_1.html?source=bdxsy";

		JEditorPane jep = new JEditorPane();
		jep.setEditable(false);
		jep.addHyperlinkListener(new LinkFollower(jep));

		try {
			jep.setPage(initialPage);
		} catch (IOException e) {
			System.err.println("Usage: java simpleWebBrowser url");
			System.err.println(e);
			System.exit(1);
		}

		JScrollPane scrollPane = new JScrollPane(jep);
		JFrame f = new JFrame("Simple Web Browser");
		f.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		f.setContentPane(scrollPane);
		f.setSize(512, 342);
		f.setVisible(true);
		f.setDefaultCloseOperation(0);
		EventQueue.invokeLater(new FrameShower(f));
	}

	private static class FrameShower implements Runnable {

		private final Frame frame;

		public FrameShower(Frame frame) {
			this.frame = frame;
		}

		public void run() {
			frame.setVisible(true);
		}

	}
}
