package com.monoly.PQT_Utilities;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

public class RetryTestCaseOnFailure  implements IRetryAnalyzer {
	
		public int count;
			
			
		public boolean retry(ITestResult result)
		{
				
			if(count<3)
			{
				count++;
				result.setStatus(ITestResult.SKIP);
				return true;
			}
			else
			{
				result.setStatus(ITestResult.FAILURE);
				return false;
			}
						
				
		}

	

}
