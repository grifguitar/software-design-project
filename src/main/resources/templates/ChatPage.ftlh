<#-- @ftlvariable name="messages" type="itmo.practice.form.MessageCredentials[]" -->
<#-- @ftlvariable name="messageForm" type="itmo.practice.form.MessageCredentials" -->
<#-- @ftlvariable name="isLogged" type="boolean" -->
<#-- @ftlvariable name="chatGet" type="itmo.practice.domain.Chat" -->
<#import "commons.ftlh" as c>

<@c.page>
    <@c.chat chat=chatGet visible=false/>
    <#if isLogged>
        <div class="form">
            <div class="body">
                <form method="post" action="" id="messageForm">
                    <input type="hidden" name="action" value="writeMessage"/>
                    <div class="field">
                        <div class="name">
                            <label for="text">Message</label>
                        </div>
                        <div class="value">
                            <textarea id="text" name="text">${messageForm.text!}</textarea>
                        </div>
                        <@c.error "messageForm.text"/>
                    </div>
                    <div class="button-field">
                        <input type="submit" value="Write">
                    </div>
                </form>
            </div>
        </div>
    </#if>
    <#list messages as message>
        <@c.message message=message/>
    </#list>
</@c.page>
