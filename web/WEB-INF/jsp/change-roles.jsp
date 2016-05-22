<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<sec:authorize access="hasRole('ROLE_ADMIN')">
    <h5 style="float: left;">Change role</h5>
    <div class="" role="alert">
        <form>
            <div style="display: inline-block;  width: 100%;">
                <div style="float: left;">
                    <div id="roleAdmin" style="display: inline-block;">
                        <label class="checkbox">
                            <input type="checkbox" id="checkbox_admin" value="ROLE_ADMIN" class="roles"> Admin
                        </label>
                    </div>
                    <div style="display: inline-block;  margin-left: 10px;">
                        <select id="role">
                            <option value="ROLE_HR">HR</option>
                            <option value="ROLE_DEV">DEV</option>
                            <option value="ROLE_BA">BA</option>
                        </select>
                    </div>
                </div>
                <button id="save_roles" class="btn btn-lg btn-primary btn-block changebtn mdl-button mdl-js-button mdl-button--raised mdl-js-ripple-effect mdl-button--colored mdl-color-text--white">Save roles</button>
            </div>
        </form>
        <div id="messageChangeRoles"></div>
    </div>
</sec:authorize>