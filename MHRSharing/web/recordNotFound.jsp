<%--
  Created by IntelliJ IDEA.
  User: d4k1d23
  Date: 5/21/16
  Time: 4:06 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<style>
    body{
        background-image: url("images/record_not_found.gif");
        background-position: top, center;
        background-repeat: no-repeat;
        background-color: #1a6eec;
    }
    .button {
        display: inline-block;
        border-radius: 4px;
        background-color: #fffbf9;
        border: none;
        color: #1a6eec;
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
        color: #fff6f9;
    }
    h3{
        font-family: "Courier New";
        text-align: center;
        color: #fff6f9;
    }
</style>
<html>
<head>
    <title>Medical Record Not Extracted</title>
    <h2>The medical record was not found or has not been extracted properly!</h2>
    <h3>Please follow the steps on the main page!</h3>
    <div align="left">
        <button class="button" type="submit" style="vertical-align:middle" onclick="location.href='/MainSharePage.jsp'"><span>Return</span></button>
    </div>
</head>
<body>

</body>
</html>
