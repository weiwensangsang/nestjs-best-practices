(function () {
    'use strict';

    angular
        .module('weiwensangsangApp')
        .controller('FaceCheckController', FaceCheckController);

    FaceCheckController.$inject = ['$state', 'Location', '$q', '$scope', 'toaster', 'FaceCheck', 'ControlBike'];

    function FaceCheckController($state, Location, $q, $scope, toaster, FaceCheck, ControlBike) {
        var vm = this;
        vm.face = 'face';
        vm.name = null;
        vm.token = null;
        vm.faceCheckName = null;
        vm.submitName = submitName;
        vm.checkIfName = checkIfName;
        vm.unlock = unlock;
        console.log(vm.data);
        $scope.visible = false;
        $scope.isNameExist = 'not-find';
        if (vm.data === null) {
            toaster.pop('error', ' ', '错误页面');
            $state.go('home');
        }
        function checkIfName() {
            var dto = {};
            dto.control = 'search';
            dto.token = vm.token;
            FaceCheck.save(dto,
                function success(result) {
                    var o = angular.fromJson(result.message);

                    if (typeof(o.error_message) === "undefined") {
                        console.log(o);
                        if (o.results[0].confidence >= '90') {
                            vm.faceCheckName = o.results[0].user_id;
                            $scope.isNameExist = 'have-found';
                        } else {
                            $scope.isNameExist = 'check-not-found';
                        }
                    } else {
                        console.log(o);
                        if (o.error_message === "EMPTY_FACESET") {
                            $scope.isNameExist = 'check-not-found';
                        }
                        else {
                            toaster.pop('error', ' ', 'FACE++的免费API就是要多点点');
                        }
                    }
                }, function error(result) {
                    toaster.pop('error', ' ', 'FACE++的免费API就是要多点点');
                });
        }

        function submitName() {
            var dto = {};
            dto.name = vm.name;
            dto.token = vm.token;
            dto.control = 'add';
            console.log(dto);
            FaceCheck.save(dto,
                function success(result) {
                    var o = angular.fromJson(result.message);
                    if (typeof(o.error_message) === "undefined") {
                        console.log(o);
                        toaster.pop('success', ' ', '已经录入你的信息, 请重新开始扫描');
                        $state.go('path', null, {reload: 'path'});
                    } else {
                        toaster.pop('error', ' ', 'FACE++的免费API就是要多点点');
                    }
                }, function error(result) {
                    toaster.pop('error', ' ', 'FACE++的免费API就是要多点点');
                });
        }

        function unlock() {
            ControlBike.save(vm.data, 'unlock', function success(result) {
                toaster.pop('success', ' ', result.message);
                // 这里跳转到新页面
                $state.go('faker', null, {reload: true});

            }, function error(result) {
                toaster.pop('error', ' ', result.data.message);
            });
        }

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
            var dto = {};
            //绘制canvas图形
            canvas.getContext('2d').drawImage(video, 0, 0, 400, 300);

            //把canvas图像转为img图片
            var data = canvas.toDataURL("image/png");
            dto.control = 'create';
            dto.content = data.replace('data:image/png;base64,', '');
            FaceCheck.save(dto,
                function success(result) {
                    var o = angular.fromJson(result.message);
                    if (typeof(o.error_message) === "undefined") {
                        vm.face = o;
                        vm.token = vm.face.faces[0].face_token;
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
