<#-- @ftlvariable name="" type="org.multibit.exchange.infrastructure.dropwizard.views.PrivateFreemarkerView" -->
<!DOCTYPE html>
<html lang="en">
<head>
<#include "../includes/common/head.ftl">
</head>

<body>
<#include "../includes/common/navbar.ftl">
<#include "../includes/common/container-header.ftl">
<div>

    <h1>Profile</h1>

    <p>Email address: "${model.user.emailAddress ! "Unknown"}"</p>

    <h1>GitHub profile</h1>
<#if model.message??>
    <h2>Create a new repository</h2>

    <form action="/github/repository" method="post" id="github_repo_form">
        <h3>Enter repository information</h3>
        <fieldset>
            <input id="repoName" name="repoName" size="20" type="text" placeholder="The repo name"/>
            <input id="repoDescription" name="repoDescription" size="200" type="text" placeholder="The repo description"/>
            <input id="repoWebsite" name="repoWebsite" size="200" type="text" placeholder="The website URL for the project"/>
            <button class="btn btn-large btn-primary" type="submit">Create</button>
        </fieldset>
    </form>
<#else>
    <p><a href="/oauth/github/authorize">Requires authorization</a></p>
</#if>

</div>

<#include "../includes/common/container-footer.ftl">

<#include "../includes/common/cdn-scripts.ftl">

</body>
</html>