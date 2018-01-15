(function () {
    'use strict';

    angular
        .module('weiwensangsangApp')
        .controller('FaceCheckController', FaceCheckController);

    FaceCheckController.$inject = ['$state', 'Location', '$q', '$scope', 'toaster', 'FaceCheck'];

    function FaceCheckController($state, Location, $q, $scope, toaster, FaceCheck) {
        var vm = this;
        vm.face = 'face';
        $scope.visible = false;
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
        }, function (strem) {
            video.src = vendorUrl.createObjectURL(strem);
            video.play();
        }, function (error) {
            //error.code
            console.log(error);
        });
        snap.addEventListener('click', function () {

            //绘制canvas图形
            canvas.getContext('2d').drawImage(video, 0, 0, 400, 300);

            //把canvas图像转为img图片
            var data = canvas.toDataURL("image/png");
            data.replace('data:image/png;base64,', '');
            FaceCheck.save({control: 'create'},
                data.replace('data:image/png;base64,', ''),
                function success(result) {

                    var o = angular.fromJson(result.message);

                    if (typeof(o.error_message) === "undefined") {
                        vm.face = o;
                        console.log(vm.face.faces[0].face_token);
                        $scope.visible = typeof (vm.face) == "object";
                        toaster.pop('success', ' ', '成功');
                    } else {
                        toaster.pop('error', ' ', 'FACE++的免费API就是要多点点');
                    }
                }, function error(result) {
                    toaster.pop('error', ' ', 'FACE++的免费API就是要多点点');
                });
        })
    }
})();
