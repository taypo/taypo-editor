(function() {
	'use strict';

	app = angular.module('viewer', []);
	app.controller('ViewerController', [
			'$http',
			'$location',
			'$routeParams',
			'$scope',
			function($http, $location, $routeParams, $scope) {
				var viewer = this;
				this.content = "File content here!";
				this.file = $routeParams.file;

				$http.get('/api/resource?path=' + viewer.file).success(
						function(data) {
							viewer.content = data;
						});

				this.edit = function() {
					$location.path('/editor/' + viewer.file);
				};


			} ]);
}());
