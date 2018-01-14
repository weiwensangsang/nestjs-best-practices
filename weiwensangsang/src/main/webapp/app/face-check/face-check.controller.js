(function () {
    'use strict';

    angular
        .module('weiwensangsangApp')
        .controller('FaceCheckController', FaceCheckController);

    FaceCheckController.$inject = ['$state', 'Location', '$q', '$timeout', 'toaster', 'FaceCheck'];

    function FaceCheckController($state, Location, $q, $timeout, toaster, FaceCheck) {
        var vm = this;

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
            console.log(strem);
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
            FaceCheck.save({control: 'create-url'},
                data.replace('data:image/png;base64,', ''),
                function success(result) {
                    var o = angular.fromJson(result.message);
                    console.log(o);
                    toaster.pop('success', ' ', result.message);
                }, function error(result) {
                    toaster.pop('error', ' ', result.data.message);
                });
        })
    }
})();
