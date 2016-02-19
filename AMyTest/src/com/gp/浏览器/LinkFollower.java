package com.gp.浏览器;

import java.io.IOException;

import javax.swing.JEditorPane;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;

/**
 * @author 高攀
 * @下午7:43:30
 */
public class LinkFollower implements HyperlinkListener{  
  
    private JEditorPane pane;  
      
    public LinkFollower(JEditorPane pane){  
        this.pane = pane;  
    }  
      
    public void hyperlinkUpdate(HyperlinkEvent evt) {  
        if(evt.getEventType() == HyperlinkEvent.EventType.ACTIVATED){  
            try {  
                pane.setPage(evt.getURL());  
            } catch (IOException e) {  
                pane.setText("<html>could not load "+evt.getURL()+"</html>");  
            }  
        }  
    }  
  
} 