<%@ page contentType="text/html;charset=utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="/fis" prefix="fis"%>

<fis:extends name="page/layout/frame_admin.jsp">
    <fis:block name="article">
		<header>
			<fis:widget name="page/widget/header/projectDetailHeader.jsp" />	
		</header>

		<main>
			<fis:widget name="page/widget/main/projectDetail.jsp" />	
		</main>
	
		<p class="haha"><c:out value="${project.accept}" /></p>
        <c:if test="${!project.accept}">
			<div class="ui buttons tic-buttons" id="projectOperation">
	            <button class="ui button" v-tap="{methods: reject}">拒绝</button>
	            <div class="or"></div>
	            <button class="ui positive button" v-tap="{methods: accept}">通过</button>
	        </div>
	    </c:if>
        <div id="toast" v-show="isChecked" v-cloak>
		    <div class="weui-mask_transparent"></div>
		    <div class="weui-toast">
		        <i class="weui-icon-success-no-circle weui-icon_toast"></i>
		        <p class="weui-toast__content">已审核</p>
		    </div>
		</div>
	</fis:block>

	<fis:block name="style">
		<fis:parent />
		<fis:require id="static/scss/weui.css" />
		<fis:require id="static/scss/detailPost.scss" />
		<fis:require id="static/scss/admin/projectDetail.scss" />
	</fis:block>

	<fis:block name="jsPre">
		<script type="text/javascript">
		    var projectInfo = {
		        "proId": '<c:out value="${project.proId}" />',
		        "proname": '<c:out value="${project.proname}" />'
		    };

		    var userInfo = {};
		</script>

		<fis:require id="static/js/admin/project/detail.js" />
	</fis:block>

	<fis:block name="js">
		<fis:parent />
    </fis:block>

 
  <%-- auto inject by fis3-preprocess-extlang--%>
  <fis:require name="page/admin/project/detail.jsp" />
</fis:extends>