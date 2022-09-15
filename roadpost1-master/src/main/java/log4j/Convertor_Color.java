package log4j;

import java.util.Arrays;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.pattern.ConverterKeys;
import org.apache.logging.log4j.core.pattern.LogEventPatternConverter;

@Plugin(name = "Convertor_Color", category = "Converter")
@ConverterKeys({"H","color","htmlColorTag"})
public class Convertor_Color extends LogEventPatternConverter{

	    private Convertor_Color(final String name, final String style) {	    	
	        super(name, style);
	    }

	    public static Convertor_Color newInstance(final String[] options) {
	        return new Convertor_Color("H", "H");
	    }

		@Override
		public void format(LogEvent event, StringBuilder toAppendTo) {
			if (event.getLevel()==Level.INFO) {
				toAppendTo.append("<font face='trendy' color= 'LimeGreen' size='3.5'>");
			}
			else if (event.getLevel()==Level.ERROR) {
				toAppendTo.append("<font face='trendy' color= 'red' size='3.5'>");
			}
			else if (event.getLevel()==Level.WARN) {
				toAppendTo.append("<font face='trendy' color= 'orange' size='3.5'>");
			}
			else if (event.getLevel()==Level.DEBUG) {
				toAppendTo.append("<font face='trendy' color= 'black' size='3.5'>");
			}
			else if (event.getLevel()==Level.FATAL) {
				toAppendTo.append("<font face='trendy' color= 'black' size='3.5'>");
			}
	}
	
}	


