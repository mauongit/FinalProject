<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Autocomplete Application using Java and JQuery</title>
<script src="/scripts/jquery.js"></script>
<script src="/scripts/jquery-ui.js"></script>
<script src="/scripts/autocompleter.js"></script>
<link rel="stylesheet" 
  href="/css/jquery-ui.css">
<!-- User defied css -->
<link rel="stylesheet" href="/css/style.css">

</head>
<body>
<img alt="verizon" style="height:165px;" src="/images/team-logo-156-130-c.jpg">
<div class="header">
        <h2>Please type below to search and validate the address</h2>
</div>
<span id="erroMessage" style="display:none;color:red">Please enter alphabet or number as the first character</span>
<br />
<br />
<div class="search-container">
        <div class="ui-widget">
                <input type="text" id="search" name="search" class="text-field" />
        </div>
  		
</div>
</body>
</html>