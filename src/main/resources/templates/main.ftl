<!DOCTYPE html>
<html>
  <!-- Adapted from CS032 main.ftl -->
  <head>
    <meta charset="UTF-8">
    <title>${title}</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/normalize/8.0.0/normalize.min.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/4.0.0/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Roboto">

    <#include "requestHeader.ftl">
  </head>

  <body>

  ${content}

  <div id="footer" align="center">
    &copy; 2018
    <a href="https://github.com/GaryZhou98" target="_blank">Gary</a>,
    <a href="https://github.com/jeffreyzhong" target="_blank">Jeffrey</a>,
    <a href="https://github.com/Yangsong525" target="_blank">Song</a> and
    <a href="https://github.com/yamstudio" target="_blank">Yujun</a>
    | <a href="https://www.brown.edu/" target="_blank">Brown University</a>
  </div>

  </body>
  <!-- See http://html5boilerplate.com/ for a good place to start
       dealing with real world issues like old browsers.  -->
</html>
