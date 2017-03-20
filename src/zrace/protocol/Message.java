package zrace.protocol;

import java.io.Serializable;

/**
 * This class supplies API for {@link Message} 
 * @author Zina K
 *
 */
public class Message implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Message is
	 */
	private int messageId;
	
	/**
	 * @param messageId
	 */
	public Message(int messageId){
		this.messageId = messageId;
	}

	/**
	 * @return int
	 */
	public int getMessageId() {
		return messageId;
	}

	/**
	 * @param messageId
	 */
	public void setMessageId(int messageId) {
		this.messageId = messageId;
	}

	@Override
	public String toString() {
		return "Message [messageId=" + messageId + "]";
	}
	

}
