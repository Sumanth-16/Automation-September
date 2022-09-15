package com.utilities;

public interface TimeOuts 
{
	int VERYSHORTWAIT = UtilityMethods.getWaitTime("VERYSHORTWAIT");
	int SHORTWAIT = UtilityMethods.getWaitTime("SHORTWAIT");
	int MEDIUMWAIT = UtilityMethods.getWaitTime("MEDIUMWAIT");
	int LONGWAIT = UtilityMethods.getWaitTime("LONGWAIT");
	int VERYLONGWAIT = UtilityMethods.getWaitTime("VERYLONGWAIT");
	int IMPLICITWAIT = UtilityMethods.getWaitTime("IMPLICITWAIT");

}
