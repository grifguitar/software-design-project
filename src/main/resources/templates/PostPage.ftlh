<#-- @ftlvariable name="comments" type="itmo.practice.form.CommentCredentials[]" -->
<#-- @ftlvariable name="commentForm" type="itmo.practice.form.CommentCredentials" -->
<#-- @ftlvariable name="isLogged" type="boolean" -->
<#-- @ftlvariable name="postGet" type="itmo.practice.domain.Post" -->
<#import "commons.ftlh" as c>

<@c.page>
    <@c.post post=postGet visible=false/>
    <#if isLogged>
        <div class="form">
            <div class="body">
                <form method="post" action="" id="messageForm">
                    <input type="hidden" name="action" value="writeComment"/>
                    <div class="field">
                        <div class="name">
                            <label for="text">Message</label>
                        </div>
                        <div class="value">
                            <textarea id="text" name="text">${commentForm.text!}</textarea>
                        </div>
                        <@c.error "commentForm.text"/>
                    </div>
                    <div class="button-field">
                        <input type="submit" value="Write">
                    </div>
                </form>
            </div>
        </div>
    </#if>
    <#list comments as comment>
        <@c.comment comment=comment/>
    </#list>
</@c.page>