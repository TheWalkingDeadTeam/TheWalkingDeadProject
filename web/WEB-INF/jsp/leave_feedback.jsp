<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<div class="container">
  <div class="col-lg-6 col-md-8 col-sm-12 col-xs-12">
    <div id="save_message"></div>
    <div>
      <div class="row container-fluid reg-head">
        <div>
          <h4 class="form-signin-heading"><spring:message code="locale.feedback"/> :</h4>
        </div>
      </div>
      <form id="feedback_form">
        <div id="regform" class="col-lg-8 col-md-8 col-sm-12 col-xs-12">
                    <textarea rows="10" cols="10" id="feedback_text" style="margin-bottom: 3px;" class="form-control"
                              placeholder=<spring:message code="locale.feedback"/> required></textarea>
        </div>
        <div  class="col-lg-4 col-md-8 col-sm-12 col-xs-12">
          <label><spring:message code="locale.mark"/>: </label>
          <input id="feedback_score" style="margin-bottom: 3px;" class="form-control"
                 placeholder="1 .. 100" type="number"
                 max="100" min="1" align="centre" required>
          <label>Special mark: </label>
          <select id="special_mark" style="margin-bottom: 3px;" class="form-control">
            <option value="none" id="none"><spring:message code="locale.none"/></option>
            <option value="reject" id="reject"><spring:message code="locale.reject"/></option>
            <option value="take on courses" id="take_on_courses"><spring:message code="locale.takeOnCourses"/></option>
            <option value="job offer" id="job_offer"><spring:message code="locale.jobOffer"/></option>
          </select>
        </div>
        <div class="col-lg-8 col-md-8 col-sm-12 col-xs-12">
          <button id="submitFeedback" style="border-radius: 4px;    margin-top: 4px ;"
                  class="btn btn-lg btn-primary btn-block mdl-button mdl-js-button mdl-button--raised mdl-js-ripple-effect mdl-button--colored mdl-color-text--white">
            <spring:message code="locale.submit"/>
          </button>
        </div>
      </form>
    </div>
  </div>
</div>
