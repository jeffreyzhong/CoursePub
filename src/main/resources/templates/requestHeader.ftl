<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/js-cookie/2.2.0/js.cookie.min.js"></script>
<script>
  $.ajaxSetup({
    beforeSend : function(xhr, settings) {
      if (settings.type == 'POST' || settings.type == 'PUT'
          || settings.type == 'DELETE') {
        if (!(/^http:.*/.test(settings.url) || /^https:.*/
            .test(settings.url))) {
          xhr.setRequestHeader("X-XSRF-TOKEN", Cookies.get('XSRF-TOKEN'));
        }
      }
    }
  });
</script>
