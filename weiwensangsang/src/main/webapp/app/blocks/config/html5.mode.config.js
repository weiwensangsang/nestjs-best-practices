/**
 * Created by xiazhen on 2017/9/27.
 */
(function() {
    'use strict';

    angular
        .module('weiwensangsangApp')
        .config(html5ModeConfig);

    html5ModeConfig.$inject = ['$locationProvider'];

    function html5ModeConfig($locationProvider) {
        $locationProvider.html5Mode({ enabled: true, requireBase: true });
    }
})();