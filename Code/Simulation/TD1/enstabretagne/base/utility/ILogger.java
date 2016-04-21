/**
* Classe ILogger.java
*@author Olivier VERRON
*@version 1.0.
*/
package enstabretagne.base.utility;

import java.time.temporal.Temporal;
import java.util.HashMap;

import enstabretagne.base.time.LogicalDateTime;


public interface ILogger {
	boolean open(HashMap<String,Object> initParams);
	void log(Temporal t, LogicalDateTime d, LogLevels level, Object obj,String function,String message,Object... args);
	void close();
	void clear(HashMap<String,Object> initParams);
}

