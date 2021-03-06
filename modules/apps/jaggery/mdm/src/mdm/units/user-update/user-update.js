/**
 * Returns a context with the user object to be populated by the edit-user page.
 *
 * @param context Object that gets updated with the dynamic state of this page to be presented
 * @returns {*} A context object that returns the dynamic state of this page to be presented
 */
function onRequest(context) {
    var userModule = require("/modules/user.js")["userModule"];

    var uri = request.getRequestURI();
    var uriMatcher = new URIMatcher(String(uri));
    var isMatched = uriMatcher.match("/{context}/users/edit-user/{username}");

    if (isMatched) {
        var matchedElements = uriMatcher.elements();
        var username = matchedElements.username;
        var response = userModule.getUser(username);

        if (response["status"] == "success") {
            context["editUser"] = response["content"];
        }

        response = userModule.getRolesByUsername(username);
        if (response["status"] == "success") {
            context["userRoles"] = response["content"];
        }
        log.info(context);
    }

    return context;
}