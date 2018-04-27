<#assign content>
  <script>
    $(document).ready(function () {
    window.setTimeout(function () {
      location.href = "/";
    }, 5000);
  });</script>
  <p>Welcome to lecturepub, ${whoami}. Redirecting you to homepage in 5 seconds.</p>
</#assign>

<#include "main.ftl">
