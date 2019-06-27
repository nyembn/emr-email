<%@ page import="java.io.File" %><%--
  Created by IntelliJ IDEA.
  User: d4k1d23
  Date: 5/23/16
  Time: 1:44 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<style>
    body{
        background-image: url("images/file_animation.gif");
        background-position: center;
        background-repeat: no-repeat;
        background-color: #e35b3a;
    }
    .button {
        display: inline-block;
        border-radius: 4px;
        background-color: #fffbf9;
        border: none;
        color: #ff814c;
        text-align: center;
        font-size: 18px;
        padding: 16px;
        width: 120px;
        height: 50px;
        transition: all 0.5s;
        cursor: pointer;
        margin: 0px;
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
    div{
        font-family: "Courier New";
        color: white;
    }
    footer{
        text-align: center;
    }
</style>
<html>
<head>
    <title>File decoded Successfully</title>
</head>
<body>
    <div align="center">
    <% out.println("The encrypted file has been decrypted at "+System.getProperty("user.home")+ File.separator+"Desktop"+File.separator); %>
    <br>
</div>
<footer>
    <button class="button" type="submit" style="vertical-align:middle" onclick="location.href='/MainSharePage.jsp'"><span>Return</span></button>
</footer>
</body>
</html>
