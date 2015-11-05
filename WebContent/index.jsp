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
<img style="height:165px;" src="/images/team-logo-156-130-c.jpg">
<h1 style="position: absolute;top: 50px;font-weight: bold;font-family: sans-serif;"> team</h1>
<div class="header" style="margin-top:-34px">
       <h2 style="font-family: sans-serif; text-align: center;">Please type below to search and validate the address</h2>
        <h3 style="color:green;font-family: sans-serif; text-align: center;">(Auto populates the matching addresses from database and validates the same)</h3>
</div>
<span id="erroMessage" style="display:none;color:red;font-family: sans-serif; text-align: center;"><h3>Please enter alphabet or number as the first character</h3></span>
<br />
<br />
<div class="search-container">
        <div class="ui-widget">
                <input type="text" id="search" name="search" class="text-field" />
        </div>
  		
</div>
<br />
<span id="erroMessageNoAddress" style="display:none;color:red;font-family: sans-serif; text-align: center;"><h3>No Address found in our system, please search for a valid address.</h3></span>

</body>
</html>