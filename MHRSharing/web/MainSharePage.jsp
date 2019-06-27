<%--
  Created by IntelliJ IDEA.
  User: d4k1d23
  Date: 5/20/16
  Time: 9:32 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>Medical Health Record Sharing</title>
</head>
<style type="text/css">
  form{
    width: 250px;
  }
  body{
    background-image: url("images/main_background.jpg");
    background-repeat: no-repeat;
    background-size:cover;
    background-position: center;
  }
  input[type=email]{
    background-image: url("images/email_logo.png");
  }
  input[name=patientID]{
    background-image: url("images/id_logo.png");
  }
  input[name=patientName]{
    background-image: url("images/first_name_logo.png");
  }
  input[name=patientLastName]{
    background-image: url("images/last_name_logo.png");
  }
  input[name=patientLastName]{
    background-image: url("images/last_name_logo.png");
  }
  input[name=ssn]{
    background-image: url("images/ssn_logo.png");
  }
  input[type=file]{
    padding-left: 0px;
    font-family: "Courier New";
  }
  input[type=email], [type=text]{
    width: 300px;
    height: 50px;
    padding-left: 45px;
    padding-top: 5px;
    font-size:100%;
    font-family: "Courier New";
    background-size: 15%;
    background-repeat: no-repeat;
    background-position: left, center;
    -webkit-transition: width 0.7s ease;
    -moz-transition: width 0.7s ease;
    -ms-transition: width 0.7s ease;
    -o-transition: width 0.7s ease;
    transition: width 0.7s ease;
  }

  input[type=email]:focus,[type=text]:focus  {
    width: 150%;
    padding-left: 30%;
    font-size: 200%;
  }

  .button {
    display: inline-block;
    border-radius: 4px;
    background-color: #f48b1f;
    border: none;
    color: #FFFFFF;
    text-align: center;
    font-size: 18px;
    padding: 18px;
    width: 120px;
    height: 50px;
    transition: all 0.5s;
    cursor: pointer;
    margin-top: 5px;
  }

  .button span {
    cursor: pointer;
    display: inline-block;
    position: relative;
    transition: 0.5s;
  }

  .button span:after {
    content: '»';
    position: absolute;
    opacity: 0;
    top: 0;
    right: -20px;
    transition: 0.5s;
  }
  h3{
    text-align: left;
    font-family: "Courier New";
  }

  .button:hover span {
    padding-right: 25px;
  }

  .button:hover span:after {
    opacity: 1;
    right: 0;
  }
  header {
    text-align:center;
    padding:5px;
  }
  section {
    margin:auto;
    padding: 5px;
  }
  footer {
    color: #000000;
    font-family: "Courier New";
    clear:both;
    text-align:right;

  }
  input[name=chooseFile]{
    alignment: left;
  }

</style>
<body>
<header>
  <h1 style="font-family:'Courier New'"align="center">Medical Health Record Sharing System ®</h1>
</header>
<section>
  <div align="center">
    <form name="patientSearch" action="/extractRecord" method="post">
      <h3>1. Patient Information</h3>
      <input type="text" name="patientID" required >
      <input type="text" name="patientName" required>
      <input type="text" name="patientLastName" required>
      <div align="left"><button class="button" type="submit"><span>SEARCH</span></button></div>
    </form>
    <form name="emailForm" action="/MailRecord" method="post">
      <h3>2. Share Health Record</h3>
      <input type="email" name="destinationEmail" required="true"/>
      <br>
      <div align="left"><button class="button" type="submit"><span>SEND</span></button></div>
    </form>
    <form name="decryptRecord" action="/decodeRecord" method="post" enctype="multipart/form-data">
      <h3>Decode received Health Record</h3>
      <div align="left"><input type="file" name="fileName" required></div>
      <div align="left"><input type="text" name="ssn" required></div>
      <div align="left"><button class="button" type="submit"><span>DECODE</span></button></div>
    </form>
  </div>
</section>
<footer>
  <img src="images/404logo.png" alt="ERROR 404 Team" width="150" height="60">
</footer>
</body>
</html>
