/**
 * Created by creed on 01.05.16.
 */
angular.module('app', ['checklist-model'])
    .controller("StudentCtrl", ["$scope", "$http",function ($scope,$http) {
        $http.get('resources/json/studentsData.json').success(function(data) {
            $scope.students = data;
        });
        $scope.sortType     = 'name'; // set the default sort type
        $scope.sortReverse  = false;  // set the default sort order
        $scope.searchFiltr   = '';
        $scope.stud = {
            students: []
        };
        $scope.checkAll = function() {
            if ($scope.selectedAll){
                $scope.stud.students = $scope.students.map(function(item) { return item.id; });
                $scope.selectdAll = true;
            }
            else {
                $scope.selectdAll = false;
                $scope.stud.students = [];
            }

        };
    }]);

it('should change state', function () {
    var value1 = element(by.binding('h.isActive'));

    expect(value1.getText()).toContain('1');

    element(by.model('h.isActive')).click();
    expect(isActive.getText()).toContain('0');

});