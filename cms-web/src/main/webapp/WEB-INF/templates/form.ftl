<#--表单相关组件-->

<#--表单-->
<#macro form action="" id="form" action="" method="post" class="" enctype="multipart/form-data">
    <form method="${method}" class="form-horizontal ${class}" enctype="${enctype}"
          <#if action!=''>action="${action}"</#if>
          <#if id!=''>id="${id}"</#if>
    >
        <div class="space-6"></div>
        <#nested />
    </form>
</#macro>

<#--输入框-->
<#macro input name label value="" inline="false" class="" placeholder="" readonly="false" type="text" required="false">
<div class="form-group <#if inline=='true'>col-lg-4 col-md-6 col-xs-12</#if>">
    <div class="col-md-3 app-label nowrap <#if inline=='true'>col-xs-12</#if>">
        <label class="<#if required=="true">required</#if>">${label}</label>
    </div>
    <div class="col-md-7 controls <#if inline=='true'>col-xs-12</#if>">
        <input type="${type}" id="${name}" <#if readonly=="true">readonly</#if> name="${name}"
               value="${value}" class="form-control ${class} <#if readonly=="true">readonly</#if>" placeholder="${(placeholder=='')?string('请输入${label}', placeholder)}"/>
    </div>
    <#if class?contains("date-picker")>
        <script>
            $('.date-picker').datepicker();
        </script>
    </#if>
</div>
</#macro>

<#--表单按钮组-->
<#macro form_actions align="center" background="true">
<#if background=="true">
<div class="clearfix form-actions">
</#if>
<div class="col-xs-12 align-${align}">
    <#nested />
    <#if background!="true">
    <div class="space-6"></div>
    </#if>
</div>
<#if background=="true">
</div>
</#if>
</#macro>

<#--表单按钮-->
<#macro button name id="" class="" icon="" dismiss="false" type="">
<button id="${id}" class="btn ${class}" data-loading-text="正在${name}..."
    <#if dismiss=="true">
         data-dismiss="modal"
    </#if>
    <#if type!=''>
        data-type="${type}"
    </#if>
>
    <#if icon != ''>
    <i class="ace-icon fa ${icon}"></i>
    </#if>
    ${name}
</button>
</#macro>
