package com.chaos.xml.semantics;

import freemarker.core.OutputFormat;
import freemarker.core.PlainTextOutputFormat;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;

import java.io.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import static freemarker.template.Configuration.DISABLE_AUTO_ESCAPING_POLICY;

public class Parser {

    public void parse(String file) throws DocumentException, IOException, TemplateException {
        Configuration cfg = new Configuration(Configuration.VERSION_2_3_27);
        cfg.setDirectoryForTemplateLoading(new File(Parser.class.getResource("/").getPath()));
        cfg.setDefaultEncoding("UTF-8");
        cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
        cfg.setLogTemplateExceptions(false);
        cfg.setWrapUncheckedExceptions(true);
        cfg.setOutputFormat(PlainTextOutputFormat.INSTANCE);

        SAXReader reader = new SAXReader();
        Document document = reader.read(new File(file));

        String projectName = document.selectSingleNode("//ROOT/PROJECT").getText().toLowerCase();
        Element beans = (Element)document.selectSingleNode("//ROOT/BEANS");

        for (Iterator<Element> it = beans.elementIterator(); it.hasNext();) {
            Element element = it.next();
            Map root = new HashMap();
            root.put("beanName", element.getName());
            root.put("projectName", projectName);
            Map fieldData = new HashMap();
            for (Iterator<Element> iit = element.elementIterator(); iit.hasNext();) {
                Element ielement = iit.next();
                fieldData.put(ielement.getName(), ielement.attribute("type").getValue()
                .replaceAll("\\[", "\\<")
                .replaceAll("]", "\\>"));
            }
            root.put("fieldData", fieldData);
            Template temp = cfg.getTemplate("test.ftlh");

            Writer out = new OutputStreamWriter(new FileOutputStream(new File("/Users/zcfrank1st/IdeaProjects/xml-semantics/src/main/java/com/chaos/xml/semantics/" + root.get("beanName") + ".java")));
            temp.process(root, out);
        }
    }
}
