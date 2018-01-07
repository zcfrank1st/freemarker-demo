import com.chaos.xml.semantics.Parser;
import freemarker.template.TemplateException;
import org.dom4j.DocumentException;

import java.io.IOException;

public class Test {
    public static void main(String[] args) throws DocumentException, IOException, TemplateException {
        new Parser().parse("/Users/zcfrank1st/Desktop/project.xml");
    }
}
