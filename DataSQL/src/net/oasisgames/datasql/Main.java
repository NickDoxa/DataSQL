package net.oasisgames.datasql;

import net.oasisgames.datasql.database.Connection;
import net.oasisgames.datasql.files.UserPreference;
import net.oasisgames.datasql.frames.controllers.MainMenuControl;

/**
 * <html><body>
 *	 <div id="div">
 *	   <h1 id="aqua">DataSQL</h1>
 *	   <h2 id="white"><i>A Windows Form App</i></h2>
 *     <p>This app was designed to simplify and expedite the process of sending/receiving
 *     data to/from an SQL Database. It is not an advanced software, it is a project I developed
 *     to test my own knowledge of Java and SQL. If you think it sucks, feel free to never let
 *     it run on your computer. If you want to use it, have at it. If you are a hiring manager
 *     looking at my portfolio, this project includes:</p>
 *     <ul id="white">
 *       <li>Java JDK/IDE Knowledge</li>
 *       <li>JFrame/NetBeans Knowledge</li>
 *       <li>Lambda and Assertion
 *       <li>SRP (Single Resource Purpose) Principals</li>
 *       <li>Thread Knowledge and API creation</li>
 *       <li>SQL Language knowledge</li>
 *       <li>SQL API creation</li>
 *       <li>Java IO file management</li>
 *       <li>Algorithmic knowledge and application</li>
 *       <li>JavaX/Swing fundamentals</li>
 *       <li>Database concepts and foundation</li>
 *     </ul>
 *     <p id="aqua"><b>Contact:</b> <i>nick@oasisgames.net</i></p>
 * 	 </div>
 * <style>
 * h1 {
 * 	font-size:18px;
 * 	font-family: Arial;
 * }
 * h2 {
 * 	font-size: 16px;
 * 	font-family: Arial;
 * }
 * p, li {
 * 	font-family: Candara;
 * 	font-size: 14px;
 * }
 * #aqua {
 *  color: #9BBACF;
 * }
 * #white {
 * 	color: white;
 * }
 * #div {
 * 	background-image: linear-gradient(to right, black , gray);
 * }
 * </style>
 * </body></html>
 * @author <p><b>Nick Doxa</b></p>
 * @version <p><b>1.0</b></p>
 */
public class Main {

	/**
	 * @apiNote The main method of the application.<br>Created by Nick Doxa.
	 */
	public static void main(String[] args) {
		Connection.setDebug(true);
		new UserPreference();
		MainMenuControl.Singleton().run();
	}
	
}
