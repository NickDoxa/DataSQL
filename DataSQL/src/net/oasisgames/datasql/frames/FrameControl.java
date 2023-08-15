package net.oasisgames.datasql.frames;

public interface FrameControl {

	public static String lookAndFeel = "javax.swing.plaf.nimbus.NimbusLookAndFeel";
	
	/**
	 * @apiNote This method opens the current window <i>if it exists already</i>
	 * @see {@link net.oasisgames.datasql.frames.FrameControl#run() run()}
	 */
	void openWindow();
	
	/**
	 * @apiNote This method closes the current window without destroying it
	 */
	void closeWindow();
	
	/**
	 * @apiNote This method is used to create, open, and activate the frame
	 */
	void run();
	
	/**
	 * @apiNote This method gets the saved previous frame
	 * @return FrameControl Interface - Returns the previously open frame if one was available
	 */
	FrameControl getPreviousFrame();
	
	/**
	 * @apiNote This method is used to set the previous frame after launching the current
	 * @param frame - (FrameControl Interface) The previous frame
	 */
	void setPreviousFrame(FrameControl frame);
	
}
