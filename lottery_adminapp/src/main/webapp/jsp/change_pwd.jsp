<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html lang="zh">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<title>index</title>
</head>
<body>
	<div class="container">
		<div class="panel panel-default">
			<div class="panel-heading">修改登录密码</div>
			<div class="panel-body">
				<div class="row">
					<div class="col-sm-6">
						<div class="alert alert-warning alert-dismissable">
							<button type="button" class="close" data-dismiss="alert"
								aria-hidden="true">&times;</button>
							<strong>Warning!</strong> Best check yo self, you're not looking
							too good.
						</div>
						<form class="form-horizontal" role="form">
							<div class="form-group">
								<label for="inputEmail3" class="col-sm-2 control-label">原始密码</label>
								<div class="col-sm-5">
									<input type="password" class="form-control" id="inputEmail3">
								</div>
							</div>
							<div class="form-group">
								<label for="inputPassword3" class="col-sm-2 control-label">新密码</label>
								<div class="col-sm-5">
									<input type="password" class="form-control" id="inputPassword3">
								</div>
							</div>
							<div class="form-group">
								<label for="inputPassword3" class="col-sm-2 control-label">重复密码</label>
								<div class="col-sm-5">
									<input type="password" class="form-control" id="inputPassword3">
								</div>
							</div>

							<div class="form-group">
								<div class="col-sm-offset-2 col-sm-10">
									<button type="submit" class="btn btn-primary">提交修改</button>
								</div>
							</div>
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>

</body>
</html>
