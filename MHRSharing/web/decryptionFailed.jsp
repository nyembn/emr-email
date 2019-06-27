<%--
  Created by IntelliJ IDEA.
  User: d4k1d23
  Date: 5/23/16
  Time: 2:11 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<style>
    body{
        background-image: url("images/error_animation.gif");
        background-position: bottom, center;
        background-repeat: no-repeat;
        background-color: #ffffff;

    }
    .button {
        display: inline-block;
        border-radius: 4px;
        background-color: #ff0707;
        border: none;
        color: #faf2ff;
        text-align: center;
        font-size: 18px;
        padding: 16px;
        width: 120px;
        height: 50px;
        transition: all 0.5s;
        cursor: pointer;
        margin: 0px;
        margin-top: 30%;
    }

    .button span {
        cursor: pointer;
        display: inline-block;
        position: relative;
        transition: 0.5s;
    }

    .button span:after {
        content: '«';
        position: absolute;
        opacity: 0;
        top: 0;
        left: 5px;
        transition: 0.5s;
    }

    .button:hover span {
        padding-left: 25px;
    }

    .button:hover span:after {
        opacity: 1;
        left: 0;
    }
    h2{
        font-family: "Courier New";
        text-align: center;
        color: #ff0711;
    }
    h3{
        font-family: "Courier New";
        text-align: center;
        color: #ff0003;
    }
</style>
<html>
<head>
    <title>Decoding failed!</title>
    <h2>There was an error while decoding the health record!</h2>
    <h3>Please enter the last 4 digits of patient's SSN!</h3>
    <div align="">
        <button class="button" type="submit" style="vertical-align:top" onclick="location.href='/MainSharePage.jsp'"><span>Return</span></button>
    </div>
</head>
<body>

</body>
</html>
