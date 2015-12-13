<%@page import="facebook.user.RetrieveUserInformation"%>

<%@page import="java.lang.StringBuilder"%>
<%@page import="java.util.List"%>


<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
    <meta name="description" content="">
    <meta name="author" content="">
    <link rel="icon" href="../favicon.ico">


    <title>Theme Template for Bootstrap</title>

   <link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.2.0/css/bootstrap.min.css">
    <link rel="stylesheet" href="lib/gridstack.js-master/dist/gridstack.css"/>
        <link rel="stylesheet" href="http://cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.3.0/css/datepicker.min.css" />
    <link rel="stylesheet" href="http://cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.3.0/css/datepicker3.min.css" />
    <link href="theme.css" rel="stylesheet">



    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
    <script src="http://cdnjs.cloudflare.com/ajax/libs/jqueryui/1.11.0/jquery-ui.js"></script>
    <script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.2.0/js/bootstrap.min.js"></script>
    <script src="http://cdnjs.cloudflare.com/ajax/libs/lodash.js/3.5.0/lodash.min.js"></script>
    <script src="lib/gridstack.js-master/dist/gridstack.js"></script>
    <script src="http://cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.3.0/js/bootstrap-datepicker.min.js"></script>
    <script src="script.js"></script>


      <%
         RetrieveUserInformation x = new RetrieveUserInformation();
		
		List<String> ret = x.retieveUserInfo();
	
	StringBuilder SB = new StringBuilder();

	for(String r  : ret)
		SB.append(r + "..." );
	String display = SB.toString();         
         %> 
  </head>

  <body role="document">

   
    <div class="container theme-showcase" role="main">

      <!-- Main jumbotron for a primary marketing message or call to action -->
     
        <div>
        
        <img src="images/bv6.jpg" height="400" class="displayed"/>
        </div>
	<br/><br/><br/>
        <form action="results.jsp" method="post">
      <div class ="row">
      <div class="col-md-2"><label class="control-label">Pick a City</label>
      </div>
	<div class="col-md-8">
	 <button type="button" class="btn btn-default" onclick="cityClick('0')" id="city-btn-0" value="no">Los Angeles</button>
        <button type="button" class="btn  btn-default" onclick="cityClick('1')" id="city-btn-1" value="no">New York</button>
	<button type="button" class="btn  btn-default" onclick="cityClick('2')" id="city-btn-2" value="no">London</button>
      </div>
            
	</div>
	<br/><br/>
 <div class ="row">
      <div class="col-md-2"><label class="control-label">What do you like?</label>
      </div>
	<div class="col-md-8">
    <button type="button" class="btn btn-default  <% if(ret.contains("Shopping")) out.print(" btn-primary");%>" onclick="IntClick('0')" id="interest-btn-0" value="Shopping">Shopping</button>
    <button type="button" class="btn  btn-default <% if(ret.contains("Movie")) out.print(" btn-primary");%>" onclick="IntClick('1')" id="interest-btn-1" value = "Movie">Movies</button>
	<button type="button" class="btn  btn-default <% if(ret.contains("Art_Gallery")) out.print(" btn-primary");%>" onclick="IntClick('2')" id="interest-btn-2" value="Art_Gallery">Art</button>
	<button type="button" class="btn btn-default <% if(ret.contains("Theatre")) out.print(" btn-primary");%>" onclick="IntClick('3')" id="interest-btn-3" value="Theatre">Theatre</button>
    <button type="button" class="btn  btn-default <% if(ret.contains("Music")) out.print(" btn-primary");%>" onclick="IntClick('4')" id="interest-btn-4" value="Music">Music</button>
    <button type="button" class="btn btn-default <% if(ret.contains("TV_Show")) out.print(" btn-primary");%>" onclick="IntClick('5')" id="interest-btn-5" value="TV_Show">TV Show</button>
    <button type="button" class="btn  btn-default <% if(ret.contains("Sports")) out.print(" btn-primary");%>" onclick="IntClick('6')" id="interest-btn-6" value="Sports">Sports</button>
	<button type="button" class="btn  btn-default <% if(ret.contains("Travel")) out.print(" btn-primary");%>" onclick="IntClick('7')" id="interest-btn-7" value="Travel">  Travel</button>
        <button type="button" class="btn  btn-default <% if(ret.contains("Religious_Organization")) out.print("btn-primary");%>"  onclick="IntClick('8')" id="interest-btn-8" value="Religious_Organization">Religion</button>
        <button type="button" class="btn  btn-default <% if(ret.contains("Museum")) out.print("btn-primary");%>"  onclick="IntClick('9')" id="interest-btn-9" value="Museum">Museum</button>

     </div>
     <div>
     <img src="images/batman.jpg" class="img-thumbnail" alt="Cinque Terre" height=45 width=45>

     </div>
            
	</div>

<br/><br/>
       <div class ="row">
      <div class="col-md-2 "><label class="control-label">When?</label>
      </div>
	<div class="col-md-8">

	<div class="row">
	<div class="col-xs-3 date">
            <div class="input-group input-append date" id="datePicker1">
                <input type="text" class="form-control" name="date1" />
                <span class="input-group-addon add-on"><span class="glyphicon glyphicon-calendar"></span></span>
            </div>

        </div>
<div class="col-xs-3 date">
            <div class="input-group input-append date" id="datePicker2">
                <input type="text" class="form-control" name="date2" />
                <span class="input-group-addon add-on"><span class="glyphicon glyphicon-calendar"></span></span>
            </div>

        </div>
	</div>
        
      </div>
            
	</div>
	<br/><br>
	<div class="row">
	<div class="col-md-10" style="visibility:hidden">hidden<</div>
	<div class="col-md-2" ><input type="button" class="btn btn-success" name="Lets Go" onclick="go()" value="Let's Go!"></button> </div>
	<div class="col-md-5" ></div>
	
	</div>
        </form>      
    </div> <!-- /container -->


  
	<script src="script.js"></script>


    <script>window.jQuery || document.write('<script src="../assets/js/vendor/jquery.min.js"><\/script>')</script>
    <script src="../dist/js/bootstrap.min.js"></script>
    <script src="../assets/js/docs.min.js"></script>
    <!-- IE10 viewport hack for Surface/desktop Windows 8 bug -->
    <script src="../assets/js/ie10-viewport-bug-workaround.js"></script>
  </body>
</html>
