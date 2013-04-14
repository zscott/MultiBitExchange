<#-- @ftlvariable name="" type="com.blurtty.peregrine.infrastructure.dropwizard.views.PublicFreemarkerView" -->
<div class="navbar navbar-inverse navbar-fixed-top">
    <div class="navbar-inner">
        <div class="container-fluid">
            <button type="button" class="btn btn-navbar" data-toggle="collapse" data-target=".nav-collapse">
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="brand" href="#">Peregrine</a>
            <div class="nav-collapse collapse">
                <ul class="nav">
                    <li class="active"><a href="#">Issues</a></li>
                    <li><a href="/about">About</a></li>
                    <li><a href="/faq">FAQ</a></li>
                <#if model.user?? >
                    <li><a href="/private/home">${model.user.firstName ! "Anonymous"}&nbsp;${model.user.lastName ! ""}</a></li>
                    <li><a href="/openid/logout">Logout</a></li>
                <#else>
                    <li><a href="/openid/login">Login</a></li>
                </#if>
                </ul>
                <form class="navbar-form pull-right">
                    <input class="span2" type="text" placeholder="Search">
                    <button type="submit" class="btn">Go</button>
                </form>
            </div><!--/.nav-collapse -->
        </div>
    </div>
</div>
<div class="alert">
    <button type="button" class="close" data-dismiss="alert">&times;</button>
    <strong>Warning!</strong> This is alpha software - you'll see bugs
</div>
