<% ui.decorateWith("appui", "standardEmrPage") %>


<%=ui.resourceLinks()%>



<% ui.includeCss("nmrsreports", "fontawesome/css/all.css") %>
<% ui.includeCss("nmrsreports", "flaticon/flaticon.css") %>
<% ui.includeCss("nmrsreports", "flaticon2/flaticon.css") %>
<% ui.includeCss("nmrsreports", "bootstrap.css") %>
<% ui.includeCss("nmrsreports", "all.css") %>
<% ui.includeCss("nmrsreports", "site.css") %>
<% ui.includeCss("nmrsreports", "jquery.dataTables.min.css") %>
<% ui.includeCss("nmrsreports", "buttons.dataTables.min.css") %>
<% ui.includeCss("nmrsreports", "custom.css") %>


<% ui.includeJavascript("nmrsreports", "jquery.js") %>
<% ui.includeJavascript("nmrsreports", "bootstrap.bundle.js") %>
<% ui.includeJavascript("nmrsreports", "site.js") %>
<% ui.includeJavascript("nmrsreports", "jquery.dataTables.min.js") %>

<style>
.dataTables_length {
    width: 50%;
    float: right;
    text-align: left !important;
}
</style>

<!-- Tab links -->
<div class="tab">

    <button class="tablinks" onclick="openTab(event, 'dashboard')"
            id="defaultOpen">Reports Dashboard</button>
    <button class="tablinks" onclick="openTab(event, 'reports')">Report Line Listings</button>
    <button class="tablinks" onclick="openTab(event, 'encounters')">Search for Encounter</button>

</div>


${ui.includeFragment("nmrsreports", "dashboard")}
${ui.includeFragment("nmrsreports", "encounters")}
${ui.includeFragment("nmrsreports", "reportslist")}



<script>

    // Get the element with id="defaultOpen" and click on it
    document.getElementById("defaultOpen").click();

    function openTab(evt, tabName) {
        // Declare all variables
        var i, tabcontent, tablinks;

        // Get all elements with class="tabcontent" and hide them
        tabcontent = document.getElementsByClassName("tabcontent");
        for (i = 0; i < tabcontent.length; i++) {
            tabcontent[i].style.display = "none";
        }

        // Get all elements with class="tablinks" and remove the class "active"
        tablinks = document.getElementsByClassName("tablinks");
        for (i = 0; i < tablinks.length; i++) {
            tablinks[i].className = tablinks[i].className.replace(" active", "");
        }

        // Show the current tab, and add an "active" class to the button that opened the tab
        document.getElementById(tabName).style.display = "block";
        evt.currentTarget.className += " active";
    }

</script>





