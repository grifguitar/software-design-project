<#-- @ftlvariable name="chats" type="itmo.practice.domain.Chat[]" -->
<#import "commons.ftlh" as c>

<@c.page>
    <#list chats as chat>
        <@c.chat chat=chat visible=true/>
    </#list>
</@c.page>
