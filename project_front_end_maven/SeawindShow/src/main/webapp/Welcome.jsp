<%@ page language="java" contentType="text/html;charset=utf-8" pageEncoding="GBK"%>
<%@ page import="java.util.*, au.edu.unsw.stackoverflow.*"%>
<%@ page isELIgnored="false"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>

<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<script
	src="http://ajax.googleapis.com/ajax/libs/prototype/1.6.0.2/prototype.js"></script>
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<script type="text/javascript">
	document.onmouseup = document.ondbclick = function() {
		var txt;
		if (document.selection) {
			txt = document.selection.createRange().text
		} else {
			txt = window.getSelection() + '';
		}
		if (txt) {
			loadResult("<div class=\"panel-body\"><b>Loading...</b></div>")
			findkey(txt);
		}
	}
	function findkey(txt) {
		var jsfind = txt;
		$.ajax({
			type : 'post',
			//dataType:'json',
			cache : false,
			data : {
				string : jsfind
			},
			url : "./",
			beforeSend : function(XMLHttpRequest) {
			},
			success : function(data) {
				//console.log(data);
				loadResult(data);
			},
			error : function() {
				alert("Internet Error!");
			}
		});
	}
	function loadResult(html_data) {
		$("#KeyS-FINDKEY").html(html_data);//refresh
		$("#KeyS-FINDKEY").parent().children().each(function() {
			//console.log(this);
			$(this).removeClass("in");
		})
		$("#KeyS-FINDKEY").addClass("in");
		$("#KeyS-FINDKEY").removeAttr("style");
	}

	$(function() {
		$("[data-toggle='tooltip']").tooltip();
	});
</script>
<style>
.down-px-70 {
	margin-top: 70px;
}

.fixed {
	position: fixed;
	top: 200px;
	right: 0px;
}

.handcursor {
	cursor: pointer;
}
</style>
<title>Welcome to StackOverflow Extension Service</title>
<%
	@SuppressWarnings("unchecked")
	ArrayList<Questions_Bean> QuestionsList = (ArrayList<Questions_Bean>) request
	.getAttribute("QuestionsList");
	
	//@SuppressWarnings("unchecked")
	//Map<String, ArrayList<String>> KeywordsMap = (Map<String,ArrayList<String>>)request
	//.getAttribute("KeywordsMap");
%>
</head>

<body>
	<nav class="navbar navbar-default navbar-fixed-top" role="navigation">
	<div class="navbar-header">

		<button type="button" class="navbar-toggle" data-toggle="collapse"
			data-target="#bs-example-navbar-collapse-1">
			<span class="sr-only">Toggle navigation</span><span class="icon-bar"></span><span
				class="icon-bar"></span><span class="icon-bar"></span>
		</button>
		<a class="navbar-brand" href="./"><b>StackOverflow Extension</b></a>
	</div>

	<div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
		<ul class="nav navbar-nav navbar-right">
			<li><a target="_blank" href="https://www.unsw.edu.au/"><img
					src="https://www.unsw.adfa.edu.au/sites/default/files/UNSW_logo_0.png"
					style="width: 54px; height: 20px;" /></a></li>
			<li class="active"><a target="_blank"
				href="http://stackoverflow.com/"><img
					src="https://upload.wikimedia.org/wikipedia/commons/thumb/0/02/Stack_Overflow_logo.svg/2000px-Stack_Overflow_logo.svg.png"
					style="width: 102px; height: 20px;" /></a></li>
			<li><a target="_blank" href="https://www.youtube.com/"><img
					src="http://blazingseollc.com/wp-content/uploads/2016/08/youtube.png"
					style="width: 40px; height: 20px;" /></a></li>
			<li class="dropdown"><a href="#" class="dropdown-toggle"
				data-toggle="dropdown">Setting<strong class="caret"></strong></a>
				<ul class="dropdown-menu">
					<li><a href="#">Action</a></li>
					<li><a href="#">Another action</a></li>
					<li><a href="#">Something else here</a></li>
					<li class="divider"></li>
					<li><a href="#">Separated link</a></li>
					<li class="divider"></li>
					<li><a href="#">One more separated link</a></li>
				</ul></li>
		</ul>
	</div>

	</nav>
	<div class="container-fluid down-px-70">
		<div class="row">
			<div class="col-md-12">
				<div class="alert alert-dismissable alert-info">

					<button type="button" class="close" data-dismiss="alert"
						aria-hidden="true">¡Á</button>
					<h4>Tips!</h4>
					<div>
						You can click highlight words <strong>OR</strong> select your own
						words to get extension videos.
					</div>
				</div>
				<div class="row">
					<div class="col-md-8">
						<h1>
							Top Questions:<br />
						</h1>
						<div class="panel-group" id="panel-932153">
							<%
								for (int i = 0; i < QuestionsList.size(); i++){
									String tmpTitle = QuestionsList.get(i).getTitle();
									String tmpQuestion = QuestionsList.get(i).getQuestion();
									//String keyword1 = QuestionsList.get(i).getKeyword1().isEmpty() ? "" : QuestionsList.get(i).getKeyword1();
									//String keyword2 = QuestionsList.get(i).getKeyword2().isEmpty() ? "" : QuestionsList.get(i).getKeyword2();
									//String keyword3 = QuestionsList.get(i).getKeyword3().isEmpty() ? "" : QuestionsList.get(i).getKeyword3();
									//String keyword4 = QuestionsList.get(i).getKeyword4().isEmpty() ? "" : QuestionsList.get(i).getKeyword4();
									String tmpAnswer = QuestionsList.get(i).getOrg_answer();
									//tmpAnswer = keyword1.equals("") ? tmpAnswer : tmpAnswer.replaceFirst("([^>|-])((?i)" + keyword1 + ")([^<|\"|=])", "$1<a class=\"handcursor\" data-toggle=\"tooltip\" title=\"Try to click it!\"><span style=\"background-color: #FFFF00\" data-toggle=\"collapse\" data-parent=\"#panel-857146\" href=\"#KeyS-" + keyword1.replace(" ", "_").replace("+", "-") + "\">" + keyword1 + "</span></a>$3");
									//tmpAnswer = keyword2.equals("") ? tmpAnswer : tmpAnswer.replaceFirst("([^>|-])((?i)" + keyword2 + ")([^<|\"|=])", "$1<a class=\"handcursor\" data-toggle=\"tooltip\" title=\"Try to click it!\"><span style=\"background-color: #FFFF00\" data-toggle=\"collapse\" data-parent=\"#panel-857146\" href=\"#KeyS-" + keyword2.replace(" ", "_").replace("+", "-") + "\">" + keyword2 + "</span></a>$3");
									//tmpAnswer = keyword3.equals("") ? tmpAnswer : tmpAnswer.replaceFirst("([^>|-])((?i)" + keyword3 + ")([^<|\"|=])", "$1<a class=\"handcursor\" data-toggle=\"tooltip\" title=\"Try to click it!\"><span style=\"background-color: #FFFF00\" data-toggle=\"collapse\" data-parent=\"#panel-857146\" href=\"#KeyS-" + keyword3.replace(" ", "_").replace("+", "-") + "\">" + keyword3 + "</span></a>$3");
									//tmpAnswer = keyword4.equals("") ? tmpAnswer : tmpAnswer.replaceFirst("([^>|-])((?i)" + keyword4 + ")([^<|\"|=])", "$1<a class=\"handcursor\" data-toggle=\"tooltip\" title=\"Try to click it!\"><span style=\"background-color: #FFFF00\" data-toggle=\"collapse\" data-parent=\"#panel-857146\" href=\"#KeyS-" + keyword4.replace(" ", "_").replace("+", "-") + "\">" + keyword4 + "</span></a>$3");
							%>
							<div class="panel panel-default">
								<div class="panel-heading">
									<a class="panel-title" data-toggle="collapse"
										href="#panel-element-10587<%=i%>"><%=i+1%>.
										<%=tmpTitle%></a>
								</div>
								<div id="panel-element-10587<%=i%>"
									class="panel-collapse collapse auto-central sticky">
									<div class="panel panel-default">
										<div class="panel-heading">
											<div class="panel-title">
												<b>Question:</b><br /> <br />
												<%=tmpQuestion%>
											</div>
										</div>
									</div>
									<div class="panel-body">
										<a class="handcursor" data-toggle="tooltip" title="Try to click it!"><span class="panel-title" data-toggle="collapse"
											data-parent="#panel-857146" href="#panel-url-87256<%=i%>"><b>Answer:</b></span></a><br />
										<br />
										<%=tmpAnswer%>
									</div>
								</div>
							</div>
							<%
								}
							%>
						</div>
					</div>
					<divf class="col-md-4 fixed panel-group" id="panel-857146">
					<div class="panel panel-default">
						<%
							for (int i = 0; i < QuestionsList.size(); i++) {
								String tmpUrl = QuestionsList.get(i).getUrl();
						%>
						<div id="panel-url-87256<%=i %>" class="panel-collapse collapse">
							<div class="panel-body">
								<embed class="media-object" width="100%" height="160"
									src="https://www.youtube.com/embed/<%=tmpUrl %>"
									alt="No" />
							</div>
						</div>
						<%
							}
						%>
						<div id="KeyS-FINDKEY" class="panel-collapse collapse">
							<div class="panel-body">
								<embed class="media-object" width="100%" height="160"
									src="${YouTubeId}" alt="No" />
							</div>
						</div>
					</div>
					</divf>
				</div>
			</div>
		</div>
	</div>


	<script type="text/javascript">
		$(function() {

			$(".sticky").sticky({ //(".sticky")
				topSpacing : 58,
				zIndex : 2,
				stopper : "#footer"
			});

		});
	</script>
</body>
</html>