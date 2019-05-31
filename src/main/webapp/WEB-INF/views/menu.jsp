<!--
Created by IntelliJ IDEA.
User: thongchai
Date: 4/5/2562
Time: 20:06 น.
To change this template use File | Settings | File Templates.
-->
<!-- Sidebar menu-->
<div class="app-sidebar__overlay" data-toggle="sidebar"></div>
<aside class="app-sidebar">
    <%--<div class="app-sidebar__user">--%>
    <%--&lt;%&ndash;<img class="app-sidebar__user-avatar" src="https://s3.amazonaws.com/uifaces/faces/twitter/jsa/48.jpg" alt="User Image">&ndash;%&gt;--%>
    <%--<div>--%>
    <%--<p class="app-sidebar__user-name"></p>--%>
    <%--<p class="app-sidebar__user-designation"></p>--%>
    <%--</div>--%>
    <%--</div>--%>
    <ul class="app-menu">
        <li class="treeview">
            <a class="app-menu__item" href="#" onclick="openLinkUrl(this)" link="/">
                <i class="app-menu__icon fa fa-dashboard"></i><span class="app-menu__label">Dashboard</span></a>
        </li>
        <li class="treeview"><a class="app-menu__item" href="#" data-toggle="treeview"><i
                class="app-menu__icon fa fa-cog"></i><span class="app-menu__label">Setting</span><i
                class="treeview-indicator fa fa-angle-right"></i></a>
            <ul class="treeview-menu">
                <li><a class="treeview-item" onclick="openLinkUrl(this)" link="/settings/machine" href="#"><i
                        class="icon fa fa-circle-o"></i> Machine</a>
                </li>
                <li><a class="treeview-item" onclick="openLinkUrl(this)" link="/settings/warning" href="#"><i
                        class="icon fa fa-circle-o"></i> Warning</a>
                </li>
                <li><a class="treeview-item" onclick="openLinkUrl(this)" link="/settings/combine" href="#"><i
                        class="icon fa fa-circle-o"></i> Combine</a></li>

                <li><a class="treeview-item" onclick="openLinkUrl(this)" link="/footprints/create" href="#"><i
                        class="icon fa fa-circle-o"></i> Foot Print</a></li>
            </ul>
        </li>

        <%--<li class="treeview "><a class="app-menu__item" href="#" data-toggle="treeview"><i--%>
        <%--class="app-menu__icon fa fa-file-text"></i><span class="app-menu__label">Pages</span><i--%>
        <%--class="treeview-indicator fa fa-angle-right"></i></a>--%>
        <%--<ul class="treeview-menu">--%>
        <%--<li><a class="treeview-item active" href="blank-page.html"><i class="icon fa fa-circle-o"></i> Blank--%>
        <%--Page</a></li>--%>
        <%--<li><a class="treeview-item" href="page-login.html"><i class="icon fa fa-circle-o"></i> Login Page</a>--%>
        <%--</li>--%>
        <%--</ul>--%>
        <%--</li>--%>
    </ul>

    <script type="text/javascript">
        var cookieMenu = "historyMenu";

        // $(function () {
        generateMenuHistory();
        // });

        function generateMenuHistory() {
            $('.treeview-item').removeClass('active');
            if (checkCookie() != null) {
                $('.app-menu [link]').each(function () {
                    var $ele = $(this);
                    const link = $ele.attr('link');
                    if (link == getCookie(cookieMenu)) {
                        $ele.addClass('active');
                        $('.is-expanded').removeClass('is-expanded');
                        $ele.closest('.treeview').addClass('is-expanded');
                        console.log($ele);
                    }
                });
            }

        }

        function openLinkUrl(ele) {
            var url = $(ele).attr('link');
            setCookie(cookieMenu, url, 1);
            location.href = url;
        }

        function setCookie(cname, cvalue, exdays) {
            var d = new Date();
            d.setTime(d.getTime() + (exdays * 24 * 60 * 60 * 1000));
            var expires = "expires=" + d.toGMTString();
            document.cookie = cname + "=" + cvalue + "; " + expires + ";path=/";
        }

        function getCookie(cname) {
            var name = cname + "=";
            var ca = document.cookie.split(';');
            for (var i = 0; i < ca.length; i++) {
                var c = ca[i];
                while (c.charAt(0) == ' ') c = c.substring(1);
                if (c.indexOf(name) == 0) {
                    return c.substring(name.length, c.length);
                }
            }
            return "";
        }

        function checkCookie() {
            var historyMenu = getCookie(cookieMenu);
            if (historyMenu != "" && historyMenu != null && typeof(historyMenu) != "undefined") {
                return historyMenu;
            } else {
                return null;
            }
        }
    </script>
</aside>