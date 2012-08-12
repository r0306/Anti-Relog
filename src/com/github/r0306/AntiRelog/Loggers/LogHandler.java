package com.github.r0306.AntiRelog.Loggers;

import java.util.logging.Handler;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

import com.github.r0306.AntiRelog.Listeners.LogPrevention;

public class LogHandler
{

	private static Logger log = Logger.getLogger("Minecraft");
	
	public LogHandler()
	{
    
		log.addHandler(new Handler()
		{
			
			@Override
			public void publish(LogRecord logRecord)
			{
	        
				String mystring = logRecord.getMessage();
	            
				if(mystring.contains(" lost connection: "))
	            {
	            
					String myarray[] = mystring.split(" ");
	                String DisconnectMessage = myarray[3];
	                LogPrevention.setDisconnectMessage(DisconnectMessage);
	            
	            }
		
			}

			@Override
			public void flush()
			{
			
			}

			@Override
			public void close() throws SecurityException
			{
			
			}
	
		});
		
	}
	
}
