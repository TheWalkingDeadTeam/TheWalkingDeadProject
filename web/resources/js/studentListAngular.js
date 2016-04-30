/**
 * Created by creed on 01.05.16.
 */
angular.module('app', ['checklist-model'])
    .controller("StudentCtrl", ["$scope", function ($scope) {
        $scope.students = [
            {
                name: "Gandon",
                isActive: "1",
                email: "orci@luctusetultrices.co.uk",
                phone: "798-0149",
                address: "7560 Ligula. Road"
            },
            {
                name: "Branddason",
                isActive: "0",
                email: "orci@luctusgfetultrices.co.uk",
                phone: "1-280-259-754237",
                address: "P.O. Box 37116, 8820 At Avenue"
            },
            {
                name: "Brandon",
                isActive: "1",
                email: "orci@luctusetultrices.co.uk",
                phone: "1-280-259-7547",
                address: "P.O. Box 376, 8820 At Avenue"
            },
            {
                name: "Charissa",
                isActive: "1",
                email: "non.enim.commodo@Fusce.com",
                phone: "1-870-298-7936",
                address: "P.O. Box 376, 8820 At Avenue"
            },{
                name: "Quinlan",
                isActive: "0",
                email: "magna.tellus@dolor.com",
                phone: "1-537-673-1024",
                address: "1654 Sem Ave"
            }, {
                name: "Nayda",
                isActive: "1",
                email: "orci.adipiscing@Nunc.com",
                phone: "1-398-890-6994",
                address: "112-9766 Tempus Avenue"
            }, {
                name: "Eugenia",
                isActive: "1",
                email: "lacinia.orci@rutrumjusto.com",
                phone: "147-4244",
                address: "625-7238 Sed Ave"

            }, {
                name: "Ora",
                isActive: "1",
                email: "egestas.a@Duiselementum.org",
                phone: "1-873-737-1875",
                address: "P.O. Box 254, 1068 Nam Street"
            }];

        $scope.filter = {
            students: []
        };

        $scope.students.forEach(function (e, i) {
            $scope.filter.students = angular.copy(name);
        });

        console.log($scope.filter);
    }]);

it('should change state', function () {
    var value1 = element(by.binding('h.isActive'));

    expect(value1.getText()).toContain('1');

    element(by.model('h.isActive')).click();
    expect(isActive.getText()).toContain('0');

});