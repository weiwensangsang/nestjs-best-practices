(function () {
    'use strict';

    angular
        .module('weiwensangsangApp')
        .factory('Register', Register);

    Register.$inject = ['$resource'];

    function Register ($resource) {
        return $resource('api/register', {}, {});
    }
})();
