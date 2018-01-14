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

        var video = document.getElementById('video'),
            canvas = document.getElementById('canvas'),
            snap = document.getElementById('tack'),
            img = document.getElementById('img'),
            vendorUrl = window.URL || window.webkitURL;

        //媒体对象
        navigator.getMedia = navigator.getUserMedia ||
            navagator.webkitGetUserMedia ||
            navigator.mozGetUserMedia ||
            navigator.msGetUserMedia;
        navigator.getMedia({
            video: true, //使用摄像头对象
            audio: false  //不适用音频
        }, function(strem){
            console.log(strem);
            video.src = vendorUrl.createObjectURL(strem);
            video.play();
        }, function(error) {
            //error.code
            console.log(error);
        });
        snap.addEventListener('click', function(){

            //绘制canvas图形
            canvas.getContext('2d').drawImage(video, 0, 0, 400, 300);

            //把canvas图像转为img图片
            img.src = canvas.toDataURL("image/png");

        })

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
