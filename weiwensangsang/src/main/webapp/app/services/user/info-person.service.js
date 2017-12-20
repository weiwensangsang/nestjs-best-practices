/**
 * Created by OlyLis on 16-8-1.
 */
(function () {
    'use strict';

    angular
        .module('weiwensangsangApp')
        .factory('PersonInfo', PersonInfo);

    PersonInfo.$inject = ['$resource'];

    function PersonInfo ($resource) {
        var service = $resource('api/user-infos', {}, {
            'query' : {method : 'GET'},
            'save' : {method : 'POST'}
        });
        return service;
    }
})();

