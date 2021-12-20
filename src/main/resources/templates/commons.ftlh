<#-- @ftlvariable name="client" type="itmo.practice.domain.Client" -->
<#-- @ftlvariable name="notification" type="java.lang.String" -->
<#import "/spring.ftl" as spring/>
<#macro page>
    <!DOCTYPE html>
    <html lang="en">
    <head>
        <meta charset="UTF-8">
        <title>AniGram</title>
        <link rel="icon" type="image/png" href="<@spring.url '/favicon.png'/>"/>
        <link rel="stylesheet" type="text/css" href="<@spring.url '/css/normalize.css'/>">
        <link rel="stylesheet" type="text/css" href="<@spring.url '/css/style.css'/>">
        <link rel="stylesheet" type="text/css" href="<@spring.url '/css/form.css'/>">
        <link rel="stylesheet" type="text/css" href="<@spring.url '/css/datatable.css'/>">
        <link rel="stylesheet" type="text/css" href="<@spring.url '/css/article.css'/>">
    </head>
    <body>
    <header>
        <p class="logo">
            <a href="/">
                <img src="<@spring.url '/img/logo.png'/>" alt="AniGram"
                     title="AniGram"/>
            </a>
        </p>
        <div class="enter-or-register-box">
            <#if client??>
                ${client.login}
                |
                <a href="/logout">Logout</a>
            <#else>
                <a href="/enter">Enter</a>
                |
                <a href="/register">Register</a>
            </#if>
        </div>
        <nav>
            <ul>
                <li><a href="/">Chats</a></li>
                <li><a href="/clients/all">Clients</a></li>
                <#if client??>
                    <li><a href="/writeFriend">Find Friend</a></li>
                    <li><a href="/friends">Friends</a></li>
                </#if>
            </ul>
        </nav>
    </header>
    <div class="middle">
        <main>
            <#nested/>
        </main>
    </div>
    <footer>
        <#if notification??>
            ${notification}
        </#if>
    </footer>
    </body>
    </html>
</#macro>

<#macro error field>
    <@spring.bind field/>
    <#if spring.status.errorMessage??>
        <div class="error">${spring.status.errorMessage}</div>
    </#if>
</#macro>

<#macro chat chat visible>
<#-- @ftlvariable name="chat" type="itmo.practice.domain.Chat" -->
    <div class="post">
        <div class="title"><a href="/chat/${chat.id}">${chat.title}</a></div>
        <#if visible>
            <div class="footer">
                <#if chat.getLastMessage()??>
                    ${chat.getLastMessage()}
                </#if>
            </div>
        </#if>
    </div>
</#macro>

<#macro message message>
<#-- @ftlvariable name="message" type="itmo.practice.form.MessageCredentials" -->
    <div class="comment">
        <div class="information">${message.login}</div>
        <div class="body">
            ${message.text?no_esc}
        </div>
    </div>
</#macro>
