<%--
  Created by IntelliJ IDEA.
  User: Alexander
  Date: 17.05.2016
  Time: 20:40
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Upload Photo</title>
</head>
<body>
<div>
    <form id="photo_form" type=post enctype="multipart/form-data">
        <div id="photoMessages"></div>
        <div class="fileinput fileinput-new" data-provides="fileinput">
            <div class="fileinput-new thumbnail" style="width: 100px; height: 100px;">
            </div>
            <div class="fileinput-preview fileinput-exists thumbnail"
                 style="max-width: 100px; max-height: 100px;"></div>
            <div>
                                            <span class="btn btn-default btn-file"><span class="fileinput-new">Select image</span><span
                                                    class="fileinput-exists">Change</span><input type="file" name="..."></span>
                <a href="#" class="btn btn-default fileinput-exists"
                   data-dismiss="fileinput">Remove</a>
                <a href="#" class="btn btn-default fileinput-exists"
                   id="photo_button" type="submit">Upload</a>
            </div>
        </div>
    </form>
</div>
</body>
</html>
