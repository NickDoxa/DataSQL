package net.oasisgames.datasql;

import java.util.*;

public abstract class TaskAPI extends Thread {

	protected static final Map<Integer, Thread> id;
	private static int count;
	static {
		id = new HashMap<Integer, Thread>();
		count = 0;
	}
	
	/**
	 * @apiNote These enums are used to determine the type of task being run:
	 * <ul>
	 * 	<li><b>SINGLE</b> - for one time tasks</li>
	 * 	<li><b>REPEAT</b> - for repeating tasks</li>
	 * </ul>
	 */
	public static enum TaskType {
		SINGLE, REPEAT;
	}
	
	/**
	 * @param run - (Runnable) The new runnable to execute
	 * @param startDelay - (long) The delay before the method executes
	 * @return Integer - The Task ID
	 * @throws InterruptedException
	 * @apiNote This method is used to create a new Thread with an optional delay before it starts
	 * and then runs the defined Runnable. Returns an Integer that is linked as the ID to the task Thread.
	 */
	public static int runTaskThread(Runnable run, long startDelay) throws InterruptedException {
		Thread thread = new Thread(() -> {
			try {
				sleep(startDelay);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			Thread task = new CreateTask(run, count);
			id.put(count, task);
			task.start();
		});
		thread.start();
		return count++;
	}
	
	/**
	 * @param run - (Runnable) The new runnable to execute
	 * @param startDelay - (long) The delay before the method executes
	 * @param repeatDelay - (long) The delay before the method executes again after the first
	 * @return Integer - The Task ID
	 * @throws InterruptedException 
	 * @apiNote This method is used to create a new Thread with an optional delay before it starts
	 * and then runs the defined Runnable. Returns an Integer that is linked as the ID to the task Thread.
	 */
	public static int runRepeatingTaskThread(Runnable run, long startDelay, long repeatDelay) throws InterruptedException {
		count++;
		Thread thread = new Thread(() -> {
			try {
				sleep(startDelay);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			Thread task = new CreateRepeatingTask(run, repeatDelay, count);
			id.put(count, task);
			task.start();
		});
		thread.start();
		return count;
	}
	
	/**
	 * @apiNote This method is used to end a running thread from this API
	 * <br><b>DO NOT USE TO END A THREAD THAT WAS NOT CREATED BY THIS API</b>
	 * @param task - (Integer) The task ID
	 * @param type - (TaskType Enum) The type of task running (SINGLE, REPEAT)
	 */
	public static void endThread(int task, TaskType type) {
		if (!id.containsKey(task)) return;
		Thread thread = id.get(task);
		switch (type) {
			case SINGLE:
				CreateTask singleTask = (CreateTask) thread;
				singleTask.end();
				break;
			case REPEAT:
				CreateRepeatingTask repeatTask = (CreateRepeatingTask) thread;
				repeatTask.end();
				break;
		}
	}
	
}

class CreateTask extends Thread {
	
	private final Runnable run;
	private final int id;
	
	/**
	 * @apiNote This class creates a one time, optionally delayed, thread
	 * @param run - (Runnable) The runnable to execute
	 * @param id - (Integer) The taskID;
	 */
	public CreateTask(Runnable run, int id) {
		this.run = run;
		this.id = id;
	}
	
	/**
	 * @apiNote This method gets the task ID
	 * @return Integer - The task ID
	 */
	public int getID() {
		return id;
	}
	
	@Override
	public void run() {
		run.run();
	}
	
	/**
	 * @apiNote This method ends the task and removes it from the id map
	 */
	public void end() {
		TaskAPI.id.remove(id);
		interrupt();
	}
	
}

class CreateRepeatingTask extends Thread {
	
	private final Runnable run;
	private final long delay;
	private boolean running;
	private final int id;
	
	/**
	 * @apiNote This class creates a repeating, optionally delayed, thread
	 * @param run - (Runnable) The runnable to execute
	 * @param delay - (Long) The delay between repeats
	 * @param id - (Integer) The task ID
	 */
	public CreateRepeatingTask(Runnable run, long delay, int id) {
		this.run = run;
		this.delay = delay;
		running = true;
		this.id = id;
	}
	
	/**
	 * @apiNote This method gets the task ID
	 * @return Integer - The task ID
	 */
	public int getID() {
		return id;
	}
	
	@Override
	public void run() {
		while(running) {
			run.run();
			try {
				sleep(delay);
			} catch (InterruptedException e) {
				System.out.println("Repeating Thread closed! ID: " + id);
				return;
			}
		}
		System.out.println("Repeating Thread closed! ID: " + id);
	}
	
	/**
	 * @apiNote This method ends the task and removes it from the id map
	 */
	public void end() {
		TaskAPI.id.remove(id);
		running = false;
		interrupt();
	}
	
}