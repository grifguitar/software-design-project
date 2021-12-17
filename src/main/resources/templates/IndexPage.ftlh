<#-- @ftlvariable name="posts" type="itmo.practice.domain.Post[]" -->
<#import "commons.ftlh" as c>

<@c.page>
    <#list posts as post>
        <@c.post post=post visible=true/>
    </#list>
</@c.page>