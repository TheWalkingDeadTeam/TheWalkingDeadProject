<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<sec:authorize access="hasRole('ROLE_ADMIN')">
    <div class="" role="alert">
        <div id="messageChangeRoles"></div>
        <form>
            <div id="roleAdmin">
                <label class="checkbox">
                    <input type="checkbox" id="checkbox_admin" value="ROLE_ADMIN" class="roles"> Admin
                </label>
            </div>
            <select id="role">
                <option value="ROLE_HR">HR</option>
                <option value="ROLE_DEV">DEV</option>
                <option value="ROLE_BA">BA</option>
            </select>
            <button id="save_roles" class="btn btn-lg btn-primary btn-block changebtn"><spring:message code="locale.save"/></button>
        </form>
    </div>
</sec:authorize>