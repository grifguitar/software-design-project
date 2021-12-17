<#-- @ftlvariable name="clients" type="itmo.practice.domain.Client[]" -->
<#import "commons.ftlh" as c>

<@c.page>
    <div class="clients datatable">
        <div class="caption">Clients</div>
        <table>
            <thead>
            <tr>
                <th>Id</th>
                <th>Login</th>
                <th>Created</th>
            </tr>
            </thead>
            <tbody>
            <#if clients?size==0>
                <tr class="noData">
                    <td colspan="3">
                        No data
                    </td>
                </tr>
            <#else>
                <#list clients as client>
                    <tr>
                        <td class="id">${client.id}</td>
                        <td class="login">${client.login}</td>
                        <td class="creationTime">${client.creationTime}</td>
                    </tr>
                </#list>
            </#if>
            </tbody>
        </table>
    </div>
</@c.page>