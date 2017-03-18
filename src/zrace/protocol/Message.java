package zrace.protocol;

import java.io.Serializable;

public class Message implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int messageId;
	
	public Message(int messageId){
		this.messageId = messageId;
	}

	public int getMessageId() {
		return messageId;
	}

	public void setMessageId(int messageId) {
		this.messageId = messageId;
	}

	@Override
	public String toString() {
		return "Message [messageId=" + messageId + "]";
	}
	

}
