<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!doctype html>
<html lang="ch-ZN">

<jsp:include page='../common/header-with-date.jsp'>
  <jsp:param name="pageTitle" value="申请&审批"/>
</jsp:include>

<body>

<div class="wrapper">

  <!--整个左侧导航栏-->
  <%@include file="hotel-navbar-left.jsp" %>

  <!--主体界面-->
  <div class="main-panel">

    <!--1.导航栏-->
    <%@include file="hotel-navbar-top.jsp" %>

    <!--2.内容-->
    <!--登记一个房间的入住人信息-->
    <div class="content">
      <div class="container-fluid">
        <%@include file="../common/error-display.jsp"%>

        <div class="row">
          <div class="col-md-8">
            <div class="card">
              <div class="card-header" data-background-color="purple">
                <h4 class="title">登记入住人信息</h4>
                <p class="category"></p>
              </div>
              <div class="card-content">
                <form action="${pageContext.request.contextPath}/hotel/register-room" method="post">
                  <!--名称行-->
                  <div class="row">
                    <label class="col-md-1 control-label">酒店</label>
                    <div class="col-md-6">
                      <h4>${hotel.name}</h4>
                    </div>
                  </div>

                  <!--订单号-->
                  <div class="row">
                    <div class="input-group">
                      <div class="col-md-6">
                        <input type="text" name="orderId" class="form-control" placeholder="订单号">
                      </div>
                    </div>
                  </div>

                  <div class="row">
                    <div class="input-group">
                      <div class="col-md-2">
                        <input type="text" name="roomNumber" class="form-control" placeholder="房间号">
                      </div>
                      <div class="col-md-2">
                        <input type="text" class="form-control" placeholder="房客1">
                      </div>
                      <div class="col-md-2">
                        <input type="text" class="form-control" placeholder="身份证">
                      </div>
                      <div class="col-md-2">
                        <input type="text" class="form-control" placeholder="房客2">
                      </div>
                      <div class="col-md-2">
                        <input type="text" class="form-control" placeholder="身份证">
                      </div>
                      <div class="col-md-1" style="padding-top: 40px;">
                        <a href="#" id="add-room-icon">
                          <i class="material-icons">add_circle_outline</i>
                        </a>
                      </div>
                    </div>
                  </div>

                  <div class="row">
                    <div class="input-group">
                      <div class="col-md-2">
                        <input type="text" name="roomNumber" class="form-control" placeholder="房间号">
                      </div>
                      <div class="col-md-2">
                        <input type="text" class="form-control" placeholder="房客1">
                      </div>
                      <div class="col-md-2">
                        <input type="text" class="form-control" placeholder="身份证">
                      </div>
                      <div class="col-md-2">
                        <input type="text" class="form-control" placeholder="房客2">
                      </div>
                      <div class="col-md-2">
                        <input type="text" class="form-control" placeholder="身份证">
                      </div>
                      <div class="col-md-1" style="padding-top: 40px;">
                        <a href="#" id="add-room-icon2">
                          <i class="material-icons">add_circle_outline</i>
                        </a>
                      </div>
                    </div>
                  </div>

                  <!--是否会员-->
                  <div class="row">
                    <label class="col-sm-2 control-label">是否会员</label>

                    <div class="col-sm-2">
                      <div class="radio">
                        <label>
                          <input type="radio" value="1" name="isMember">
                          是
                        </label>
                      </div>
                    </div>

                    <div class="col-sm-2">
                      <div class="radio">
                        <label>
                          <input type="radio" value="0" name="isMember">
                          否
                        </label>
                      </div>
                    </div>
                  </div>

                  <!--付款方式-->
                  <div class="row">
                    <label class="col-sm-2 control-label">付款方式</label>

                    <div class="col-sm-2">
                      <div class="radio">
                        <label>
                          <input type="radio" value="0" name="paymentType">
                          会员卡
                        </label>
                      </div>
                    </div>

                    <div class="col-sm-3">
                      <div class="radio">
                        <label>
                          <input type="radio" value="1" name="paymentType">
                          现金结账
                        </label>
                      </div>
                    </div>
                  </div>

                  <button type="submit" class="btn btn-primary pull-left">
                    确定
                  </button>
                  <div class="clearfix"></div>
                </form>

              </div>
            </div>
          </div>
        </div>
      </div>
    </div>

    <!--3.页脚-->
    <%@include file="../common/footer.jsp" %>
  </div>
</div>

</body>

<%@include file="../common/js-file.jsp" %>

</html>