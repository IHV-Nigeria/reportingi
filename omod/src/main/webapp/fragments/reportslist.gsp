<%

    def appFrameworkService = context.getService(context.loadClass("org.openmrs.module.appframework.service.AppFrameworkService"))
    def overviews = appFrameworkService.getExtensionsForCurrentUser("org.openmrs.module.reportingui.reports.overview")
    def monitoringReports = appFrameworkService.getExtensionsForCurrentUser("org.openmrs.module.reportingui.reports.monitoring")
    def dataQualityReports = appFrameworkService.getExtensionsForCurrentUser("org.openmrs.module.reportingui.reports.dataquality")
    def dataExports = appFrameworkService.getExtensionsForCurrentUser("org.openmrs.module.reportingui.reports.dataexport")
    def contextModel = [:]
%>

<div id="reports" class="tabcontent">
    <% if (metadataModuleVersion != null) { %>
    <div id="accordion">
        <div class="card">
            <div class="card-header" id="headingOne" data-toggle="collapse" data-target="#collapseOne">
                <h5 class="mb-0">

                    <% if (monitoringReports) { %>
                    <p>${ui.message("reportingui.reportsapp.monitoringReports")}</p>
                    <% } %>
                </h5>
            </div>

            <div id="collapseOne" class="collapse show" aria-labelledby="headingOne" data-parent="#accordion">
                
                    <% if (monitoringReports) { %>
                    <table class="table table-striped table-hover" id="uploadDataTable" style="margin: 0px 0 !important;">
                        <% monitoringReports.each { %>
                        <tr style="height: 50px;">
                            <th><div><span class="light-text"><span class="flaticon2-list"></span>&nbsp; &nbsp; </span><b>${
                                    ui.includeFragment("uicommons", "extension", [extension: it, contextModel: contextModel])}</b></div></th>
                        </tr>
                        <% } %>
                    </table>
                    <% } %>
               
            </div>
        </div>

        <div class="card">
            <div class="card-header" id="headingTwo" collapsed" data-toggle="collapse" data-target="#collapseTwo">
            <h5 class="mb-0">
                <% if (dataQualityReports) { %>
                <p>${ui.message("reportingui.reportsapp.dataQualityReports")}</p>
                <% } %>
            </h5>
        </div>

        <div id="collapseTwo" class="collapse" aria-labelledby="headingTwo" data-parent="#accordion">
            <div class="card-body">
                <% if (dataQualityReports) { %>
                <table>

                    <% dataQualityReports.each { %>
                    <tr>
                        <th>
                            ${ui.includeFragment("uicommons", "extension", [extension: it, contextModel: contextModel])}
                        </th>
                    </tr>
                    <% } %>
                </table>
                <% } %>
            </div>
        </div>
    </div>
    <% } else { %>
    <div class="alert alert-danger text-center" role="alert">
        <b>An old version of Nmrs Metadata data module is installed</b> <br>Please click <a
            href="/openmrs/admin/modules/module.list">here</a> to update
    </div>
    <% } %>
</div>



