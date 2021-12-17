<#-- @ftlvariable name="friend" type="itmo.practice.form.FriendCredentials" -->
<#import "/spring.ftl" as spring>
<#import "commons.ftlh" as c>

<@c.page>
    <div class="registration form-box">
        <div class="header">Add Friend</div>
        <div class="body">
            <form method="post" action="">
                <input type="hidden" name="action" value="writeFriend"/>
                <input type="hidden" id="currentLogin" name="currentLogin" value="${friend.currentLogin!}"/>
                <div class=" field">
                    <div class="name">
                        <label for="login">Friend Login</label>
                    </div>
                    <div class="value">
                        <input id="login" name="login" value="${friend.login!}"/>
                    </div>
                    <@c.error "friend.login"/>
                </div>
                <div class="button-field">
                    <input type="submit" value="Add">
                </div>
            </form>
        </div>
    </div>
</@c.page>