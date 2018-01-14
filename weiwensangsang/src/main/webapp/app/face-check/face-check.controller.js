(function () {
    'use strict';

    angular
        .module('weiwensangsangApp')
        .controller('FaceCheckController', FaceCheckController);

    FaceCheckController.$inject = ['$state', 'Location', '$q', '$timeout', '$rootScope', 'TopoConfig'];

    function FaceCheckController($state, Location, $q, $timeout, $rootScope, TopoConfig) {
        var vm = this;
        vm.result = {};
        vm.tense = null;
        vm.model = 'tense';
        vm.config = {};
        vm.changeModel = changeModel;
        vm.changeState = changeState;
        vm.changeLength = changeLength;
        vm.textLength = 0;
        vm.showLucky = 'normal';

        function changeModel() {
            var data = vm.config;
            if (vm.model === 'light') {
                data.type = 'tense';
            } else if (vm.model === 'tense') {
                data.type = 'light';
            }
            TopoConfig.save({}, data, function success(result) {
                //toaster.pop('success', ' ', '已生成');
            }, function error(result) {
                //toaster.pop('error', ' ', result.data.message);
            });
            $state.go('location', null, {reload: true});
        }

        function changeLength() {
            var data = vm.config;
            data.tense = vm.textLength;
            TopoConfig.save({}, data, function success(result) {
                //toaster.pop('success', ' ', '已生成');
            }, function error(result) {
                //toaster.pop('error', ' ', result.data.message);
            });
            $state.go('location', null, {reload: true});
        }

        function changeState() {
            var data = vm.config;
            if (vm.showLucky === 'normal') {
                data.state = 'show';
            } else if (vm.showLucky === 'show') {
                data.state = 'normal';
            }
            TopoConfig.save({}, data, function success(result) {
                //toaster.pop('success', ' ', '已生成');
            }, function error(result) {
                //toaster.pop('error', ' ', result.data.message);
            });
            $state.go('location', null, {reload: true});
        }

        var deferA = $q.defer();
        setTimeout(function () {
            Location.query(function (result) {
                vm.result = result;
                vm.tense = -70 * vm.result.locationList.length;
                deferA.resolve()
            });
        }, 0);
        var deferB = $q.defer();
        setTimeout(function () {
            TopoConfig.query(function (result) {
                console.log(result);
                vm.config = result;
                vm.model = result.type;
                vm.showLucky = result.state;
                vm.textLength = result.tense;
                deferB.resolve()
            });
        }, 0);

        var p = $q.all({
            dataA: deferA.promise, dataB: deferB.promise,
        })
        p.then(function () {

        });
    }
})();
