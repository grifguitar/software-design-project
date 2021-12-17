<#-- @ftlvariable name="friends" type="itmo.practice.domain.Client[]" -->
<#-- @ftlvariable name="friend" type="itmo.practice.form.FriendCredentials" -->
<#import "commons.ftlh" as c>

<@c.page>
    <div class="friends datatable">
        <div class="caption">Friends</div>
        <table>
            <thead>
            <tr>
                <th>Id</th>
                <th>Login</th>
                <th>Create chat <@c.error "friend.login"/></th>
            </tr>
            </thead>
            <tbody>
            <#if friends?size==0>
                <tr class="noData">
                    <td colspan="3">
                        No data
                    </td>
                </tr>
            <#else>
                <#list friends as client>
                    <tr>
                        <td class="id">${client.id}</td>
                        <td class="login">${client.login}</td>
                        <td class="creationTime">
                            <form method="post" action="">
                                <input type="hidden" name="action" value="writeFriend"/>
                                <input type="hidden" id="login" name="login" value="${client.login!}"/>
                                <input type="hidden" id="currentLogin" name="currentLogin"
                                       value="${friend.currentLogin!}"/>
                                <div class="button-field">
                                    <input type="submit" value="chat">
                                </div>
                            </form>
                        </td>
                    </tr>
                </#list>
            </#if>
            </tbody>
        </table>
    </div>
</@c.page>