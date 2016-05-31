<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<div id="all_feedbacks">
  <div class="widget">
    <div class="widget-header clearfix">
      <h3><i class="icon ion-ios-browsers"></i> <span>
                    <p id="special_mark_display"><spring:message code="locale.specmark"/>: </p>


        </span></h3>
      <ul class="nav nav-tabs pull-right">
        <li class="active"><a href="#tab1" data-toggle="tab"><i class="icon ion-gear-b"></i> Developer <span
                id="dev_score" class="label label-info label-as-badge pull-left">55</span></a></li>
        <li class=""><a href="#tab2" data-toggle="tab"><i class="icon ion-help-circled"></i> HR/BA <span
                id="hr_score" class="label label-info label-as-badge pull-left">75</span></a></li>
      </ul>
    </div>

    <div class="widget-content tab-content">
      <div class="tab-pane fade active in" id="tab1">
        <p id="dev_feedback">Dev <spring:message code="locale.feedback"/></p>
      </div>
      <div class="tab-pane fade" id="tab2">
        <p id="hr_feedback">Hr <spring:message code="locale.feedback"/></p>
      </div>
    </div>
  </div>
</div>
