package log4j;

import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.pattern.ConverterKeys;
import org.apache.logging.log4j.core.pattern.LogEventPatternConverter;

@Plugin(name = "Convertor_Br", category = "Converter")
@ConverterKeys({"h","Br"})
public class Convertor_Br extends LogEventPatternConverter{

	    private Convertor_Br(final String name, final String style) {
	    	
	        super(name, style);
	    }

	    public static Convertor_Br newInstance(final String[] options) {
	        return new Convertor_Br("Br", "Br");
	    }

		@Override
		public void format(LogEvent event, StringBuilder toAppendTo) {
			toAppendTo.append("</font><br>");
		}
	
}	


