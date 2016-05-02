/**
 * Created by creed on 01.05.16.
 */
angular.module('app', ['checklist-model'])
    .controller("StudentCtrl", ["$scope", "$http", function ($scope, $http) {
        $http.get('resources/json/studentsData.json').success(function (data) {
            $scope.students = data;
        });
        $scope.sortType = 'name';
        $scope.sortReverse = false;
        $scope.searchFiltr = '';
        $scope.dataStudents = {
            studId: []
        };
        $scope.checkAll = function () {
            if ($scope.selectedAll) {
                $scope.dataStudents.studId = $scope.students.map(function (item) {
                    return item.id;
                });
                $scope.selectdAll = true;
            }
            else {
                $scope.selectdAll = false;
                $scope.dataStudents.studId = [];
            }

        };
        $scope.activateStud = function () {
            var dataObj = {
                type: 'activate',
                values: $scope.dataStudents.studId
            }
            var res = $http.post('/students', dataObj);
            res.success(function (data, status, headers, config) {
                $scope.message = data;
            });
            res.error(function (data, status, headers, config) {
                alert("failure message: " + JSON.stringify({data: data}));
            });
        }
        $scope.deactivateStud = function () {
            var dataObj = {
                type: 'deactivate',
                values: $scope.dataStudents.studId
            }
            var res = $http.post('/students', dataObj);
            res.success(function (data, status, headers, config) {
                $scope.message = data;
            });
            res.error(function (data, status, headers, config) {
                alert("failure message: " + JSON.stringify({data: data}));
            });
        }
        $scope.rejectStud = function () {
            var dataObj = {
                type: 'reject',
                values: $scope.dataStudents.studId
            }
            var res = $http.post('/students', dataObj);
            res.success(function (data, status, headers, config) {
                $scope.message = data;
            });
            res.error(function (data, status, headers, config) {
                alert("failure message: " + JSON.stringify({data: data}));
            });
        }

    }]);

it('should change state', function () {
    var value1 = element(by.binding('h.isActive'));

    expect(value1.getText()).toContain('1');

    element(by.model('h.isActive')).click();
    expect(isActive.getText()).toContain('0');

});