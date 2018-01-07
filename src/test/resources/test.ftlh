<#ftl auto_esc=false>
package com.chaos.xml.${projectName};

import lombok.Data;

@Data
class ${beanName} {
    <#list fieldData?keys as key>
    private ${fieldData[key]} ${key};
    </#list>
}